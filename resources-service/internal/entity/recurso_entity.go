package entity

import "time"

// EstadoRecurso define los posibles estados de un recurso.
type EstadoRecurso string

const (
	// Activo indica que el recurso está disponible.
	Activo EstadoRecurso = "ACTIVO"
	// Mantenimiento indica que el recurso está en mantenimiento.
	Mantenimiento EstadoRecurso = "MANTENIMIENTO"
)

// Recurso es la entidad del dominio que representa un recurso.
type Recurso struct {
	ID          int64         `json:"id"`
	Nombre      string        `json:"nombre"`
	Descripcion string        `json:"descripcion"`
	HrefPhoto   string        `json:"href_photo"`
	Estado      EstadoRecurso `json:"estado"`
	CreatedAt   time.Time     `json:"created_at"`
	UpdatedAt   time.Time     `json:"updated_at"`
}

