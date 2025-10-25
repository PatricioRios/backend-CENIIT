FROM minio/mc
COPY setup-minio.sh /setup-minio.sh
COPY policy.json /policy.json
RUN chmod +x /setup-minio.sh
ENTRYPOINT ["/setup-minio.sh"]