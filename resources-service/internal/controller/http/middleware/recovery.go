package middleware

import (
	"fmt"
	"runtime/debug"
	"strings"

	errorlogports "github.com/evrone/go-clean-template/internal/usecase/errorlog/ports"
	"github.com/evrone/go-clean-template/pkg/logger"
	"github.com/gofiber/fiber/v2"
	fiberRecover "github.com/gofiber/fiber/v2/middleware/recover"
)

func buildPanicMessage(ctx *fiber.Ctx, err interface{}) string {
	var result strings.Builder

	result.WriteString(ctx.IP())
	result.WriteString(" - ")
	result.WriteString(ctx.Method())
	result.WriteString(" ")
	result.WriteString(ctx.OriginalURL())
	result.WriteString(" PANIC DETECTED: ")
	result.WriteString(fmt.Sprintf("%v\n%s\n", err, debug.Stack()))

	return result.String()
}

func logPanic(l logger.Interface, uc errorlogports.LogErrorUseCase) func(c *fiber.Ctx, err interface{}) {
	return func(ctx *fiber.Ctx, err interface{}) {
		// Log to stdout
		l.Error(buildPanicMessage(ctx, err))

		// Persist error
		input := errorlogports.LogErrorInput{
			Err:           fmt.Errorf("%v", err),
			RequestPath:   ctx.OriginalURL(),
			RequestMethod: ctx.Method(),
		}
		_ = uc.Execute(ctx.UserContext(), input)
	}
}

func Recovery(l logger.Interface, uc errorlogports.LogErrorUseCase) func(c *fiber.Ctx) error {
	return fiberRecover.New(fiberRecover.Config{
		EnableStackTrace:  true,
		StackTraceHandler: logPanic(l, uc),
	})
}
