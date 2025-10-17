package middleware

import (
	"errors"
	"fmt"

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
		fmt.Println(roles)
		for index, role := range roles {
			fmt.Printf("Checking role: %s == %s = %t\n", role, requiredRole, role == requiredRole)
			fmt.Printf("index: %d\n", index)
			if role == requiredRole {
				return c.Next()
			}
		}

		return c.Status(fiber.StatusForbidden).JSON(fiber.Map{"error": ErrForbidden.Error()})
	}
}
