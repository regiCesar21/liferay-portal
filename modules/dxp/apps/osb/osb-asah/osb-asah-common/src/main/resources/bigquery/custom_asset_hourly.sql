WITH 
	CustomAssetEventProperty AS (
		SELECT
			Event.eventDate,
			Event.id,
			EventProperty.name,
			EventProperty.value
		FROM
			`$[AC_PROJECT_ID].event` AS Event,
			UNNEST(Event.properties) AS EventProperty
		WHERE
			Event.applicationId = 'Custom' AND
			Event.assetId IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR)
	),
	CustomAssetEvent AS (
		SELECT
			Event.channelId,
			Event.eventDate,
			Event.eventId,
			Event.sessionId,
			TO_HEX(
				SHA256(
					CONCAT(
						Event.assetId,
						COALESCE(category.value, 'default'),
						Event.channelId
					)
				)
			) AS assetPrimaryKey,
			formEnabled.value AS formEnabled
		FROM
			`$[AC_PROJECT_ID].event` AS Event
		LEFT JOIN CustomAssetEventProperty AS category ON (
			category.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			category.id = Event.id AND
			category.name = 'category'
		)
		LEFT JOIN CustomAssetEventProperty AS formEnabled ON (
			formEnabled.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			formEnabled.id = Event.id AND
			formEnabled.name = 'formEnabled'
		)
		WHERE
			Event.applicationId = 'Custom' AND
			Event.assetId IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR)
	),
	CustomAssetFinalizedEvent AS (
		SELECT
			CustomAssetEvent.assetPrimaryKey,
			CustomAssetEvent.eventDate,
			CustomAssetEvent.eventId,
			CustomAssetEvent.formEnabled,
			CustomAssetEvent.sessionId
		FROM
			CustomAssetEvent
		INNER JOIN `$[AC_PROJECT_ID].session` Session ON
			CustomAssetEvent.sessionId = Session.id
		WHERE
			Session.sessionStart > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR)
	),
	Metrics AS (
		SELECT
			assetPrimaryKey,
			channelId,
			COUNTIF(eventId = 'assetClicked') AS clicks,
			COUNTIF(eventId = 'assetDownloaded') AS downloads,
			TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
			COUNT(DISTINCT(sessionId)) AS sessions,
			COUNTIF(eventId = 'assetSubmitted') AS submissions,
			COUNTIF(eventId = 'assetViewed') AS views
		FROM
			CustomAssetEvent
		GROUP BY
			assetPrimaryKey,
			channelId,
			normalizedEventDate
	),
	Abandoments AS (
		SELECT
			GREATEST(
				0,
				COUNTIF(eventId = 'assetViewed' AND formEnabled = 'true') -
				COUNTIF(eventId = 'assetSubmitted')
			) AS abandonments,
			assetPrimaryKey,
			TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate
		FROM
			CustomAssetFinalizedEvent
		GROUP BY
			assetPrimaryKey,
			normalizedEventDate
	),
	ReadTime AS (
		SELECT
			assetPrimaryKey,
			TIMESTAMP_TRUNC(maxEventDate, HOUR) AS normalizedEventDate,
			SUM(readtime) AS readTime
		FROM
			(
				SELECT
					assetPrimaryKey,
					MAX(CASE WHEN eventId != 'assetViewed' THEN eventDate END) AS maxEventDate,
					UNIX_SECONDS(MAX(CASE WHEN eventId != 'assetViewed' THEN eventDate END)) - UNIX_SECONDS(MIN(eventDate)) AS readTime,
					sessionId
				FROM
					CustomAssetFinalizedEvent
				GROUP BY
					assetPrimaryKey,
					sessionId
			) AS TMP
		WHERE
			maxEventDate IS NOT NULL
		GROUP BY
			assetprimarykey,
			normalizedEventDate
	),
	SubmissionTime AS (
		SELECT
			assetPrimaryKey,
			TIMESTAMP_TRUNC(minSubmissionDate, HOUR) AS normalizedEventDate,
			SUM(submissionTime) AS submissionsTime
		FROM
			(
				SELECT
					assetprimarykey,
					MIN(CASE WHEN eventId = 'assetSubmitted' THEN eventDate END) AS minSubmissionDate,
					sessionid,
					UNIX_SECONDS(MAX(CASE WHEN eventId = 'assetSubmitted' THEN eventDate END)) - UNIX_SECONDS(MIN(CASE WHEN eventId = 'assetViewed' THEN eventDate END)) AS submissionTime
				FROM
					CustomAssetFinalizedEvent
				GROUP BY
					assetPrimaryKey,
					sessionId
			) AS TMP
		WHERE
			minSubmissionDate IS NOT NULL
		GROUP BY
			assetPrimaryKey,
			normalizedEventDate
	)
	SELECT
		COALESCE(abandonments.abandonments, 0) AS abandonments,
		metrics.assetPrimaryKey,
		metrics.channelId,
		metrics.clicks,
		metrics.downloads,
		metrics.normalizedEventDate AS eventDate,
		metrics.submissions,
		metrics.views,
		metrics.sessions,
		COALESCE (readTime.readTime, 0) * 1000 AS readTime,
		COALESCE (submissionTime.submissionsTime, 0) * 1000 AS submissionsTime
	FROM
		Metrics metrics
	LEFT JOIN Abandoments abandonments ON (
		metrics.assetPrimaryKey = abandonments.assetprimarykey AND
		metrics.normalizedEventDate = abandonments.normalizedEventDate
	)
	LEFT JOIN ReadTime readTime ON (
		metrics.assetPrimaryKey = readTime.assetPrimaryKey AND
		metrics.normalizedEventDate = readTime.normalizedEventDate
	)
	LEFT JOIN SubmissionTime submissionTime ON (
		metrics.assetPrimaryKey = submissionTime.assetPrimaryKey AND
		metrics.normalizedEventDate = submissionTime.normalizedEventDate
	)