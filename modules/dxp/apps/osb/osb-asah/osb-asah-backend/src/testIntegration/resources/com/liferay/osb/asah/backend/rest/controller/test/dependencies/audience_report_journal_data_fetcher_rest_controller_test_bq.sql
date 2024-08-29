INSERT INTO MembershipChange (identitiesCount, segmentId) VALUES (2, 24680);

INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_b', 'individual_b', 24680);
INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_c', 'individual_c', 24680);

INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, userId, views) VALUES ('Journal1', 'Web Content Title 1', 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 'identity_a', 1);
INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, userId, views) VALUES ('Journal1', 'Web Content Title 1', 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 'identity_b', 1);
INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, userId, views) VALUES ('Journal2', 'Web Content Title 2', 12345, TIMESTAMP(DATETIME_TRUNC(timestamp '${today-4d}', HOUR)), 'identity_c', 1);

INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_a', 'individual_a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_b', 'individual_b');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_c', 'individual_c');

INSERT INTO Individual (emailAddress, id) VALUES ('individual.a@liferay.com', 'individual_a');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.b@liferay.com', 'individual_b');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.c@liferay.com', 'individual_c');