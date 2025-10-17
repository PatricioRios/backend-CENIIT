#!/bin/bash

# Este script ejecuta 'make run', captura el ID del proceso (PID),
# y luego le envía una señal de parada (SIGSTOP).

echo "Ejecutando 'make run' en segundo plano..."

# Ejecuta 'make run' y lo pone en el fondo (&)
make run &

# Captura el PID del último proceso ejecutado en segundo plano
PID=$!

echo "El programa se ha iniciado con el PID: $PID"

# Espera 2 segundos para dar tiempo a que el programa se inicie completamente.
# Puedes ajustar este tiempo si tu programa tarda más en arrancar.
sleep 2

echo "Enviando señal de parada (SIGSTOP) al proceso $PID..."

# Envía la señal SIGSTOP al proceso usando su PID
kill -STOP $PID

if [ $? -eq 0 ]; then
  echo "¡Éxito! El proceso $PID ha sido detenido."
  echo "Puedes reanudarlo con el comando: kill -CONT $PID"
  echo "O puedes terminarlo definitivamente con: kill -KILL $PID"
else
  echo "Error: No se pudo detener el proceso $PID. Puede que ya haya terminado."
fi
