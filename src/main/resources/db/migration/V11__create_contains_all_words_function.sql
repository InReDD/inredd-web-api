CREATE OR REPLACE FUNCTION contains_all_words(text, text) RETURNS boolean AS $$
DECLARE
  words text[];
  word text;
BEGIN
  words := string_to_array(lower($2), ' ');
  FOREACH word IN ARRAY words LOOP
    IF position(word in lower($1)) = 0 THEN
      RETURN false;
    END IF;
  END LOOP;
  RETURN true;
END;
$$ LANGUAGE plpgsql;