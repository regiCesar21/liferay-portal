BEGIN
	BEGIN TRANSACTION;

	${anonymize_activities_statement}

	DELETE FROM Suppression WHERE emailAddress IN (${email_addresses});

	UPDATE BQIndividual SET suppressed = false WHERE emailAddress IN (${email_addresses});
	
	UPDATE Identity_Raw SET individualId = NULL WHERE individualId IN (${individual_ids}) AND createDate BETWEEN timestamp '${range_start_date}' AND timestamp '${range_end_date}';

	COMMIT TRANSACTION;

EXCEPTION WHEN ERROR THEN
	ROLLBACK TRANSACTION;

	UPDATE Suppression SET hidden = false WHERE emailAddress IN (${email_addresses});

	SELECT ERROR(@@error.message);
END