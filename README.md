Guia:

Comandos de ciclo de vida con Docker Compose
Es útil conocer la diferencia entre los comandos para saber cuál usar en cada situación:

docker compose up: Crea y inicia todos los servicios.

docker compose start: Inicia los contenedores que ya existen y están detenidos.

docker compose stop: Detiene los contenedores, pero no los elimina.

docker compose down: Detiene y elimina los contenedores, redes y volúmenes (si usas la bandera -v).


Comando para realizar un backup completo a la base de datos de app-postgres.
    docker exec -t -e PGPASSWORD=app_password app-postgres pg_dumpall -U app_user --clean --if-exists > nuevo_respaldo_app_completo.sql
