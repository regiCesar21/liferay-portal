INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '1', '1');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '2', '2');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '3', '2');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '4', '3');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '5', '3');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '6', '4');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Web Content 1', 1, TIMESTAMP '${today-1dT00:00:00.000Z}', 'webContentViewed', '7', '5');

INSERT INTO Identity_Raw (createDate, id) VALUES (TIMESTAMP '${today-1dT00:00:00.000Z}', '1');
INSERT INTO Identity_Raw (createDate, id) VALUES (TIMESTAMP '${today-1dT00:00:00.000Z}', '2');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (TIMESTAMP '${today-1dT00:00:00.000Z}', '3', '3');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (TIMESTAMP '${today-1dT00:00:00.000Z}', '4', '4');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (TIMESTAMP '${today-1dT00:00:00.000Z}', '5', '5');

INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '1');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '2');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '3');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '4');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '5');

INSERT INTO Individual (id) VALUES ('3');
INSERT INTO Individual (id) VALUES ('4');
INSERT INTO Individual (id) VALUES ('5');

INSERT INTO MembershipChange (channelId, createDate, identitiesCount, individualsCount, segmentId) VALUES (1, TIMESTAMP '${today-1dT00:00:00.000Z}', 5, 3, 1);