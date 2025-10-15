--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;



--
-- Drop roles
--

DROP ROLE app_user;
DROP ROLE recursos_user;
DROP ROLE reservas_user;


--
-- Roles
--

CREATE ROLE app_user;
ALTER ROLE app_user WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:SfVqdkZ/a7FdVcUr99zlYA==$Uiz+N85N2AoR10uhSug2YPqMHIw6VjwUmOxqx7iPnHc=:LNLFRWNDZ8qt0UwQkXZcmunDWjgLjsNb7wjNCdyMXGM=';
CREATE ROLE recursos_user;
ALTER ROLE recursos_user WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:V2lDvDDQuuGhHVXcsWpcGA==$S6/N8UpG3R3j+5veBSRlqf+N+3MXz8GfT8v/a0KP0Gc=:Z0gubtlcDa8iljA3XEJn7Ueam1yzsfc0c34QdK8TaR0=';
CREATE ROLE reservas_user;
ALTER ROLE reservas_user WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:zCvfoIdLVFij27UI8WSaaA==$nUC7rygLGS5tyZe+1qIUBpLpVozy7sFQYlXcY2N4fbw=:rtV55ElQ6IzUyc9wIxrtKmYNCafttInd2d0jnroKa5s=';

--
-- User Configurations
--








--
-- PostgreSQL database cluster dump complete
--

