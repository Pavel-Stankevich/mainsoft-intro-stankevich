DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA IF NOT EXISTS public;

DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigserial,
    password character varying(60)  NOT NULL,
    username character varying(64) NOT NULL,
    CONSTRAINT users_pk_id PRIMARY KEY (id),
    CONSTRAINT users_uk_username UNIQUE (username)
);

DROP TABLE IF EXISTS public.races;

CREATE TABLE IF NOT EXISTS public.races
(
    id bigserial,
    distance numeric(21,3) NOT NULL,
    duration time without time zone NOT NULL,
    start_time timestamp without time zone NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT races_pk_id PRIMARY KEY (id),
    CONSTRAINT races_fk_user FOREIGN KEY (user_id)
        REFERENCES public.users (id)
)
