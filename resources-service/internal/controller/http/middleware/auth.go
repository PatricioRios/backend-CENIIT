package middleware

import (
	"context"
	"errors"
	"fmt"
	"strings"

	"github.com/coreos/go-oidc/v3/oidc"
	"github.com/evrone/go-clean-template/config"
	"github.com/evrone/go-clean-template/pkg/logger"
	"github.com/gofiber/fiber/v2"
)

var (
	ErrJWTMissing = errors.New("missing or malformed JWT")
)

// AuthMiddleware creates a new authentication middleware.
func AuthMiddleware(cfg *config.Keycloak, l logger.Interface) (fiber.Handler, error) {

	keySet := oidc.NewRemoteKeySet(context.Background(), cfg.JwksURL)
	verifier := oidc.NewVerifier(cfg.IssuerURL, keySet, &oidc.Config{ClientID: cfg.ClientID})

	return func(c *fiber.Ctx) error {
		authHeader := c.Get("Authorization")
		if authHeader == "" {
			return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{"error": ErrJWTMissing.Error()})
		}

		parts := strings.Split(authHeader, " ")
		if len(parts) != 2 || !strings.EqualFold(parts[0], "Bearer") {
			return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{"error": ErrJWTMissing.Error()})
		}

		tokenString := parts[1]

		idToken, err := verifier.Verify(c.Context(), tokenString)
		if err != nil {
			l.Info(fmt.Sprintf("failed to verify token: %v", err))
			return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{"error": "Invalid or Expired Token"})
		}

		var claims map[string]interface{}
		if err := idToken.Claims(&claims); err != nil {
			return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"error": "failed to parse claims"})
		}

		// Extract resource access roles
		userRoles := make([]string, 0)
		if resourceAccess, ok := claims["resource_access"].(map[string]interface{}); ok {
			if clientAccess, ok := resourceAccess[cfg.ClientID].(map[string]interface{}); ok {
				if roles, ok := clientAccess["roles"].([]interface{}); ok {
					for _, role := range roles {
						if r, ok := role.(string); ok {
							userRoles = append(userRoles, r)
						}
					}
				}
			}
		}

		c.Locals("roles", userRoles)
		c.Locals("claims", claims)

		return c.Next()
	}, nil
}
