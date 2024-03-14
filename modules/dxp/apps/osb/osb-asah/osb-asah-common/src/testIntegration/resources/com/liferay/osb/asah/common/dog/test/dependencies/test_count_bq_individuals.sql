INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-1d}', '1', 'A', timestamp '${today-1d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-5d}', '1', 'A', timestamp '${today-5d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-5d}', '2', 'B', timestamp '${today-5d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-8d}', '2', 'B', timestamp '${today-8d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-9d}', '3', 'C', timestamp '${today-9d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-13d}', '4', null, timestamp '${today-13d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-18d}', '4', null, timestamp '${today-18d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-22d}', '5', null, timestamp '${today-22d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-34d}', '6', null, timestamp '${today-34d}');
INSERT INTO IdentityActivitySummary (activitiesCount, channelId, dataSourceId, eventId, firstActivityDate, identityId, individualId, lastActivityDate) VALUES (1, 1, 567, 'pageViewed', timestamp '${today-41d}', '7', null, timestamp '${today-41d}');

INSERT INTO Identity_Raw (id, individualId) VALUES ('1', 'A');
INSERT INTO Identity_Raw (id, individualId) VALUES ('2', 'B');
INSERT INTO Identity_Raw (id, individualId) VALUES ('3', 'C');
INSERT INTO Identity_Raw (id, individualId) VALUES ('4', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('5', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('6', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('7', null);

INSERT INTO Individual (id, lastName) VALUES ('A', 'Test');
INSERT INTO Individual (id) VALUES ('B');
INSERT INTO Individual (id, suppressed) VALUES ('C', true);

INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (0, 1, 150000, '1', ['https://google.com'], timestamp '${today-1d}', timestamp '${today-1d}', ['https://liferay.com'], '1');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (1, 1, 300000, '2', ['https://google.com'], timestamp '${today-5d}', timestamp '${today-5d}', ['https://liferay.com'], '1');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (1, 1, 120000, '3', ['https://google.com'], timestamp '${today-5d}', timestamp '${today-5d}', ['https://liferay.com'], '2');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (0, 1, 60000, '4', ['https://google.com'], timestamp '${today-8d}', timestamp '${today-8d}', ['https://liferay.com'], '2');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (0, 1, 60000, '5', ['https://google.com'], timestamp '${today-9d}', timestamp '${today-9d}', ['https://liferay.com'], '3');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (1, 1, 120000, '6', ['https://google.com'], timestamp '${today-13d}', timestamp '${today-13d}', ['https://liferay.com'], '4');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (1, 1, 180000, '7', ['https://google.com'], timestamp '${today-18d}', timestamp '${today-18d}', ['https://liferay.com'], '4');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (0, 1, 120000, '8', ['https://google.com'], timestamp '${today-22d}', timestamp '${today-22d}', ['https://liferay.com'], '5');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (1, 1, 60000, '9', ['https://google.com'], timestamp '${today-34d}', timestamp '${today-34d}', ['https://liferay.com'], '6');
INSERT INTO Session (bounce, channelId, duration, id, referrers, sessionEnd, sessionStart, urls, userId) VALUES (0, 1, 120000, '10', ['https://google.com'], timestamp '${today-41d}', timestamp '${today-41d}', ['https://liferay.com'], '7');