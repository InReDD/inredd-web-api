CREATE TABLE invite_request (
  id             BIGSERIAL PRIMARY KEY,
  email          VARCHAR(45) NOT NULL,
  group_id       BIGINT       NOT NULL REFERENCES groups(id_groups),
  invite_token   VARCHAR(64)  NOT NULL UNIQUE,
  created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  expires_at     TIMESTAMP WITH TIME ZONE NOT NULL,
  consumed_at    TIMESTAMP WITH TIME ZONE NULL
);