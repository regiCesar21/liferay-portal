INSERT INTO Identity_Raw(id, individualId) VALUES ('1', '1');
INSERT INTO Identity_Raw(id, individualId) VALUES ('2', '2');

INSERT INTO Individual(id) VALUES ('1');
INSERT INTO Individual(id) VALUES ('2');
INSERT INTO Individual(id) VALUES ('3');
INSERT INTO Individual(id) VALUES ('4');

INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (1, '1', timestamp '${now}', timestamp '${now}', '1');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (1, '2', timestamp '${now-3m}', timestamp '${now-2m}', '2');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (1, '3', timestamp '${now-4m}', timestamp '${now-3m}', '3');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (1, '4', timestamp '${now-2d}', timestamp '${now-1d}', '4');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (1, '5', timestamp '${now-4d}', timestamp '${now-3d}', '1');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (1, '8', timestamp '${now-10d}', timestamp '${now-10d}', '4');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (2, '9', timestamp '${now-12d}', timestamp '${now-12d}', '1');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (2, '10', timestamp '${now-3d}', timestamp '${now-3d}', '2');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (2, '11', timestamp '${now-3d}', timestamp '${now-3d}', '3');
INSERT INTO Session (channelId, id, sessionStart, sessionEnd, userId) VALUES (2, '12', timestamp '${now-3d}', timestamp '${now-3d}', '4');