INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'blogDepthReached', timestamp '${now}', '1', '1', timestamp '${now}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'tabBlurred', timestamp '${now-5d}', '2', '2', timestamp '${now-5d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'tabFocused', timestamp '${now-10d}', '3', '3', timestamp '${now-10d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'VOTE', timestamp '${now-10d}', '4', '4', timestamp '${now-10d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'pageUnloaded', timestamp '${now-15d}', '5', '5', timestamp '${now-15d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'fieldBlurred', timestamp '${now-20d}', '6', '6', timestamp '${now-20d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'pageDepthReached', timestamp '${now-20d}', '7', '7', timestamp '${now-20d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 1, 'webContentViewed', timestamp '${now-25d}', '8', '8', timestamp '${now-25d}');

INSERT INTO Identity_Raw (id, individualId) VALUES ('1', '1');
INSERT INTO Identity_Raw (id, individualId) VALUES ('2', '2');
INSERT INTO Identity_Raw (id, individualId) VALUES ('3', '3');
INSERT INTO Identity_Raw (id, individualId) VALUES ('4', '4');
INSERT INTO Identity_Raw (id, individualId) VALUES ('5', '5');
INSERT INTO Identity_Raw (id, individualId) VALUES ('6', '6');
INSERT INTO Identity_Raw (id, individualId) VALUES ('7', '7');
INSERT INTO Identity_Raw (id, individualId) VALUES ('8', '8');

INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now}', 'test1@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '1', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-5d}', 'test2@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '2', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-10d}', 'test3@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '3', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-10d}', 'test4@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '4', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-15d}', 'test5@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '5', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-20d}', 'test6@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '6', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-20d}', 'test7@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '7', timestamp '${now}');
INSERT INTO Individual (createDate, emailAddress, fields, id, modifiedDate) VALUES (timestamp '${now-25d}', 'test8@liferay.com', ARRAY<STRUCT<dataSourceId INT64, name STRING, value STRING>> [(null, null, null)], '8', timestamp '${now}');
