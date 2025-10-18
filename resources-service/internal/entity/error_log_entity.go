package entity

import "time"

// ErrorLog representa un registro de error en el sistema.
type ErrorLog struct {
	ID            int64     `json:"id"`
	Timestamp     time.Time `json:"timestamp"`
	ServiceName   string    `json:"service_name"`
	ErrorMessage  string    `json:"error_message"`
	StackTrace    string    `json:"stack_trace,omitempty"`
	RequestPath   string    `json:"request_path"`
	RequestMethod string    `json:"request_method"`
}
