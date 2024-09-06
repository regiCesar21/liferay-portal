INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-15d}', HOUR)), '1', 1);

INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 2, 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 1, '2');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 2, 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), 1, '2');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 2, 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), 1, '2');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), 1, '2');

INSERT INTO Identity_Raw (createDate, id) VALUES (timestamp '${now-8h}', '1');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (timestamp '${now-26h}', '2', '2');

INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-15d}', HOUR)), '1', 1);