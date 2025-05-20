CREATE TABLE IF NOT EXISTS group_permission (
  id_groups     BIGINT NOT NULL
    REFERENCES groups(id_groups) ON DELETE CASCADE,
  id_permission BIGINT NOT NULL
    REFERENCES permission(id)    ON DELETE CASCADE,
  PRIMARY KEY(id_groups, id_permission)
);