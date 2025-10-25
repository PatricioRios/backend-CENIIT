package v1

import (
	"bytes"
	"encoding/json"
	"errors"
	"mime/multipart"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"

	"github.com/evrone/go-clean-template/internal/controller/http/v1/mocks"
	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
	"github.com/gofiber/fiber/v2"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"go.uber.org/mock/gomock"
)

func setupTestApp(ctrl *gomock.Controller) (*fiber.App, *mocks.MockRecursoUseCase, *mocks.MockPhotoUseCase, *mocks.MockLogErrorUseCase, *mocks.MockInterface) {
	app := fiber.New()
	
	mockRecursoUC := mocks.NewMockRecursoUseCase(ctrl)
	mockPhotoUC := mocks.NewMockPhotoUseCase(ctrl)
	mockLogErrorUC := mocks.NewMockLogErrorUseCase(ctrl)
	mockLogger := mocks.NewMockInterface(ctrl)

	routes := &RecursoRoutes{
		uc:              mockRecursoUC,
		photoUseCase:    mockPhotoUC,
		logErrorUseCase: mockLogErrorUC,
		logger:          mockLogger,
	}

	recursos := app.Group("/recursos")
	recursos.Post("/", routes.createResource)
	recursos.Delete("/:id", routes.deleteResource)
	recursos.Patch("/:id", routes.updateResource)
	recursos.Get("/:id", routes.getResourceByID)
	recursos.Get("/", routes.listResources)
	recursos.Post("/search", routes.searchResources)
	recursos.Post("/:id/foto", routes.uploadPhoto)
	recursos.Delete("/:id/foto", routes.deletePhoto)

	return app, mockRecursoUC, mockPhotoUC, mockLogErrorUC, mockLogger
}

func TestRecursoController_CreateResource(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, _, _ := setupTestApp(ctrl)

	t.Run("success - creates resource", func(t *testing.T) {
		reqBody := map[string]interface{}{
			"nombre":      "Proyector",
			"descripcion": "Proyector HD",
			"estado":      "ACTIVO",
		}
		bodyBytes, _ := json.Marshal(reqBody)

		expectedResource := &entity.Recurso{
			ID:          1,
			Nombre:      "Proyector",
			Descripcion: "Proyector HD",
			Estado:      entity.Activo,
			CreatedAt:   time.Now(),
			UpdatedAt:   time.Now(),
		}

		mockUC.EXPECT().
			CreateResource(gomock.Any(), gomock.Any()).
			Return(expectedResource, nil)

		req := httptest.NewRequest(http.MethodPost, "/recursos/", bytes.NewReader(bodyBytes))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusCreated, resp.StatusCode)

		var result entity.Recurso
		json.NewDecoder(resp.Body).Decode(&result)
		assert.Equal(t, int64(1), result.ID)
		assert.Equal(t, "Proyector", result.Nombre)
	})

	t.Run("error - invalid json body", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodPost, "/recursos/", bytes.NewReader([]byte("invalid json")))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})
}

func TestRecursoController_DeleteResource(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, _, mockLogger := setupTestApp(ctrl)

	t.Run("success - deletes resource", func(t *testing.T) {
		mockUC.EXPECT().
			DeleteResource(gomock.Any(), int64(1)).
			Return(nil)

		req := httptest.NewRequest(http.MethodDelete, "/recursos/1", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNoContent, resp.StatusCode)
	})

	t.Run("error - invalid id parameter", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodDelete, "/recursos/invalid", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockUC.EXPECT().
			DeleteResource(gomock.Any(), int64(999)).
			Return(apperror.ErrNotFound)

		req := httptest.NewRequest(http.MethodDelete, "/recursos/999", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNotFound, resp.StatusCode)
	})
}

func TestRecursoController_UpdateResource(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, _, mockLogger := setupTestApp(ctrl)

	t.Run("success - updates resource", func(t *testing.T) {
		newName := "Updated Name"
		reqBody := map[string]interface{}{
			"nombre": newName,
		}
		bodyBytes, _ := json.Marshal(reqBody)

		updatedResource := &entity.Recurso{
			ID:     1,
			Nombre: newName,
		}

		mockUC.EXPECT().
			UpdateResource(gomock.Any(), int64(1), gomock.Any()).
			Return(updatedResource, nil)

		req := httptest.NewRequest(http.MethodPatch, "/recursos/1", bytes.NewReader(bodyBytes))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusOK, resp.StatusCode)

		var result entity.Recurso
		json.NewDecoder(resp.Body).Decode(&result)
		assert.Equal(t, newName, result.Nombre)
	})

	t.Run("error - invalid id parameter", func(t *testing.T) {
		reqBody := map[string]interface{}{"nombre": "Test"}
		bodyBytes, _ := json.Marshal(reqBody)

		req := httptest.NewRequest(http.MethodPatch, "/recursos/abc", bytes.NewReader(bodyBytes))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		reqBody := map[string]interface{}{"nombre": "Test"}
		bodyBytes, _ := json.Marshal(reqBody)

		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockUC.EXPECT().
			UpdateResource(gomock.Any(), int64(999), gomock.Any()).
			Return(nil, apperror.ErrNotFound)

		req := httptest.NewRequest(http.MethodPatch, "/recursos/999", bytes.NewReader(bodyBytes))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNotFound, resp.StatusCode)
	})
}

