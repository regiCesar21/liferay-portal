INSERT INTO BlogDaily (assetId, assetTitle, canonicalUrl, channelId, clicks, comments, eventDate, ratings, readTime, userId, views) VALUES ('Blog1', 'Blog 1', 'https://www.beryl.com/blogs/blog-1', 12345, 1, 1, TIMESTAMP '${today-4d}', 1, 1, 'identity_a', 1);
INSERT INTO BlogDaily (assetId, assetTitle, canonicalUrl, channelId, clicks, comments, eventDate, ratings, readTime, userId, views) VALUES ('Blog1', 'Blog 1', 'https://www.beryl.com/blogs/blog-1', 12345, 1, 1, TIMESTAMP '${today-4d}', 1, 1, 'identity_b', 1);
INSERT INTO BlogDaily (assetId, assetTitle, canonicalUrl, channelId, clicks, comments, eventDate, ratings, readTime, userId, views) VALUES ('Blog2', 'Blog 2', 'https://www.beryl.com/blogs/blog-2', 12345, 1, 1, TIMESTAMP '${today-4d}', 1, 1, 'identity_c', 1);

INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_a', 'individual_a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_b', 'individual_b');
INSERT INTO Identity_Raw (id, individualId) VALUES ('identity_c', 'individual_c');

INSERT INTO Individual (emailAddress, id) VALUES ('individual.a@liferay.com', 'individual_a');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.b@liferay.com', 'individual_b');
INSERT INTO Individual (emailAddress, id) VALUES ('individual.c@liferay.com', 'individual_c');

INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_b', 'individual_b', 24680);
INSERT INTO Membership (channelId, identityId, individualId, segmentId) VALUES (12345, 'identity_c', 'individual_c', 24680);

INSERT INTO MembershipChange (identitiesCount, segmentId) VALUES (2, 24680);