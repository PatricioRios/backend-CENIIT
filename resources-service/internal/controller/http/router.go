// Package v1 implements routing paths. Each services in own file.
package http

import (
	"net/http"

	"github.com/ansrivas/fiberprometheus/v2"
	"github.com/evrone/go-clean-template/config"
	_ "github.com/evrone/go-clean-template/docs" // Swagger docs.
	"github.com/evrone/go-clean-template/internal/controller/http/middleware"
	v1 "github.com/evrone/go-clean-template/internal/controller/http/v1"
	"github.com/evrone/go-clean-template/internal/usecase/recursos/ports"
	"github.com/evrone/go-clean-template/pkg/logger"
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/swagger"
)

// NewRouter -.
func NewRouter(app *fiber.App, cfg *config.Config, recursoUseCase ports.RecursoUseCase, l logger.Interface) {
	// Options
	app.Use(middleware.Logger(l))
	app.Use(middleware.Recovery(l))

	// Prometheus metrics
	if cfg.Metrics.Enabled {
		prometheus := fiberprometheus.New("my-service-name")
		prometheus.RegisterAt(app, "/metrics")
		app.Use(prometheus.Middleware)
	}

	// Swagger
	if cfg.Swagger.Enabled {
		app.Get("/swagger/*", swagger.HandlerDefault)
	}

	// K8s probe
	app.Get("/healthz", func(ctx *fiber.Ctx) error { return ctx.SendStatus(http.StatusOK) })

	// Auth middleware
	authMiddleware, err := middleware.AuthMiddleware(&cfg.Keycloak, l)
	if err != nil {
		l.Fatal("failed to create auth middleware: %v", err)
	}

	// Routers
	apiV1Group := app.Group("/v1")
	apiV1Group.Use(authMiddleware)
	{
		v1.NewRecursoRoutes(apiV1Group, recursoUseCase)
	}
}
