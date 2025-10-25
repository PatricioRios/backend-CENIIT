package persistent

import (
	"context"
	"fmt"
	"io"
	"mime/multipart"
	"net/http"
	"path/filepath"
	"strconv"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/evrone/go-clean-template/config"
	"github.com/google/uuid"
	"github.com/pkg/errors"
)

// FileStorageMinio implements the FileStorageRepository interface using MinIO.
type FileStorageMinio struct {
	client     *s3.Client
	bucketName string
	endpoint   string
}

// NewFileStorageMinio creates a new instance of FileStorageMinio.
func NewFileStorageMinio(client *s3.Client, cfg config.Minio) *FileStorageMinio {
	return &FileStorageMinio{
		client:     client,
		bucketName: cfg.BucketName,
		endpoint:   cfg.Endpoint,
	}
}

// Upload uploads a file to MinIO and returns its URL.
func (r *FileStorageMinio) Upload(ctx context.Context, file *multipart.FileHeader, resourceID int64) (string, error) {
	src, err := file.Open()
	if err != nil {
		return "", errors.Wrap(err, "failed to open file")
	}
	defer src.Close()

	// Read the first 512 bytes to detect the content type
	buffer := make([]byte, 512)
	n, err := src.Read(buffer)
	if err != nil && err != io.EOF {
		return "", errors.Wrap(err, "failed to read file for content type detection")
	}
	contentType := http.DetectContentType(buffer[:n])

	// Rewind the file to the beginning
	_, err = src.Seek(0, io.SeekStart)
	if err != nil {
		return "", errors.Wrap(err, "failed to rewind file")
	}

	// Generate a unique object name
	objectName := uuid.New().String() + filepath.Ext(file.Filename)

	_, err = r.client.PutObject(ctx, &s3.PutObjectInput{
		Bucket:      aws.String(r.bucketName),
		Key:         aws.String(objectName),
		Body:        src,
		ContentType: aws.String(contentType),
		Metadata: map[string]string{
			"resource-id": strconv.FormatInt(resourceID, 10),
		},
	})
	if err != nil {
		return "", errors.Wrap(err, "failed to upload file to minio")
	}

	// Construct the URL
	fileURL := fmt.Sprintf("/media/%s/%s", r.bucketName, objectName)

	return fileURL, nil
}

// Delete removes a file from MinIO.
func (r *FileStorageMinio) Delete(ctx context.Context, objectName string) error {
	_, err := r.client.DeleteObject(ctx, &s3.DeleteObjectInput{
		Bucket: aws.String(r.bucketName),
		Key:    aws.String(objectName),
	})
	if err != nil {
		return errors.Wrap(err, "failed to delete file from minio")
	}

	return nil
}
