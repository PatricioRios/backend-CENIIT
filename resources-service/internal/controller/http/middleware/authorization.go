package middleware

import (
	"errors"

	"github.com/gofiber/fiber/v2"
)

var (
	ErrForbidden = errors.New("forbidden")
)

// RequireRole creates a middleware that checks for a specific role.
func RequireRole(requiredRole string) fiber.Handler {
	return func(c *fiber.Ctx) error {
		roles, ok := c.Locals("roles").([]string)
		if !ok {
			return c.Status(fiber.StatusForbidden).JSON(fiber.Map{"error": "could not retrieve roles"})
		}
		for _, role := range roles {
			if role == requiredRole {
				return c.Next()
			}
		}
		return c.Status(fiber.StatusForbidden).JSON(fiber.Map{"error": ErrForbidden.Error()})
	}
}
