# 🚀 Guía Rápida de Tests

## ✅ Estado Actual
- **54 tests implementados**
- **Todos pasando** ✅
- **Cobertura UseCase:** 98.7%
- **Cobertura Controller:** 76.4%

---

## 📋 Comandos Principales

### Ejecutar todos los tests
```bash
make test
```

### Ver cobertura detallada
```bash
go test -v -race -coverprofile=coverage.txt ./internal/...
go tool cover -html=coverage.txt
```

### Ejecutar tests por capa
```bash
# UseCase tests
go test -v ./internal/usecase/recursos/usecases/...

# Controller tests
go test -v ./internal/controller/http/v1/...
```

---

## 📂 Estructura de Tests

```
internal/
├── controller/http/v1/
│   ├── recursos_controller.go
│   ├── recursos_controller_test.go ✅ (30 tests)
│   └── mocks/
│       ├── mock_recursos_usecase.go
│       ├── mock_photo_usecase.go
│       ├── mock_log_error_usecase.go
│       └── mock_logger.go
│
└── usecase/recursos/usecases/
    ├── recursos_usecases_impl.go
    ├── recursos_usecases_impl_test.go ✅ (13 tests)
    ├── photo_usecases_impl.go
    ├── photo_usecases_impl_test.go ✅ (11 tests)
    └── mocks/
        ├── mock_repository.go
        └── mock_file_storage.go
```

---

## 🔄 Regenerar Mocks

Si modificas alguna interface, regenera los mocks:

```bash
# Mocks de Repository
mockgen -source=./internal/usecase/recursos/ports/persistant_port.go \
  -destination=./internal/usecase/recursos/usecases/mocks/mock_repository.go \
  -package=mocks

# Mocks de FileStorage
mockgen -source=./internal/usecase/recursos/ports/file_storage_port.go \
  -destination=./internal/usecase/recursos/usecases/mocks/mock_file_storage.go \
  -package=mocks

# Mocks de UseCases
mockgen -source=./internal/usecase/recursos/ports/recursos_usecases.go \
  -destination=./internal/controller/http/v1/mocks/mock_recursos_usecase.go \
  -package=mocks

mockgen -source=./internal/usecase/recursos/ports/photo_usecases.go \
  -destination=./internal/controller/http/v1/mocks/mock_photo_usecase.go \
  -package=mocks
```

---

## 📊 Cobertura por Archivo

| Archivo | Cobertura | Tests |
|---------|-----------|-------|
| `recursos_usecases_impl.go` | 98.7% | 13 ✅ |
| `photo_usecases_impl.go` | 100% | 11 ✅ |
| `recursos_controller.go` | 76.4% | 30 ✅ |

---

## ✨ Lo que se Testea

### ✅ UseCase Layer
- CreateResource (2 tests)
- DeleteResource (2 tests)
- UpdateResource (4 tests)
- GetResourceByID (2 tests)
- ListResources (6 tests)
- UploadPhoto (6 tests)
- DeletePhoto (5 tests)

### ✅ Controller Layer
- POST /recursos/ (2 tests)
- DELETE /recursos/:id (3 tests)
- PATCH /recursos/:id (3 tests)
- GET /recursos/:id (3 tests)
- GET /recursos/ (2 tests)
- POST /recursos/search (2 tests)
- POST /recursos/:id/foto (4 tests)
- DELETE /recursos/:id/foto (3 tests)
- Error Handling (4 tests)

---

## 🐛 Bug Corregido

**Archivo:** `internal/controller/http/middleware/auth.go` (línea 40)

**Antes:**
```go
l.Info(fmt.Sprintf("failed to verify token: ", err))
```

**Después:**
```go
l.Info(fmt.Sprintf("failed to verify token: %v", err))
```

---

## 📚 Documentación

- **`TEST_SUMMARY.md`** - Resumen en inglés con detalles técnicos
- **`TESTS_RESUMEN_COMPLETO.md`** - Resumen completo en español
- **`GUIA_RAPIDA_TESTS.md`** - Este archivo (guía rápida)

---

## ✅ Verificación Rápida

```bash
# Verifica que todo funcione
cd /home/patriciorios/Proyectos/Universidad/trabajo_final/resources-service
make test

# Deberías ver:
# ✅ ok github.com/evrone/go-clean-template/internal/controller/http/v1
# ✅ ok github.com/evrone/go-clean-template/internal/usecase/recursos/usecases
```

---

**Última actualización:** 24 de Octubre, 2024  
**Estado:** ✅ Producción Ready
