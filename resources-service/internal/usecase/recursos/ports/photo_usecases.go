package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
)

// PhotoUseCase defines the contract for the photo management use cases.
type PhotoUseCase interface {
	UploadPhoto(ctx context.Context, input DTOs.UploadPhotoInput) (*entity.Recurso, error)
	DeletePhoto(ctx context.Context, resourceID int64) error
}
