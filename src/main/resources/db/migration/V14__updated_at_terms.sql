ALTER TABLE terms_of_service
ADD COLUMN updated_at TIMESTAMP NULL;

ALTER TABLE privacy_policy
ADD COLUMN updated_at TIMESTAMP NULL;