INSERT INTO Event (applicationId, canonicalUrl, channelId, createDate, eventDate, eventId, title, userId) VALUES ('Page', 'https://www.liferay.com', 1, TIMESTAMP '${now}', TIMESTAMP '${now}', 'pageViewed', 'Page 1', 'test1');
INSERT INTO Event (applicationId, canonicalUrl, channelId, createDate, eventDate, eventId, title, userId) VALUES ('Page', 'https://www.liferay.com', 1, TIMESTAMP '${now}', TIMESTAMP '${now}', 'pageViewed', 'Page 1', 'test6');
INSERT INTO Event (applicationId, canonicalUrl, channelId, createDate, eventDate, eventId, title, userId) VALUES ('Page', 'https://www.liferay.com', 1, TIMESTAMP '${now}', TIMESTAMP '${now}', 'pageViewed', 'Page 1', 'test4');
INSERT INTO Event (applicationId, canonicalUrl, channelId, createDate, eventDate, eventId, title, userId) VALUES ('Page', 'https://www.liferay.com', 1, TIMESTAMP '${now}', TIMESTAMP '${now}', 'pageViewed', 'Page 1', 'test4');
INSERT INTO Event (applicationId, canonicalUrl, channelId, createDate, eventDate, eventId, title, userId) VALUES ('Page', 'https://www.liferay.com', 1, TIMESTAMP '${now}', TIMESTAMP '${now}', 'pageViewed', 'Page 1', 'test5');
INSERT INTO Event (applicationId, canonicalUrl, channelId, createDate, eventDate, eventId, title, userId) VALUES ('Page', 'https://www.liferay.com', 1, TIMESTAMP '${now}', TIMESTAMP '${now}', 'pageViewed', 'Page 1', 'test6');

INSERT INTO Identity_Raw (id, individualId) VALUES ('test1', '761319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb');
INSERT INTO Identity_Raw (id, individualId) VALUES ('test2', '5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('test3', '261319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb');
INSERT INTO Identity_Raw (id, individualId) VALUES ('test4', '4970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('test5', '161319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb');
INSERT INTO Identity_Raw (id, individualId) VALUES ('test6', '6970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a');

INSERT INTO Individual (emailAddress, firstName, id, lastName) VALUES ('test1@liferay.com', 'Test1', '761319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb', 'Test');
INSERT INTO Individual (emailAddress, firstName, id, lastName) VALUES ('test2@liferay.com', 'Test2', '5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a', 'Test');
INSERT INTO Individual (emailAddress, firstName, id, lastName) VALUES ('test3@liferay.com', 'Test3', '261319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb', 'Test');
INSERT INTO Individual (emailAddress, firstName, id, lastName) VALUES ('test4@liferay.com', 'Test4', '4970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a', 'Test');
INSERT INTO Individual (emailAddress, firstName, id, lastName) VALUES ('test5@liferay.com', 'Test5','161319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb', 'Test');
INSERT INTO Individual (emailAddress, firstName, id, lastName) VALUES ('test6@liferay.com', 'Test5','6970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a', 'Test');

INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.liferay.com', 1, TIMESTAMP '${today-1dT10:40:00.000Z}', 'Page 1', 'test3', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.liferay.com', 1, TIMESTAMP '${today-2dT10:40:00.000Z}', 'Page 1', 'test2', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.liferay.com', 1, TIMESTAMP '${today-1dT10:40:00.000Z}', 'Page 1', 'test3', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.liferay.com', 1, TIMESTAMP '${today-2dT10:40:00.000Z}', 'Page 1', 'test3', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.liferay.com', 1, TIMESTAMP '${today-1dT10:40:00.000Z}', 'Page 1', 'test3', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, eventDate, title, userId, views) VALUES ('https://www.liferay.com', 1, TIMESTAMP '${today-2dT10:40:00.000Z}', 'Page 1', 'test3', 1);