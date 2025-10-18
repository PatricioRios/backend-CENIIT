package ports

import (
	"context"

	"github.com/evrone/go-clean-template/internal/entity"
)

// ErrorLogRepository define el contrato para la persistencia de logs de error.
type ErrorLogRepository interface {
	Save(ctx context.Context, errorLog *entity.ErrorLog) error
}
