INSERT INTO MembershipChange (identitiesCount, segmentId) VALUES (2, 24680);

INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_b', 'individual_b', 24680);
INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_c', 'individual_c', 24680);

INSERT INTO DocumentLibraryDaily (assetId, assetTitle, canonicalUrl, downloads, channelId, eventDate, pageTitle, previews, userId) VALUES ('DocumentLibrary1', 'Document 1', 'https://www.beryl.com/documents/document-1', 1, 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 'Document Page 1', 1, 'identity_a');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, canonicalUrl, downloads, channelId, eventDate, pageTitle, previews, userId) VALUES ('DocumentLibrary1', 'Document 1', 'https://www.beryl.com/documents/document-1', 1, 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 'Document Page 1', 1, 'identity_b');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, canonicalUrl, downloads, channelId, eventDate, pageTitle, previews, userId) VALUES ('DocumentLibrary2', 'Document 2', 'https://www.beryl.com/documents/document-2', 1, 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 'Document Page 2', 1, 'identity_c');

INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_a', 'individual_a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_b', 'individual_b');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_c', 'individual_c');

INSERT INTO Individual (emailAddress, id) VALUES ('individual.a@liferay.com', 'individual_a');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.b@liferay.com', 'individual_b');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.c@liferay.com', 'individual_c');