INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), 'Page Title 1', '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 5, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), 'Page Title 2', '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), 'Page Title 3', '2', 10);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 'Page Title 4', '2', 4);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 'Page Title 1', '1', 5);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), 'Page Title 1', '2', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), 'Page Title 2', '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), 'Page Title 1', '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), 'Page Title 2', '1', 1);
INSERT INTO BlogDaily (assetId, channelId, comments, eventDate, pageTitle, userId, views) VALUES ('e131fabc', 1, 0, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-15d}', HOUR)), 'Page Title 1', '1', 1);

INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), 'Page Title 1', 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 4, 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), 'Page Title 2', 6, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), 'Page Title 3', 7, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 'Page Title 4', 2, '2');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 2, 6, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 'Page Title 1', 1, '2');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), 'Page Title 1', 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 1, 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), 'Page Title 2', 1, '1');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 2, 2, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), 'Page Title 2', 1, '2');
INSERT INTO DocumentLibraryDaily (assetId, assetTitle, channelId, comments, downloads, eventDate, pageTitle, previews, userId) VALUES ('zsrwerf', 'Document 1', 1, 3, 3, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), 'Page Title 3', 1, '2');

INSERT INTO Identity_Raw (createDate, id) VALUES (timestamp '${now-8h}', '1');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (timestamp '${now-26h}', '2', '2');

INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), 'Page Title 1', '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), 'Page Title 1', '1', 6);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), 'Page Title 2', '2', 3);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 'Page Title 3', '1', 2);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), 'Page Title 4', '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), 'Page Title 1', '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), 'Page Title 2', '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), 'Page Title 2', '1', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), 'Page Title 3', '2', 1);
INSERT INTO JournalDaily (assetId, channelId, eventDate, pageTitle, userId, views) VALUES ('egdasdf', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-15d}', HOUR)), 'Page Title 1', '1', 1);