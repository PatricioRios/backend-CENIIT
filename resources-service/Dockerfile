# Etapa 1: Compilar la aplicación
FROM golang:1.25-alpine AS build

WORKDIR /app

COPY go.mod ./
COPY go.sum ./
RUN go mod download

# Instalar swag para generar la documentación de Swagger
RUN go install github.com/swaggo/swag/cmd/swag@latest

COPY . .

# Generar la documentación de Swagger antes de compilar
RUN swag init -g internal/controller/http/router.go
# Asegurar que exista doc.json para el middleware de Swagger
RUN if [ ! -f docs/doc.json ]; then cp docs/swagger.json docs/doc.json; fi

RUN CGO_ENABLED=0 GOOS=linux go build -o /app/main ./cmd/app/main.go

# Etapa 2: Crear la imagen final
FROM alpine:latest

WORKDIR /app

COPY --from=build /app/main .

EXPOSE 8082

ENTRYPOINT ["./main"]