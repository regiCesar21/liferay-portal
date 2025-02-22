CREATE TABLE IF NOT EXISTS Individual (
    id TEXT PRIMARY KEY,
    emailAddress TEXT,
    fields JSON
);

CREATE TABLE IF NOT EXISTS IndividualActivity (
	id TEXT PRIMARY KEY,
	applicationId TEXT,
	channelId BIGINT,
	context JSON,
	eventDate TIMESTAMPTZ,
	eventId TEXT,
	properties JSON,
	individualId TEXT
);