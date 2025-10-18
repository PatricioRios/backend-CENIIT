package persistent

import (
	"context"
	"errors"
	"fmt"

	"github.com/Masterminds/squirrel"
	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
	"github.com/evrone/go-clean-template/pkg/postgres"
	"github.com/jackc/pgx/v5"
	"github.com/jackc/pgx/v5/pgconn"
)

// RecursoRepository es la implementación del repositorio de recursos para PostgreSQL.
type RecursoRepository struct {
	*postgres.Postgres
}

// NewRecursoRepository crea una nueva instancia de RecursoRepository.
func NewRecursoRepository(pg *postgres.Postgres) *RecursoRepository {
	return &RecursoRepository{pg}
}

// Save guarda un nuevo recurso en la base de datos.
func (r *RecursoRepository) Save(ctx context.Context, recurso *entity.Recurso) error {
	sql, args, err := r.Builder.
		Insert("recursos_schema.recursos").
		Columns("nombre", "descripcion", "href_photo", "estado").
		Values(recurso.Nombre, recurso.Descripcion, recurso.HrefPhoto, recurso.Estado).
		Suffix("RETURNING id, created_at, updated_at").
		ToSql()
	if err != nil {
		return fmt.Errorf("RecursoRepository - Save - r.Builder: %w", err)
	}
	err = r.Pool.QueryRow(ctx, sql, args...).Scan(&recurso.ID, &recurso.CreatedAt, &recurso.UpdatedAt)
	if err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) && pgErr.Code == "23505" {
			return fmt.Errorf("%w: ya existe un recurso con un campo que debe ser único", apperror.ErrConflict)
		}
		return fmt.Errorf("%w: %v", apperror.ErrInternal, err)
	}
	return nil
}

// Delete elimina un recurso de la base de datos por su ID.
func (r *RecursoRepository) Delete(ctx context.Context, id int64) error {
	sql, args, err := r.Builder.
		Delete("recursos_schema.recursos").
		Where("id = ?", id).
		ToSql()
	if err != nil {
		return fmt.Errorf("RecursoRepository - Delete - r.Builder: %w", err)
	}
	commandTag, err := r.Pool.Exec(ctx, sql, args...)
	if err != nil {
		return fmt.Errorf("%w: %v", apperror.ErrInternal, err)
	}
	if commandTag.RowsAffected() == 0 {
		return apperror.ErrNotFound
	}
	return nil
}

// GetByID busca un recurso por su ID.
func (r *RecursoRepository) GetByID(ctx context.Context, id int64) (*entity.Recurso, error) {
	sql, args, err := r.Builder.
		Select("id", "nombre", "descripcion", "href_photo", "estado", "created_at", "updated_at").
		From("recursos_schema.recursos").
		Where("id = ?", id).
		ToSql()
	if err != nil {
		return nil, fmt.Errorf("RecursoRepository - GetByID - r.Builder: %w", err)
	}
	var recurso entity.Recurso
	err = r.Pool.QueryRow(ctx, sql, args...).Scan(
		&recurso.ID, &recurso.Nombre, &recurso.Descripcion,
		&recurso.HrefPhoto, &recurso.Estado, &recurso.CreatedAt, &recurso.UpdatedAt,
	)
	if err != nil {
		if errors.Is(err, pgx.ErrNoRows) {
			return nil, apperror.ErrNotFound
		}
		return nil, fmt.Errorf("%w: %v", apperror.ErrInternal, err)
	}
	return &recurso, nil
}

// Update actualiza un recurso existente en la base de datos.
func (r *RecursoRepository) Update(ctx context.Context, recurso *entity.Recurso) error {
	sql, args, err := r.Builder.
		Update("recursos_schema.recursos").
		Set("nombre", recurso.Nombre).
		Set("descripcion", recurso.Descripcion).
		Set("href_photo", recurso.HrefPhoto).
		Set("estado", recurso.Estado).
		Where("id = ?", recurso.ID).
		Suffix("RETURNING updated_at").
		ToSql()
	if err != nil {
		return fmt.Errorf("RecursoRepository - Update - r.Builder: %w", err)
	}
	err = r.Pool.QueryRow(ctx, sql, args...).Scan(&recurso.UpdatedAt)
	if err != nil {
		if errors.Is(err, pgx.ErrNoRows) {
			return apperror.ErrNotFound
		}
		return fmt.Errorf("%w: %v", apperror.ErrInternal, err)
	}
	return nil
}

