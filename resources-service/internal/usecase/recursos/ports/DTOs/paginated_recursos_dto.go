package DTOs

import "github.com/evrone/go-clean-template/internal/entity"

// PaginatedRecursosOutput is a data container for returning paginated data from the use case.
type PaginatedRecursosOutput struct {
	Recursos      []entity.Recurso
	TotalElements int64
	Limit         uint64
	Offset        uint64
}