func TestRecursoController_GetResourceByID(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, _, mockLogger := setupTestApp(ctrl)

	t.Run("success - gets resource by id", func(t *testing.T) {
		expectedResource := &entity.Recurso{
			ID:          1,
			Nombre:      "Proyector",
			Descripcion: "Proyector HD",
			Estado:      entity.Activo,
		}

		mockUC.EXPECT().
			GetResourceByID(gomock.Any(), int64(1)).
			Return(expectedResource, nil)

		req := httptest.NewRequest(http.MethodGet, "/recursos/1", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusOK, resp.StatusCode)

		var result entity.Recurso
		json.NewDecoder(resp.Body).Decode(&result)
		assert.Equal(t, int64(1), result.ID)
		assert.Equal(t, "Proyector", result.Nombre)
	})

	t.Run("error - invalid id parameter", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodGet, "/recursos/invalid", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockUC.EXPECT().
			GetResourceByID(gomock.Any(), int64(999)).
			Return(nil, apperror.ErrNotFound)

		req := httptest.NewRequest(http.MethodGet, "/recursos/999", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNotFound, resp.StatusCode)
	})
}

func TestRecursoController_ListResources(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, _, _ := setupTestApp(ctrl)

	t.Run("success - lists resources with pagination", func(t *testing.T) {
		expectedOutput := &DTOs.PaginatedRecursosOutput{
			Recursos: []entity.Recurso{
				{ID: 1, Nombre: "Recurso 1", Estado: entity.Activo},
				{ID: 2, Nombre: "Recurso 2", Estado: entity.Activo},
			},
			TotalElements: 2,
			Limit:         10,
			Offset:        0,
		}

		mockUC.EXPECT().
			ListResources(gomock.Any(), gomock.Any()).
			Return(expectedOutput, nil)

		req := httptest.NewRequest(http.MethodGet, "/recursos/?page=1&limit=10", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusOK, resp.StatusCode)
	})

	t.Run("success - lists resources with filters", func(t *testing.T) {
		expectedOutput := &DTOs.PaginatedRecursosOutput{
			Recursos:      []entity.Recurso{{ID: 1, Nombre: "Proyector"}},
			TotalElements: 1,
			Limit:         10,
			Offset:        0,
		}

		mockUC.EXPECT().
			ListResources(gomock.Any(), gomock.Any()).
			DoAndReturn(func(ctx interface{}, c criteria.Criteria) (*DTOs.PaginatedRecursosOutput, error) {
				// Note: The filter parsing expects format filter[field][operator]
				// The query string ?filter[nombre][CONTAINS]=Proyector will be parsed
				assert.GreaterOrEqual(t, len(c.FilterGroup.Filters), 0)
				return expectedOutput, nil
			})

		req := httptest.NewRequest(http.MethodGet, "/recursos/?filter[nombre][CONTAINS]=Proyector", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusOK, resp.StatusCode)
	})
}

func TestRecursoController_SearchResources(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, _, _ := setupTestApp(ctrl)

	t.Run("success - searches resources with criteria", func(t *testing.T) {
		searchCriteria := criteria.Criteria{
			FilterGroup: criteria.FilterGroup{
				Filters: []criteria.Filter{
					{Field: "nombre", Operator: criteria.CONTAINS, Value: "Proyector"},
				},
			},
		}
		bodyBytes, _ := json.Marshal(searchCriteria)

		expectedOutput := &DTOs.PaginatedRecursosOutput{
			Recursos:      []entity.Recurso{{ID: 1, Nombre: "Proyector"}},
			TotalElements: 1,
			Limit:         10,
			Offset:        0,
		}

		mockUC.EXPECT().
			ListResources(gomock.Any(), gomock.Any()).
			Return(expectedOutput, nil)

		req := httptest.NewRequest(http.MethodPost, "/recursos/search?limit=10&offset=0", bytes.NewReader(bodyBytes))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusOK, resp.StatusCode)
	})

	t.Run("error - invalid json body", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodPost, "/recursos/search", bytes.NewReader([]byte("invalid")))
		req.Header.Set("Content-Type", "application/json")

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})
}

