INSERT INTO Channel(id) VALUES (1);

INSERT INTO DataControlTask (id, batchId, completeDate, continueDate, emailAddresses, startDate, status, type, userId, userName) VALUES (1111, 12345, timestamp '2025-01-01T00:00:00.000Z', timestamp '2025-01-01T00:00:00.000Z', ARRAY ['test1@liferay.com'], timestamp '2025-01-01T00:00:00.000Z', 'COMPLETED', 'SUPPRESS', '12345', 'Test Test');
INSERT INTO DataControlTask (id, batchId, completeDate, continueDate, emailAddresses, startDate, status, type, userId, userName) VALUES (2222, 12345, null, null, ARRAY ['test1@liferay.com'], null, 'PENDING', 'DELETE', '12345', 'Test Test');

INSERT INTO Segment (channelId, filter, id, type) VALUES (1, '(((demographics/email/value ne null)))', 1, 'DYNAMIC');