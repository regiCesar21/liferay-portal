INSERT INTO Channel (id, createDate) VALUES (1, '2021-05-31');
INSERT INTO Channel (id, createDate) VALUES (2, '2021-06-01');
INSERT INTO Channel (id, createDate) VALUES (3, '2021-06-02');

INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('STRING', 'TEST_NAME', 13456, 'name', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('STRING', 'TEST_URL', 12345, 'testUrl', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('STRING', 'TEST_TITLE', 23456, 'testTitle', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('NUMBER', 'TEST_CODE', 34567, 'testCode', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('NUMBER', 'TEST_RATING', 45678, 'testRating', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('DATE', 'TEST_DATE', 56789, 'testDate', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('BOOLEAN', 'TEST_BOOLEAN', 12456, 'like', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('BOOLEAN', 'TEST_MEMBER', 67890, 'testMember', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('NUMBER', 'TEST_DURATION', 78901, 'testDuration', 'LOCAL');

INSERT INTO EventDefinition (applicationId, id, blocked, description, displayName, name, type) VALUES ('CustomEvent', 135791, false, 'Test Description 2', 'Test Display Name 2', 'testEvent2', 'CUSTOM');
INSERT INTO EventDefinition (applicationId, id, blocked, description, displayName, name, type) VALUES ('CustomEvent', 246810, false, 'Test Description 1', 'Test Display Name 1', 'testEvent1', 'CUSTOM');