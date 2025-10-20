package usecases

import (
	"context"
	"fmt"
	"strings"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
)

// PhotoUseCaseImpl is the implementation of the PhotoUseCase interface.
type PhotoUseCaseImpl struct {
	recursoRepo ports.RecursoRepository
	fileRepo    ports.FileStorageRepository
}

// NewPhotoUseCase creates a new instance of PhotoUseCaseImpl.
func NewPhotoUseCase(recursoRepo ports.RecursoRepository, fileRepo ports.FileStorageRepository) *PhotoUseCaseImpl {
	return &PhotoUseCaseImpl{
		recursoRepo: recursoRepo,
		fileRepo:    fileRepo,
	}
}

// UploadPhoto handles the logic for uploading or updating a resource's photo.
func (uc *PhotoUseCaseImpl) UploadPhoto(ctx context.Context, input DTOs.UploadPhotoInput) (*entity.Recurso, error) {
	recurso, err := uc.recursoRepo.GetByID(ctx, input.ResourceID)
	if err != nil {
		return nil, fmt.Errorf("failed to get resource: %w", err)
	}

	// If the resource already has a photo, delete the old one.
	if recurso.HrefPhoto != "" {
		// Extract object name from URL
		parts := strings.Split(recurso.HrefPhoto, "/")
		if len(parts) > 0 {
			objectName := parts[len(parts)-1]
			err := uc.fileRepo.Delete(ctx, objectName)
			if err != nil {
				// Log the error but don't block the upload of the new photo
				fmt.Printf("failed to delete old photo: %v\n", err)
			}
		}
	}

	// Upload the new photo.
	fileURL, err := uc.fileRepo.Upload(ctx, input.File)
	if err != nil {
		return nil, fmt.Errorf("failed to upload photo: %w", err)
	}

	// Update the resource with the new photo URL.
	recurso.HrefPhoto = fileURL
	err = uc.recursoRepo.Update(ctx, recurso)
	if err != nil {
		return nil, fmt.Errorf("failed to update resource with new photo: %w", err)
	}

	return recurso, nil
}

// DeletePhoto handles the logic for deleting a resource's photo.
func (uc *PhotoUseCaseImpl) DeletePhoto(ctx context.Context, resourceID int64) error {
	recurso, err := uc.recursoRepo.GetByID(ctx, resourceID)
	if err != nil {
		return fmt.Errorf("failed to get resource: %w", err)
	}

	if recurso.HrefPhoto == "" {
		// No photo to delete.
		return nil
	}

	// Extract object name from URL
	parts := strings.Split(recurso.HrefPhoto, "/")
	if len(parts) > 0 {
		objectName := parts[len(parts)-1]
		err = uc.fileRepo.Delete(ctx, objectName)
		if err != nil {
			return fmt.Errorf("failed to delete photo from storage: %w", err)
		}
	}

	// Update the resource to remove the photo URL.
	recurso.HrefPhoto = ""
	err = uc.recursoRepo.Update(ctx, recurso)
	if err != nil {
		return fmt.Errorf("failed to update resource after deleting photo: %w", err)
	}

	return nil
}
