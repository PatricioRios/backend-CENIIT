--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

--DROP DATABASE "TP";
--DROP DATABASE app_db;
--DROP DATABASE paradigmas;
--DROP DATABASE test_1;




--
-- Drop roles
--

--DROP ROLE app_user;
--DROP ROLE recursos_user;
--DROP ROLE reservas_user;


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
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 16.9 (Debian 16.9-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: app_user
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO app_user;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: app_user
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: app_user
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: app_user
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "TP" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 16.9 (Debian 16.9-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: TP; Type: DATABASE; Schema: -; Owner: app_user
--

CREATE DATABASE "TP" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE "TP" OWNER TO app_user;

\connect "TP"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

--
-- Database "app_db" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 16.9 (Debian 16.9-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: app_db; Type: DATABASE; Schema: -; Owner: app_user
--

CREATE DATABASE app_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE app_db OWNER TO app_user;

\connect app_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: logging_schema; Type: SCHEMA; Schema: -; Owner: app_user
--

CREATE SCHEMA logging_schema;


ALTER SCHEMA logging_schema OWNER TO app_user;

--
-- Name: recursos_schema; Type: SCHEMA; Schema: -; Owner: app_user
--

CREATE SCHEMA recursos_schema;


ALTER SCHEMA recursos_schema OWNER TO app_user;

--
-- Name: reservas_schema; Type: SCHEMA; Schema: -; Owner: app_user
--

CREATE SCHEMA reservas_schema;


ALTER SCHEMA reservas_schema OWNER TO app_user;

--
-- Name: estado_recurso; Type: TYPE; Schema: public; Owner: app_user
--

CREATE TYPE public.estado_recurso AS ENUM (
    'ACTIVO',
    'INACTIVO',
    'MANTENIMIENTO'
);


ALTER TYPE public.estado_recurso OWNER TO app_user;

--
-- Name: estado_recurso; Type: TYPE; Schema: recursos_schema; Owner: app_user
--

CREATE TYPE recursos_schema.estado_recurso AS ENUM (
    'ACEPTAD',
    'EN_USO',
    'PENDIENTE'
);


ALTER TYPE recursos_schema.estado_recurso OWNER TO app_user;

--
-- Name: estado_reserva; Type: TYPE; Schema: reservas_schema; Owner: app_user
--

CREATE TYPE reservas_schema.estado_reserva AS ENUM (
    'PENDIENTE',
    'CONFIRMADA',
    'RECHAZADA',
    'CANCELADO'
);


ALTER TYPE reservas_schema.estado_reserva OWNER TO app_user;

--
-- Name: set_timestamp_on_update(); Type: FUNCTION; Schema: recursos_schema; Owner: app_user
--

CREATE FUNCTION recursos_schema.set_timestamp_on_update() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$;


ALTER FUNCTION recursos_schema.set_timestamp_on_update() OWNER TO app_user;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: error_logs; Type: TABLE; Schema: logging_schema; Owner: app_user
--

CREATE TABLE logging_schema.error_logs (
    id bigint NOT NULL,
    "timestamp" timestamp with time zone DEFAULT now() NOT NULL,
    service_name character varying(255) NOT NULL,
    error_message text NOT NULL,
    stack_trace text,
    request_path text,
    request_method character varying(10)
);


ALTER TABLE logging_schema.error_logs OWNER TO app_user;

--
-- Name: TABLE error_logs; Type: COMMENT; Schema: logging_schema; Owner: app_user
--

COMMENT ON TABLE logging_schema.error_logs IS 'Registra errores internos inesperados de la aplicación para auditoría y depuración.';


--
-- Name: error_logs_id_seq; Type: SEQUENCE; Schema: logging_schema; Owner: app_user
--

CREATE SEQUENCE logging_schema.error_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE logging_schema.error_logs_id_seq OWNER TO app_user;

--
-- Name: error_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: logging_schema; Owner: app_user
--

ALTER SEQUENCE logging_schema.error_logs_id_seq OWNED BY logging_schema.error_logs.id;


--
-- Name: error_logs; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.error_logs (
    id bigint NOT NULL,
    "timestamp" timestamp with time zone DEFAULT now() NOT NULL,
    service_name character varying(255) NOT NULL,
    error_message text NOT NULL,
    stack_trace text,
    request_path text,
    request_method character varying(10)
);


ALTER TABLE public.error_logs OWNER TO app_user;

--
-- Name: TABLE error_logs; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.error_logs IS 'Registra errores internos inesperados de la aplicación para auditoría y depuración.';


--
-- Name: error_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.error_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.error_logs_id_seq OWNER TO app_user;

--
-- Name: error_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_user
--

ALTER SEQUENCE public.error_logs_id_seq OWNED BY public.error_logs.id;


--
-- Name: history; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.history (
    id integer NOT NULL,
    source character varying(255),
    destination character varying(255),
    original character varying(255),
    translation character varying(255)
);


ALTER TABLE public.history OWNER TO app_user;

--
-- Name: history_id_seq; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.history_id_seq OWNER TO app_user;

--
-- Name: history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_user
--

ALTER SEQUENCE public.history_id_seq OWNED BY public.history.id;


--
-- Name: schema_migrations; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.schema_migrations (
    version bigint NOT NULL,
    dirty boolean NOT NULL
);


ALTER TABLE public.schema_migrations OWNER TO app_user;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    email character varying(200) NOT NULL,
    dni integer,
    nombre character varying(200) NOT NULL,
    segundo_nombre character varying(200),
    apellido character varying(200) NOT NULL,
    segundo_apellido character varying(200),
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    username character varying(200) NOT NULL
);


ALTER TABLE public.usuario OWNER TO app_user;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_seq OWNER TO app_user;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_user
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- Name: recursos; Type: TABLE; Schema: recursos_schema; Owner: app_user
--

CREATE TABLE recursos_schema.recursos (
    id integer NOT NULL,
    nombre character varying(200),
    descripcion character varying(200),
    href_photo character varying(300),
    estado public.estado_recurso NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE recursos_schema.recursos OWNER TO app_user;

--
-- Name: recursos_id_seq; Type: SEQUENCE; Schema: recursos_schema; Owner: app_user
--

CREATE SEQUENCE recursos_schema.recursos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE recursos_schema.recursos_id_seq OWNER TO app_user;

--
-- Name: recursos_id_seq; Type: SEQUENCE OWNED BY; Schema: recursos_schema; Owner: app_user
--

ALTER SEQUENCE recursos_schema.recursos_id_seq OWNED BY recursos_schema.recursos.id;


--
-- Name: reserva; Type: TABLE; Schema: reservas_schema; Owner: app_user
--

CREATE TABLE reservas_schema.reserva (
    id integer NOT NULL,
    nombre character varying(200),
    descripcion character varying,
    estado reservas_schema.estado_reserva NOT NULL,
    usuario_id integer NOT NULL
);


ALTER TABLE reservas_schema.reserva OWNER TO app_user;

--
-- Name: reserva_id_seq; Type: SEQUENCE; Schema: reservas_schema; Owner: app_user
--

CREATE SEQUENCE reservas_schema.reserva_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE reservas_schema.reserva_id_seq OWNER TO app_user;

--
-- Name: reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: reservas_schema; Owner: app_user
--

ALTER SEQUENCE reservas_schema.reserva_id_seq OWNED BY reservas_schema.reserva.id;


--
-- Name: reserva_recursos; Type: TABLE; Schema: reservas_schema; Owner: app_user
--

CREATE TABLE reservas_schema.reserva_recursos (
    reserva_id integer NOT NULL,
    recursos_id integer NOT NULL
);


ALTER TABLE reservas_schema.reserva_recursos OWNER TO app_user;

--
-- Name: reserva_recursos_recursos_id_seq; Type: SEQUENCE; Schema: reservas_schema; Owner: app_user
--

CREATE SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq OWNER TO app_user;

--
-- Name: reserva_recursos_recursos_id_seq; Type: SEQUENCE OWNED BY; Schema: reservas_schema; Owner: app_user
--

ALTER SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq OWNED BY reservas_schema.reserva_recursos.recursos_id;


--
-- Name: reserva_recursos_reserva_id_seq; Type: SEQUENCE; Schema: reservas_schema; Owner: app_user
--

CREATE SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq OWNER TO app_user;

--
-- Name: reserva_recursos_reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: reservas_schema; Owner: app_user
--

ALTER SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq OWNED BY reservas_schema.reserva_recursos.reserva_id;


--
-- Name: error_logs id; Type: DEFAULT; Schema: logging_schema; Owner: app_user
--

ALTER TABLE ONLY logging_schema.error_logs ALTER COLUMN id SET DEFAULT nextval('logging_schema.error_logs_id_seq'::regclass);


--
-- Name: error_logs id; Type: DEFAULT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.error_logs ALTER COLUMN id SET DEFAULT nextval('public.error_logs_id_seq'::regclass);


--
-- Name: history id; Type: DEFAULT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.history ALTER COLUMN id SET DEFAULT nextval('public.history_id_seq'::regclass);


--
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- Name: recursos id; Type: DEFAULT; Schema: recursos_schema; Owner: app_user
--

ALTER TABLE ONLY recursos_schema.recursos ALTER COLUMN id SET DEFAULT nextval('recursos_schema.recursos_id_seq'::regclass);


--
-- Name: reserva id; Type: DEFAULT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva ALTER COLUMN id SET DEFAULT nextval('reservas_schema.reserva_id_seq'::regclass);


--
-- Name: reserva_recursos reserva_id; Type: DEFAULT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos ALTER COLUMN reserva_id SET DEFAULT nextval('reservas_schema.reserva_recursos_reserva_id_seq'::regclass);


--
-- Name: reserva_recursos recursos_id; Type: DEFAULT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos ALTER COLUMN recursos_id SET DEFAULT nextval('reservas_schema.reserva_recursos_recursos_id_seq'::regclass);


--
-- Data for Name: error_logs; Type: TABLE DATA; Schema: logging_schema; Owner: app_user
--

COPY logging_schema.error_logs (id, "timestamp", service_name, error_message, stack_trace, request_path, request_method) FROM stdin;
\.


--
-- Data for Name: error_logs; Type: TABLE DATA; Schema: public; Owner: app_user
--

COPY public.error_logs (id, "timestamp", service_name, error_message, stack_trace, request_path, request_method) FROM stdin;
\.


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: app_user
--

COPY public.history (id, source, destination, original, translation) FROM stdin;
\.


--
-- Data for Name: schema_migrations; Type: TABLE DATA; Schema: public; Owner: app_user
--

COPY public.schema_migrations (version, dirty) FROM stdin;
20210221023242	f
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: app_user
--

COPY public.usuario (id, uuid, email, dni, nombre, segundo_nombre, apellido, segundo_apellido, created_at, updated_at, username) FROM stdin;
3	2f562cd4-d660-4249-bb9a-218e1b74f5ae	maria.angela@gmail.com	44329343	marta	Angela	Rios	Reynoso	2025-09-10 20:41:17.008696+00	2025-09-10 20:43:17.902433+00	maria
4	03fe8219-8677-4fc6-80e5-f647d2d0cc1f	juan.perez@ejemplo.com	\N	Juan		Perez		2025-09-10 22:28:49.238968+00	2025-09-10 22:28:49.238988+00	juan.perez
5	ac5ed85c-3cf8-491b-8e45-f9a189132077	ana.gomez@ejemplo.com	\N	Ana		Gomez		2025-09-10 22:28:49.309122+00	2025-09-10 22:28:49.309129+00	ana.gomez
6	cdc5ccee-f183-44b1-9c8b-aaeff768c8ee	carlos.lopez@ejemplo.com	\N	Carlos		Lopez		2025-09-10 22:28:49.367732+00	2025-09-10 22:28:49.367739+00	carlos.lopez
7	7912fe97-e581-4758-9d55-000f819feb26	laura.martinez@ejemplo.com	\N	Laura		Martinez		2025-09-10 22:28:49.426743+00	2025-09-10 22:28:49.42675+00	laura.martinez
8	28540263-f792-4947-8ce1-c3bc092344dd	diego.rodriguez@ejemplo.com	\N	Diego		Rodriguez		2025-09-10 22:28:49.484895+00	2025-09-10 22:28:49.484903+00	diego.rodriguez
9	f185bfb4-a209-448a-bb00-ea5ba4bba50f	sofia.fernandez@ejemplo.com	\N	Sofia		Fernandez		2025-09-10 22:28:49.536702+00	2025-09-10 22:28:49.536708+00	sofia.fernandez
10	204ba769-dbe0-4d4c-9fd5-a0db062b97b4	javier.diaz@ejemplo.com	\N	Javier		Diaz		2025-09-10 22:28:49.581618+00	2025-09-10 22:28:49.581631+00	javier.diaz
11	59899dcd-6b77-47ce-b172-f9d378d24531	valentina.sanchez@ejemplo.com	\N	Valentina		Sanchez		2025-09-10 22:28:49.623843+00	2025-09-10 22:28:49.623847+00	valentina.sanchez
12	c70221b7-a502-4c76-9ee1-5c13686b87e2	martin.torres@ejemplo.com	\N	Martin		Torres		2025-09-10 22:28:49.664306+00	2025-09-10 22:28:49.664312+00	martin.torres
13	0a5fb9e5-5269-4270-822f-f2c8e9fa9e18	camila.ramirez@ejemplo.com	\N	Camila		Ramirez		2025-09-10 22:28:49.705683+00	2025-09-10 22:28:49.705695+00	camila.ramirez
14	e4aff204-6bba-4a0c-b148-64ca79071c00	lucas.garcia@ejemplo.com	\N	Lucas		Garcia		2025-09-10 22:28:49.746417+00	2025-09-10 22:28:49.746421+00	lucas.garcia
15	1a114d03-3e8a-4421-9754-da4b0b502559	isabella.vazquez@ejemplo.com	\N	Isabella		Vazquez		2025-09-10 22:28:49.785877+00	2025-09-10 22:28:49.785881+00	isabella.vazquez
16	b2b6e334-3352-4346-8a24-394a1d7c7e7d	mateo.castillo@ejemplo.com	\N	Mateo		Castillo		2025-09-10 22:28:49.826717+00	2025-09-10 22:28:49.826728+00	mateo.castillo
17	c14c5abd-1363-4125-95ce-7c5410c41a51	florencia.ruiz@ejemplo.com	\N	Florencia		Ruiz		2025-09-10 22:28:49.870159+00	2025-09-10 22:28:49.870164+00	florencia.ruiz
18	71f0eb62-74b3-434c-a118-017a3ca4b8ad	sebastian.romero@ejemplo.com	\N	Sebastian		Romero		2025-09-10 22:28:49.910953+00	2025-09-10 22:28:49.910961+00	sebastian.romero
19	02ef316d-9edf-4d02-a434-1320368b1057	agustina.suarez@ejemplo.com	\N	Agustina		Suarez		2025-09-10 22:28:49.950297+00	2025-09-10 22:28:49.950304+00	agustina.suarez
20	3f12677a-b092-4451-b350-8ef5ea379d03	nicolas.herrera@ejemplo.com	\N	Nicolas		Herrera		2025-09-10 22:28:49.988617+00	2025-09-10 22:28:49.988621+00	nicolas.herrera
21	f631d211-40a4-4ab5-9653-78d6565c3a78	julieta.molina@ejemplo.com	\N	Julieta		Molina		2025-09-10 22:28:50.024196+00	2025-09-10 22:28:50.0242+00	julieta.molina
22	d24ba7c9-9ef7-475a-b09b-8b612e9a8807	emiliano.castro@ejemplo.com	\N	Emiliano		Castro		2025-09-10 22:28:50.060536+00	2025-09-10 22:28:50.060542+00	emiliano.castro
23	fa941538-d2fa-438e-a350-60c45758ea4d	catalina.ortega@ejemplo.com	\N	Catalina		Ortega		2025-09-10 22:28:50.096005+00	2025-09-10 22:28:50.09601+00	catalina.ortega
24	0c08a11d-8262-4685-9d5f-dd26dcf94c81	mariae@jemplo.com	\N	maria		perez		2025-09-11 22:05:47.777063+00	2025-09-11 22:05:47.777082+00	maria1
25	163d6243-3d88-4161-8f5a-42218124d430	mariae@jemplo2.com	\N	maria		perez		2025-09-12 00:47:53.299987+00	2025-09-12 00:47:53.300013+00	maria12
26	0674f41c-85da-4443-bb14-c9e00bc02677	mariaeasd@jemplo.com	\N	mariaass		peressz		2025-09-12 00:59:56.83427+00	2025-09-12 00:59:56.834288+00	roman
\.


--
-- Data for Name: recursos; Type: TABLE DATA; Schema: recursos_schema; Owner: app_user
--

COPY recursos_schema.recursos (id, nombre, descripcion, href_photo, estado, created_at, updated_at) FROM stdin;
6	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 00:00:00+00	2025-10-17 00:00:00+00
7	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 00:00:00+00	2025-10-17 00:00:00+00
8	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 00:00:00+00	2025-10-17 00:00:00+00
9	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 00:00:00+00	2025-10-17 00:00:00+00
10	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 00:00:00+00	2025-10-17 00:00:00+00
11	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 00:00:00+00	2025-10-17 00:00:00+00
13	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 14:58:11.723625+00	2025-10-17 14:58:11.723625+00
14	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 14:58:14.772401+00	2025-10-17 14:58:14.772401+00
15	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:18.75848+00	2025-10-17 15:15:18.75848+00
16	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:22.985856+00	2025-10-17 15:15:22.985856+00
17	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:25.209386+00	2025-10-17 15:15:25.209386+00
18	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:26.068907+00	2025-10-17 15:15:26.068907+00
19	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:27.11791+00	2025-10-17 15:15:27.11791+00
20	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:28.108199+00	2025-10-17 15:15:28.108199+00
21	Cañón Proyector Aula Magna	Proyector principal del salón de actos, modelo Epson PowerLite 2250U.	https://example.com/photos/proyector_magna.jpg	MANTENIMIENTO	2025-10-17 15:15:28.978299+00	2025-10-17 15:15:28.978299+00
5	Nuevo nombre 111	Nueva descripcion	https://example.com/photos/proyector_magna.jpg	ACTIVO	2025-10-17 00:00:00+00	2025-10-17 22:35:11.103286+00
\.


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: reservas_schema; Owner: app_user
--

COPY reservas_schema.reserva (id, nombre, descripcion, estado, usuario_id) FROM stdin;
\.


--
-- Data for Name: reserva_recursos; Type: TABLE DATA; Schema: reservas_schema; Owner: app_user
--

COPY reservas_schema.reserva_recursos (reserva_id, recursos_id) FROM stdin;
\.


--
-- Name: error_logs_id_seq; Type: SEQUENCE SET; Schema: logging_schema; Owner: app_user
--

SELECT pg_catalog.setval('logging_schema.error_logs_id_seq', 1, false);


--
-- Name: error_logs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_user
--

SELECT pg_catalog.setval('public.error_logs_id_seq', 1, false);


--
-- Name: history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_user
--

SELECT pg_catalog.setval('public.history_id_seq', 1, false);


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_user
--

SELECT pg_catalog.setval('public.usuario_id_seq', 26, true);


--
-- Name: recursos_id_seq; Type: SEQUENCE SET; Schema: recursos_schema; Owner: app_user
--

SELECT pg_catalog.setval('recursos_schema.recursos_id_seq', 21, true);


--
-- Name: reserva_id_seq; Type: SEQUENCE SET; Schema: reservas_schema; Owner: app_user
--

SELECT pg_catalog.setval('reservas_schema.reserva_id_seq', 1, false);


--
-- Name: reserva_recursos_recursos_id_seq; Type: SEQUENCE SET; Schema: reservas_schema; Owner: app_user
--

SELECT pg_catalog.setval('reservas_schema.reserva_recursos_recursos_id_seq', 1, false);


--
-- Name: reserva_recursos_reserva_id_seq; Type: SEQUENCE SET; Schema: reservas_schema; Owner: app_user
--

SELECT pg_catalog.setval('reservas_schema.reserva_recursos_reserva_id_seq', 1, false);


--
-- Name: error_logs error_logs_pkey; Type: CONSTRAINT; Schema: logging_schema; Owner: app_user
--

ALTER TABLE ONLY logging_schema.error_logs
    ADD CONSTRAINT error_logs_pkey PRIMARY KEY (id);


--
-- Name: usuario dni; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT dni UNIQUE (dni);


--
-- Name: error_logs error_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.error_logs
    ADD CONSTRAINT error_logs_pkey PRIMARY KEY (id);


--
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (id);


--
-- Name: usuario ids; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT ids UNIQUE (uuid, id);


--
-- Name: schema_migrations schema_migrations_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.schema_migrations
    ADD CONSTRAINT schema_migrations_pkey PRIMARY KEY (version);


--
-- Name: usuario uk863n1y3x0jalatoir4325ehal; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk863n1y3x0jalatoir4325ehal UNIQUE (username);


--
-- Name: usuario uk_id; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_id UNIQUE (id);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (uuid);


--
-- Name: recursos recursos_pkey; Type: CONSTRAINT; Schema: recursos_schema; Owner: app_user
--

ALTER TABLE ONLY recursos_schema.recursos
    ADD CONSTRAINT recursos_pkey PRIMARY KEY (id);


--
-- Name: reserva reserva_pkey; Type: CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva
    ADD CONSTRAINT reserva_pkey PRIMARY KEY (id);


--
-- Name: idx_error_logs_service_name; Type: INDEX; Schema: logging_schema; Owner: app_user
--

CREATE INDEX idx_error_logs_service_name ON logging_schema.error_logs USING btree (service_name);


--
-- Name: idx_error_logs_timestamp; Type: INDEX; Schema: logging_schema; Owner: app_user
--

CREATE INDEX idx_error_logs_timestamp ON logging_schema.error_logs USING btree ("timestamp" DESC);


--
-- Name: idx_error_logs_service_name; Type: INDEX; Schema: public; Owner: app_user
--

CREATE INDEX idx_error_logs_service_name ON public.error_logs USING btree (service_name);


--
-- Name: idx_error_logs_timestamp; Type: INDEX; Schema: public; Owner: app_user
--

CREATE INDEX idx_error_logs_timestamp ON public.error_logs USING btree ("timestamp" DESC);


--
-- Name: recursos set_timestamp_on_recursos_update; Type: TRIGGER; Schema: recursos_schema; Owner: app_user
--

CREATE TRIGGER set_timestamp_on_recursos_update BEFORE UPDATE ON recursos_schema.recursos FOR EACH ROW EXECUTE FUNCTION recursos_schema.set_timestamp_on_update();


--
-- Name: reserva fk_usuario; Type: FK CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva
    ADD CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- Name: reserva_recursos reserva_recursos_recursos_id_fkey; Type: FK CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos
    ADD CONSTRAINT reserva_recursos_recursos_id_fkey FOREIGN KEY (recursos_id) REFERENCES recursos_schema.recursos(id) NOT VALID;


--
-- Name: reserva_recursos reserva_recursos_reserva_id_fkey; Type: FK CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos
    ADD CONSTRAINT reserva_recursos_reserva_id_fkey FOREIGN KEY (reserva_id) REFERENCES reservas_schema.reserva(id) NOT VALID;


--
-- Name: SCHEMA recursos_schema; Type: ACL; Schema: -; Owner: app_user
--

GRANT USAGE ON SCHEMA recursos_schema TO recursos_user;


--
-- Name: SCHEMA reservas_schema; Type: ACL; Schema: -; Owner: app_user
--

GRANT USAGE ON SCHEMA reservas_schema TO reservas_user;


--
-- Name: TABLE recursos; Type: ACL; Schema: recursos_schema; Owner: app_user
--

GRANT SELECT ON TABLE recursos_schema.recursos TO reservas_user;


--
-- PostgreSQL database dump complete
--

--
-- Database "paradigmas" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 16.9 (Debian 16.9-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: paradigmas; Type: DATABASE; Schema: -; Owner: app_user
--

CREATE DATABASE paradigmas WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE paradigmas OWNER TO app_user;

\connect paradigmas

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 16.9 (Debian 16.9-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: app_user
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO app_user;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: app_user
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- Database "test_1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 16.9 (Debian 16.9-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: test_1; Type: DATABASE; Schema: -; Owner: app_user
--

CREATE DATABASE test_1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE test_1 OWNER TO app_user;

\connect test_1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: recursos_schema; Type: SCHEMA; Schema: -; Owner: app_user
--

CREATE SCHEMA recursos_schema;


ALTER SCHEMA recursos_schema OWNER TO app_user;

--
-- Name: reservas_schema; Type: SCHEMA; Schema: -; Owner: app_user
--

CREATE SCHEMA reservas_schema;


ALTER SCHEMA reservas_schema OWNER TO app_user;

--
-- Name: estado_recurso; Type: TYPE; Schema: public; Owner: app_user
--

CREATE TYPE public.estado_recurso AS ENUM (
    'ACTIVO',
    'INACTIVO',
    'MANTENIMIENTO'
);


ALTER TYPE public.estado_recurso OWNER TO app_user;

--
-- Name: estado_recurso; Type: TYPE; Schema: recursos_schema; Owner: app_user
--

CREATE TYPE recursos_schema.estado_recurso AS ENUM (
    'ACEPTAD',
    'EN_USO',
    'PENDIENTE'
);


ALTER TYPE recursos_schema.estado_recurso OWNER TO app_user;

--
-- Name: estado_reserva; Type: TYPE; Schema: reservas_schema; Owner: app_user
--

CREATE TYPE reservas_schema.estado_reserva AS ENUM (
    'PENDIENTE',
    'CONFIRMADA',
    'RECHAZADA',
    'CANCELADO'
);


ALTER TYPE reservas_schema.estado_reserva OWNER TO app_user;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    email character varying(200) NOT NULL,
    dni integer,
    nombre character varying(200) NOT NULL,
    segundo_nombre character varying(200),
    apellido character varying(200) NOT NULL,
    segundo_apellido character varying(200),
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    username character varying(200) NOT NULL
);


ALTER TABLE public.usuario OWNER TO app_user;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_seq OWNER TO app_user;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: app_user
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- Name: recursos; Type: TABLE; Schema: recursos_schema; Owner: app_user
--

CREATE TABLE recursos_schema.recursos (
    id integer NOT NULL,
    nombre character varying(200),
    descripcion character varying(200),
    href_photo character varying(300),
    estado public.estado_recurso NOT NULL
);


ALTER TABLE recursos_schema.recursos OWNER TO app_user;

--
-- Name: recursos_id_seq; Type: SEQUENCE; Schema: recursos_schema; Owner: app_user
--

CREATE SEQUENCE recursos_schema.recursos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE recursos_schema.recursos_id_seq OWNER TO app_user;

--
-- Name: recursos_id_seq; Type: SEQUENCE OWNED BY; Schema: recursos_schema; Owner: app_user
--

ALTER SEQUENCE recursos_schema.recursos_id_seq OWNED BY recursos_schema.recursos.id;


--
-- Name: reserva; Type: TABLE; Schema: reservas_schema; Owner: app_user
--

CREATE TABLE reservas_schema.reserva (
    id integer NOT NULL,
    nombre character varying(200),
    descripcion character varying,
    estado reservas_schema.estado_reserva NOT NULL,
    usuario_id integer NOT NULL
);


ALTER TABLE reservas_schema.reserva OWNER TO app_user;

--
-- Name: reserva_id_seq; Type: SEQUENCE; Schema: reservas_schema; Owner: app_user
--

CREATE SEQUENCE reservas_schema.reserva_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE reservas_schema.reserva_id_seq OWNER TO app_user;

--
-- Name: reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: reservas_schema; Owner: app_user
--

ALTER SEQUENCE reservas_schema.reserva_id_seq OWNED BY reservas_schema.reserva.id;


--
-- Name: reserva_recursos; Type: TABLE; Schema: reservas_schema; Owner: app_user
--

CREATE TABLE reservas_schema.reserva_recursos (
    reserva_id integer NOT NULL,
    recursos_id integer NOT NULL
);


ALTER TABLE reservas_schema.reserva_recursos OWNER TO app_user;

--
-- Name: reserva_recursos_recursos_id_seq; Type: SEQUENCE; Schema: reservas_schema; Owner: app_user
--

CREATE SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq OWNER TO app_user;

--
-- Name: reserva_recursos_recursos_id_seq; Type: SEQUENCE OWNED BY; Schema: reservas_schema; Owner: app_user
--

ALTER SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq OWNED BY reservas_schema.reserva_recursos.recursos_id;


--
-- Name: reserva_recursos_reserva_id_seq; Type: SEQUENCE; Schema: reservas_schema; Owner: app_user
--

CREATE SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq OWNER TO app_user;

--
-- Name: reserva_recursos_reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: reservas_schema; Owner: app_user
--

ALTER SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq OWNED BY reservas_schema.reserva_recursos.reserva_id;


--
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- Name: recursos id; Type: DEFAULT; Schema: recursos_schema; Owner: app_user
--

ALTER TABLE ONLY recursos_schema.recursos ALTER COLUMN id SET DEFAULT nextval('recursos_schema.recursos_id_seq'::regclass);


--
-- Name: reserva id; Type: DEFAULT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva ALTER COLUMN id SET DEFAULT nextval('reservas_schema.reserva_id_seq'::regclass);


--
-- Name: reserva_recursos reserva_id; Type: DEFAULT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos ALTER COLUMN reserva_id SET DEFAULT nextval('reservas_schema.reserva_recursos_reserva_id_seq'::regclass);


--
-- Name: reserva_recursos recursos_id; Type: DEFAULT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos ALTER COLUMN recursos_id SET DEFAULT nextval('reservas_schema.reserva_recursos_recursos_id_seq'::regclass);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: app_user
--

COPY public.usuario (id, uuid, email, dni, nombre, segundo_nombre, apellido, segundo_apellido, created_at, updated_at, username) FROM stdin;
3	2f562cd4-d660-4249-bb9a-218e1b74f5ae	maria.angela@gmail.com	44329343	marta	Angela	Rios	Reynoso	2025-09-10 20:41:17.008696+00	2025-09-10 20:43:17.902433+00	maria
4	03fe8219-8677-4fc6-80e5-f647d2d0cc1f	juan.perez@ejemplo.com	\N	Juan		Perez		2025-09-10 22:28:49.238968+00	2025-09-10 22:28:49.238988+00	juan.perez
5	ac5ed85c-3cf8-491b-8e45-f9a189132077	ana.gomez@ejemplo.com	\N	Ana		Gomez		2025-09-10 22:28:49.309122+00	2025-09-10 22:28:49.309129+00	ana.gomez
6	cdc5ccee-f183-44b1-9c8b-aaeff768c8ee	carlos.lopez@ejemplo.com	\N	Carlos		Lopez		2025-09-10 22:28:49.367732+00	2025-09-10 22:28:49.367739+00	carlos.lopez
7	7912fe97-e581-4758-9d55-000f819feb26	laura.martinez@ejemplo.com	\N	Laura		Martinez		2025-09-10 22:28:49.426743+00	2025-09-10 22:28:49.42675+00	laura.martinez
8	28540263-f792-4947-8ce1-c3bc092344dd	diego.rodriguez@ejemplo.com	\N	Diego		Rodriguez		2025-09-10 22:28:49.484895+00	2025-09-10 22:28:49.484903+00	diego.rodriguez
9	f185bfb4-a209-448a-bb00-ea5ba4bba50f	sofia.fernandez@ejemplo.com	\N	Sofia		Fernandez		2025-09-10 22:28:49.536702+00	2025-09-10 22:28:49.536708+00	sofia.fernandez
10	204ba769-dbe0-4d4c-9fd5-a0db062b97b4	javier.diaz@ejemplo.com	\N	Javier		Diaz		2025-09-10 22:28:49.581618+00	2025-09-10 22:28:49.581631+00	javier.diaz
11	59899dcd-6b77-47ce-b172-f9d378d24531	valentina.sanchez@ejemplo.com	\N	Valentina		Sanchez		2025-09-10 22:28:49.623843+00	2025-09-10 22:28:49.623847+00	valentina.sanchez
12	c70221b7-a502-4c76-9ee1-5c13686b87e2	martin.torres@ejemplo.com	\N	Martin		Torres		2025-09-10 22:28:49.664306+00	2025-09-10 22:28:49.664312+00	martin.torres
13	0a5fb9e5-5269-4270-822f-f2c8e9fa9e18	camila.ramirez@ejemplo.com	\N	Camila		Ramirez		2025-09-10 22:28:49.705683+00	2025-09-10 22:28:49.705695+00	camila.ramirez
14	e4aff204-6bba-4a0c-b148-64ca79071c00	lucas.garcia@ejemplo.com	\N	Lucas		Garcia		2025-09-10 22:28:49.746417+00	2025-09-10 22:28:49.746421+00	lucas.garcia
15	1a114d03-3e8a-4421-9754-da4b0b502559	isabella.vazquez@ejemplo.com	\N	Isabella		Vazquez		2025-09-10 22:28:49.785877+00	2025-09-10 22:28:49.785881+00	isabella.vazquez
16	b2b6e334-3352-4346-8a24-394a1d7c7e7d	mateo.castillo@ejemplo.com	\N	Mateo		Castillo		2025-09-10 22:28:49.826717+00	2025-09-10 22:28:49.826728+00	mateo.castillo
17	c14c5abd-1363-4125-95ce-7c5410c41a51	florencia.ruiz@ejemplo.com	\N	Florencia		Ruiz		2025-09-10 22:28:49.870159+00	2025-09-10 22:28:49.870164+00	florencia.ruiz
18	71f0eb62-74b3-434c-a118-017a3ca4b8ad	sebastian.romero@ejemplo.com	\N	Sebastian		Romero		2025-09-10 22:28:49.910953+00	2025-09-10 22:28:49.910961+00	sebastian.romero
19	02ef316d-9edf-4d02-a434-1320368b1057	agustina.suarez@ejemplo.com	\N	Agustina		Suarez		2025-09-10 22:28:49.950297+00	2025-09-10 22:28:49.950304+00	agustina.suarez
20	3f12677a-b092-4451-b350-8ef5ea379d03	nicolas.herrera@ejemplo.com	\N	Nicolas		Herrera		2025-09-10 22:28:49.988617+00	2025-09-10 22:28:49.988621+00	nicolas.herrera
21	f631d211-40a4-4ab5-9653-78d6565c3a78	julieta.molina@ejemplo.com	\N	Julieta		Molina		2025-09-10 22:28:50.024196+00	2025-09-10 22:28:50.0242+00	julieta.molina
22	d24ba7c9-9ef7-475a-b09b-8b612e9a8807	emiliano.castro@ejemplo.com	\N	Emiliano		Castro		2025-09-10 22:28:50.060536+00	2025-09-10 22:28:50.060542+00	emiliano.castro
23	fa941538-d2fa-438e-a350-60c45758ea4d	catalina.ortega@ejemplo.com	\N	Catalina		Ortega		2025-09-10 22:28:50.096005+00	2025-09-10 22:28:50.09601+00	catalina.ortega
24	0c08a11d-8262-4685-9d5f-dd26dcf94c81	mariae@jemplo.com	\N	maria		perez		2025-09-11 22:05:47.777063+00	2025-09-11 22:05:47.777082+00	maria1
25	163d6243-3d88-4161-8f5a-42218124d430	mariae@jemplo2.com	\N	maria		perez		2025-09-12 00:47:53.299987+00	2025-09-12 00:47:53.300013+00	maria12
26	0674f41c-85da-4443-bb14-c9e00bc02677	mariaeasd@jemplo.com	\N	mariaass		peressz		2025-09-12 00:59:56.83427+00	2025-09-12 00:59:56.834288+00	roman
\.


--
-- Data for Name: recursos; Type: TABLE DATA; Schema: recursos_schema; Owner: app_user
--

COPY recursos_schema.recursos (id, nombre, descripcion, href_photo, estado) FROM stdin;
\.


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: reservas_schema; Owner: app_user
--

COPY reservas_schema.reserva (id, nombre, descripcion, estado, usuario_id) FROM stdin;
\.


--
-- Data for Name: reserva_recursos; Type: TABLE DATA; Schema: reservas_schema; Owner: app_user
--

COPY reservas_schema.reserva_recursos (reserva_id, recursos_id) FROM stdin;
\.


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: app_user
--

SELECT pg_catalog.setval('public.usuario_id_seq', 26, true);


--
-- Name: recursos_id_seq; Type: SEQUENCE SET; Schema: recursos_schema; Owner: app_user
--

SELECT pg_catalog.setval('recursos_schema.recursos_id_seq', 1, false);


--
-- Name: reserva_id_seq; Type: SEQUENCE SET; Schema: reservas_schema; Owner: app_user
--

SELECT pg_catalog.setval('reservas_schema.reserva_id_seq', 1, false);


--
-- Name: reserva_recursos_recursos_id_seq; Type: SEQUENCE SET; Schema: reservas_schema; Owner: app_user
--

SELECT pg_catalog.setval('reservas_schema.reserva_recursos_recursos_id_seq', 1, false);


--
-- Name: reserva_recursos_reserva_id_seq; Type: SEQUENCE SET; Schema: reservas_schema; Owner: app_user
--

SELECT pg_catalog.setval('reservas_schema.reserva_recursos_reserva_id_seq', 1, false);


--
-- Name: usuario dni; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT dni UNIQUE (dni);


--
-- Name: usuario ids; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT ids UNIQUE (uuid, id);


--
-- Name: usuario uk863n1y3x0jalatoir4325ehal; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk863n1y3x0jalatoir4325ehal UNIQUE (username);


--
-- Name: usuario uk_id; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_id UNIQUE (id);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (uuid);


--
-- Name: recursos recursos_pkey; Type: CONSTRAINT; Schema: recursos_schema; Owner: app_user
--

ALTER TABLE ONLY recursos_schema.recursos
    ADD CONSTRAINT recursos_pkey PRIMARY KEY (id);


--
-- Name: reserva reserva_pkey; Type: CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva
    ADD CONSTRAINT reserva_pkey PRIMARY KEY (id);


--
-- Name: reserva fk_usuario; Type: FK CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva
    ADD CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- Name: reserva_recursos reserva_recursos_recursos_id_fkey; Type: FK CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos
    ADD CONSTRAINT reserva_recursos_recursos_id_fkey FOREIGN KEY (recursos_id) REFERENCES recursos_schema.recursos(id) NOT VALID;


--
-- Name: reserva_recursos reserva_recursos_reserva_id_fkey; Type: FK CONSTRAINT; Schema: reservas_schema; Owner: app_user
--

ALTER TABLE ONLY reservas_schema.reserva_recursos
    ADD CONSTRAINT reserva_recursos_reserva_id_fkey FOREIGN KEY (reserva_id) REFERENCES reservas_schema.reserva(id) NOT VALID;


--
-- Name: SCHEMA recursos_schema; Type: ACL; Schema: -; Owner: app_user
--

GRANT USAGE ON SCHEMA recursos_schema TO recursos_user;


--
-- Name: SCHEMA reservas_schema; Type: ACL; Schema: -; Owner: app_user
--

GRANT USAGE ON SCHEMA reservas_schema TO reservas_user;


--
-- Name: TABLE recursos; Type: ACL; Schema: recursos_schema; Owner: app_user
--

GRANT SELECT ON TABLE recursos_schema.recursos TO reservas_user;


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

