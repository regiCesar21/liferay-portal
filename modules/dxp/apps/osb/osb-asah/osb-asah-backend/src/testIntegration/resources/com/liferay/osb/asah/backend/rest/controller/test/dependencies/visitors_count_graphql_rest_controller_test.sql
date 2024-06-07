INSERT INTO Identity_Raw (id, individualId) VALUES ('1', 'test1');
INSERT INTO Identity_Raw (id, individualId) VALUES ('2', 'test2');
INSERT INTO Identity_Raw (id, individualId) VALUES ('3', 'test3');

INSERT INTO Individual (id) VALUES ('test1');
INSERT INTO Individual (id) VALUES ('test2');
INSERT INTO Individual (id) VALUES ('test3');

INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '1', TIMESTAMP '2024-05-05T13:00:00.000Z', TIMESTAMP '2024-05-05T01:30:00.000Z', '1');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '2', TIMESTAMP '2024-05-05T14:00:00.000Z', TIMESTAMP '2024-05-05T02:40:00.000Z', '1');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '3', TIMESTAMP '2024-05-05T18:00:00.000Z', TIMESTAMP '2024-05-05T03:20:00.000Z', '1');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '4', TIMESTAMP '2024-05-06T16:00:00.000Z', TIMESTAMP '2024-05-06T04:50:00.000Z', '2');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '5', TIMESTAMP '2024-05-06T17:00:00.000Z', TIMESTAMP '2024-05-06T05:10:00.000Z', '2');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '6', TIMESTAMP '2024-05-06T20:00:00.000Z', TIMESTAMP '2024-05-06T06:30:00.000Z', '2');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '7', TIMESTAMP '2024-05-07T15:00:00.000Z', TIMESTAMP '2024-05-07T06:30:00.000Z', '2');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '8', TIMESTAMP '2024-05-07T16:00:00.000Z', TIMESTAMP '2024-05-07T07:40:00.000Z', '3');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '9', TIMESTAMP '2024-05-07T19:00:00.000Z', TIMESTAMP '2024-05-07T07:50:00.000Z', '3');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '10', TIMESTAMP '2024-05-07T21:00:00.000Z', TIMESTAMP '2024-05-07T08:40:00.000Z', '3');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '12', TIMESTAMP '2024-05-08T21:00:00.000Z', TIMESTAMP '2024-05-08T09:30:00.000Z', '4');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '13', TIMESTAMP '2024-05-08T21:00:00.000Z', TIMESTAMP '2024-05-08T09:20:00.000Z', '4');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '14', TIMESTAMP '2024-05-08T21:00:00.000Z', TIMESTAMP '2024-05-08T10:30:00.000Z', '4');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '15', TIMESTAMP '2024-05-08T21:00:00.000Z', TIMESTAMP '2024-05-08T10:30:00.000Z', '5');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '16', TIMESTAMP '2024-05-09T21:00:00.000Z', TIMESTAMP '2024-05-09T11:00:00.000Z', '5');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '17', TIMESTAMP '2024-05-09T21:00:00.000Z', TIMESTAMP '2024-05-09T12:25:00.000Z', '5');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '18', TIMESTAMP '2024-05-09T21:00:00.000Z', TIMESTAMP '2024-05-09T13:10:00.000Z', '6');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '19', TIMESTAMP '2024-05-10T21:00:00.000Z', TIMESTAMP '2024-05-10T14:20:00.000Z', '6');
INSERT INTO Session (channelId, id, sessionEnd, sessionStart, userId) VALUES (1, '20', TIMESTAMP '2024-05-10T21:00:00.000Z', TIMESTAMP '2024-05-10T15:40:00.000Z', '6');