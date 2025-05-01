ALTER TABLE DocumentLibraryDaily ADD COLUMN impressions INT64;

ALTER TABLE Event ADD COLUMN externalReferenceCode STRING;
ALTER TABLE Event ADD COLUMN objectType STRING;

UPDATE DocumentLibraryDaily SET impressions = previews WHERE previews IS NOT NULL;