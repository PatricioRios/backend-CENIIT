#!/bin/bash

# -----------------------------------------------------------------------------
# Script para la creación masiva de 20 usuarios de prueba.
#
# Instrucciones:
# 1. Reemplaza el valor de la variable JWT con tu token de autenticación.
# 2. Guarda el archivo como 'crear_usuarios.sh'.
# 3. Dale permisos de ejecución con el comando: chmod +x crear_usuarios.sh
# 4. Ejecuta el script desde tu terminal con: ./crear_usuarios.sh
# -----------------------------------------------------------------------------

# Coloca tu JWT aquí
JWT="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5MlFsSjZFa3U0bkYxZGZGMTJsV25FX2pGRTlrNXVSODFaWk0wQjR2UGdnIn0.eyJleHAiOjE3NTc1NDM1MjEsImlhdCI6MTc1NzU0MzIyMSwianRpIjoib25ydHJvOjNhOWRkNDgwLTBmNzgtYjRkYi1lNmRiLTRkOWMzZjg2M2U0MSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MzAzMC9yZWFsbXMvZGV2ZWxvcC1zcHJpbmctcmVhbG0iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNDFjMGE1YmQtYWM2ZS00M2YzLThkNDMtNmJiYWVlMjJhZjI1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY2VuaWl0LWJhY2tlbmQtZGV2ZWxvcCIsInNpZCI6Ijc5MDY1ZWNhLTMxMjMtNDdjNS1iNzJlLTcwNWYwOTk2MDQzMiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicmVhbG0tdXNlciIsInJlYWxtLWFkbWluIiwibGlzdC1yb2xlcyIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJwdXQtcm9sZXMtb24tdXNlciIsImRlZmF1bHQtcm9sZXMtZGV2ZWxvcC1zcHJpbmctcmVhbG0iXX0sInJlc291cmNlX2FjY2VzcyI6eyJjZW5paXQtYmFja2VuZC1kZXZlbG9wIjp7InJvbGVzIjpbImJhY2tlbmQtdXNlciIsImxpc3Qtcm9sZXMiLCJiYWNrZW5kLWFkbWluIiwicHV0LXJvbGVzLW9uLXVzZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoicGF0cmljaW8gcmlvcyIsInByZWZlcnJlZF91c2VybmFtZSI6InBhdHJpY2lvcmlvcyIsImdpdmVuX25hbWUiOiJwYXRyaWNpbyIsImZhbWlseV9uYW1lIjoicmlvcyIsImVtYWlsIjoicGF0cmlvY2lvcmlvc0BnbWFpbC5jb20ifQ.c_fxvHj95LUMAvxJ4IcNCMbkI68Jil5be8PUbICyGtCCWHU1fGVUlmg8wdcUSFmV2vzFZKbUDO41eZTlK7tGbIo7T2-nvnWeNYqSas9HQAV-9EgL8I2DQ8mTObRi_EN9fpwRXKQuBj-NjIK9tvmhMihr-8hVdtzFlCktpjWIMaAw5FXa6Qnwkg_8VjYFXmIumfQ8uYBoeGXr7a_MxuJXzJ4jaX8qanFa7Hc9u7-hIKAhSrraEU-5dLbC8E_UYQQ8iV20m1LJq79PClggC-u8S1L-EmqO627r1e5cqRCJ7BGNdsWjQnpA7AU4E1kzNkLdx0BaFdc57EYN0c8Yt8Mlgw"

# Contraseña para todos los usuarios
PASSWORD="12345678"

# URL del endpoint de registro
URL="localhost:8080/auth/register"

echo "Iniciando la creación de 20 usuarios..."

# Usuario 1
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"juan.perez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"juan.perez@ejemplo.com\",
  \"firstName\": \"Juan\",
  \"lastName\": \"Perez\"
}"

# Usuario 2
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"ana.gomez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"ana.gomez@ejemplo.com\",
  \"firstName\": \"Ana\",
  \"lastName\": \"Gomez\"
}"

# Usuario 3
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"carlos.lopez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"carlos.lopez@ejemplo.com\",
  \"firstName\": \"Carlos\",
  \"lastName\": \"Lopez\"
}"

