
CREATE TABLE IF NOT EXISTS post (
	id uuid NOT NULL,
	content varchar(777) NULL,
	type varchar(50) NULL,
	original_post_id uuid,
	CONSTRAINT post_pkey PRIMARY KEY (id)
);

ALTER TABLE public.post ADD CONSTRAINT post_fkey FOREIGN KEY (original_post_id) REFERENCES public.post(id);