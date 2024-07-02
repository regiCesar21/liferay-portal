INSERT INTO Event(applicationId, canonicalUrl, channelId, createDate, context, dataSourceId, eventDate, eventId, id, languageId, properties, sessionId, userId) VALUES ('Page', 'canonicalUrlValue', 1, timestamp '${now}', '{}', 1, timestamp '${now}', 'assetClicked', 'abc-123', 'pt-BR', ARRAY<STRUCT<name STRING, value STRING>> [('viewDuration', 'viewDurationValue')], 'sessionId', '1');
INSERT INTO Event(applicationId, canonicalUrl, channelId, createDate, context, dataSourceId, eventDate, eventId, id, languageId, properties, sessionId, userId) VALUES ('Page', 'canonicalUrlValue', 1, timestamp '${now}', '{}', 1, timestamp '${now}', 'assetDownloaded', 'efg-246', 'pt-BR', ARRAY<STRUCT<name STRING, value STRING>> [('viewDuration', 'viewDurationValue')], 'sessionId', '1');

INSERT INTO Identity_Raw (id, individualId) VALUES ('1', '1');

INSERT INTO Session(channelId, id, sessionEnd, sessionStart) VALUES (1, 'sessionId', timestamp '${now}', timestamp '${now}');