-- Table: public.tbl_user_payment_method
-- DROP TABLE public.tbl_user_payment_method;
CREATE TABLE public.tbl_user_payment_method
(
    col_id character varying(54) COLLATE pg_catalog."default" NOT NULL,
    col_username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_pmethod_id character varying(3) COLLATE pg_catalog."default" NOT NULL,
    col_balance numeric(18,6) NOT NULL,
    CONSTRAINT tbl_user_payment_method_pk PRIMARY KEY (col_id)
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_user_payment_method
    IS '"User Payment Methods" entity';
    
--------------------------------------------------------------------------------------

-- Table: public.tbl_evoucher_purchase
-- DROP TABLE public.tbl_evoucher_purchase;
CREATE TABLE public.tbl_evoucher_purchase
(
    col_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    col_evoucher_def_id character varying(8) COLLATE pg_catalog."default" NOT NULL,
    col_only_me_usage_count numeric(10,0) NOT NULL,
    col_gift_to_others_count numeric(10,0) NOT NULL,
    col_paid_method character(54) COLLATE pg_catalog."default" NOT NULL,
    col_cost numeric(18,6) NOT NULL,
    col_discount numeric(18,6) NOT NULL,
    col_purchase_datetime timestamp with time zone NOT NULL,
    col_purchase_by character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tbl_evoucher_purchase_pk PRIMARY KEY (col_id),
    CONSTRAINT col_paid_method_fk FOREIGN KEY (col_paid_method)
        REFERENCES public.tbl_user_payment_method (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_evoucher_purchase
    IS '"EVoucher Purchase" entity';
    
--------------------------------------------------------------------------------------

CREATE TABLE public.tbl_evoucher_owner
(
    col_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    col_ep_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    col_owner_ph_number character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_evoucher_count numeric(10,0) NOT NULL,
    col_for_self boolean NOT NULL,
    CONSTRAINT tbl_evoucher_owner_pk PRIMARY KEY (col_id),
    CONSTRAINT col_ep_id_fk FOREIGN KEY (col_ep_id)
        REFERENCES public.tbl_evoucher_purchase (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_evoucher_owner
    IS '"EVoucher Owner" entity';

--------------------------------------------------------------------------------------

-- Table: public.tbl_evoucher
-- DROP TABLE public.tbl_evoucher;
CREATE TABLE public.tbl_evoucher
(
    col_promo_code character varying(11) COLLATE pg_catalog."default" NOT NULL,
    col_ep_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    col_owner_ph_number character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_qr_codefile_location character varying(300) COLLATE pg_catalog."default" NOT NULL,
    col_gen_datetime timestamp with time zone NOT NULL,
    col_expiry_date timestamp with time zone NOT NULL,
    CONSTRAINT tbl_evoucher_pk PRIMARY KEY (col_promo_code),
    CONSTRAINT col_ep_id_fk FOREIGN KEY (col_ep_id)
        REFERENCES public.tbl_evoucher_purchase (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_evoucher
    IS '"EVoucher" entity';

--------------------------------------------------------------------------------------

-- Table: public.tbl_evoucher_task
-- DROP TABLE public.tbl_evoucher_task;
CREATE TABLE public.tbl_evoucher_task
(
    col_eo_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    col_ep_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    col_is_task_complete boolean NOT NULL,
    col_creation_datetime timestamp with time zone NOT NULL,
    col_complete_datetime timestamp with time zone,
    CONSTRAINT tbl_evoucher_task_pk PRIMARY KEY (col_eo_id),
    CONSTRAINT col_eo_id_fk FOREIGN KEY (col_eo_id)
        REFERENCES public.tbl_evoucher_owner (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT col_ep_id_fk FOREIGN KEY (col_ep_id)
        REFERENCES public.tbl_evoucher_purchase (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_evoucher_task
    IS '"Evoucher Task" Entity';

--------------------------------------------------------------------------------------