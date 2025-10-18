package mock

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/pkg/logger"
)

// ErrorLogRepositoryMock es una implementación simulada de ErrorLogRepository.
type ErrorLogRepositoryMock struct {
	l logger.Interface
}

// NewErrorLogRepositoryMock crea una nueva instancia del mock.
func NewErrorLogRepositoryMock(l logger.Interface) *ErrorLogRepositoryMock {
	return &ErrorLogRepositoryMock{l: l}
}

// Save simula el guardado de un log de error, imprimiéndolo en la consola.
func (r *ErrorLogRepositoryMock) Save(ctx context.Context, errorLog *entity.ErrorLog) error {
	r.l.Info(
		"--- MOCK: Saving internal error to DB ---",
		"service", errorLog.ServiceName,
		"path", errorLog.RequestPath,
		"error", errorLog.ErrorMessage,
	)
	return nil
}
