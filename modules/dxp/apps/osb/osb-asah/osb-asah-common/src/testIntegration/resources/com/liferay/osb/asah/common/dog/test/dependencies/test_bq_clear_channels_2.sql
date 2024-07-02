INSERT INTO BlogDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, timestamp '${now-2d}', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, timestamp '${now-6d}', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, timestamp '${now-7d}', 1);

INSERT INTO DocumentLibraryDaily (assetId, canonicalUrl, channelId, eventDate, previews) VALUES ('e131fabc', 'https://www.beryl.com/delivery', 1, timestamp '${today-2d}', 1);
INSERT INTO DocumentLibraryDaily (assetId, canonicalUrl, channelId, eventDate, previews) VALUES ('e131fabc', 'https://www.beryl.com/delivery', 1, timestamp '${today-6d}', 1);
INSERT INTO DocumentLibraryDaily (assetId, canonicalUrl, channelId, eventDate, previews) VALUES ('e131fabc', 'https://www.beryl.com/delivery', 1, timestamp '${today-8d}', 1);

INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-1d}', 'testEvent1', '1', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944213421', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-1d}', 'testEvent2', '2', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215919', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-2d}', 'testEvent2', '3', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215920', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-2d}', 'testEvent2', '4', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215922', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-5d}', 'testEvent2', '5', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215923', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-9d}', 'testEvent2', '6', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215924', '2');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-12d}', 'testEvent2', '7', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215925', '1');
INSERT INTO Event (applicationId, channelId, eventDate, eventId, id, properties, sessionId, userId) VALUES ('CustomEvent', 1, timestamp '${today-14d}', 'testEvent2', '8', ARRAY<STRUCT<name STRING, value STRING>> [], '366909399944215926', '2');

INSERT INTO FormDaily (assetId, browserName, channelId, eventDate, views) VALUES ('e131fabc', 'Chrome', 1, timestamp '${now-2d}', 2);
INSERT INTO FormDaily (assetId, browserName, channelId, eventDate, views) VALUES ('e131fabc', 'Chrome', 1, timestamp '${now-5d}', 2);
INSERT INTO FormDaily (assetId, browserName, channelId, eventDate, views) VALUES ('e131fabc', 'Chrome', 1, timestamp '${now-8d}', 2);

INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('http://www.liferay.com/productize', 1, '337984659206412898', 'productize best-of-breed web services', 'productize best-of-breed web services', 1);

INSERT INTO IdentityInterestScore(channelId, identityId, interestScore, keyword, recordedDate) VALUES(1, '1', 1.23, 'clicks-and-mortar e-tailers', DATE(timestamp '${now-1d}'));
INSERT INTO IdentityInterestScore(channelId, identityId, interestScore, keyword, recordedDate) VALUES(1, '1', 1.23, 'clicks-and-mortar e-tailers', DATE(timestamp '${now-6d}'));
INSERT INTO IdentityInterestScore(channelId, identityId, interestScore, keyword, recordedDate) VALUES(1, '1', 1.23, 'clicks-and-mortar e-tailers', DATE(timestamp '${now-11d}'));

INSERT INTO JournalDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, timestamp '${now-1d}', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, timestamp '${now-6d}', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, views) VALUES ('e131fabc', 1, timestamp '${now-10d}', 1);

INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, views) VALUES ('https://www.beryl.com/delivery', 1, timestamp '${today-1d}', 'Delivery', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, views) VALUES ('https://www.beryl.com/delivery', 1, timestamp '${today-7d}', 'Delivery', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, views) VALUES ('https://www.beryl.com/delivery', 1, timestamp '${today-9d}', 'Delivery', 1);

INSERT INTO Session (channelId, id, sessionStart) VALUES (1, '366909399944213421', timestamp '${today-2d}');
INSERT INTO Session (channelId, id, sessionStart) VALUES (3, '366909399944215919', timestamp '${today-9d}');

INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (1, TRUE, 'compelling action-items', DATE(timestamp  '${today-1d}'), '366909399944213421');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (1, TRUE, 'compelling action-items', DATE(timestamp  '${today-10d}'), '366909399944215919');