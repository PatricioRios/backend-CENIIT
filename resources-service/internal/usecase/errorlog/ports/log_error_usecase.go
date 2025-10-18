package ports

import (
	"context"
)

// LogErrorInput es el DTO de entrada para el caso de uso.
type LogErrorInput struct {
	Err           error
	RequestPath   string
	RequestMethod string
}

// LogErrorUseCase define el contrato para el caso de uso de registro de errores.
type LogErrorUseCase interface {
	Execute(ctx context.Context, input LogErrorInput) error
}
