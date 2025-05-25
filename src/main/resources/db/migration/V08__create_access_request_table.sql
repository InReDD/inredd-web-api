CREATE TABLE IF NOT EXISTS access_request (
  id               BIGSERIAL PRIMARY KEY,
  email            VARCHAR(45) NOT NULL,
  first_name       VARCHAR(45) NOT NULL,
  solution         VARCHAR(20) NOT NULL,
  reason           TEXT,
  request_token    VARCHAR(64) UNIQUE        NOT NULL,
  expires_at       TIMESTAMP WITH TIME ZONE  NOT NULL,
  created_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  consumed_at      TIMESTAMP WITH TIME ZONE NULL,
  user_id          BIGINT NULL,
  CONSTRAINT fk_accessreq_user FOREIGN KEY (user_id) REFERENCES "user"(id_user)
);