package logger

import (
	"os"
	"strings"

	"github.com/rs/zerolog"
)

// Interface -. 
type Interface interface {
	Debug(message string, args ...interface{})
	Info(message string, args ...interface{})
	Warn(message string, args ...interface{})
	Error(message string, args ...interface{})
	Fatal(message string, args ...interface{})
}

// Logger -. 
type Logger struct {
	logger *zerolog.Logger
}

var _ Interface = (*Logger)(nil)

// New -. 
func New(level string) *Logger {
	var l zerolog.Level

	switch strings.ToLower(level) {
	case "error":
		l = zerolog.ErrorLevel
	case "warn":
		l = zerolog.WarnLevel
	case "info":
		l = zerolog.InfoLevel
	case "debug":
		l = zerolog.DebugLevel
	default:
		l = zerolog.InfoLevel
	}

	zerolog.SetGlobalLevel(l)

	skipFrameCount := 3
	logger := zerolog.New(os.Stdout).With().Timestamp().CallerWithSkipFrameCount(zerolog.CallerSkipFrameCount + skipFrameCount).Logger()

	return &Logger{
		logger: &logger,
	}
}

func (l *Logger) log(level zerolog.Level, message string, args ...interface{}) {
	if len(args)%2 != 0 {
		l.logger.WithLevel(level).Msgf("malformed key-value pairs for log message: %s", message)
		return
	}

	event := l.logger.WithLevel(level)
	for i := 0; i < len(args); i += 2 {
		key, ok := args[i].(string)
		if !ok {
			continue // Or handle error
		}
		event = event.Interface(key, args[i+1])
	}

	event.Msg(message)
}

// Debug -. 
func (l *Logger) Debug(message string, args ...interface{}) {
	l.log(zerolog.DebugLevel, message, args...)
}

// Info -. 
func (l *Logger) Info(message string, args ...interface{}) {
	l.log(zerolog.InfoLevel, message, args...)
}

// Warn -. 
func (l *Logger) Warn(message string, args ...interface{}) {
	l.log(zerolog.WarnLevel, message, args...)
}

// Error -. 
func (l *Logger) Error(message string, args ...interface{}) {
	l.log(zerolog.ErrorLevel, message, args...)
}

// Fatal -. 
func (l *Logger) Fatal(message string, args ...interface{}) {
	l.log(zerolog.FatalLevel, message, args...)
	os.Exit(1)
}

