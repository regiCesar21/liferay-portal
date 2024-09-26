UPDATE
	BlogDaily
SET
	assetTitle = REPLACE(REPLACE(assetTitle, '\u200B', ''), '\uFEFF', ''),
	pageTitle = REPLACE(REPLACE(pageTitle, '\u200B', ''), '\uFEFF', '')
WHERE
	1=1;

UPDATE
	BQEvent
SET
	title = REPLACE(REPLACE(title, '\u200B', ''), '\uFEFF', ''), 
	assetTitle = REPLACE(REPLACE(assetTitle, '\u200B', ''), '\uFEFF', '')
WHERE
	1=1;

UPDATE
	DocumentLibraryDaily
SET
	assetTitle = REPLACE(REPLACE(assetTitle, '\u200B', ''), '\uFEFF', ''),
	pageTitle = REPLACE(REPLACE(pageTitle, '\u200B', ''), '\uFEFF', '')
WHERE
	1=1;

UPDATE
	FormDaily
SET
	assetTitle = REPLACE(REPLACE(assetTitle, '\u200B', ''), '\uFEFF', ''),
	pageTitle = REPLACE(REPLACE(pageTitle, '\u200B', ''), '\uFEFF', '')
WHERE
	1=1;

UPDATE
	JournalDaily
SET
	assetTitle = REPLACE(REPLACE(assetTitle, '\u200B', ''), '\uFEFF', ''),
	pageTitle = REPLACE(REPLACE(pageTitle, '\u200B', ''), '\uFEFF', '')
WHERE
	1=1;

UPDATE
	PageDaily
 SET 
	title = REPLACE(REPLACE(title, '\u200B', ''), '\uFEFF', '')
WHERE
	1=1;