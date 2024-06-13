INSERT INTO Event (applicationId, channelId, createDate, eventDate, eventId, id, userId) VALUES ('Blog', 1, timestamp '${now-2d}', timestamp '${now-2d}', 'blogClicked', '1', 'abc-123');
INSERT INTO Event (applicationId, channelId, createDate, eventDate, eventId, id, userId) VALUES ('Blog', 1, timestamp '${now-4d}', timestamp '${now-4d}', 'blogClicked', '2', 'bcd-456');
INSERT INTO Event (applicationId, channelId, createDate, eventDate, eventId, id, userId) VALUES ('Blog', 1, timestamp '${now-11d}', timestamp '${now-11d}', 'blogClicked', '3', 'efg-789');
INSERT INTO Event (applicationId, channelId, createDate, eventDate, eventId, id, userId) VALUES ('Blog', 1, timestamp '${now-35d}', timestamp '${now-35d}', 'blogClicked', '4', 'ghi-101');
INSERT INTO Event (applicationId, channelId, createDate, eventDate, eventId, id, userId) VALUES ('Blog', 1, timestamp '${now-11d}', timestamp '${now-11d}', 'blogClicked', '5', 'ijk-321');
INSERT INTO Event (applicationId, channelId, createDate, eventDate, eventId, id, userId) VALUES ('Blog', 1, timestamp '${now-35d}', timestamp '${now-35d}', 'blogClicked', '6', 'xyz-345');

INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, 'abc-123', '761319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb', timestamp '${now-3d}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, 'bcd-456', '5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a', timestamp '${now-5d}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, 'efg-789', '5f20f61b2cfaa86c4f3cb3557751a702776af029deabed8e943fb55cfa604e34', timestamp '${now-11d}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, 'ghi-101', 'def73c7b1d2934d8bcdc8080a221c39df40e7ccfa499ad49d862138f5bc055f9', timestamp '${now-36d}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, 'ijk-321', null, timestamp '${now-11d}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, 'xyz-345', null, timestamp '${now-36d}');

INSERT INTO Identity_Raw (id, individualId) VALUES ('abc-123', '761319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb');
INSERT INTO Identity_Raw (id, individualId) VALUES ('bcd-456', '5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a');
INSERT INTO Identity_Raw (id, individualId) VALUES ('efg-789', '5f20f61b2cfaa86c4f3cb3557751a702776af029deabed8e943fb55cfa604e34');
INSERT INTO Identity_Raw (id, individualId) VALUES ('ghi-101', 'def73c7b1d2934d8bcdc8080a221c39df40e7ccfa499ad49d862138f5bc055f9');
INSERT INTO Identity_Raw (id, individualId) VALUES ('ijk-321', null);
INSERT INTO Identity_Raw (id, individualId) VALUES ('xyz-345', null);

INSERT INTO Individual (emailAddress, id) VALUES ('joe@alpha.com', '761319ac0d9f6e0f3467ad26bc8c63989d06c5f491849d6aa12fabdbd6c6b7bb');
INSERT INTO Individual (emailAddress, id) VALUES ('marcus@beta.com', '5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a');
INSERT INTO Individual (emailAddress, id) VALUES ('nina@delta.com', '5f20f61b2cfaa86c4f3cb3557751a702776af029deabed8e943fb55cfa604e34');
INSERT INTO Individual (emailAddress, id) VALUES ('chris@gamma.com', 'def73c7b1d2934d8bcdc8080a221c39df40e7ccfa499ad49d862138f5bc055f9');

INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (0, 'Chrome', 1, 'Chicago', 'United States', 'Desktop', 180000, '1', 'Win10', ['https://l.facebook.com'], timestamp '${now-4m}', timestamp '${now}', ['https://url1.com', 'http://url2.com'], 'abc-123');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Chrome', 1, 'Chicago', 'United States', 'Desktop', 3600000, '2', 'Linux', ['https://google.com', 'https://facebook.com'], timestamp '${now-3m}', timestamp '${now-2m}', ['https://url1.com', 'http://url2.com'], 'bcd-456');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Firefox', 1, 'New York', 'United States', 'Desktop', 3600000, '3', 'Linux', ['https://yahoo.com'], timestamp '${now-4m}', timestamp '${now-2m}', ['https://url2.com'], 'efg-789');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Firefox', 1, 'Martha\'s Vineyard', 'United States', 'Desktop', 3600000, '4', 'Linux', ['https://bing.com'], timestamp '${now-6d}', timestamp '${now-7d}', ['https://url3.com'], 'bcd-456');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Chrome', 1, 'Los Angeles', 'United States', 'Phone', 3600000, '5', 'IOS', ['https://google.com'], timestamp '${now-6d}', timestamp '${now-7d}', ['https://url1.com', 'https://url2.com'], 'efg-789');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Firefox', 1, 'Martha\'s Vineyard', 'United States', 'Phone', 3600000, '6', 'IOS', ['https://google.com'], timestamp '${now-15d}', timestamp '${now-16d}', ['https://url2.com'], 'ghi-101');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Chrome', 1, 'Recife', 'Brazil', 'Phone', 3600000, '7', 'Android', ['https://facebook.com'], timestamp '${now-16d}', timestamp '${now-17d}', ['https://url3.com'], 'bcd-456');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Chrome', 1, 'Recife', 'Brazil', 'Phone', 3600000, '8', 'Android', ['https://facebook.com'], timestamp '${now-16d}', timestamp '${now-17d}', ['https://url3.com'], 'ijk-321');
INSERT INTO Session (bounce, browserName, channelId, city, country, deviceType, duration, id, platformName, referrers, sessionStart, sessionEnd, urls, userId) VALUES (1, 'Chrome', 1, 'Sao Paolo', 'Brazil', 'Phone', 3600000, '9', 'IOS', ['https://google.com'], timestamp '${now-6d}', timestamp '${now-7d}', ['https://url1.com', 'https://url2.com'], 'xyz-345');