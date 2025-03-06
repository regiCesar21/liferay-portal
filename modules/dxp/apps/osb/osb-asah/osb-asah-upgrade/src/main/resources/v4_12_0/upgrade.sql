CREATE TABLE IF NOT EXISTS Individual (
	id TEXT PRIMARY KEY,
	emailAddress TEXT,
	fields JSON,
	suppressed BOOLEAN
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

CREATE TABLE IF NOT EXISTS IndividualInterest (
	channelId BIGINT,
	individualId TEXT,
	interested BOOLEAN,
	interestScore DOUBLE PRECISION,
	keyword TEXT,
	recordedDate DATE
);

CREATE TABLE IF NOT EXISTS IndividualSegment (
	createDate TIMESTAMPTZ,
	channelId BIGINT,
	individualId TEXT,
	modifiedDate TIMESTAMPTZ,
	segmentId BIGINT,
	status TEXT
);