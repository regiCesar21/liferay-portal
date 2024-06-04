INSERT INTO Identity_Raw (id, individualId) VALUES ('1', 'A');
INSERT INTO Identity_Raw (id, individualId) VALUES ('2', 'B');
INSERT INTO Identity_Raw (id, individualId) VALUES ('3', 'C');
INSERT INTO Identity_Raw (id, individualId) VALUES ('4', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('5', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('6', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('7', null);

INSERT INTO Individual (id) VALUES ('A');
INSERT INTO Individual (id) VALUES ('B');
INSERT INTO Individual (id) VALUES ('C');
INSERT INTO Individual (id) VALUES ('D');

INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cedric-rodriguez.net', 1, 4, TIMESTAMP '${today-2dT11:00:00.000Z}', 1, '1', 'Cedric Rodriguez', '1', 8);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cedric-rodriguez.net', 1, 1, TIMESTAMP '${today-2dT11:00:00.000Z}', 0, '2', 'Cedric Rodriguez', '2', 2);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cedric-rodriguez.net', 1, 1, TIMESTAMP '${today-2dT11:00:00.000Z}', 0, '3', 'Cedric Rodriguez', '3', 2);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cedric-rodriguez.net', 1, 2, TIMESTAMP '${today-2dT11:00:00.000Z}', 1, '4', 'Cedric Rodriguez', '4', 3);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cherelle-ullrich.org', 1, 2, TIMESTAMP '${today-2dT10:00:00.000Z}', 0, '1', 'Cherelle Ullrich', '1', 5);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cherelle-ullrich.org', 1, 2, TIMESTAMP '${today-2dT10:00:00.000Z}', 1, '2', 'Cherelle Ullrich', '2', 3);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.cherelle-ullrich.org', 1, 2, TIMESTAMP '${today-2dT10:00:00.000Z}', 0, '3', 'Cherelle Ullrich', '3', 2);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.dino-boyer.com', 1, 1, TIMESTAMP '${today-2dT11:50:00.000Z}', 1, '1', 'Dino Boyer', '1', 3);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.dino-boyer.com', 1, 1, TIMESTAMP '${today-2dT11:50:00.000Z}', 1, '2', 'Dino Boyer', '2', 2);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.dino-boyer.com', 1, 1, TIMESTAMP '${today-2dT11:50:00.000Z}', 1, '3', 'Dino Boyer', '3', 2);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.dino-boyer.com', 1, 0, TIMESTAMP '${today-2dT11:50:00.000Z}', 0, '4', 'Dino Boyer', '4', 2);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.debra-huel.name', 1, 1, TIMESTAMP '${today-2dT12:15:00.000Z}', 0, '1', 'Debra Huel', '1', 1);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.liferay.com', 1, 7, TIMESTAMP '${today-2dT10:40:00.000Z}', 0, '1', 'Liferay | Home', '1', 13);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.liferay.com', 1, 2, TIMESTAMP '${today-2dT10:40:00.000Z}', 1, '2', 'Liferay | Home', '2', 13);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.liferay.com', 1, 1, TIMESTAMP '${today-2dT10:40:00.000Z}', 1, '3', 'Liferay | Home', '3', 13);
INSERT INTO PageDaily (canonicalUrl, channelId, entrances, eventDate, exits, sessionId, title, userId, views) VALUES ('https://www.ray-life.com', 1, 0, TIMESTAMP '${today-2dT10:40:00.000Z}', 1, '1', 'Ray Life', '1', 13);

INSERT INTO Session (channelId, id, userId) VALUES(1, '1', '1');
INSERT INTO Session (channelId, id, userId) VALUES(2, '2', '2');
INSERT INTO Session (channelId, id, userId) VALUES(3, '3', '3');
INSERT INTO Session (channelId, id, userId) VALUES(4, '4', '4');
INSERT INTO Session (channelId, id, userId) VALUES(5, '5', '5');