toc.dat                                                                                             0000600 0004000 0002000 00000012063 13513326735 0014451 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        PGDMP       %    :                w        
   Luxprinter    11.3    11.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                    0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false                    0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false                    1262    16393 
   Luxprinter    DATABASE     �   CREATE DATABASE "Luxprinter" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Polish_Poland.1250' LC_CTYPE = 'Polish_Poland.1250';
    DROP DATABASE "Luxprinter";
             postgres    false         �            1259    16660    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public       postgres    false         �            1259    16877    location    TABLE     �   CREATE TABLE public.location (
    id bigint NOT NULL,
    firsttwooctetsipaddress character varying(20),
    namelocation character varying(100)
);
    DROP TABLE public.location;
       public         postgres    false         �            1259    16875    location_id_seq    SEQUENCE     x   CREATE SEQUENCE public.location_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.location_id_seq;
       public       postgres    false    199                    0    0    location_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.location_id_seq OWNED BY public.location.id;
            public       postgres    false    198         �            1259    16897    print    TABLE     
  CREATE TABLE public.print (
    id bigint NOT NULL,
    owner character varying(100) NOT NULL,
    namedocument character varying(100) NOT NULL,
    numberpages integer NOT NULL,
    dateprint timestamp without time zone NOT NULL,
    id_location bigint NOT NULL
);
    DROP TABLE public.print;
       public         postgres    false         �            1259    16740    printer    TABLE     j   CREATE TABLE public.printer (
    id bigint NOT NULL,
    serialnumber character varying(100) NOT NULL
);
    DROP TABLE public.printer;
       public         postgres    false         �
           2604    16880    location id    DEFAULT     j   ALTER TABLE ONLY public.location ALTER COLUMN id SET DEFAULT nextval('public.location_id_seq'::regclass);
 :   ALTER TABLE public.location ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    198    199    199                   0    16877    location 
   TABLE DATA               M   COPY public.location (id, firsttwooctetsipaddress, namelocation) FROM stdin;
    public       postgres    false    199       2827.dat           0    16897    print 
   TABLE DATA               ]   COPY public.print (id, owner, namedocument, numberpages, dateprint, id_location) FROM stdin;
    public       postgres    false    200       2828.dat 	          0    16740    printer 
   TABLE DATA               3   COPY public.printer (id, serialnumber) FROM stdin;
    public       postgres    false    197       2825.dat            0    0    hibernate_sequence    SEQUENCE SET     B   SELECT pg_catalog.setval('public.hibernate_sequence', 396, true);
            public       postgres    false    196                    0    0    location_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.location_id_seq', 2, true);
            public       postgres    false    198         �
           2606    16882    location location_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.location DROP CONSTRAINT location_pkey;
       public         postgres    false    199         �
           2606    16901    print print_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.print
    ADD CONSTRAINT print_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.print DROP CONSTRAINT print_pkey;
       public         postgres    false    200         �
           2606    16744    printer printer_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.printer
    ADD CONSTRAINT printer_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.printer DROP CONSTRAINT printer_pkey;
       public         postgres    false    197         �
           2606    16902    print print_id_location_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.print
    ADD CONSTRAINT print_id_location_fkey FOREIGN KEY (id_location) REFERENCES public.location(id);
 F   ALTER TABLE ONLY public.print DROP CONSTRAINT print_id_location_fkey;
       public       postgres    false    199    200    2699                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     2827.dat                                                                                            0000600 0004000 0002000 00000000621 13513326735 0014263 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	10.1.	Radziwiłowska
2	10.2.	Królewska
3	10.3.	Zwycięska (ZONE)
4	10.4.	Laboratorium
5	10.5.	Księgowość
6	10.6.	Koncertowa
7	10.7.	Krasińskiego (Estetyczna/Mama)
8	10.10.	Karmelicka
9	10.11.	Biłgoraj 18
10	10.12.	Chełm
11	10.13.	Biłgoraj 50
12	10.14.	Kraśnik
13	10.15.	Zamość
14	10.16.	Krasnystaw
15	10.20.	Samodzielne punkty pobrań
16	10.30.	Użytkownicy zewnętrzni
395	10.0.	\N
\.


                                                                                                               2828.dat                                                                                            0000600 0004000 0002000 00000000744 13513326735 0014272 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        388	t.rozaliuk	Stronatestowa	1	2019-07-16 10:49:46.623063	8
