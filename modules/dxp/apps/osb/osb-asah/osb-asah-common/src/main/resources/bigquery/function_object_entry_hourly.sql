CREATE OR REPLACE TABLE FUNCTION `$[AC_PROJECT_ID].objectentry_hourly`(endDate TIMESTAMP, startDate TIMESTAMP)
AS (
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
				Event.eventDate >= startDate AND
				Event.eventDate < endDate AND
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
				externalReferenceCode.eventDate >= startDate AND
				externalReferenceCode.eventDate < endDate AND
				externalReferenceCode.id = Event.id AND
				externalReferenceCode.name = 'externalReferenceCode'
			)
			WHERE
				Event.applicationId = 'ObjectEntry' AND
				Event.canonicalUrl IS NOT NULL AND
				Event.eventDate >= startDate AND
				Event.eventDate < endDate AND
				externalReferenceCode.value IS NOT NULL
		)
	SELECT
		ObjectEntryEvent.canonicalUrl,
		ObjectEntryEvent.dataSourceId,
		COUNTIF(ObjectEntryEvent.eventId = 'objectEntryDownloaded') AS downloads,
		ObjectEntryEvent.normalizedEventDate AS eventDate,
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
);