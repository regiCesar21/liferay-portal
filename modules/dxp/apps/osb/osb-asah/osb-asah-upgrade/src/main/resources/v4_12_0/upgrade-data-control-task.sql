ALTER TABLE DataControlTask ADD COLUMN emailAddresses TEXT[];

UPDATE DataControlTask SET emailAddresses = ARRAY[emailAddress] WHERE emailAddress IS NOT null;