package usecases

import (
	"context"
	"mime/multipart"
	"testing"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/usecases/mocks"
	"github.com/pkg/errors"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"go.uber.org/mock/gomock"
)

func TestPhotoUseCaseImpl_UploadPhoto(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRecursoRepo := mocks.NewMockRecursoRepository(ctrl)
	mockFileRepo := mocks.NewMockFileStorageRepository(ctrl)
	useCase := NewPhotoUseCase(mockRecursoRepo, mockFileRepo)
	ctx := context.Background()

	t.Run("success - upload new photo for resource without existing photo", func(t *testing.T) {
		resourceID := int64(1)
		mockFile := &multipart.FileHeader{
			Filename: "photo.jpg",
			Size:     1024,
		}

		input := DTOs.UploadPhotoInput{
			ResourceID: resourceID,
			File:       mockFile,
		}

		existingResource := &entity.Recurso{
			ID:        resourceID,
			Nombre:    "Proyector",
			HrefPhoto: "",
		}

		newPhotoURL := "http://storage.example.com/photos/1/photo.jpg"

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Upload(ctx, mockFile, resourceID).
			Return(newPhotoURL, nil)

		mockRecursoRepo.EXPECT().
			Update(ctx, gomock.Any()).
			DoAndReturn(func(ctx context.Context, r *entity.Recurso) error {
				assert.Equal(t, newPhotoURL, r.HrefPhoto)
				return nil
			})

		result, err := useCase.UploadPhoto(ctx, input)

		require.NoError(t, err)
		assert.NotNil(t, result)
		assert.Equal(t, newPhotoURL, result.HrefPhoto)
	})

	t.Run("success - replace existing photo", func(t *testing.T) {
		resourceID := int64(1)
		mockFile := &multipart.FileHeader{
			Filename: "new-photo.jpg",
			Size:     2048,
		}

		input := DTOs.UploadPhotoInput{
			ResourceID: resourceID,
			File:       mockFile,
		}

		oldPhotoURL := "http://storage.example.com/photos/1/old-photo.jpg"
		existingResource := &entity.Recurso{
			ID:        resourceID,
			Nombre:    "Proyector",
			HrefPhoto: oldPhotoURL,
		}

		newPhotoURL := "http://storage.example.com/photos/1/new-photo.jpg"

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Delete(ctx, "old-photo.jpg").
			Return(nil)

		mockFileRepo.EXPECT().
			Upload(ctx, mockFile, resourceID).
			Return(newPhotoURL, nil)

		mockRecursoRepo.EXPECT().
			Update(ctx, gomock.Any()).
			DoAndReturn(func(ctx context.Context, r *entity.Recurso) error {
				assert.Equal(t, newPhotoURL, r.HrefPhoto)
				return nil
			})

		result, err := useCase.UploadPhoto(ctx, input)

		require.NoError(t, err)
		assert.Equal(t, newPhotoURL, result.HrefPhoto)
	})

	t.Run("success - continues even if old photo deletion fails", func(t *testing.T) {
		resourceID := int64(1)
		mockFile := &multipart.FileHeader{Filename: "photo.jpg"}

		input := DTOs.UploadPhotoInput{
			ResourceID: resourceID,
			File:       mockFile,
		}

		oldPhotoURL := "http://storage.example.com/photos/1/old-photo.jpg"
		existingResource := &entity.Recurso{
			ID:        resourceID,
			HrefPhoto: oldPhotoURL,
		}

		newPhotoURL := "http://storage.example.com/photos/1/new-photo.jpg"

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Delete(ctx, "old-photo.jpg").
			Return(errors.New("deletion failed"))

		mockFileRepo.EXPECT().
			Upload(ctx, mockFile, resourceID).
			Return(newPhotoURL, nil)

		mockRecursoRepo.EXPECT().
			Update(ctx, gomock.Any()).
			Return(nil)

		result, err := useCase.UploadPhoto(ctx, input)

		require.NoError(t, err)
		assert.Equal(t, newPhotoURL, result.HrefPhoto)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		resourceID := int64(999)
		mockFile := &multipart.FileHeader{Filename: "photo.jpg"}

		input := DTOs.UploadPhotoInput{
			ResourceID: resourceID,
			File:       mockFile,
		}

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(nil, apperror.ErrNotFound)

		result, err := useCase.UploadPhoto(ctx, input)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "failed to get resource")
	})

	t.Run("error - file upload fails", func(t *testing.T) {
		resourceID := int64(1)
		mockFile := &multipart.FileHeader{Filename: "photo.jpg"}

		input := DTOs.UploadPhotoInput{
			ResourceID: resourceID,
			File:       mockFile,
		}

		existingResource := &entity.Recurso{
			ID:        resourceID,
			HrefPhoto: "",
		}

		uploadErr := errors.New("upload failed")

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Upload(ctx, mockFile, resourceID).
			Return("", uploadErr)

		result, err := useCase.UploadPhoto(ctx, input)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "failed to upload photo")
	})

	t.Run("error - resource update fails after upload", func(t *testing.T) {
		resourceID := int64(1)
		mockFile := &multipart.FileHeader{Filename: "photo.jpg"}

		input := DTOs.UploadPhotoInput{
			ResourceID: resourceID,
			File:       mockFile,
		}

		existingResource := &entity.Recurso{
			ID:        resourceID,
			HrefPhoto: "",
		}

		newPhotoURL := "http://storage.example.com/photos/1/photo.jpg"
		updateErr := errors.New("update failed")

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Upload(ctx, mockFile, resourceID).
			Return(newPhotoURL, nil)

		mockRecursoRepo.EXPECT().
			Update(ctx, gomock.Any()).
			Return(updateErr)

		result, err := useCase.UploadPhoto(ctx, input)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "failed to update resource with new photo")
	})
}

