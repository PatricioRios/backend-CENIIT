package response

import "time"

// ErrorResponseDTO is a standard DTO for API error responses.
type ErrorResponseDTO struct {
	// The date and time the error occurred.
	Timestamp time.Time `json:"timestamp"`
	// The HTTP status code.
	Status int `json:"status"`
	// The HTTP status reason phrase (e.g., "Not Found").
	Error string `json:"error"`
	// A descriptive and human-readable error message.
	Message string `json:"message"`
	// The path of the endpoint that was invoked.
	Path string `json:"path"`
	// Additional and structured data about the error. Can be nil.
	Details interface{} `json:"details,omitempty"`
}

// NewErrorResponseDTO creates a new ErrorResponseDTO without details.
func NewErrorResponseDTO(status int, err string, message string, path string) *ErrorResponseDTO {
	return &ErrorResponseDTO{
		Timestamp: time.Now(),
		Status:    status,
		Error:     err,
		Message:   message,
		Path:      path,
	}
}

// NewErrorResponseDTOWithDetails creates a new ErrorResponseDTO with details.
func NewErrorResponseDTOWithDetails(status int, err string, message string, path string, details interface{}) *ErrorResponseDTO {
	return &ErrorResponseDTO{
		Timestamp: time.Now(),
		Status:    status,
		Error:     err,
		Message:   message,
		Path:      path,
		Details:   details,
	}
}