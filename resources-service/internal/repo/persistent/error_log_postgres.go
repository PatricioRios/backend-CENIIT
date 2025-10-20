package persistent

import (
	"context"
	"fmt"

	"github.com/evrone/go-clean-template/internal/entity"
	"github.com/evrone/go-clean-template/pkg/postgres"
)

// ErrorLogRepositoryPostgres es la implementación de ErrorLogRepository para PostgreSQL.
type ErrorLogRepositoryPostgres struct {
	*postgres.Postgres
}

// NewErrorLogRepositoryPostgres crea una nueva instancia de ErrorLogRepositoryPostgres.
func NewErrorLogRepositoryPostgres(pg *postgres.Postgres) *ErrorLogRepositoryPostgres {
	return &ErrorLogRepositoryPostgres{pg}
}

// Save guarda un nuevo log de error en la base de datos.
func (r *ErrorLogRepositoryPostgres) Save(ctx context.Context, errorLog *entity.ErrorLog) error {
	fmt.Println("--- Saving internal error to DB ---")
	fmt.Println(errorLog)

	sql, args, err := r.Builder.
		Insert("logging_schema.error_logs").
		Columns("service_name", "error_message", "stack_trace", "request_path", "request_method").
		Values(
			errorLog.ServiceName,
			errorLog.ErrorMessage,
			errorLog.StackTrace,
			errorLog.RequestPath,
			errorLog.RequestMethod,
		).
		ToSql()

	if err != nil {
		// No podemos registrar este error en la BD (sería un bucle), así que solo lo devolvemos.
		fmt.Println("ErrorLogRepository - Save - r.Builder.ToSql:", err)
		return fmt.Errorf("ErrorLogRepository - Save - r.Builder: %w", err)
	}

	// Usamos Exec ya que no necesitamos que nos devuelva ninguna fila.
	if _, err := r.Pool.Exec(ctx, sql, args...); err != nil {
		fmt.Println("ErrorLogRepository - Save - r.Pool.Exec:", err)
		return fmt.Errorf("ErrorLogRepository - Save - r.Pool.Exec: %w", err)
	}

	return nil
}
