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
	individualId TEXT,
	properties JSON
);

CREATE TABLE IF NOT EXISTS IndividualInterest (
	channelId BIGINT,
	identityId TEXT,
	individualId TEXT,
	interested BOOLEAN,
	interestScore DOUBLE PRECISION,
	keyword TEXT,
	recordedDate DATE,
	PRIMARY KEY (channelId, identityId, individualId, keyword, recordedDate)
);

CREATE TABLE IF NOT EXISTS IndividualSegment (
	createDate TIMESTAMPTZ,
	channelId BIGINT,
	individualId TEXT,
	modifiedDate TIMESTAMPTZ,
	segmentId BIGINT,
	status TEXT,
	PRIMARY KEY (channelId, individualId, segmentId)
);

CREATE INDEX IF NOT EXISTS IX_INDIVIDUALACTIVITY_EDII ON IndividualActivity (eventDate, individualId);

CREATE INDEX IF NOT EXISTS IX_INDIVIDUALINTEREST_II ON IndividualInterest (individualId);