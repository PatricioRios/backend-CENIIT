package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
)

// RecursoRepository defines the contrato for data access for resources.
type RecursoRepository interface {
	Save(ctx context.Context, recurso *entity.Recurso) error
	Delete(ctx context.Context, id int64) error
	GetByID(ctx context.Context, id int64) (*entity.Recurso, error)
	Update(ctx context.Context, recurso *entity.Recurso) error

	// FindByCriteria searches for resources based on dynamic criteria.
	FindByCriteria(ctx context.Context, criteria criteria.Criteria) ([]entity.Recurso, error)
}
