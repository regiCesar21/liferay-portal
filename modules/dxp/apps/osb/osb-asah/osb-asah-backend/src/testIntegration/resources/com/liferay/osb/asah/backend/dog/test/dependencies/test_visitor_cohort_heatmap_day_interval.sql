INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity1', 'user1');
INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity2', 'user2');
INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity3', 'user3');
INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity4', 'user4');
INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity5', null);
INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity6', 'fake_individual_id1');
INSERT INTO Identity_Raw (createDate, id, individualId) values (CURRENT_TIMESTAMP, 'identity7', 'fake_individual_id2');

INSERT INTO Individual (createDate, emailAddress, id, modifiedDate) values (CURRENT_TIMESTAMP, 'user1@gmail.com', 'user1', CURRENT_TIMESTAMP);
INSERT INTO Individual (createDate, emailAddress, id, modifiedDate) values (CURRENT_TIMESTAMP, 'user2@gmail.com', 'user2', CURRENT_TIMESTAMP);
INSERT INTO Individual (createDate, emailAddress, id, modifiedDate) values (CURRENT_TIMESTAMP, 'user3@gmail.com', 'user3', CURRENT_TIMESTAMP);
INSERT INTO Individual (createDate, emailAddress, id, modifiedDate) values (CURRENT_TIMESTAMP, 'user4@gmail.com', 'user4', CURRENT_TIMESTAMP);

INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session1', TIMESTAMP '${today-5d}', TIMESTAMP '${today-5d}', 'identity1');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session2', TIMESTAMP '${today-5d}', TIMESTAMP '${today-5d}', 'identity2');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session3', TIMESTAMP '${today-5d}', TIMESTAMP '${today-5d}', 'identity5');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session4', TIMESTAMP '${today-5d}', TIMESTAMP '${today-5d}', 'identity6');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session5', TIMESTAMP '${today-4d}', TIMESTAMP '${today-4d}', 'identity2');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session6', TIMESTAMP '${today-4d}', TIMESTAMP '${today-4d}', 'identity6');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session7', TIMESTAMP '${today-4d}', TIMESTAMP '${today-4d}', 'identity1');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session8', TIMESTAMP '${today-1d}', TIMESTAMP '${today-1d}', 'identity7');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session9', TIMESTAMP '${today}', TIMESTAMP '${today}', 'identity4');
INSERT INTO Session (bounce, channelId, duration, id, sessionEnd, sessionStart, userId) values (1, 1, 60000, 'session10', TIMESTAMP '${today}', TIMESTAMP '${today}', 'identity3');