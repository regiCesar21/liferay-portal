INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/car', 1, '1', 'car', 'These are my interests - Car', 13);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/dog', 1, '1', 'dog', 'These are my interests - Dog', 13);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/football', 1, '1', 'football', 'These are my interests - Football', 7);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.liferay-fc.com/football', 1, '1', 'football', 'Liferay - Football Club', 10);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/bike', 1, '2', 'bike', 'These are my interests - Bike', 2);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/cat', 1, '2', 'cat', 'These are my interests - Cat', 6);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.liferay-latam-fc.com/football', 1, '2', 'football', 'Liferay Latam - Football Club', 7);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.liferay-cars.com', 1, '3', 'car', 'Liferay - Cars', 13);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/cat', 1, '3', 'cat', 'These are my interests - Cat', 7);
INSERT INTO IdentityInterestPage (canonicalUrl, channelId, identityId, keyword, title, views) VALUES ('https://www.these-are-my-interests.com/football', 1, '3', 'football', 'These are my interests - Football', 7);

INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '1', '1', 'test');
INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '2', '2', 'test');
INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '3', '3', 'test');

INSERT INTO Individual (id) VALUES ('1');
INSERT INTO Individual (id) VALUES ('2');
INSERT INTO Individual (id) VALUES ('3');