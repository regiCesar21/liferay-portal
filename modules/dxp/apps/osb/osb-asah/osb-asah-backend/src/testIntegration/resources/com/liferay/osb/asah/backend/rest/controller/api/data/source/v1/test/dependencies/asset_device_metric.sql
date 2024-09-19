INSERT INTO BlogDaily (assetId, channelId, comments, deviceType, eventDate, userId, views) VALUES ('e131fabc', 1, 3, 'Desktop', TIMESTAMP(DATETIME_TRUNC(timestamp '${now-4d}', HOUR)), '2', 2);
INSERT INTO BlogDaily (assetId, channelId, comments, deviceType, eventDate, userId, views) VALUES ('e131fabc', 1, 5, 'Tablet', TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), '1', 1);

INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, deviceType, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 'Tablet', 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), 7, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, deviceType, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 4, 'Mobile', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 4, '2');

INSERT INTO Identity_Raw (createDate, id) VALUES (timestamp '${now-8h}', '1');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (timestamp '${now-26h}', '2', '2');

INSERT INTO JournalDaily (assetId, channelId, deviceType, eventDate, userId, views) VALUES ('egdasdf', 1, 'Desktop', TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), '1', 2);
INSERT INTO JournalDaily (assetId, channelId, deviceType, eventDate, userId, views) VALUES ('egdasdf', 1, 'Mobile', TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), '2', 1);