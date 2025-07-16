WITH
	ObjectEntryEvent AS (
		SELECT
			Event.canonicalUrl,
			Event.dataSourceId,
			Event.eventId,
			Event.externalReferenceCode,
			Event.groupId,
			Event.id,
			TIMESTAMP_TRUNC(Event.eventDate, HOUR) AS normalizedEventDate,
			Event.userId
		FROM
			`$[AC_PROJECT_ID].event` AS Event
		WHERE
			Event.applicationId = 'ObjectEntry' AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			Event.eventId IN ('objectEntryDownloaded', 'objectEntryImpressionMade', 'objectEntryViewed') AND
			Event.externalReferenceCode IS NOT NULL
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