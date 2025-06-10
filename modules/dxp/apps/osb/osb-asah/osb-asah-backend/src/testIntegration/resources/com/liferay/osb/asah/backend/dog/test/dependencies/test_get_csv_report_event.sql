INSERT INTO Event (applicationId, canonicalUrl, channelId, eventDate, eventId, properties, referrer, title, url, userId) VALUES ('Page', 'http://localhost:8080', 1, TIMESTAMP(DATETIME_TRUNC(TIMESTAMP '2023-11-04T16:10:00.666Z', HOUR)), 'pageViewed', ARRAY<STRUCT<name STRING, value STRING>> [], 'http://localhost:8080', 'Page 1', 'http://localhost:8080', '1');
INSERT INTO Event (applicationId, canonicalUrl, channelId, eventDate, eventId, properties, referrer, title, url, userId) VALUES ('Page', 'http://localhost:8080', 1, TIMESTAMP(DATETIME_TRUNC(TIMESTAMP '2023-11-04T17:10:00.666Z', HOUR)), 'pageViewed', ARRAY<STRUCT<name STRING, value STRING>> [], 'http://localhost:8080', 'Page 1', 'http://localhost:8080', '1');
INSERT INTO Event (applicationId, canonicalUrl, channelId, eventDate, eventId, properties, referrer, title, url, userId) VALUES ('Page', 'http://localhost:8080', 1, TIMESTAMP(DATETIME_TRUNC(TIMESTAMP '2023-11-04T18:10:00.666Z', HOUR)), 'pageViewed', ARRAY<STRUCT<name STRING, value STRING>> [], 'http://localhost:8080', 'Page 1', 'http://localhost:8080', '2');

INSERT INTO Identity_Raw (id, individualId) VALUES ('1', '1');
INSERT INTO Identity_Raw (id, individualId) VALUES ('2', '2');
INSERT INTO Identity_Raw (id, individualId) VALUES ('3', '3');
INSERT INTO Identity_Raw (id, individualId) VALUES ('4', '4');