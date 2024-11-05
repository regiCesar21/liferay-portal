INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', 1, DATETIME_TRUNC(timestamp '${now}', HOUR), 'webContentViewed', '1', '374790569167317525');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', 1, DATETIME_TRUNC(timestamp '${now}', HOUR), 'webContentViewed', '2', '374790572309620075');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', 1, DATETIME_TRUNC(timestamp '${now-60d}', HOUR), 'webContentViewed', '3', '374790572703144534');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', 1, DATETIME_TRUNC(timestamp '${now-90d}', HOUR), 'webContentViewed', '4', '374790575409131096');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', 1, DATETIME_TRUNC(timestamp '${now}', HOUR), 'webContentViewed', '1', '374790575409131097');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', 1, DATETIME_TRUNC(timestamp '${now}', HOUR), 'webContentViewed', '1', '374790575409131098');

INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '374790569167317525');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '374790572309620075');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '374790572703144534');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '374790575409131096');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '386190546467211094');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '374790575409131097');
INSERT INTO IdentityActivitySummary (channelId, identityId) VALUES (1, '374790575409131098');