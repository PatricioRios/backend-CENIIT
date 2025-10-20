package ports

import (
	"context"
	"mime/multipart"
)

// FileStorageRepository defines the contract for file storage operations.
type FileStorageRepository interface {
	Upload(ctx context.Context, file *multipart.FileHeader) (string, error)
	Delete(ctx context.Context, fileURL string) error
}
