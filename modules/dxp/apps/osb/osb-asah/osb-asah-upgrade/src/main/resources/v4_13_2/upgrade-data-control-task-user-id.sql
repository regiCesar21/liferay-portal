UPDATE DataControlTask SET userId='0' WHERE userId IS NULL OR userId !~ '^[0-9]+$';

ALTER TABLE DataControlTask ALTER COLUMN userId TYPE BIGINT USING userId::BIGINT;
