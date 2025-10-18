package usecases

import (
	"context"
	"time"

	"github.com/evrone/go-clean-template/config"
	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/errorlog/ports"
)

// LogErrorUseCaseImpl es la implementación del caso de uso para registrar errores.
type LogErrorUseCaseImpl struct {
	repo ports.ErrorLogRepository
	cfg  *config.Config
}

// NewLogErrorUseCase crea una nueva instancia de LogErrorUseCaseImpl.
func NewLogErrorUseCase(repo ports.ErrorLogRepository, cfg *config.Config) *LogErrorUseCaseImpl {
	return &LogErrorUseCaseImpl{
		repo: repo,
		cfg:  cfg,
	}
}

// Execute maneja la lógica para registrar un error.
func (uc *LogErrorUseCaseImpl) Execute(ctx context.Context, input ports.LogErrorInput) error {
	errorLog := &entity.ErrorLog{
		Timestamp:     time.Now(),
		ServiceName:   uc.cfg.App.Name,
		ErrorMessage:  input.Err.Error(),
		RequestPath:   input.RequestPath,
		RequestMethod: input.RequestMethod,
		// StackTrace se puede añadir aquí si se utiliza una librería que los capture.
	}

	// Guardamos el error de forma asíncrona para no bloquear la respuesta al cliente.
	go uc.repo.Save(context.Background(), errorLog)

	return nil
}