389	t.rozaliuk	Stronatestowa	1	2019-07-16 10:49:57.478318	8
390	t.rozaliuk	Stronatestowa	1	2019-07-16 10:50:08.042487	8
391	t.rozaliuk	Stronatestowa	1	2019-07-16 10:50:18.595652	8
392	t.rozaliuk	Stronatestowa	1	2019-07-16 10:50:18.595652	8
393	t.rozaliuk	Stronatestowa	1	2019-07-16 10:50:29.30674	8
394	t.rozaliuk	Stronatestowa	1	2019-07-16 10:50:39.840793	8
396	t.rozaliuk	Stronatestowa	1	2019-07-16 12:34:11.468918	8
\.


                            2825.dat                                                                                            0000600 0004000 0002000 00000000005 13513326735 0014255 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        \.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           restore.sql                                                                                         0000600 0004000 0002000 00000011443 13513326735 0015377 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        --
-- NOTE:
--
-- File paths need to be edited. Search for $$PATH$$ and
-- replace it with the path to the directory containing
-- the extracted data files.
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3
-- Dumped by pg_dump version 11.3

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

DROP DATABASE "Luxprinter";
--
-- Name: Luxprinter; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "Luxprinter" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Polish_Poland.1250' LC_CTYPE = 'Polish_Poland.1250';


ALTER DATABASE "Luxprinter" OWNER TO postgres;

\connect "Luxprinter"

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
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: location; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.location (
    id bigint NOT NULL,
    firsttwooctetsipaddress character varying(20),
    namelocation character varying(100)
);


ALTER TABLE public.location OWNER TO postgres;

--
-- Name: location_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.location_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.location_id_seq OWNER TO postgres;

--
-- Name: location_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.location_id_seq OWNED BY public.location.id;


--
-- Name: print; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.print (
    id bigint NOT NULL,
    owner character varying(100) NOT NULL,
    namedocument character varying(100) NOT NULL,
    numberpages integer NOT NULL,
    dateprint timestamp without time zone NOT NULL,
    id_location bigint NOT NULL
);


ALTER TABLE public.print OWNER TO postgres;

--
-- Name: printer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.printer (
    id bigint NOT NULL,
    serialnumber character varying(100) NOT NULL
);


ALTER TABLE public.printer OWNER TO postgres;

--
-- Name: location id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.location ALTER COLUMN id SET DEFAULT nextval('public.location_id_seq'::regclass);


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.location (id, firsttwooctetsipaddress, namelocation) FROM stdin;
\.
COPY public.location (id, firsttwooctetsipaddress, namelocation) FROM '$$PATH$$/2827.dat';

--
-- Data for Name: print; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.print (id, owner, namedocument, numberpages, dateprint, id_location) FROM stdin;
\.
COPY public.print (id, owner, namedocument, numberpages, dateprint, id_location) FROM '$$PATH$$/2828.dat';

--
-- Data for Name: printer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.printer (id, serialnumber) FROM stdin;
\.
COPY public.printer (id, serialnumber) FROM '$$PATH$$/2825.dat';

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 396, true);


--
-- Name: location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.location_id_seq', 2, true);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- Name: print print_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.print
    ADD CONSTRAINT print_pkey PRIMARY KEY (id);


--
-- Name: printer printer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.printer
    ADD CONSTRAINT printer_pkey PRIMARY KEY (id);


--
-- Name: print print_id_location_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.print
    ADD CONSTRAINT print_id_location_fkey FOREIGN KEY (id_location) REFERENCES public.location(id);


--
-- PostgreSQL database dump complete
--

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             