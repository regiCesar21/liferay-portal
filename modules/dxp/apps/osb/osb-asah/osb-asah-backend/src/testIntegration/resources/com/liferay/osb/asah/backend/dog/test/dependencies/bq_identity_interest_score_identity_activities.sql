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

INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790569167317525", true, 2.614959778036198, "javascript", DATE("${now}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790569167317525", true, 1.7676619176489945, "clicks-and-mortar e-tailers", DATE("${now}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790569167317525", true, 1.7676619176489945, "compelling metrics", DATE("${now}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790572309620075", true, 1.7676619176489945, "clicks-and-mortar e-tailers", DATE("${now}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790572309620075", true, 2.1041341542702074, "compelling metrics", DATE("${now}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790572703144534", true, 2.1041341542702074, "compelling metrics", DATE("${now-60d}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790575409131096", true, 0.7702225204735745, "javascript", DATE("${now-90d}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (1, "374790575409131096", true, 2.1041341542702074, "compelling metrics", DATE("${now-90d}"));
INSERT INTO IdentityInterestScore (channelId, identityId, interested, interestScore, keyword, recordedDate) VALUES (2, "386190546467211094", true, 1.4546849849874945, "sales", DATE("2019-05-15T00:00:00.000Z"));