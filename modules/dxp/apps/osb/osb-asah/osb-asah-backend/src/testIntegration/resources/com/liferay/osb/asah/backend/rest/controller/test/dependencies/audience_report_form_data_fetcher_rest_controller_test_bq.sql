INSERT INTO MembershipChange (identitiesCount, segmentId) VALUES (2, 24680);

INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_b', 'individual_b', 24680);
INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_c', 'individual_c', 24680);

INSERT INTO FormDaily (assetId, assetTitle, canonicalUrl, channelId, eventDate, submissions, userId) VALUES ('Form1', 'Form 1', 'https://www.beryl.com/forms/form-1', 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 1, 'identity_a');
INSERT INTO FormDaily (assetId, assetTitle, canonicalUrl, channelId, eventDate, submissions, userId) VALUES ('Form1', 'Form 1', 'https://www.beryl.com/forms/form-1', 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 1, 'identity_b');
INSERT INTO FormDaily (assetId, assetTitle, canonicalUrl, channelId, eventDate, submissions, userId) VALUES ('Form2', 'Form 2', 'https://www.beryl.com/forms/form-2', 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 1, 'identity_c');

INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_a', 'individual_a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_b', 'individual_b');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_c', 'individual_c');

INSERT INTO Individual (emailAddress, id) VALUES ('individual.a@liferay.com', 'individual_a');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.b@liferay.com', 'individual_b');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.c@liferay.com', 'individual_c');