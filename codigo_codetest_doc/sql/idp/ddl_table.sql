-- Table: public.tbl_mst_authority
-- DROP TABLE public.tbl_mst_authority;
CREATE TABLE public.tbl_mst_authority
(
    col_authority character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_display_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tbl_mst_authority_pk PRIMARY KEY (col_authority)
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_mst_authority
    IS '"Authority Master" entity';
    
--------------------------------------------------------------------------------------

-- Table: public.users
-- DROP TABLE public.users;
CREATE TABLE public.users
(
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    enabled boolean NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (username)
)
TABLESPACE pg_default;
COMMENT ON TABLE public.users
    IS '"User" Entity';

--------------------------------------------------------------------------------------

-- Table: public.authorities
-- DROP TABLE public.authorities;
CREATE TABLE public.authorities
(
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    authority character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT authority_fk FOREIGN KEY (authority)
        REFERENCES public.tbl_mst_authority (col_authority) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT username_fk FOREIGN KEY (username)
        REFERENCES public.users (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
COMMENT ON TABLE public.authorities
    IS '"Authorities" entity';
-- Index: ix_auth_username
-- DROP INDEX public.ix_auth_username;
CREATE UNIQUE INDEX ix_auth_username
    ON public.authorities USING btree
    (username COLLATE pg_catalog."default" ASC NULLS LAST)
    INCLUDE(authority)
    TABLESPACE pg_default;

--------------------------------------------------------------------------------------