UPDATE AuditEvent SET userId='0' WHERE userId IS NULL OR userId !~ '^[0-9]+$';

ALTER TABLE AuditEvent ALTER COLUMN userId TYPE BIGINT USING userId::BIGINT;
