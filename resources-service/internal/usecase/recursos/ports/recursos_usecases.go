package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
)

// RecursoUseCase define el contrato para los casos de uso de recursos.
type RecursoUseCase interface {
	// CreateResource maneja la lógica para crear un nuevo recurso.
	CreateResource(ctx context.Context, input DTOs.CreateResourceInput) (*entity.Recurso, error)
}
