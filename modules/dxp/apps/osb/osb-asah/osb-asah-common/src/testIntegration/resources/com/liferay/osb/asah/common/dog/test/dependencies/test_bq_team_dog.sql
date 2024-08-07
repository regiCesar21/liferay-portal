INSERT INTO ExpandoColumn (columnId, dataSourceId, dataType, id, name) VALUES ('1', 123, 'STRING', '1', 'column');

INSERT INTO Team (dataSourceId, groupId, id, modifiedDate, name, teamId) VALUES (123, 1, '1', timestamp '${now}', 'Test', 1);
INSERT INTO Team (dataSourceId, groupId, id, modifiedDate, name, teamId) VALUES (123, 2, '2', timestamp '${now}', 'Liferay', 2);