// FindByCriteria busca recursos aplicando un conjunto dinámico de criterios.
func (r *RecursoRepository) FindByCriteria(ctx context.Context, c criteria.Criteria) ([]entity.Recurso, error) {
	fmt.Println("llego hasta aca")

	visitor := newSqlVisitor(r.Builder)
	fmt.Println("build del query")
	sql, args, err := visitor.BuildQuery(c)
	if err != nil {
		return nil, fmt.Errorf("RecursoRepository - FindByCriteria - visitor.BuildQuery: %w", err)
	}
	fmt.Println("ejecucion del query")
	rows, err := r.Pool.Query(ctx, sql, args...)
	if err != nil {
		fmt.Println(err)
		return nil, fmt.Errorf("RecursoRepository - FindByCriteria - r.Pool.Query: %w", err)
	}
	fmt.Println("build del query 2")

	defer rows.Close()
	var recursos []entity.Recurso
	for rows.Next() {
		var recurso entity.Recurso
		err = rows.Scan(
			&recurso.ID, &recurso.Nombre, &recurso.Descripcion,
			&recurso.HrefPhoto, &recurso.Estado, &recurso.CreatedAt, &recurso.UpdatedAt,
		)
		if err != nil {
			return nil, fmt.Errorf("RecursoRepository - FindByCriteria - rows.Scan: %w", err)
		}
		recursos = append(recursos, recurso)
	}
	fmt.Println("llego hasta aca (final)")
	return recursos, nil
}

// --- sqlVisitor (Implementación privada del patrón Visitor) ---

type sqlVisitor struct {
	builder squirrel.SelectBuilder
}

func newSqlVisitor(builder squirrel.StatementBuilderType) *sqlVisitor {
	return &sqlVisitor{
		builder: builder.Select("id", "nombre", "descripcion", "href_photo", "estado", "created_at", "updated_at").
			From("recursos_schema.recursos"),
	}
}

func (v *sqlVisitor) BuildQuery(c criteria.Criteria) (string, []any, error) {
	mainFilter, err := v.visitFilterGroup(c.FilterGroup)
	if err != nil {
		return "", nil, err
	}
	if mainFilter != nil {
		v.builder = v.builder.Where(mainFilter)
	}

	if err := v.visitSort(c.Sort); err != nil {
		return "", nil, err
	}
	if err := v.visitPagination(c.Pagination); err != nil {
		return "", nil, err
	}
	return v.builder.ToSql()
}

func (v *sqlVisitor) visitFilterGroup(fg criteria.FilterGroup) (squirrel.Sqlizer, error) {
	if len(fg.Filters) == 0 && len(fg.FilterGroups) == 0 {
		return nil, nil
	}

	var predicates []squirrel.Sqlizer

	for _, f := range fg.Filters {
		pred, err := v.visitFilter(f)
		if err != nil {
			return nil, err
		}
		predicates = append(predicates, pred)
	}

	for _, group := range fg.FilterGroups {
		pred, err := v.visitFilterGroup(group)
		if err != nil {
			return nil, err
		}
		if pred != nil {
			predicates = append(predicates, pred)
		}
	}

	if len(predicates) == 0 {
		return nil, nil
	}

	if fg.Operator == criteria.OR {
		return squirrel.Or(predicates), nil
	}

	return squirrel.And(predicates), nil
}

func (v *sqlVisitor) visitFilter(f criteria.Filter) (squirrel.Sqlizer, error) {
	value := f.Value
	var operator string

	switch f.Operator {
	case criteria.EQUALS:
		operator = "="
	case criteria.NOT_EQUALS:
		operator = "<>"
	case criteria.GREATER_THAN:
		operator = ">"
	case criteria.GREATER_EQUALS, criteria.GTE:
		operator = ">="
	case criteria.LESS_THAN:
		operator = "<"
	case criteria.LESS_EQUALS:
		operator = "<="
	case criteria.CONTAINS:
		operator = "LIKE"
		val, ok := f.Value.(string)
		if !ok {
			return nil, fmt.Errorf("el valor para el operador CONTAINS debe ser un string")
		}
		value = fmt.Sprintf("%%%s%%", val)
	case criteria.NOT_CONTAINS:
		operator = "NOT LIKE"
		val, ok := f.Value.(string)
		if !ok {
			return nil, fmt.Errorf("el valor para el operador NOT_CONTAINS debe ser un string")
		}
		value = fmt.Sprintf("%%%s%%", val)
	case criteria.ISTARTS_WITH:
		operator = "ILIKE"
		val, ok := f.Value.(string)
		if !ok {
			return nil, fmt.Errorf("el valor para el operador ISTARTS_WITH debe ser un string")
		}
		value = fmt.Sprintf("%s%%", val)
	case criteria.LIKE:
		operator = "LIKE"
	case criteria.ILIKE:
		operator = "ILIKE"
	default:
		return nil, fmt.Errorf("operador de filtro no soportado: %s", f.Operator)
	}

	return squirrel.Expr(fmt.Sprintf("%s %s ?", f.Field, operator), value), nil
}

func (v *sqlVisitor) visitSort(sorts []criteria.Sort) error {
	for _, s := range sorts {
		v.builder = v.builder.OrderBy(fmt.Sprintf("%s %s", s.Field, s.Direction))
	}
	return nil
}

func (v *sqlVisitor) visitPagination(p *criteria.Pagination) error {
	if p != nil {
		v.builder = v.builder.Limit(p.Limit).Offset(p.Offset)
	}
	return nil
}
