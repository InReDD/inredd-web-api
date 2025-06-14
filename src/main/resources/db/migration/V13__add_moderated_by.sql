-- Adiciona a coluna moderated_by em access_request
ALTER TABLE access_request
    ADD COLUMN moderated_by BIGINT NULL;

-- Cria a constraint de chave estrangeira
ALTER TABLE access_request
    ADD CONSTRAINT fk_accessreq_moderator FOREIGN KEY (moderated_by)
        REFERENCES "user"(id_user);