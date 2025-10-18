package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
)

// RecursoUseCase defines the contract for the resource use cases.
type RecursoUseCase interface {
	CreateResource(ctx context.Context, input DTOs.CreateResourceInput) (*entity.Recurso, error)
	DeleteResource(ctx context.Context, id int64) error
	UpdateResource(ctx context.Context, id int64, input DTOs.UpdateResourceInput) (*entity.Recurso, error)
	GetResourceByID(ctx context.Context, id int64) (*entity.Recurso, error)

	// ListResources lists resources based on dynamic criteria.
	ListResources(ctx context.Context, criteria criteria.Criteria) (*DTOs.PaginatedRecursosOutput, error)
}
