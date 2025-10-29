// Package v1 implements routing paths. Each services in own file.
package http

import (
	"fmt"
	"net/http"

	"github.com/ansrivas/fiberprometheus/v2"
	"github.com/evrone/go-clean-template/config"
	_ "github.com/evrone/go-clean-template/docs" // Swagger docs.
	"github.com/evrone/go-clean-template/internal/controller/http/middleware"
	v1 "github.com/evrone/go-clean-template/internal/controller/http/v1"
	errorlogports "github.com/evrone/go-clean-template/internal/usecase/errorlog/ports"
	recursosports "github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/pkg/logger"
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/swagger"
	"github.com/swaggo/swag"
)

// @title Resources Service API
// @version 1.0
// @description API para gestión de recursos educativos
// @BasePath /v1
// @securityDefinitions.apikey BearerAuth
// @in header
// @name Authorization
// @description Token de autenticación JWT. Formato: "Bearer {token}"

// NewRouter -.
func NewRouter(app *fiber.App, cfg *config.Config, recursoUseCase recursosports.RecursoUseCase, photoUseCase recursosports.PhotoUseCase, logErrorUseCase errorlogports.LogErrorUseCase, l logger.Interface) {
	// Options
	app.Use(middleware.Logger(l))
	app.Use(middleware.Recovery(l, logErrorUseCase))

	// Prometheus metrics
	if cfg.Metrics.Enabled {
		prometheus := fiberprometheus.New("my-service-name")
		prometheus.RegisterAt(app, "/metrics")
		app.Use(prometheus.Middleware)
	}

	// Swagger
	if cfg.Swagger.Enabled {
		app.Get("/api/resources/swagger/*", swagger.HandlerDefault)
		// Endpoint explícito para doc.json, usando el nombre de la instancia de Swagger
		app.Get("/api/resources/swagger/doc.json", func(c *fiber.Ctx) error {
			doc, err := swag.ReadDoc("swagger") // "swagger" es el InfoInstanceName en docs.go
			if err != nil {
				// Agregar log de depuración
				fmt.Printf("DEBUG: Error reading swagger doc: %v\n", err)
				// Si falla, proporcionamos una documentación mínima
				doc = `{"swagger":"2.0","info":{"title":"API","version":"1.0"},"paths":{}}`
			} else {
				// Agregar log de depuración
				fmt.Printf("DEBUG: Successfully read swagger doc, length: %d\n", len(doc))
			}
			c.Set("Content-Type", "application/json")
			return c.SendString(doc)
		})
	}

	// K8s probe
	app.Get("/healthz", func(ctx *fiber.Ctx) error { return ctx.SendStatus(http.StatusOK) })

	// Auth middleware
	authMiddleware, err := middleware.AuthMiddleware(&cfg.Keycloak, l)
	if err != nil {
		l.Fatal(fmt.Sprintf("failed to create auth middleware: %s", err))
	}

	// Routers
	apiV1Group := app.Group("/v1")
	apiV1Group.Use(authMiddleware)
	{
		v1.NewRecursoRoutes(apiV1Group, recursoUseCase, photoUseCase, logErrorUseCase, l)
	}
}
