package response

import (
	"time"

	"github.com/evrone/go-clean-template/internal/entity"
)

// Link represents a HATEOAS link.
type Link struct {
	Href string `json:"href"`
}

// Links represents a collection of HATEOAS links.
type Links struct {
	Self Link  `json:"self"`
	Next *Link `json:"next,omitempty"`
	Prev *Link `json:"prev,omitempty"`
}

// PageInfo represents the pagination metadata.
type PageInfo struct {
	Size          uint64 `json:"size"`
	TotalElements int64  `json:"totalElements"`
	TotalPages    int64  `json:"totalPages"`
	Number        int64  `json:"number"`
}

// RecursoResponse defines the structure of a resource in the API response.
type RecursoResponse struct {
	ID          int64               `json:"id"`
	Nombre      string              `json:"nombre"`
	Descripcion string              `json:"descripcion"`
	HrefPhoto   string              `json:"href_photo"`
	Estado      entity.EstadoRecurso `json:"estado"`
	CreatedAt   time.Time           `json:"created_at"`
	UpdatedAt   time.Time           `json:"updated_at"`
}

// RecursoHATEOAS wraps a RecursoResponse to include HATEOAS links.
type RecursoHATEOAS struct {
	RecursoResponse
	Links struct {
		Self Link `json:"self"`
	} `json:"_links"`
}

// EmbeddedRecursos holds the list of HATEOAS-enhanced resources.
type EmbeddedRecursos struct {
	Recursos []RecursoHATEOAS `json:"recursos"`
}

// PaginatedResponse is the top-level structure for paginated API responses.
type PaginatedResponse struct {
	Embedded EmbeddedRecursos `json:"_embedded"`
	Links    Links            `json:"_links"`
	Page     PageInfo         `json:"page"`
}
