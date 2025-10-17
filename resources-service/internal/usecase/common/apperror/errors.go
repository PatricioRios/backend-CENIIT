package apperror

import "errors"

// Errores predefinidos para ser usados a través de las capas de la aplicación.
var (
    // ErrNotFound se utiliza cuando un recurso solicitado no se encuentra.
    ErrNotFound   = errors.New("recurso no encontrado")

    // ErrConflict se utiliza cuando una operación causa un conflicto de datos,
    // como intentar crear un recurso que ya existe (violación de unicidad).
    ErrConflict   = errors.New("conflicto de datos")

    // ErrInvalidData se utiliza para errores de validación de datos de entrada
    // que no son capturados por el framework web (ej: lógica de negocio).
    ErrInvalidData = errors.New("datos de entrada inválidos")

    // ErrInternal se utiliza para errores inesperados en el servidor.
    // Este error debería enmascarar el error original para no exponer detalles.
    ErrInternal   = errors.New("error interno del servidor")
)