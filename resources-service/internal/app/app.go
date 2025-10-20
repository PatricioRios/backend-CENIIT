// Package app configures and runs application.
package app

import (
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"github.com/evrone/go-clean-template/config"
	"github.com/evrone/go-clean-template/internal/controller/http"
	"github.com/evrone/go-clean-template/internal/repo/persistent"
	errorlogusecase "github.com/evrone/go-clean-template/internal/usecase/errorlog/usecases"
	recursosusecase "github.com/evrone/go-clean-template/internal/usecase/recursos/usecases"
	"github.com/evrone/go-clean-template/pkg/httpserver"
	"github.com/evrone/go-clean-template/pkg/logger"
	"github.com/evrone/go-clean-template/pkg/minio"
	"github.com/evrone/go-clean-template/pkg/postgres"
)

// Run creates objects via constructors.
func Run(cfg *config.Config) {
	l := logger.New(cfg.Log.Level)

	// Repository
	pg, err := postgres.New(cfg.PG.URL, postgres.MaxPoolSize(cfg.PG.PoolMax))
	if err != nil {
		l.Fatal(fmt.Errorf("app - Run - postgres.New: %w", err).Error())
	}
	defer pg.Close()

	// Minio client
	minioClient, err := minio.New(cfg.Minio)
	if err != nil {
		l.Fatal(fmt.Errorf("app - Run - minio.New: %w", err).Error())
	}

	// Repositories
	recursoRepo := persistent.NewRecursoRepository(pg)
	errorLogRepo := persistent.NewErrorLogRepositoryPostgres(pg) // Using real repo
	fileStorageRepo := persistent.NewFileStorageMinio(minioClient, cfg.Minio)

	// Use-Cases
	recursoUseCase := recursosusecase.NewRecursoUseCase(recursoRepo)
	photoUseCase := recursosusecase.NewPhotoUseCase(recursoRepo, fileStorageRepo)
	logErrorUseCase := errorlogusecase.NewLogErrorUseCase(errorLogRepo, cfg)

	// HTTP Server
	httpServer := httpserver.New(l, httpserver.Port(cfg.HTTP.Port), httpserver.Prefork(cfg.HTTP.UsePreforkMode))
	http.NewRouter(httpServer.App, cfg, recursoUseCase, photoUseCase, logErrorUseCase, l)

	// Start servers
	httpServer.Start()

	// Waiting signal
	interrupt := make(chan os.Signal, 1)
	signal.Notify(interrupt, os.Interrupt, syscall.SIGTERM)

	select {
	case s := <-interrupt:
		l.Info("app - Run - signal: %s", s.String())
	case err = <-httpServer.Notify():
		l.Error(fmt.Errorf("app - Run - httpServer.Notify: %w", err).Error())
	}

	// Shutdown
	err = httpServer.Shutdown()
	if err != nil {
		l.Error(fmt.Errorf("app - Run - httpServer.Shutdown: %w", err).Error())
	}
}
