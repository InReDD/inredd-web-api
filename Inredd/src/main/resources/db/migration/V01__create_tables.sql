-- -----------------------------------------------------
-- Table "User"
-- -----------------------------------------------------
-- "User" is a reserved word in PostgreSQL, so we quote it.
CREATE TABLE IF NOT EXISTS "user" (
  id_user    BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(45),
  last_name  VARCHAR(45),
  email  VARCHAR(45),
  password VARCHAR(150) NOT NULL,
  active  BOOLEAN NOT NULL,
  contact VARCHAR(45)
);

-- -----------------------------------------------------
-- Table Address
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS address (
  id_address INT PRIMARY KEY,
  address   VARCHAR(45),
  country   VARCHAR(45),
  state     VARCHAR(45),
  id_user    INT NOT NULL,
  
  CONSTRAINT fk_address_user
    FOREIGN KEY (id_user)
    REFERENCES "user"(id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table Academic
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS academic (
  contact VARCHAR(50),
  id_user INT, 
  id_address INT,
  title VARCHAR(50),
  institution VARCHAR(255),
  lattes_id VARCHAR(255),
  profile_picture BYTEA,
  biography TEXT,

  CONSTRAINT fk_academic_user
    FOREIGN KEY (id_user)
    REFERENCES "user"(id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_academic_address
    FOREIGN KEY (id_address)
    REFERENCES Address(id_address)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table Groups
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS groups (
  id_groups INT NOT NULL,
  id_permissions INT,
  name VARCHAR(45),
  description VARCHAR(45),
  PRIMARY KEY (id_groups)
);

-- -----------------------------------------------------
-- Table Groups_has_User
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS groups_has_user (
  id_groups INT NOT NULL,
  id_user INT NOT NULL,

  PRIMARY KEY (id_groups, id_user),

  CONSTRAINT fk_groups_has_user_groups1
    FOREIGN KEY (id_groups)
    REFERENCES groups(id_groups)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_groups_has_user_user1
    FOREIGN KEY (id_user)
    REFERENCES "user"(id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table Permissions
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS permission (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_permission (
    id_user_permission BIGINT NOT NULL,
    id_permission BIGINT NOT NULL,
    PRIMARY KEY (id_user_permission, id_permission),
    FOREIGN KEY (id_user_permission) REFERENCES "user"(id_user),
    FOREIGN KEY (id_permission) REFERENCES permission(id)
);

-- -----------------------------------------------------
-- Table Paper
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS paper (
    id_paper         INT PRIMARY KEY,      -- from __key__.name
    url_doi    VARCHAR(255),                  -- from urlDOI
    publish_date VARCHAR(50),                 -- from publishDate (adjust type as needed)
    title      TEXT,                          -- from title
    authors    TEXT,                          -- from authors
    doi        VARCHAR(100),                  -- from DOI
    tags       TEXT[]                         -- from tags array
);

-- -----------------------------------------------------
-- Table Paper_has_User
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS paper_has_user (
  id_paper INT NOT NULL,
  id_user INT NOT NULL,

  PRIMARY KEY (id_paper, id_user),

  CONSTRAINT fk_paper_has_user_paper1
    FOREIGN KEY (id_paper)
    REFERENCES paper(id_paper)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  
  CONSTRAINT fk_paper_has_user_user1
    FOREIGN KEY (id_user)
    REFERENCES "user"(id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table Patients
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS patients (
  id_patients INT NOT NULL,
  name VARCHAR(45),
  birth_date TIMESTAMP,
  gender TEXT,
  PRIMARY KEY (id_patients)
);

-- -----------------------------------------------------
-- Table User_has_Patients
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS user_has_patients (
  id_patients INT NOT NULL,
  id_user INT NOT NULL,
  
  PRIMARY KEY (id_patients, id_user),
  
  CONSTRAINT fk_patients_has_user_patients1
    FOREIGN KEY (id_patients)
    REFERENCES patients(id_patients)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  
  CONSTRAINT fk_patients_has_user_user1
    FOREIGN KEY (id_user)
    REFERENCES "user"(id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table Registers
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS registers (
  id_registers INT NOT NULL,
  patients_id_patients INT NOT NULL,
  d2l_viewer_context JSON,
  d2l_text_context JSON,
  PRIMARY KEY (id_registers),
  CONSTRAINT fk_registers_patients1
    FOREIGN KEY (patients_id_patients)
    REFERENCES patients(id_patients)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table Acess
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS access (
  id_access INT NOT NULL,
  solution VARCHAR(45),
  status VARCHAR(45),
  created_at VARCHAR(45),
  moderated_at VARCHAR(45),
  justification VARCHAR(45),
  PRIMARY KEY (id_access)
);

-- -----------------------------------------------------
-- Table User_has_Acess
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS user_has_access (
  id_user INT NOT NULL,
  id_access INT NOT NULL,
  PRIMARY KEY (id_user, id_access),
  CONSTRAINT fk_user_has_access_user1
    FOREIGN KEY (id_user)
    REFERENCES "user"(id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_user_has_access_access1
    FOREIGN KEY (id_access)
    REFERENCES access(id_access)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);