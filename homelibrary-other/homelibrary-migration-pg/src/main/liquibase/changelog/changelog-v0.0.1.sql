--liquibase formatted sql

--changeset ymelnikov:1 labels:v0.0.1

CREATE TABLE "eds" (
	"id" text primary key constraint eds_id_length_ctr check (length("id") < 64),
	"title" text constraint eds_title_length_ctr check (length(title) < 256),
	"author" text constraint eds_author_length_ctr check (length(title) < 256),
	"isbn" text constraint eds_isbn_length_ctr check (length(title) < 128),
	"year" text constraint eds_year_length_ctr check (length(title) < 6),
	"owner_id" text not null constraint eds_owner_id_length_ctr check (length(id) < 64),
	"lock" text not null constraint eds_lock_length_ctr check (length(id) < 64)
);

CREATE INDEX eds_owner_id_idx on "eds" using hash ("owner_id");

CREATE INDEX eds_isbn_id_idx on "eds" using hash ("isbn");

CREATE INDEX eds_author_id_idx on "eds" using hash ("author");