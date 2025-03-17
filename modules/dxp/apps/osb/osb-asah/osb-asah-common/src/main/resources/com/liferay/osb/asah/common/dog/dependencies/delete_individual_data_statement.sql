BEGIN
	BEGIN TRANSACTION;

	-- Individual's personal information

	DELETE FROM BQExpandoValue WHERE CONCAT(classPK, dataSourceId) IN ( SELECT CONCAT(dxpUserId, dataSourceId) FROM BQUser WHERE individualId IN (${individual_ids}) ) AND classType = 'com.liferay.portal.kernel.model.User';
	DELETE FROM BQIndividual WHERE id IN (${individual_ids});
	DELETE FROM DXPEntity WHERE CONCAT(classPK, dataSourceId) IN ( SELECT CONCAT(dxpUserId, dataSourceId) FROM BQUser WHERE individualId IN (${individual_ids}) ) AND type = 'com.liferay.portal.kernel.model.User';

	DELETE FROM BQUser WHERE individualId IN (${individual_ids});

	-- Individual's activities anonymization

	UPDATE BQEvent SET emailAddressHashed = NULL WHERE emailAddressHashed IN (${individual_ids}) AND eventDate <= timestamp '${range_end_date}';

	UPDATE BQIdentityActivitySummary SET individualId = NULL WHERE individualId IN (${individual_ids}) AND lastActivityDate <= timestamp '${range_end_date}';
	UPDATE BQIdentity_Raw SET individualId = NULL WHERE individualId IN (${individual_ids}) AND createDate <= timestamp '${range_end_date}';

	COMMIT TRANSACTION;

EXCEPTION WHEN ERROR THEN
	ROLLBACK TRANSACTION;

	SELECT ERROR(@@error.message);
END