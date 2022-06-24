
CREATE TABLE IF NOT EXISTS post (
	id UUID NOT NULL,
	content VARCHAR(777) NULL,
	type VARCHAR(50) NULL,
	original_post_id UUID,
	user_id UUID,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	CONSTRAINT post_pkey PRIMARY KEY (id)
);

ALTER TABLE public.post ADD CONSTRAINT post_fkey FOREIGN KEY (original_post_id) REFERENCES public.post(id);

CREATE TABLE IF NOT EXISTS posterr_user (
	id UUID NOT NULL,
	username VARCHAR(14) NOT NULL UNIQUE,
	posts_amount BIGINT DEFAULT 0,
	daily_posts_amount INTEGER DEFAULT 0,
	last_post_date DATE,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);

INSERT INTO posterr_user (id, username) values ('d201e18b-83b0-4ea1-928d-81dee88eb8eb', 'user1');
INSERT INTO posterr_user (id, username) values ('a4ce0058-cd5d-456b-8f30-7fd85e3650d5', 'user2');
INSERT INTO posterr_user (id, username) values ('bf19618b-4794-4ec6-8dcb-40128fd0fc5a', 'user3');
INSERT INTO posterr_user (id, username) values ('5c6a3d59-c2aa-4b5e-a0ec-2f2563080a22', 'user4');