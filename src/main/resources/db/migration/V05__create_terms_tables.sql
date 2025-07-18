CREATE TABLE IF NOT EXISTS terms_of_service (
  id_terms_of_service BIGSERIAL PRIMARY KEY,
  content             TEXT       NOT NULL,
  created_at          TIMESTAMP  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS privacy_policy (
  id_privacy_policy   BIGSERIAL PRIMARY KEY,
  content             TEXT       NOT NULL,
  created_at          TIMESTAMP  NOT NULL DEFAULT NOW()
);

INSERT INTO terms_of_service (content)
VALUES (
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
   Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
   Ut enim ad minim veniam, quis nostrud exercitation ullamco 
   laboris nisi ut aliquip ex ea commodo consequat. 
   Duis aute irure dolor in reprehenderit in voluptate velit 
   esse cillum dolore eu fugiat nulla pariatur. 
   Excepteur sint occaecat cupidatat non proident, sunt in culpa 
   qui officia deserunt mollit anim id est laborum.'
);

INSERT INTO privacy_policy (content)
VALUES (
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
   sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
   Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris 
   nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor 
   in reprehenderit in voluptate velit esse cillum dolore eu fugiat 
   nulla pariatur. Excepteur sint occaecat cupidatat non proident, 
   sunt in culpa qui officia deserunt mollit anim id est laborum.'
);