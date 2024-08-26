INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-1d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-2d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-6d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-8d}', HOUR)), '2', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-9d}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-12d}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-14d}', HOUR)), '1', 1);
INSERT INTO BlogDaily (assetId, channelId, eventDate, userId, views) VALUES ('e131fabc', 1, TIMESTAMP(DATETIME_TRUNC(timestamp '${now-15d}', HOUR)), '1', 1);

INSERT INTO Identity_Raw (createDate, id) VALUES (timestamp '${now-8h}', '1');
INSERT INTO Identity_Raw (createDate, id, individualId) VALUES (timestamp '${now-26h}', '2', '2');