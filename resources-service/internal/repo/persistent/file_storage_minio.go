package persistent

import (
	"context"
	"fmt"
	"mime/multipart"
	"path/filepath"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/evrone/go-clean-template/config"
	"github.com/google/uuid"
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
func (r *FileStorageMinio) Upload(ctx context.Context, file *multipart.FileHeader) (string, error) {
	src, err := file.Open()
	if err != nil {
		return "", fmt.Errorf("failed to open file: %w", err)
	}
	defer src.Close()

	// Generate a unique object name
	objectName := uuid.New().String() + filepath.Ext(file.Filename)

	_, err = r.client.PutObject(ctx, &s3.PutObjectInput{
		Bucket: aws.String(r.bucketName),
		Key:    aws.String(objectName),
		Body:   src,
	})
	if err != nil {
		return "", fmt.Errorf("failed to upload file to minio: %w", err)
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
		return fmt.Errorf("failed to delete file from minio: %w", err)
	}

	return nil
}