func TestPhotoUseCaseImpl_DeletePhoto(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRecursoRepo := mocks.NewMockRecursoRepository(ctrl)
	mockFileRepo := mocks.NewMockFileStorageRepository(ctrl)
	useCase := NewPhotoUseCase(mockRecursoRepo, mockFileRepo)
	ctx := context.Background()

	t.Run("success - deletes photo successfully", func(t *testing.T) {
		resourceID := int64(1)
		photoURL := "http://storage.example.com/photos/1/photo.jpg"

		existingResource := &entity.Recurso{
			ID:        resourceID,
			Nombre:    "Proyector",
			HrefPhoto: photoURL,
		}

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Delete(ctx, "photo.jpg").
			Return(nil)

		mockRecursoRepo.EXPECT().
			Update(ctx, gomock.Any()).
			DoAndReturn(func(ctx context.Context, r *entity.Recurso) error {
				assert.Equal(t, "", r.HrefPhoto)
				return nil
			})

		err := useCase.DeletePhoto(ctx, resourceID)

		require.NoError(t, err)
	})

	t.Run("success - no photo to delete", func(t *testing.T) {
		resourceID := int64(1)

		existingResource := &entity.Recurso{
			ID:        resourceID,
			Nombre:    "Proyector",
			HrefPhoto: "",
		}

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		err := useCase.DeletePhoto(ctx, resourceID)

		require.NoError(t, err)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		resourceID := int64(999)

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(nil, apperror.ErrNotFound)

		err := useCase.DeletePhoto(ctx, resourceID)

		require.Error(t, err)
		assert.Contains(t, err.Error(), "failed to get resource")
	})

	t.Run("error - file deletion fails", func(t *testing.T) {
		resourceID := int64(1)
		photoURL := "http://storage.example.com/photos/1/photo.jpg"

		existingResource := &entity.Recurso{
			ID:        resourceID,
			HrefPhoto: photoURL,
		}

		deleteErr := errors.New("deletion failed")

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Delete(ctx, "photo.jpg").
			Return(deleteErr)

		err := useCase.DeletePhoto(ctx, resourceID)

		require.Error(t, err)
		assert.Contains(t, err.Error(), "failed to delete photo from storage")
	})

	t.Run("error - resource update fails after photo deletion", func(t *testing.T) {
		resourceID := int64(1)
		photoURL := "http://storage.example.com/photos/1/photo.jpg"

		existingResource := &entity.Recurso{
			ID:        resourceID,
			HrefPhoto: photoURL,
		}

		updateErr := errors.New("update failed")

		mockRecursoRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockFileRepo.EXPECT().
			Delete(ctx, "photo.jpg").
			Return(nil)

		mockRecursoRepo.EXPECT().
			Update(ctx, gomock.Any()).
			Return(updateErr)

		err := useCase.DeletePhoto(ctx, resourceID)

		require.Error(t, err)
		assert.Contains(t, err.Error(), "failed to update resource after deleting photo")
	})
}
