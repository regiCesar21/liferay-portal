INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_a', 'individual_a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_b', 'individual_b');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_c', 'individual_c');

INSERT INTO Individual (emailAddress, id) VALUES ('individual.a@liferay.com', 'individual_a');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.b@liferay.com', 'individual_b');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.c@liferay.com', 'individual_c');

INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_b', 'individual_b', 24680);
INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_c', 'individual_c', 24680);

INSERT INTO MembershipChange (identitiesCount, segmentId) VALUES (2, 24680);

INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.beryl.com/page-1', 12345, TIMESTAMP '${today-4d}', 'Page Title 1', 'identity_a', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.beryl.com/page-1', 12345, TIMESTAMP '${today-4d}', 'Page Title 1', 'identity_b', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.beryl.com/page-2', 12345, TIMESTAMP '${today-4d}', 'Page Title 2', 'identity_c', 1);