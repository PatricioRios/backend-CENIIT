package usecases

import (
	"context"
	"fmt"
	"time"

	"github.com/evrone/go-clean-template/config"
	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/errorlog/ports"
	"github.com/pkg/errors"
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
	// stackTracer es una interfaz para errores que tienen un stack trace.
	type stackTracer interface {
		StackTrace() errors.StackTrace
	}

	// ... en Execute ...
	errorLog := &entity.ErrorLog{
		Timestamp:     time.Now(),
		ServiceName:   uc.cfg.App.Name,
		ErrorMessage:  input.Err.Error(),
		RequestPath:   input.RequestPath,
		RequestMethod: input.RequestMethod,
	}

	if st, ok := input.Err.(stackTracer); ok {
		errorLog.StackTrace = fmt.Sprintf("%+v", st.StackTrace())
	}

	// Guardamos el error de forma asíncrona para no bloquear la respuesta al cliente.
	go uc.repo.Save(context.Background(), errorLog)

	return nil
}
