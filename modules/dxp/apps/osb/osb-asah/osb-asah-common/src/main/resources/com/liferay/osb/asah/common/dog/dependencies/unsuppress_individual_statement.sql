BEGIN
	BEGIN TRANSACTION;

	DELETE FROM Suppression WHERE emailAddress = '${email_address}';

	UPDATE BQIndividual SET suppressed = false WHERE emailAddress = '${email_address}';

	UPDATE Identity_Raw SET individualId = NULL WHERE individualId = '${individual_id}' AND createDate BETWEEN timestamp '${range_start_date}' AND timestamp '${range_end_date}';
	
	COMMIT TRANSACTION;

EXCEPTION WHEN ERROR THEN
	ROLLBACK TRANSACTION;

	SELECT ERROR(@@error.message);
END