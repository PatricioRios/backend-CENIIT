package v1

import (
	"errors"
	"fmt"
	"net/http"
	"strconv"
	"strings"

	"github.com/evrone/go-clean-template/internal/controller/http/common/response"
	"github.com/evrone/go-clean-template/internal/controller/http/middleware"
	"github.com/evrone/go-clean-template/internal/controller/http/v1/request"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/DTOs"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports/criteria"
	"github.com/gofiber/fiber/v2"
)

type RecursoRoutes struct {
	uc ports.RecursoUseCase
}

func NewRecursoRoutes(router fiber.Router, uc ports.RecursoUseCase) {
	r := &RecursoRoutes{uc: uc}
	h := router.Group("/recursos")
	{
		h.Post("/", middleware.RequireRole("CREAR_RECURSO"), r.createResource)
		h.Delete("/:id", middleware.RequireRole("CREAR_RECURSO"), r.deleteResource)
		h.Patch("/:id", middleware.RequireRole("CREAR_RECURSO"), r.updateResource)
		h.Get("/:id", middleware.RequireRole("CREAR_RECURSO"), r.getResourceByID)
		h.Get("/", middleware.RequireRole("CREAR_RECURSO"), r.listResources)
		h.Post("/search", middleware.RequireRole("CREAR_RECURSO"), r.searchResources)
	}
}

// --- Handlers ---

func (r *RecursoRoutes) listResources(c *fiber.Ctx) error {
	criteria, err := buildCriteriaFromQuery(c)
	if err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", err.Error(), c.Path()))
	}

	recursos, err := r.uc.ListResources(c.Context(), criteria)
	if err != nil {
		return handleError(c, err)
	}

	return c.Status(http.StatusOK).JSON(recursos)
}

func (r *RecursoRoutes) searchResources(c *fiber.Ctx) error {
	var crit criteria.Criteria
	if err := c.BodyParser(&crit); err != nil {
		return c.Status(http.StatusBadRequest).JSON(response.NewErrorResponseDTO(http.StatusBadRequest, "Bad Request", "Cuerpo de la petición JSON inválido", c.Path()))
	}

	// Parse pagination from query params
	limit, _ := strconv.ParseUint(c.Query("limit", "10"), 10, 64)
	offset, _ := strconv.ParseUint(c.Query("offset", "0"), 10, 64)
	crit.Pagination = &criteria.Pagination{
		Limit:  limit,
		Offset: offset,
	}

	recursos, err := r.uc.ListResources(c.Context(), crit)
	if err != nil {
		return handleError(c, err)
	}

	return c.Status(http.StatusOK).JSON(recursos)
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

// --- Mappers & Helpers ---

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
	fmt.Println(err)
	switch {
	case errors.Is(err, apperror.ErrConflict):
		return c.Status(http.StatusConflict).JSON(response.NewErrorResponseDTO(http.StatusConflict, "Conflict", err.Error(), c.Path()))
	case errors.Is(err, apperror.ErrNotFound):
		return c.Status(http.StatusNotFound).JSON(response.NewErrorResponseDTO(http.StatusNotFound, "Not Found", err.Error(), c.Path()))
	default:
		return c.Status(http.StatusInternalServerError).JSON(response.NewErrorResponseDTO(http.StatusInternalServerError, "Internal Server Error", "Ocurrió un error inesperado", c.Path()))
	}
}

// buildCriteriaFromQuery parsea los query params de la URL para construir un objeto Criteria.
// Ejemplo: ?filter[nombre][CONTAINS]=laptop&filter[estado][EQUALS]=ACTIVO&sort=-created_at&page=1&limit=10&logical_op=OR
func buildCriteriaFromQuery(c *fiber.Ctx) (criteria.Criteria, error) {
	var filters []criteria.Filter
	for key, value := range c.Queries() {
		if strings.HasPrefix(key, "filter[") {
			parts := strings.Split(strings.TrimSuffix(key, "]"), "[")
			if len(parts) == 4 { // filter, field, operator, ""
				field := parts[1]
				opStr := strings.TrimSuffix(parts[2], "]")
				filters = append(filters, criteria.Filter{
					Field:    field,
					Operator: criteria.FilterOperator(opStr),
					Value:    value,
				})
			}
		}
	}

	var sorts []criteria.Sort
	if sortQuery := c.Query("sort"); sortQuery != "" {
		fields := strings.Split(sortQuery, ",")
		for _, f := range fields {
			direction := criteria.ASC
			if strings.HasPrefix(f, "-") {
				direction = criteria.DESC
				f = strings.TrimPrefix(f, "-")
			}
			sorts = append(sorts, criteria.Sort{Field: f, Direction: direction})
		}
	}

	var pag *criteria.Pagination
	page, _ := strconv.ParseUint(c.Query("page", "1"), 10, 64)
	limit, _ := strconv.ParseUint(c.Query("limit", "10"), 10, 64)
	if page > 0 {
		pag = &criteria.Pagination{
			Limit:  limit,
			Offset: (page - 1) * limit,
		}
	}

	logicalOp := criteria.AND
	if strings.ToUpper(c.Query("logical_op")) == "OR" {
		logicalOp = criteria.OR
	}

	return criteria.Criteria{
		FilterGroup: criteria.FilterGroup{
			Operator: logicalOp,
			Filters:  filters,
		},
		Sort:       sorts,
		Pagination: pag,
	}, nil
}
