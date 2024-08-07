INSERT INTO Channel (id, createDate, name) VALUES (2468, '2021-05-12 18:12:00', 'testChannelName');

INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('STRING', 'Test Attribute 1', 12346, 'testUrl', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('STRING', 'Test Attribute 2', 23456, 'testCode', 'LOCAL');
INSERT INTO EventAttributeDefinition (dataType, displayName, id, name, type) VALUES ('STRING', 'Test Attribute 3', 34567, 'testRating', 'LOCAL');

INSERT INTO EventDefinition (applicationId, id, blocked, description, displayName, name, type) VALUES ('CustomEvent', 12345, false, 'Test Event Description', 'Test Display Name', 'testName', 'CUSTOM');

INSERT INTO EventDefinitionEventAttributeDefinition (eventAttributeDefinitionId, eventDefinitionId, sampleValue) VALUES (12346, 12345, 'Sample Text');
INSERT INTO EventDefinitionEventAttributeDefinition (eventAttributeDefinitionId, eventDefinitionId, sampleValue) VALUES (23456, 12345, 'Sample Text 1');
INSERT INTO EventDefinitionEventAttributeDefinition (eventAttributeDefinitionId, eventDefinitionId, sampleValue) VALUES (34567, 12345, 'Sample Text 2');