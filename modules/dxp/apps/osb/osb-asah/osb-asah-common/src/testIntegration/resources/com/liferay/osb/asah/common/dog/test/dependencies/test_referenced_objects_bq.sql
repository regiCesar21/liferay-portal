INSERT INTO Event (applicationId, assetId, assetTitle, canonicalUrl, channelId, dataSourceId, eventDate, eventId, properties, title) VALUES ('Form', '5678', 'Title', 'http://liferay.com', 1, 123456789, timestamp '${now}', 'formViewed', ARRAY<STRUCT<name STRING, value STRING>> [], 'Liferay');

INSERT INTO `Group` (dataSourceId, groupId, id, modifiedDate, name) VALUES (123456789, 5632478, 'c4f359787171fadfbcb37c96c324254260e0d164d03dd3384bd0d8eee6976cf5', timestamp '${now}', 'Group 1');

INSERT INTO Organization (createDate, dataSourceId, id, modifiedDate, name, organizationId) VALUES (timestamp '${now}', 123456789, '49b58366cb287f8d67cbb53ec1666cbc7e479d5d769c5dacadfc2b8981f287b0', timestamp '${now}', 'Organization 1', 8789);

INSERT INTO Role (dataSourceId, id, modifiedDate, name, roleId) VALUES (123456789, '75524e028bbe9a84278b7b6285362da90600545f4e8f08a74568bff12d5a2760', timestamp '${now}', 'Role 1', 32426);

INSERT INTO Team (dataSourceId, groupId, id, modifiedDate, name, teamId) VALUES (123456789, 5632478, '2ca148650ffe346c482251a3b6e76dbb705d1eed0ebfa564d202fad2f0df0966', timestamp '${now}', 'Team 1', 87678);

INSERT INTO User (dataSourceId, dxpUserId, emailAddress, id, modifiedDate) VALUES (123456789, 63534, 'test@liferay.com', 'e8931884a5cfc10b436de0bb5c35a0a37b7574c1f854c46a4cf3a7e522f7ecb2', timestamp '${now}');

INSERT INTO UserGroup (dataSourceId, id, modifiedDate, name, userGroupId) VALUES (123456789, 'a5c97f6263b76cc59857c88139a3ed110ccb9bde454d5c2cde6556c12191337e', timestamp '${now}', 'User Group 1', 13426);

INSERT INTO MembershipChange (identitiesCount, segmentId) VALUES (1, 1);