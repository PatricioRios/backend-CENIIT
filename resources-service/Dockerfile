# Etapa 1: Compilar la aplicación
FROM golang:1.25-alpine AS build

WORKDIR /app

COPY go.mod ./
COPY go.sum ./
RUN go mod download

COPY . .

RUN CGO_ENABLED=0 GOOS=linux go build -o /app/main ./cmd/app/main.go

# Etapa 2: Crear la imagen final
FROM alpine:latest

WORKDIR /app

COPY --from=build /app/main .

EXPOSE 8082

ENTRYPOINT ["./main"]