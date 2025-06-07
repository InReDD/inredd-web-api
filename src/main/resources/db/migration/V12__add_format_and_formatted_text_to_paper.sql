DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'paper_format') THEN
      CREATE TYPE paper_format AS ENUM (
         'BIBTEX',
         'RIS',
         'ENDNOTE',
         'OTHER'
      );
   END IF;
END$$;

ALTER TABLE paper
   ADD COLUMN IF NOT EXISTS format paper_format NOT NULL DEFAULT 'BIBTEX';

ALTER TABLE paper
   ADD COLUMN IF NOT EXISTS formatted_text TEXT;

ALTER TABLE paper
  ALTER COLUMN format TYPE TEXT
    USING format::text;