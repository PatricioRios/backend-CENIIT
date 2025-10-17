package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
)

// RecursoRepository define el contrato para el acceso a datos de recursos.
type RecursoRepository interface {
	// Save guarda un nuevo recurso en la base de datos.
	// La implementación debe modificar el struct 'recurso' recibido,
	// asignándole el ID generado por la base de datos tras la inserción.
	Save(ctx context.Context, recurso *entity.Recurso) error
}
