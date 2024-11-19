INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '1', '1', 'test');
INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '2', '2', 'test');
INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '3', '3', 'test');

INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, '1', '1', TIMESTAMP '${today}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, '2', '2', TIMESTAMP '${today}');
INSERT INTO IdentityActivitySummary (channelId, identityId, individualId, lastActivityDate) VALUES (1, '3', '3', TIMESTAMP '${today}');

INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '1', true, 'car', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '1', true, 'dog', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '1', true, 'football', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '2', true, 'cat', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '2', true, 'football', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '2', true, 'motorcycle', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '3', true, 'bike', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '3', true, 'home', CURRENT_DATE());

INSERT INTO Individual (id) VALUES ('1');
INSERT INTO Individual (id) VALUES ('2');
INSERT INTO Individual (id) VALUES ('3');

INSERT INTO Membership (identityId, individualId, segmentId) VALUES ('1', '1', 1);
INSERT INTO Membership (identityId, individualId, segmentId) VALUES ('2', '2', 1);