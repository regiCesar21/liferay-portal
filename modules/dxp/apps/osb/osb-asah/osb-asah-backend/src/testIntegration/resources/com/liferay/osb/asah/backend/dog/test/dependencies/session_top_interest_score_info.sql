INSERT INTO Session(channelId, id, sessionStart) VALUES(2, '1', TIMESTAMP '${today-20d}');
INSERT INTO Session(channelId, id, sessionStart) VALUES(2, '2', TIMESTAMP '${today-10d}');
INSERT INTO Session(channelId, id, sessionStart) VALUES(2, '3', TIMESTAMP '${today-1d}');
INSERT INTO Session(channelId, id, sessionStart) VALUES(2, '4', TIMESTAMP '${today}');
INSERT INTO Session(channelId, id, sessionStart) VALUES(2, '5', TIMESTAMP '${today-1d}');

INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword1', DATE(TIMESTAMP '${today-20d}'), '1');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword2', DATE(TIMESTAMP '${today-20d}'), '1');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword2', DATE(TIMESTAMP '${today-1d}'), '3');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword3', DATE(TIMESTAMP '${today-10d}'), '2');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword4', DATE(TIMESTAMP '${today-10d}'), '2');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword5', DATE(TIMESTAMP '${today-1d}'), '3');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword5', DATE(TIMESTAMP '${today-10d}'), '2');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword5', DATE(TIMESTAMP '${today-20d}'), '1');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword6', DATE(TIMESTAMP '${today-1d}'), '3');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword7', DATE(TIMESTAMP '${today-1d}'), '5');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword7', DATE(TIMESTAMP '${today-1d}'), '3');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword7', DATE(TIMESTAMP '${today-10d}'), '2');
INSERT INTO SessionInterestScore(channelId, interested, keyword, recordedDate, sessionId) VALUES (2, TRUE, 'keyword7', DATE(TIMESTAMP '${today-20d}'), '1');