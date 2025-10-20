package DTOs

import "mime/multipart"

// UploadPhotoInput is the DTO for the UploadPhoto use case.
type UploadPhotoInput struct {
	ResourceID int64
	File       *multipart.FileHeader
}
