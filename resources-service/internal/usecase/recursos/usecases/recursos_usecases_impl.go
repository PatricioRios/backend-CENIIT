package usecases

import (
	"context"
	"fmt"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
)

// RecursoUseCaseImpl es la implementación del caso de uso para recursos.
type RecursoUseCaseImpl struct {
	repo ports.RecursoRepository
}

// NewRecursoUseCase crea una nueva instancia de RecursoUseCaseImpl.
func NewRecursoUseCase(repo ports.RecursoRepository) *RecursoUseCaseImpl {
	return &RecursoUseCaseImpl{
		repo: repo,
	}
}

// CreateResource maneja la lógica para crear un nuevo recurso.
func (uc *RecursoUseCaseImpl) CreateResource(ctx context.Context, input DTOs.CreateResourceInput) (*entity.Recurso, error) {
	recursoAcrear := &entity.Recurso{
		Nombre:      input.Nombre,
		Descripcion: input.Descripcion,
		HrefPhoto:   input.HrefPhoto,
		Estado:      input.Estado,
	}

	err := uc.repo.Save(ctx, recursoAcrear)
	if err != nil {
		return nil, fmt.Errorf("error al guardar el recurso: %w", err)
	}

	return recursoAcrear, nil
}
