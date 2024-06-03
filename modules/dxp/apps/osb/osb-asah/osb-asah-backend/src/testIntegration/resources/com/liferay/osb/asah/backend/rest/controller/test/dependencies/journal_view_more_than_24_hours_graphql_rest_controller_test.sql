INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '1', 'Journal 1', 1, TIMESTAMP '${now-2h}', 'webContentViewed', '1', '1');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '2', 'Journal 2', 1, TIMESTAMP '${now-4h}', 'webContentViewed', '2', '2');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '3', 'Journal 3', 1, TIMESTAMP '${now-3h}', 'webContentViewed', '3', '3');
INSERT INTO Event (applicationId, assetId, assetTitle, channelId, eventDate, eventId, id, userId) VALUES ('WebContent', '4', 'Journal 4', 1, TIMESTAMP '${now-1h}', 'webContentViewed', '4', '4');

INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, views) VALUES ('5', 'Journal 5', 1, TIMESTAMP '${today-1d}', 5);
INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, views) VALUES ('6', 'Journal 6', 1, TIMESTAMP '${today-3d}', 6);
INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, views) VALUES ('7', 'Journal 7', 1, TIMESTAMP '${today-2d}', 7);
INSERT INTO JournalDaily (assetId, assetTitle, channelId, eventDate, views) VALUES ('8', 'Journal 8', 1, TIMESTAMP '${today-6d}', 8);