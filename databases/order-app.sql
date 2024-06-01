--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Ubuntu 16.2-1.pgdg22.04+1)
-- Dumped by pg_dump version 16.2 (Ubuntu 16.2-1.pgdg22.04+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu (
    menu_id bigint NOT NULL,
    name character varying NOT NULL,
    rating smallint,
    harga integer,
    level smallint,
    resto_id bigint,
    created_time timestamp without time zone,
    modified_time timestamp without time zone
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    order_id bigint NOT NULL,
    user_id bigint,
    resto_id bigint,
    menu_id bigint,
    order_date timestamp without time zone,
    quantity integer,
    total_harga integer,
    status character varying,
    created_time timestamp without time zone,
    modified_time timestamp without time zone
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: order_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_order_id_seq OWNER TO postgres;

--
-- Name: order_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_order_id_seq OWNED BY public.orders.order_id;


--
-- Name: restaurant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurant (
    resto_id bigint NOT NULL,
    name character varying NOT NULL,
    alamat character varying,
    time_open character varying,
    created_time timestamp without time zone,
    modified_time timestamp without time zone
);


ALTER TABLE public.restaurant OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    username character varying NOT NULL,
    fullname character varying,
    password character varying,
    alamat character varying,
    created_time timestamp without time zone,
    modified_time timestamp without time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menu (menu_id, name, rating, harga, level, resto_id, created_time, modified_time) FROM stdin;
1	Ayam Goreng	4	10000	4	1	\N	\N
2	Ayam Geprek	3	15000	5	1	\N	\N
3	Ayam Bakar	3	30000	3	1	\N	\N
4	Ayam Crispy	5	8000	4	1	\N	\N
5	Keripik Singkong	5	5000	2	2	\N	\N
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (order_id, user_id, resto_id, menu_id, order_date, quantity, total_harga, status, created_time, modified_time) FROM stdin;
1	19	1	2	2024-04-04 13:26:37.598974	2	30000	Completed	2024-04-04 13:26:37.598974	2024-04-04 13:26:52.156898
2	19	1	1	2024-04-04 13:26:37.598974	2	20000	Completed	2024-04-04 13:26:37.598974	2024-04-04 13:26:52.171478
3	19	1	3	2024-04-04 13:27:40.764986	3	90000	Cancelled	2024-04-04 13:27:40.764986	2024-04-04 13:28:11.400437
4	19	1	4	2024-04-04 13:27:40.764986	3	24000	Cancelled	2024-04-04 13:27:40.764986	2024-04-04 13:28:11.421777
5	19	1	1	2024-04-03 13:28:53.459	1	10000	Ongoing	2024-04-03 13:28:53.459	\N
6	19	2	5	2024-04-04 14:03:05.039883	1	5000	Completed	2024-04-04 14:03:05.039883	2024-04-04 14:03:22.077281
7	19	2	5	2024-04-04 14:57:13.459842	2	10000	Ongoing	2024-04-04 14:57:13.459842	\N
8	35	2	5	2024-04-05 14:40:15.091564	3	15000	Completed	2024-04-05 14:40:15.091564	2024-04-05 14:40:30.222449
9	35	1	1	2024-04-05 14:43:13.071314	1	10000	Ongoing	2024-04-05 14:43:13.071314	\N
10	35	1	2	2024-04-05 14:43:13.071314	1	15000	Ongoing	2024-04-05 14:43:13.071314	\N
11	35	2	5	2024-04-05 15:06:56.498937	1	5000	Cancelled	2024-04-05 15:06:56.498937	2024-04-05 15:21:31.630657
\.


--
-- Data for Name: restaurant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.restaurant (resto_id, name, alamat, time_open, created_time, modified_time) FROM stdin;
1	Ayam Bakar Bandung	Jl. Soekarno Hatta No. 10	10:00 - 20:00	2024-03-21 11:45:49.96	2024-03-21 11:45:49.96
2	Pusat Cemilan Bandung	Jl. Cihampelas No. 10	09:00 - 21:00	2024-04-04 13:57:08.544	2024-04-04 13:57:08.544
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, username, fullname, password, alamat, created_time, modified_time) FROM stdin;
14	user12	User	$2a$10$JvuO.9skd73AxV3f0va2Aum5njJcWIn0DqRkFs0zOrip911XesUZ6	Bandung	2024-03-26 11:18:20.016662	\N
15	user13	User	$2a$10$m6VixceXbUuaPwOl3puyXufMVMFhujvNYLx6tmtGkGNeYnfhDWgwK	Bandung	2024-03-26 11:25:43.351299	\N
18	ujangmaman	Ujang	$2a$10$5OBJX8MltVDcqNtVEp3.fOmrIlMP1SkP9sBINSO3K2Ms2M2YwIKSy	Bandung	2024-03-28 11:41:38.745347	\N
19	komeng	Komeng	$2a$10$4cYyc5b202y2i.Y2Br6C8OqicxljWsef1lRCuhH9aqBglhqVc/1Gu	Bandung	2024-03-28 12:07:46.127895	\N
22	komeng1	Komeng Uhuyy	$2a$10$FiznSZKI5BwTiZ2Lv5V1RuDvE8xWuQhT8nMhZe.Ai0fAWHOkUj.Ti	Bandung	2024-04-02 11:55:25.496533	\N
24	aduladul	Adul Mini	$2a$10$7eYAXDtpZ4vI0jEpMCzbgO.t.EgwmF4rV3Y6FgYjzLFNGo2HbiQXe	Jl. Pasteur No 99	2024-04-02 12:36:54.46682	\N
26	markus	Markus Horizon	$2a$10$wV0g6fyvcZCiZvB/QJUVJulFHf4wHtzKeJjP9jqwNZYnzu3ojVuim	Jl. Dago Atas No. 39	2024-04-02 13:16:19.59338	\N
27	userdummy	Dummy User	$2a$10$I8BE/.KNfaNvN6LcLwoA4uVLscZMGgNeD2lUATDLEZWfE1OjJhasK	Jl. Unknown No. 0	2024-04-02 13:18:17.566387	\N
35	andi	Andi Hidayat	$2a$10$/SJDj4BpULp/.uPGKouhzOP2DQMYUnMj4Re6AfUzKHFT3XdESlhfq	Jl. Raya Banjaran No. 10	2024-04-02 13:42:34.310089	\N
\.


--
-- Name: order_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_order_id_seq', 11, true);


--
-- Name: menu menu_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_pk PRIMARY KEY (menu_id);


--
-- Name: orders order_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_pk PRIMARY KEY (order_id);


--
-- Name: restaurant restaurant_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant
    ADD CONSTRAINT restaurant_pk PRIMARY KEY (resto_id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- Name: menu menu_restaurant_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_restaurant_fk FOREIGN KEY (resto_id) REFERENCES public.restaurant(resto_id);


--
-- Name: orders order_menu_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_menu_fk FOREIGN KEY (menu_id) REFERENCES public.menu(menu_id);


--
-- Name: orders order_restaurant_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_restaurant_fk FOREIGN KEY (resto_id) REFERENCES public.restaurant(resto_id);


--
-- Name: orders order_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT order_users_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- PostgreSQL database dump complete
--

