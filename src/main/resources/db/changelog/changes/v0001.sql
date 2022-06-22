
CREATE TABLE IF NOT EXISTS post (
	id uuid NOT NULL,
	content varchar(255) NULL,
	CONSTRAINT post_pkey PRIMARY KEY (id)
);

