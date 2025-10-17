package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
)

// RecursoUseCase define el contrato para los casos de uso de recursos.
type RecursoUseCase interface {
	CreateResource(ctx context.Context, input DTOs.CreateResourceInput) (*entity.Recurso, error)
	DeleteResource(ctx context.Context, id int64) error
	UpdateResource(ctx context.Context, id int64, input DTOs.UpdateResourceInput) (*entity.Recurso, error)

	// GetResourceByID busca y devuelve un recurso por su ID.
	GetResourceByID(ctx context.Context, id int64) (*entity.Recurso, error)
}
