#!/bin/sh
set -e

# Configure mc to connect to the Minio service
/usr/bin/mc alias set minio http://minio:9000 minioadmin minioadminsecret

# Wait for Minio to be ready by polling its health check endpoint
until /usr/bin/mc admin info minio; do
  echo "Waiting for Minio..."
  sleep 1
done

# Create the bucket if it doesn't exist
/usr/bin/mc mb minio/recursos || true

# Set the bucket policy to public
echo "Setting bucket policy..."
/usr/bin/mc anonymous set public minio/recursos
echo "Policy set."

# Get and print the bucket policy to verify
echo "Verifying bucket policy:"
/usr/bin/mc anonymous get minio/recursos

echo "Minio setup complete."