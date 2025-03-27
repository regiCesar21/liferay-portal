INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '1', '1', 'test');
INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '2', '2', 'test');
INSERT INTO Identity_Raw (createDate, id, individualId, projectId) VALUES (TIMESTAMP '${today}', '3', '3', 'test');

INSERT INTO IdentityActivitySummary (channelId, identityId, lastActivityDate) VALUES (1, '1', TIMESTAMP '${today}');
INSERT INTO IdentityActivitySummary (channelId, identityId, lastActivityDate) VALUES (1, '2', TIMESTAMP '${today}');
INSERT INTO IdentityActivitySummary (channelId, identityId, lastActivityDate) VALUES (1, '3', TIMESTAMP '${today}');

INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '1', false, 'car', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '1', true, 'analytics', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '1', false, 'football', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '2', true, 'cat', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '2', true, 'analytics', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '2', false, 'motorcycle', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '3', false, 'bike', CURRENT_DATE());
INSERT INTO IdentityInterestScore(channelId, identityId, interested, keyword, recordedDate) VALUES(1, '3', false, 'home', CURRENT_DATE());

INSERT INTO Individual (id) VALUES ('1');
INSERT INTO Individual (id) VALUES ('2');
INSERT INTO Individual (id) VALUES ('3');

INSERT INTO Membership (identityId, segmentId) VALUES ('1', 1);
INSERT INTO Membership (identityId, segmentId) VALUES ('2', 1);
INSERT INTO Membership (identityId, segmentId) VALUES ('3', 1);