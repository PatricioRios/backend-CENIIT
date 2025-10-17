package persistent

import (
	"context"
	"fmt"
	"math/rand"
	"sync"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/internal/usecase/common/apperror"
)

// RecursoRepositoryMock es una implementación en memoria de RecursoRepository para pruebas.
type RecursoRepositoryMock struct {
	mu       sync.RWMutex
	recursos map[int64]entity.Recurso
}

// NewRecursoRepositoryMock crea una nueva instancia de RecursoRepositoryMock.
func NewRecursoRepositoryMock() *RecursoRepositoryMock {
	return &RecursoRepositoryMock{
		recursos: make(map[int64]entity.Recurso),
	}
}

// Save guarda un nuevo recurso en el mapa en memoria.
func (r *RecursoRepositoryMock) Save(ctx context.Context, recurso *entity.Recurso) error {
	r.mu.Lock()
	defer r.mu.Unlock()

	// Simular chequeo de unicidad por nombre
	for _, rsc := range r.recursos {
		if rsc.Nombre == recurso.Nombre {
			return fmt.Errorf("%w: ya existe un recurso con el nombre '%s'", apperror.ErrConflict, recurso.Nombre)
		}
	}

	// Generar un nuevo ID y guardarlo
	newID := int64(rand.Intn(1000) + 1)
	recurso.ID = newID
	r.recursos[newID] = *recurso

	return nil
}
