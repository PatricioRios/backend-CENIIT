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

ALTER TABLE ONLY reservas_schema.reserva_recursos DROP CONSTRAINT reserva_recursos_reserva_id_fkey;
ALTER TABLE ONLY reservas_schema.reserva_recursos DROP CONSTRAINT reserva_recursos_recursos_id_fkey;
ALTER TABLE ONLY reservas_schema.reserva DROP CONSTRAINT fk_usuario;
ALTER TABLE ONLY reservas_schema.reserva DROP CONSTRAINT reserva_pkey;
ALTER TABLE ONLY recursos_schema.recursos DROP CONSTRAINT recursos_pkey;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT uk_id;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT uk863n1y3x0jalatoir4325ehal;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT ids;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT dni;
ALTER TABLE reservas_schema.reserva_recursos ALTER COLUMN recursos_id DROP DEFAULT;
ALTER TABLE reservas_schema.reserva_recursos ALTER COLUMN reserva_id DROP DEFAULT;
ALTER TABLE reservas_schema.reserva ALTER COLUMN id DROP DEFAULT;
ALTER TABLE recursos_schema.recursos ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.usuario ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE reservas_schema.reserva_recursos_reserva_id_seq;
DROP SEQUENCE reservas_schema.reserva_recursos_recursos_id_seq;
DROP TABLE reservas_schema.reserva_recursos;
DROP SEQUENCE reservas_schema.reserva_id_seq;
DROP TABLE reservas_schema.reserva;
DROP SEQUENCE recursos_schema.recursos_id_seq;
DROP TABLE recursos_schema.recursos;
DROP SEQUENCE public.usuario_id_seq;
DROP TABLE public.usuario;
DROP TYPE reservas_schema.estado_reserva;
DROP TYPE recursos_schema.estado_recurso;
DROP TYPE public.estado_recurso;
DROP SCHEMA reservas_schema;
DROP SCHEMA recursos_schema;
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