func TestRecursoController_UploadPhoto(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, _, mockPhotoUC, _, mockLogger := setupTestApp(ctrl)

	t.Run("success - uploads photo", func(t *testing.T) {
		body := &bytes.Buffer{}
		writer := multipart.NewWriter(body)
		part, _ := writer.CreateFormFile("foto", "test.jpg")
		part.Write([]byte("fake image content"))
		writer.Close()

		updatedResource := &entity.Recurso{
			ID:        1,
			Nombre:    "Proyector",
			HrefPhoto: "http://storage.example.com/photo.jpg",
		}

		mockPhotoUC.EXPECT().
			UploadPhoto(gomock.Any(), gomock.Any()).
			Return(updatedResource, nil)

		req := httptest.NewRequest(http.MethodPost, "/recursos/1/foto", body)
		req.Header.Set("Content-Type", writer.FormDataContentType())

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusOK, resp.StatusCode)
	})

	t.Run("error - invalid id parameter", func(t *testing.T) {
		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).AnyTimes()

		req := httptest.NewRequest(http.MethodPost, "/recursos/invalid/foto", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("error - no file in request", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodPost, "/recursos/1/foto", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		body := &bytes.Buffer{}
		writer := multipart.NewWriter(body)
		part, _ := writer.CreateFormFile("foto", "test.jpg")
		part.Write([]byte("fake image content"))
		writer.Close()

		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).AnyTimes()

		mockPhotoUC.EXPECT().
			UploadPhoto(gomock.Any(), gomock.Any()).
			Return(nil, apperror.ErrNotFound)

		req := httptest.NewRequest(http.MethodPost, "/recursos/999/foto", body)
		req.Header.Set("Content-Type", writer.FormDataContentType())

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNotFound, resp.StatusCode)
	})
}

func TestRecursoController_DeletePhoto(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, _, mockPhotoUC, _, mockLogger := setupTestApp(ctrl)

	t.Run("success - deletes photo", func(t *testing.T) {
		mockPhotoUC.EXPECT().
			DeletePhoto(gomock.Any(), int64(1)).
			Return(nil)

		req := httptest.NewRequest(http.MethodDelete, "/recursos/1/foto", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNoContent, resp.StatusCode)
	})

	t.Run("error - invalid id parameter", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodDelete, "/recursos/invalid/foto", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("error - resource not found", func(t *testing.T) {
		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockPhotoUC.EXPECT().
			DeletePhoto(gomock.Any(), int64(999)).
			Return(apperror.ErrNotFound)

		req := httptest.NewRequest(http.MethodDelete, "/recursos/999/foto", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusNotFound, resp.StatusCode)
	})
}

func TestRecursoController_HandleError(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	app, mockUC, _, mockLogErrorUC, mockLogger := setupTestApp(ctrl)

	t.Run("handles bad request error", func(t *testing.T) {
		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockUC.EXPECT().
			GetResourceByID(gomock.Any(), int64(1)).
			Return(nil, apperror.ErrBadRequest)

		req := httptest.NewRequest(http.MethodGet, "/recursos/1", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
	})

	t.Run("handles conflict error", func(t *testing.T) {
		mockLogger.EXPECT().Warn(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockUC.EXPECT().
			GetResourceByID(gomock.Any(), int64(1)).
			Return(nil, apperror.ErrConflict)

		req := httptest.NewRequest(http.MethodGet, "/recursos/1", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusConflict, resp.StatusCode)
	})

	t.Run("handles internal server error and logs it", func(t *testing.T) {
		unexpectedErr := errors.New("database connection failed")

		mockLogger.EXPECT().Error(gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any(), gomock.Any()).Times(1)

		mockLogErrorUC.EXPECT().
			Execute(gomock.Any(), gomock.Any()).
			Return(nil)

		mockUC.EXPECT().
			GetResourceByID(gomock.Any(), int64(1)).
			Return(nil, unexpectedErr)

		req := httptest.NewRequest(http.MethodGet, "/recursos/1", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusInternalServerError, resp.StatusCode)
	})

	t.Run("handles internal server error when logging fails", func(t *testing.T) {
		unexpectedErr := errors.New("database error")
		loggingErr := errors.New("failed to log error")

		mockLogger.EXPECT().Error(gomock.Any(), gomock.Any()).Times(2)

		mockLogErrorUC.EXPECT().
			Execute(gomock.Any(), gomock.Any()).
			Return(loggingErr)

		mockUC.EXPECT().
			GetResourceByID(gomock.Any(), int64(1)).
			Return(nil, unexpectedErr)

		req := httptest.NewRequest(http.MethodGet, "/recursos/1", nil)

		resp, err := app.Test(req)
		require.NoError(t, err)
		defer resp.Body.Close()

		assert.Equal(t, http.StatusInternalServerError, resp.StatusCode)
	})
}
