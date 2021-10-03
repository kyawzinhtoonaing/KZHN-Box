-- Table: public.tbl_job
-- DROP TABLE public.tbl_job;
CREATE TABLE public.tbl_job
(
    col_id character varying(21) COLLATE pg_catalog."default" NOT NULL,
    col_file1name character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_file2name character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_status character(1) COLLATE pg_catalog."default" NOT NULL,
    col_creation_datetime timestamp with time zone NOT NULL,
    col_started_datetime timestamp with time zone,
    col_finished_datetime timestamp with time zone,
    CONSTRAINT tbl_job_pk PRIMARY KEY (col_id)
)

TABLESPACE pg_default;

ALTER TABLE public.tbl_job
    OWNER to postgres;

COMMENT ON COLUMN public.tbl_job.col_id
    IS 'jid format
> {yyyymmddhhmmssmi}{2nd part of UUID}';

COMMENT ON COLUMN public.tbl_job.col_status
    IS 'status predefined value
> 0 (not started)
> 1 (in progress)
> 2 (finished)';

--------------------------------------------------------------------------------------

-- Table: public.tbl_job_report_summary
-- DROP TABLE public.tbl_job_report_summary;
CREATE TABLE public.tbl_job_report_summary
(
    col_id character varying(21) COLLATE pg_catalog."default" NOT NULL,
    col_file1name character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_file1_line_count numeric(10,0) NOT NULL DEFAULT 0,
    col_file1_match_count numeric(10,0) NOT NULL DEFAULT 0,
    col_file1_unmatch_count numeric(10,0) NOT NULL DEFAULT 0,
    col_file2name character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_file2_line_count numeric(10,0) NOT NULL DEFAULT 0,
    col_file2_match_count numeric(10,0) NOT NULL DEFAULT 0,
    col_file2_unmatch_count numeric(10,0) NOT NULL DEFAULT 0,
    col_all_match boolean NOT NULL,
    col_fm_count numeric(10,0) NOT NULL DEFAULT 0,
    col_mnmm_count numeric(10,0) NOT NULL DEFAULT 0,
    col_ftmmht_count numeric(10,0) NOT NULL DEFAULT 0,
    col_ftmmlt_count numeric(10,0) NOT NULL DEFAULT 0,
    col_srmmlc_count numeric(10,0) NOT NULL DEFAULT 0,
    col_fmm_count numeric(10,0) NOT NULL DEFAULT 0,
    CONSTRAINT tbl_job_report_summary_pk PRIMARY KEY (col_id),
    CONSTRAINT col_id_fk FOREIGN KEY (col_id)
        REFERENCES public.tbl_job (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE public.tbl_job_report_summary
    OWNER to postgres;
    
--------------------------------------------------------------------------------------

-- Table: public.tbl_job_tx
-- DROP TABLE public.tbl_job_tx;
CREATE TABLE public.tbl_job_tx
(
    col_jid character varying(21) COLLATE pg_catalog."default" NOT NULL,
    col_txid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    col_filename character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_lineno numeric(10,0) NOT NULL,
    col_pf_name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    col_tx_date character varying(34) COLLATE pg_catalog."default" NOT NULL,
    col_tx_amt character varying COLLATE pg_catalog."default" NOT NULL,
    col_tx_nrtive character varying(300) COLLATE pg_catalog."default" NOT NULL,
    col_tx_dscpt character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_tx_type character(2) COLLATE pg_catalog."default" NOT NULL,
    col_wallet_ref character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tbl_job_tx_pk PRIMARY KEY (col_jid, col_txid, col_filename, col_lineno),
    CONSTRAINT col_jid_fk FOREIGN KEY (col_jid)
        REFERENCES public.tbl_job (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tbl_job_tx
    OWNER to postgres;

COMMENT ON COLUMN public.tbl_job_tx.col_txid
    IS 'TransactionID';

COMMENT ON COLUMN public.tbl_job_tx.col_pf_name
    IS 'ProfileName';

COMMENT ON COLUMN public.tbl_job_tx.col_tx_date
    IS 'TransactionDate';

COMMENT ON COLUMN public.tbl_job_tx.col_tx_amt
    IS 'TransactionAmount';

COMMENT ON COLUMN public.tbl_job_tx.col_tx_nrtive
    IS 'TransactionNarrative';

COMMENT ON COLUMN public.tbl_job_tx.col_tx_dscpt
    IS 'TransactionDescription';

COMMENT ON COLUMN public.tbl_job_tx.col_tx_type
    IS 'TransactionType';

COMMENT ON COLUMN public.tbl_job_tx.col_wallet_ref
    IS 'WalletReference';
    
--------------------------------------------------------------------------------------

-- Table: public.tbl_job_tx_mismatch_report
-- DROP TABLE public.tbl_job_tx_mismatch_report;
CREATE TABLE public.tbl_job_tx_mismatch_report
(
    col_jid character varying(21) COLLATE pg_catalog."default" NOT NULL,
    col_txid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    col_filename character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_lineno numeric(10,0) NOT NULL,
    col_pf_name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    col_tx_date character varying(34) COLLATE pg_catalog."default" NOT NULL,
    col_tx_amt character varying COLLATE pg_catalog."default" NOT NULL,
    col_tx_nrtive character varying(300) COLLATE pg_catalog."default" NOT NULL,
    col_tx_dscpt character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_tx_type character(2) COLLATE pg_catalog."default" NOT NULL,
    col_wallet_ref character varying(100) COLLATE pg_catalog."default" NOT NULL,
    col_txinfo_matched boolean NOT NULL,
    col_txeinfo_matched boolean NOT NULL,
    col_txsinfo_matched boolean NOT NULL,
    CONSTRAINT tbl_job_tx_mismatch_report_pk PRIMARY KEY (col_jid, col_txid, col_filename, col_lineno),
    CONSTRAINT col_jid_fk FOREIGN KEY (col_jid)
        REFERENCES public.tbl_job (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tbl_job_tx_mismatch_report
    OWNER to postgres;

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_txid
    IS 'TransactionID';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_pf_name
    IS 'ProfileName';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_tx_date
    IS 'TransactionDate';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_tx_amt
    IS 'TransactionAmount';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_tx_nrtive
    IS 'TransactionNarrative';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_tx_dscpt
    IS 'TransactionDescription';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_tx_type
    IS 'TransactionType';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_wallet_ref
    IS 'WalletReference';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_txinfo_matched
    IS '> stands for Transaction Information Matched
> true (if all transaction information are matched)
> false (if there are mismatches)';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_txeinfo_matched
    IS '> stands for Transaction Event Information Matched
> true (if all transaction event information are matched)
> false (if there are mismatches) ';

COMMENT ON COLUMN public.tbl_job_tx_mismatch_report.col_txsinfo_matched
    IS '> stands for Transaction Supplement Information Matched
> true (if all transaction supplement information are matched)
> false (if there are mismatches) ';

--------------------------------------------------------------------------------------

-- Table: public.tbl_job_tx_broken_integrity_report
-- DROP TABLE public.tbl_job_tx_broken_integrity_report;
CREATE TABLE public.tbl_job_tx_broken_integrity_report
(
    col_jid character varying(21) COLLATE pg_catalog."default" NOT NULL,
    col_txid character varying(20) COLLATE pg_catalog."default" NOT NULL,
    col_filename character varying(500) COLLATE pg_catalog."default" NOT NULL,
    col_lineno numeric(10,0) NOT NULL,
    col_pf_name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    col_tx_date character varying(34) COLLATE pg_catalog."default" NOT NULL,
    col_tx_amt character varying COLLATE pg_catalog."default" NOT NULL,
    col_tx_nrtive character varying(300) COLLATE pg_catalog."default" NOT NULL,
    col_tx_dscpt character varying(50) COLLATE pg_catalog."default" NOT NULL,
    col_tx_type character(2) COLLATE pg_catalog."default" NOT NULL,
    col_wallet_ref character varying(100) COLLATE pg_catalog."default" NOT NULL,
    col_counterpart_lineno numeric(10,0) NOT NULL,
    CONSTRAINT tbl_job_tx_broken_integrity_report_pk PRIMARY KEY (col_jid, col_txid, col_filename, col_lineno),
    CONSTRAINT col_jid_fk FOREIGN KEY (col_jid)
        REFERENCES public.tbl_job (col_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tbl_job_tx_broken_integrity_report
    OWNER to postgres;

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_txid
    IS 'TransactionID';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_pf_name
    IS 'ProfileName';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_tx_date
    IS 'TransactionDate';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_tx_amt
    IS 'TransactionAmount';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_tx_nrtive
    IS 'TransactionNarrative';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_tx_dscpt
    IS 'TransactionDescription';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_tx_type
    IS 'TransactionType';

COMMENT ON COLUMN public.tbl_job_tx_broken_integrity_report.col_wallet_ref
    IS 'WalletReference';
    
--------------------------------------------------------------------------------------