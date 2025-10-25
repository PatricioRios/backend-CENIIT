package usecases

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
	"github.com/pkg/errors"
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

// ListResources lista los recursos aplicando un conjunto de criterios y devuelve datos de paginación.
func (uc *RecursoUseCaseImpl) ListResources(ctx context.Context, c criteria.Criteria) (*DTOs.PaginatedRecursosOutput, error) {
	// Get the resources for the current page
	recursos, err := uc.repo.FindByCriteria(ctx, c)
	if err != nil {
		// Si es un error conocido del cliente (ej: Bad Request), lo devolvemos directamente.
		if errors.Is(err, apperror.ErrBadRequest) || errors.Is(err, apperror.ErrNotFound) {
			return nil, err
		}
		// Para otros errores, sí añadimos contexto porque son inesperados.
		return nil, errors.Wrap(err, "error al listar recursos")
	}

	// Get the total count of resources matching the criteria (without pagination)
	total, err := uc.repo.CountByCriteria(ctx, c)
	if err != nil {
		// Si es un error conocido del cliente, lo devolvemos directamente.
		if errors.Is(err, apperror.ErrBadRequest) {
			return nil, err
		}
		// Para otros errores, sí añadimos contexto.
		return nil, errors.Wrap(err, "error al contar recursos")
	}

	// The pagination object can be nil, so we need to handle that.
	var limit, offset uint64
	if c.Pagination != nil {
		limit = c.Pagination.Limit
		offset = c.Pagination.Offset
	}

	return &DTOs.PaginatedRecursosOutput{
		Recursos:      recursos,
		TotalElements: total,
		Limit:         limit,
		Offset:        offset,
	}, nil
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
		return nil, errors.Wrap(err, "error al guardar el recurso")
	}

	return recursoAcrear, nil
}

// DeleteResource elimina un recurso por su ID.
func (uc *RecursoUseCaseImpl) DeleteResource(ctx context.Context, id int64) error {
	err := uc.repo.Delete(ctx, id)
	if err != nil {
		return errors.Wrap(err, "error al eliminar el recurso")
	}
	return nil
}

// UpdateResource actualiza parcialmente un recurso.
func (uc *RecursoUseCaseImpl) UpdateResource(ctx context.Context, id int64, input DTOs.UpdateResourceInput) (*entity.Recurso, error) {
	recurso, err := uc.repo.GetByID(ctx, id)
	if err != nil {
		return nil, errors.Wrap(err, "error al obtener recurso para actualizar")
	}

	if input.Nombre != nil {
		recurso.Nombre = *input.Nombre
	}
	if input.Descripcion != nil {
		recurso.Descripcion = *input.Descripcion
	}
	if input.HrefPhoto != nil {
		recurso.HrefPhoto = *input.HrefPhoto
	}
	if input.Estado != nil {
		recurso.Estado = *input.Estado
	}

	err = uc.repo.Update(ctx, recurso)
	if err != nil {
		return nil, errors.Wrap(err, "error al guardar cambios del recurso")
	}

	return recurso, nil
}

// GetResourceByID busca y devuelve un recurso por su ID.
func (uc *RecursoUseCaseImpl) GetResourceByID(ctx context.Context, id int64) (*entity.Recurso, error) {
	recurso, err := uc.repo.GetByID(ctx, id)
	if err != nil {
		return nil, errors.Wrap(err, "error al obtener recurso por ID")
	}
	return recurso, nil
}
