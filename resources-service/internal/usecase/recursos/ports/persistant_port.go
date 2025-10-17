package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
)

// RecursoRepository define el contrato para el acceso a datos de recursos.
type RecursoRepository interface {
	Save(ctx context.Context, recurso *entity.Recurso) error
	Delete(ctx context.Context, id int64) error

	// GetByID busca un recurso por su ID.
	GetByID(ctx context.Context, id int64) (*entity.Recurso, error)

	// Update actualiza un recurso existente en la base de datos.
	Update(ctx context.Context, recurso *entity.Recurso) error
}
