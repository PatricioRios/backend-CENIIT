package request

import "github.com/evrone/go-clean-template/internal/entity"

// CreateResource es el DTO para la petición de crear un nuevo recurso.
type CreateResource struct {
	Nombre      string               `json:"nombre" binding:"required"`
	Descripcion string               `json:"descripcion"`
	HrefPhoto   string               `json:"href_photo"`
	Estado      entity.EstadoRecurso `json:"estado" binding:"required,oneof=ACTIVO MANTENIMIENTO"`
}

// UpdateResource es el DTO para la petición de actualizar un recurso (PATCH).
// Los punteros se usan para distinguir entre un campo no proporcionado y un campo con valor cero (ej: "").
type UpdateResource struct {
	Nombre      *string               `json:"nombre,omitempty"`
	Descripcion *string               `json:"descripcion,omitempty"`
	HrefPhoto   *string               `json:"href_photo,omitempty"`
	Estado      *entity.EstadoRecurso `json:"estado,omitempty" binding:"omitempty,oneof=ACTIVO MANTENIMIENTO"`
}
