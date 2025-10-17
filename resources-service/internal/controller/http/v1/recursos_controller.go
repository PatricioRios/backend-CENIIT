package v1

import (
	"errors"
	"net/http"

	"github.com/evrone/go-clean-template/internal/controller/http/common/response"
	"github.com/evrone/go-clean-template/internal/controller/http/v1/request"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
	"github.com/gofiber/fiber/v2"
)

type RecursoRoutes struct {
	uc ports.RecursoUseCase
}

func NewRecursoRoutes(router fiber.Router, uc ports.RecursoUseCase) {
	r := &RecursoRoutes{uc: uc}
	h := router.Group("/recursos")
	{
		h.Post("/", r.createResource)
		h.Delete("/:id", r.deleteResource)
		h.Patch("/:id", r.updateResource)
		h.Get("/:id", r.getResourceByID)
	}
}

func (r *RecursoRoutes) createResource(c *fiber.Ctx) error {
	var req request.CreateResource
	if err := c.BodyParser(&req); err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", "Cuerpo de la petición inválido", c.Path()))
	}

	input := toCreateResourceInput(req)

	recurso, err := r.uc.CreateResource(c.Context(), input)
	if err != nil {
		return handleError(c, err)
	}

	return c.Status(http.StatusCreated).JSON(recurso)
}

func (r *RecursoRoutes) deleteResource(c *fiber.Ctx) error {
	id, err := c.ParamsInt("id")
	if err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", "ID de recurso inválido", c.Path()))
	}

	err = r.uc.DeleteResource(c.Context(), int64(id))
	if err != nil {
		return handleError(c, err)
	}

	return c.SendStatus(http.StatusNoContent)
}

func (r *RecursoRoutes) updateResource(c *fiber.Ctx) error {
	id, err := c.ParamsInt("id")
	if err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", "ID de recurso inválido", c.Path()))
	}

	var req request.UpdateResource
	if err := c.BodyParser(&req); err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", "Cuerpo de la petición inválido", c.Path()))
	}

	input := toUpdateResourceInput(req)

	recurso, err := r.uc.UpdateResource(c.Context(), int64(id), input)
	if err != nil {
		return handleError(c, err)
	}

	return c.Status(http.StatusOK).JSON(recurso)
}

func (r *RecursoRoutes) getResourceByID(c *fiber.Ctx) error {
	id, err := c.ParamsInt("id")
	if err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", "ID de recurso inválido", c.Path()))
	}

	recurso, err := r.uc.GetResourceByID(c.Context(), int64(id))
	if err != nil {
		return handleError(c, err)
	}

	return c.Status(http.StatusOK).JSON(recurso)
}

func toCreateResourceInput(req request.CreateResource) DTOs.CreateResourceInput {
	return DTOs.CreateResourceInput{
		Nombre:      req.Nombre,
		Descripcion: req.Descripcion,
		HrefPhoto:   req.HrefPhoto,
		Estado:      req.Estado,
	}
}

func toUpdateResourceInput(req request.UpdateResource) DTOs.UpdateResourceInput {
	return DTOs.UpdateResourceInput{
		Nombre:      req.Nombre,
		Descripcion: req.Descripcion,
		HrefPhoto:   req.HrefPhoto,
		Estado:      req.Estado,
	}
}

func handleError(c *fiber.Ctx, err error) error {
	switch {
	case errors.Is(err, apperror.ErrConflict):
		return c.Status(http.StatusConflict).JSON(response.NewErrorResponseDTO(http.StatusConflict, "Conflict", err.Error(), c.Path()))
	case errors.Is(err, apperror.ErrNotFound):
		return c.Status(http.StatusNotFound).JSON(response.NewErrorResponseDTO(http.StatusNotFound, "Not Found", err.Error(), c.Path()))
	default:
		return c.Status(http.StatusInternalServerError).JSON(response.NewErrorResponseDTO(http.StatusInternalServerError, "Internal Server Error", "Ocurrió un error inesperado", c.Path()))
	}
}