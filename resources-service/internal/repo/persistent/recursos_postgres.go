package persistent

import (
	"context"
	"errors"
	"fmt"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/pkg/postgres"
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
