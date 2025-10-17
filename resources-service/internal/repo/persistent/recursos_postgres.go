package persistent

import (
	"context"
	"errors"
	"fmt"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
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
