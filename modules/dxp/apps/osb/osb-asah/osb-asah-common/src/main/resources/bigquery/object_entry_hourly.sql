WITH
	EventProperty AS (
		SELECT
			Event.eventDate,
			Event.id,
			EventProperty.name,
			EventProperty.value
		FROM
			`$[AC_PROJECT_ID].event` AS Event,
			UNNEST(Event.properties) AS EventProperty
		WHERE
			Event.applicationId IN ('ObjectEntry') AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			Event.eventId IN ('objectEntryDownloaded', 'objectEntryImpressionMade', 'objectEntryViewed')
	),
	ObjectEntryEvent AS (
		SELECT
			Event.canonicalUrl,
			Event.dataSourceId,
			Event.eventId,
			Event.groupId,
			Event.id,
			TIMESTAMP_TRUNC(Event.eventDate, HOUR) AS normalizedEventDate,
			Event.userId,
			externalReferenceCode.value AS externalReferenceCode
		FROM
			`$[AC_PROJECT_ID].event` AS Event
		LEFT JOIN EventProperty AS externalReferenceCode ON (
			externalReferenceCode.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			externalReferenceCode.id = Event.id AND
			externalReferenceCode.name = 'externalReferenceCode'
		)
		WHERE
			Event.applicationId = 'ObjectEntry' AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			externalReferenceCode.value IS NOT NULL
	)
SELECT
	ObjectEntryEvent.canonicalUrl,
	ObjectEntryEvent.dataSourceId,
	COUNTIF(ObjectEntryEvent.eventId = 'objectEntryDownloaded') AS downloads,
	ObjectEntryEvent.normalizedEventDate as eventDate,
	ObjectEntryEvent.eventId,
	ObjectEntryEvent.externalReferenceCode,
	ObjectEntryEvent.groupId,
	COUNTIF(ObjectEntryEvent.eventId = 'objectEntryImpressionMade') AS impressions,
	ObjectEntryEvent.userId,
	COUNTIF(ObjectEntryEvent.eventId = 'objectEntryViewed') AS views
FROM
	ObjectEntryEvent
GROUP BY
	canonicalUrl, dataSourceId, eventId, externalReferenceCode, groupId, normalizedEventDate, userId