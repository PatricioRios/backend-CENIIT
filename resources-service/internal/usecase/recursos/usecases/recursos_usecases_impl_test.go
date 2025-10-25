package usecases

import (
	"context"
	"testing"
	"time"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/usecases/mocks"
	"github.com/pkg/errors"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"go.uber.org/mock/gomock"
)

func TestRecursoUseCaseImpl_CreateResource(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRepo := mocks.NewMockRecursoRepository(ctrl)
	useCase := NewRecursoUseCase(mockRepo)
	ctx := context.Background()

	t.Run("success - creates resource successfully", func(t *testing.T) {
		input := DTOs.CreateResourceInput{
			Nombre:      "Proyector",
			Descripcion: "Proyector HD",
			HrefPhoto:   "http://example.com/photo.jpg",
			Estado:      entity.Activo,
		}

		mockRepo.EXPECT().
			Save(ctx, gomock.Any()).
			DoAndReturn(func(ctx context.Context, r *entity.Recurso) error {
				r.ID = 1
				r.CreatedAt = time.Now()
				r.UpdatedAt = time.Now()
				return nil
			})

		result, err := useCase.CreateResource(ctx, input)

		require.NoError(t, err)
		assert.NotNil(t, result)
		assert.Equal(t, int64(1), result.ID)
		assert.Equal(t, "Proyector", result.Nombre)
		assert.Equal(t, "Proyector HD", result.Descripcion)
		assert.Equal(t, entity.Activo, result.Estado)
	})

	t.Run("error - repository save fails", func(t *testing.T) {
		input := DTOs.CreateResourceInput{
			Nombre:      "Proyector",
			Descripcion: "Proyector HD",
			Estado:      entity.Activo,
		}

		expectedErr := errors.New("database error")
		mockRepo.EXPECT().
			Save(ctx, gomock.Any()).
			Return(expectedErr)

		result, err := useCase.CreateResource(ctx, input)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "error al guardar el recurso")
	})
}

func TestRecursoUseCaseImpl_DeleteResource(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRepo := mocks.NewMockRecursoRepository(ctrl)
	useCase := NewRecursoUseCase(mockRepo)
	ctx := context.Background()

	t.Run("success - deletes resource successfully", func(t *testing.T) {
		resourceID := int64(1)

		mockRepo.EXPECT().
			Delete(ctx, resourceID).
			Return(nil)

		err := useCase.DeleteResource(ctx, resourceID)

		require.NoError(t, err)
	})

	t.Run("error - repository delete fails", func(t *testing.T) {
		resourceID := int64(999)
		expectedErr := apperror.ErrNotFound

		mockRepo.EXPECT().
			Delete(ctx, resourceID).
			Return(expectedErr)

		err := useCase.DeleteResource(ctx, resourceID)

		require.Error(t, err)
		assert.Contains(t, err.Error(), "error al eliminar el recurso")
	})
}

func TestRecursoUseCaseImpl_UpdateResource(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRepo := mocks.NewMockRecursoRepository(ctrl)
	useCase := NewRecursoUseCase(mockRepo)
	ctx := context.Background()

	t.Run("success - updates all fields", func(t *testing.T) {
		resourceID := int64(1)
		existingResource := &entity.Recurso{
			ID:          resourceID,
			Nombre:      "Old Name",
			Descripcion: "Old Description",
			Estado:      entity.Activo,
		}

		newName := "New Name"
		newDesc := "New Description"
		newEstado := entity.Mantenimiento

		input := DTOs.UpdateResourceInput{
			Nombre:      &newName,
			Descripcion: &newDesc,
			Estado:      &newEstado,
		}

		mockRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockRepo.EXPECT().
			Update(ctx, gomock.Any()).
			DoAndReturn(func(ctx context.Context, r *entity.Recurso) error {
				assert.Equal(t, newName, r.Nombre)
				assert.Equal(t, newDesc, r.Descripcion)
				assert.Equal(t, newEstado, r.Estado)
				return nil
			})

		result, err := useCase.UpdateResource(ctx, resourceID, input)

		require.NoError(t, err)
		assert.NotNil(t, result)
		assert.Equal(t, newName, result.Nombre)
		assert.Equal(t, newDesc, result.Descripcion)
		assert.Equal(t, newEstado, result.Estado)
	})

	t.Run("success - partial update", func(t *testing.T) {
		resourceID := int64(1)
		existingResource := &entity.Recurso{
			ID:          resourceID,
			Nombre:      "Old Name",
			Descripcion: "Old Description",
			Estado:      entity.Activo,
		}

		newName := "New Name"
		input := DTOs.UpdateResourceInput{
			Nombre: &newName,
		}

		mockRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		mockRepo.EXPECT().
			Update(ctx, gomock.Any()).
			DoAndReturn(func(ctx context.Context, r *entity.Recurso) error {
				assert.Equal(t, newName, r.Nombre)
				assert.Equal(t, "Old Description", r.Descripcion)
				return nil
			})

		result, err := useCase.UpdateResource(ctx, resourceID, input)

		require.NoError(t, err)
		assert.Equal(t, newName, result.Nombre)
		assert.Equal(t, "Old Description", result.Descripcion)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		resourceID := int64(999)
		input := DTOs.UpdateResourceInput{}

		mockRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(nil, apperror.ErrNotFound)

		result, err := useCase.UpdateResource(ctx, resourceID, input)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "error al obtener recurso para actualizar")
	})

	t.Run("error - update fails", func(t *testing.T) {
		resourceID := int64(1)
		existingResource := &entity.Recurso{
			ID:     resourceID,
			Nombre: "Old Name",
		}

		newName := "New Name"
		input := DTOs.UpdateResourceInput{
			Nombre: &newName,
		}

		mockRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(existingResource, nil)

		expectedErr := errors.New("database error")
		mockRepo.EXPECT().
			Update(ctx, gomock.Any()).
			Return(expectedErr)

		result, err := useCase.UpdateResource(ctx, resourceID, input)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "error al guardar cambios del recurso")
	})
}

