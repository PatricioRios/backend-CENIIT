package DTOs

import "github.com/evrone/go-clean-template/internal/entity"

// CreateResourceInput es el DTO de entrada para el caso de uso de creación de recursos.
type CreateResourceInput struct {
	Nombre      string
	Descripcion string
	HrefPhoto   string
	Estado      entity.EstadoRecurso
}

// UpdateResourceInput es el DTO de entrada para actualizar un recurso.
type UpdateResourceInput struct {
	Nombre      *string
	Descripcion *string
	HrefPhoto   *string
	Estado      *entity.EstadoRecurso
}