ALTER TABLE DocumentLibraryDaily ADD COLUMN impressions INT64;

UPDATE DocumentLibraryDaily SET impressions = previews WHERE previews IS NOT NULL;