INSERT INTO BlogDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(TIMESTAMP '${now-6d}', HOUR)), 1);

INSERT INTO CustomAssetDaily (assetPrimaryKey, channelId, eventDate, views) VALUES ('77c27102e79cd05d7cd602b2b1d5c6f399fdcf2ed9a6b044388483b1cb122b32', 1, DATETIME_TRUNC(TIMESTAMP '${today-6d}', HOUR), 1);

INSERT INTO DocumentLibraryDaily (assetId, canonicalUrl, channelId, eventDate, impressions) VALUES ('e131fabc', 'https://www.beryl.com/delivery', 1, DATETIME_TRUNC(TIMESTAMP '${today-6d}', HOUR), 1);

INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, '2021-05-14', 'testEvent1', '1', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944213421', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 3, '2021-05-15', 'testEvent2', '2', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215 properties,919', '1');

INSERT INTO FormDaily (assetId, browserName, channelId, eventDate, views) VALUES ('e131fabc', 'Chrome', 1, DATETIME_TRUNC(TIMESTAMP '${now-5d}', HOUR), 2);

INSERT INTO IdentityActivitySummary (channelId, firstActivityDate, identityId) VALUES (1, TIMESTAMP '${today-6d}', '1');

INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('http://www.liferay.com/productize', 1, '337984659206412898', 'productize best-of-breed web services', 'productize best-of-breed web services', 1);

INSERT INTO IdentityInterestScore(channelId, identityId, interestScore, keyword, recordedDate) VALUES(1, '1', 1.23, 'clicks-and-mortar e-tailers', DATE('2019-05-15'));

INSERT INTO JournalDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(TIMESTAMP '${now-6d}', HOUR)), 1);

INSERT INTO Membership (channelId, createDate, identityId, individualId, segmentId) VALUES (1, TIMESTAMP '${now-6d}', '1', '1', 1);

INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, views) VALUES ('https://www.beryl.com/delivery', 1, DATETIME_TRUNC(TIMESTAMP '${today-6d}', HOUR), 'Delivery', 1);

INSERT INTO Session (channelId, id, sessionStart) VALUES (1, '366909399944213421', '2021-05-14');
INSERT INTO Session (channelId, id, sessionStart) VALUES (3, '366909399944215919', '2021-05-15');

INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (1, TRUE, 'compelling action-items', DATE(TIMESTAMP  '${today-400d}'), '1');