# Usuario 4
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"laura.martinez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"laura.martinez@ejemplo.com\",
  \"firstName\": \"Laura\",
  \"lastName\": \"Martinez\"
}"

# Usuario 5
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"diego.rodriguez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"diego.rodriguez@ejemplo.com\",
  \"firstName\": \"Diego\",
  \"lastName\": \"Rodriguez\"
}"

# Usuario 6
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"sofia.fernandez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"sofia.fernandez@ejemplo.com\",
  \"firstName\": \"Sofia\",
  \"lastName\": \"Fernandez\"
}"

# Usuario 7
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"javier.diaz\",
  \"password\": \"$PASSWORD\",
  \"email\": \"javier.diaz@ejemplo.com\",
  \"firstName\": \"Javier\",
  \"lastName\": \"Diaz\"
}"

# Usuario 8
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"valentina.sanchez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"valentina.sanchez@ejemplo.com\",
  \"firstName\": \"Valentina\",
  \"lastName\": \"Sanchez\"
}"

# Usuario 9
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"martin.torres\",
  \"password\": \"$PASSWORD\",
  \"email\": \"martin.torres@ejemplo.com\",
  \"firstName\": \"Martin\",
  \"lastName\": \"Torres\"
}"

# Usuario 10
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"camila.ramirez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"camila.ramirez@ejemplo.com\",
  \"firstName\": \"Camila\",
  \"lastName\": \"Ramirez\"
}"

# Usuario 11
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"lucas.garcia\",
  \"password\": \"$PASSWORD\",
  \"email\": \"lucas.garcia@ejemplo.com\",
  \"firstName\": \"Lucas\",
  \"lastName\": \"Garcia\"
}"

# Usuario 12
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"isabella.vazquez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"isabella.vazquez@ejemplo.com\",
  \"firstName\": \"Isabella\",
  \"lastName\": \"Vazquez\"
}"

# Usuario 13
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"mateo.castillo\",
  \"password\": \"$PASSWORD\",
  \"email\": \"mateo.castillo@ejemplo.com\",
  \"firstName\": \"Mateo\",
  \"lastName\": \"Castillo\"
}"

# Usuario 14
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"florencia.ruiz\",
  \"password\": \"$PASSWORD\",
  \"email\": \"florencia.ruiz@ejemplo.com\",
  \"firstName\": \"Florencia\",
  \"lastName\": \"Ruiz\"
}"

# Usuario 15
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"sebastian.romero\",
  \"password\": \"$PASSWORD\",
  \"email\": \"sebastian.romero@ejemplo.com\",
  \"firstName\": \"Sebastian\",
  \"lastName\": \"Romero\"
}"

# Usuario 16
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"agustina.suarez\",
  \"password\": \"$PASSWORD\",
  \"email\": \"agustina.suarez@ejemplo.com\",
  \"firstName\": \"Agustina\",
  \"lastName\": \"Suarez\"
}"

# Usuario 17
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"nicolas.herrera\",
  \"password\": \"$PASSWORD\",
  \"email\": \"nicolas.herrera@ejemplo.com\",
  \"firstName\": \"Nicolas\",
  \"lastName\": \"Herrera\"
}"

# Usuario 18
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"julieta.molina\",
  \"password\": \"$PASSWORD\",
  \"email\": \"julieta.molina@ejemplo.com\",
  \"firstName\": \"Julieta\",
  \"lastName\": \"Molina\"
}"

# Usuario 19
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"emiliano.castro\",
  \"password\": \"$PASSWORD\",
  \"email\": \"emiliano.castro@ejemplo.com\",
  \"firstName\": \"Emiliano\",
  \"lastName\": \"Castro\"
}"

# Usuario 20
curl --location "$URL" \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $JWT" \
--data-raw "{
  \"username\": \"catalina.ortega\",
  \"password\": \"$PASSWORD\",
  \"email\": \"catalina.ortega@ejemplo.com\",
  \"firstName\": \"Catalina\",
  \"lastName\": \"Ortega\"
}"

echo "Proceso de creación finalizado."