func TestRecursoUseCaseImpl_GetResourceByID(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRepo := mocks.NewMockRecursoRepository(ctrl)
	useCase := NewRecursoUseCase(mockRepo)
	ctx := context.Background()

	t.Run("success - finds resource by id", func(t *testing.T) {
		resourceID := int64(1)
		expectedResource := &entity.Recurso{
			ID:          resourceID,
			Nombre:      "Proyector",
			Descripcion: "Proyector HD",
			Estado:      entity.Activo,
		}

		mockRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(expectedResource, nil)

		result, err := useCase.GetResourceByID(ctx, resourceID)

		require.NoError(t, err)
		assert.Equal(t, expectedResource, result)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		resourceID := int64(999)

		mockRepo.EXPECT().
			GetByID(ctx, resourceID).
			Return(nil, apperror.ErrNotFound)

		result, err := useCase.GetResourceByID(ctx, resourceID)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "error al obtener recurso por ID")
	})
}

func TestRecursoUseCaseImpl_ListResources(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockRepo := mocks.NewMockRecursoRepository(ctrl)
	useCase := NewRecursoUseCase(mockRepo)
	ctx := context.Background()

	t.Run("success - lists resources with pagination", func(t *testing.T) {
		c := criteria.Criteria{
			Pagination: &criteria.Pagination{
				Limit:  10,
				Offset: 0,
			},
		}

		expectedResources := []entity.Recurso{
			{ID: 1, Nombre: "Recurso 1", Estado: entity.Activo},
			{ID: 2, Nombre: "Recurso 2", Estado: entity.Activo},
		}

		mockRepo.EXPECT().
			FindByCriteria(ctx, c).
			Return(expectedResources, nil)

		mockRepo.EXPECT().
			CountByCriteria(ctx, c).
			Return(int64(2), nil)

		result, err := useCase.ListResources(ctx, c)

		require.NoError(t, err)
		assert.NotNil(t, result)
		assert.Equal(t, 2, len(result.Recursos))
		assert.Equal(t, int64(2), result.TotalElements)
		assert.Equal(t, uint64(10), result.Limit)
		assert.Equal(t, uint64(0), result.Offset)
	})

	t.Run("success - lists resources without pagination", func(t *testing.T) {
		c := criteria.Criteria{
			Pagination: nil,
		}

		expectedResources := []entity.Recurso{
			{ID: 1, Nombre: "Recurso 1"},
		}

		mockRepo.EXPECT().
			FindByCriteria(ctx, c).
			Return(expectedResources, nil)

		mockRepo.EXPECT().
			CountByCriteria(ctx, c).
			Return(int64(1), nil)

		result, err := useCase.ListResources(ctx, c)

		require.NoError(t, err)
		assert.Equal(t, uint64(0), result.Limit)
		assert.Equal(t, uint64(0), result.Offset)
	})

	t.Run("error - find criteria fails with bad request", func(t *testing.T) {
		c := criteria.Criteria{}

		mockRepo.EXPECT().
			FindByCriteria(ctx, c).
			Return(nil, apperror.ErrBadRequest)

		result, err := useCase.ListResources(ctx, c)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Equal(t, apperror.ErrBadRequest, err)
	})

	t.Run("error - find criteria fails with unexpected error", func(t *testing.T) {
		c := criteria.Criteria{}
		expectedErr := errors.New("database connection error")

		mockRepo.EXPECT().
			FindByCriteria(ctx, c).
			Return(nil, expectedErr)

		result, err := useCase.ListResources(ctx, c)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "error al listar recursos")
	})

	t.Run("error - count criteria fails with bad request", func(t *testing.T) {
		c := criteria.Criteria{}
		expectedResources := []entity.Recurso{{ID: 1}}

		mockRepo.EXPECT().
			FindByCriteria(ctx, c).
			Return(expectedResources, nil)

		mockRepo.EXPECT().
			CountByCriteria(ctx, c).
			Return(int64(0), apperror.ErrBadRequest)

		result, err := useCase.ListResources(ctx, c)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Equal(t, apperror.ErrBadRequest, err)
	})

	t.Run("error - count criteria fails with unexpected error", func(t *testing.T) {
		c := criteria.Criteria{}
		expectedResources := []entity.Recurso{{ID: 1}}
		expectedErr := errors.New("count error")

		mockRepo.EXPECT().
			FindByCriteria(ctx, c).
			Return(expectedResources, nil)

		mockRepo.EXPECT().
			CountByCriteria(ctx, c).
			Return(int64(0), expectedErr)

		result, err := useCase.ListResources(ctx, c)

		require.Error(t, err)
		assert.Nil(t, result)
		assert.Contains(t, err.Error(), "error al contar recursos")
	})
}
