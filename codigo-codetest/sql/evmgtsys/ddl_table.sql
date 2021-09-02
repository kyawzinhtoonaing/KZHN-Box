-- Table: public.tbl_evoucher_def
-- DROP TABLE public.tbl_evoucher_def;
CREATE TABLE public.tbl_evoucher_def
(
    col_id character varying(8) COLLATE pg_catalog."default" NOT NULL,
    col_title character varying(200) COLLATE pg_catalog."default" NOT NULL,
    col_description character varying(300) COLLATE pg_catalog."default",
    col_expiry_date timestamp with time zone NOT NULL,
    col_image_location character varying(300) COLLATE pg_catalog."default" NOT NULL,
    col_amount numeric(18,6) NOT NULL,
    col_quantity numeric(10,0) NOT NULL,
    col_buying_type character varying(1) COLLATE pg_catalog."default" NOT NULL,
    col_max_evoucher_limit numeric(10,0) NOT NULL,
    col_gift_per_user_limit numeric(10,0) NOT NULL,
    col_isactive boolean NOT NULL,
    col_created_by character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_created_on timestamp with time zone NOT NULL,
    col_modified_by character varying(50) COLLATE pg_catalog."default",
    col_modified_on timestamp with time zone,
    CONSTRAINT tbl_evoucher_def_pk PRIMARY KEY (col_id)
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_evoucher_def
    IS '"EVoucher Definition" entity';
    
--------------------------------------------------------------------------------------
    
-- Table: public.tbl_mst_payment_method
-- DROP TABLE public.tbl_mst_payment_method;
CREATE TABLE public.tbl_mst_payment_method
(
    col_id character varying(3) COLLATE pg_catalog."default" NOT NULL,
    col_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    col_description character varying(200) COLLATE pg_catalog."default",
    CONSTRAINT tbl_mst_payment_method_pk PRIMARY KEY (col_id)
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_mst_payment_method
    IS '"Payment Method Master" entity';
    
--------------------------------------------------------------------------------------
    
-- Table: public.tbl_payment_method_discount
-- DROP TABLE public.tbl_payment_method_discount;
CREATE TABLE public.tbl_payment_method_discount
(
    col_id character varying(12) COLLATE pg_catalog."default" NOT NULL,
    col_evoucher_def_id character varying(8) COLLATE pg_catalog."default" NOT NULL,
    col_pmethod_id character varying(3) COLLATE pg_catalog."default" NOT NULL,
    col_discount_percent numeric(3,0) NOT NULL,
    CONSTRAINT tbl_payment_method_discount_pk PRIMARY KEY (col_id),
    CONSTRAINT col_evoucher_def_id_fk FOREIGN KEY (col_evoucher_def_id)
        REFERENCES public.tbl_evoucher_def (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT col_pmethod_id_fk FOREIGN KEY (col_pmethod_id)
        REFERENCES public.tbl_mst_payment_method (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
COMMENT ON TABLE public.tbl_payment_method_discount
    IS '"Payment Method Discount" entity';
    
--------------------------------------------------------------------------------------

-- Table: public.tbl_buy_type
-- DROP TABLE public.tbl_buy_type;
-- CREATE TABLE public.tbl_buy_type
-- (
--     col_id character varying(10) COLLATE pg_catalog."default" NOT NULL,
--     col_evoucher_def_id character varying(8) COLLATE pg_catalog."default" NOT NULL,
--     col_buying_type character varying(1) COLLATE pg_catalog."default" NOT NULL,
--     col_max_evoucher_limit numeric(10,0) NOT NULL,
--     col_gift_per_user_limit numeric(10,0) NOT NULL,
--     CONSTRAINT tbl_buy_type_pk PRIMARY KEY (col_id),
--     CONSTRAINT col_evoucher_def_id_fk FOREIGN KEY (col_evoucher_def_id)
--         REFERENCES public.tbl_evoucher_def (col_id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
-- )
-- TABLESPACE pg_default;
-- COMMENT ON TABLE public.tbl_buy_type
--     IS '"Buy Type" entity';

--------------------------------------------------------------------------------------