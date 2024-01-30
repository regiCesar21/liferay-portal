BEGIN
	BEGIN TRANSACTION;

	DELETE FROM Suppression WHERE emailAddress = '${email_address}';

	UPDATE BQIndividual SET suppressed = false WHERE emailAddress = '${email_address}';

	COMMIT TRANSACTION;

EXCEPTION WHEN ERROR THEN
	ROLLBACK TRANSACTION;

	SELECT ERROR(@@error.message);
END