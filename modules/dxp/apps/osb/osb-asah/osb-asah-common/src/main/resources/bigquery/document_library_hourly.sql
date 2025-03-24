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
			Event.applicationId IN ('Comment', 'Document', 'Ratings') AND
			Event.assetId IS NOT NULL AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			Event.eventId IN ('documentDownloaded', 'documentImpressionMade', 'documentPreviewed', 'posted', 'VOTE') AND
			Event.title IS NOT NULL
	),
	CommentEvent AS (
		SELECT
			Event.assetId,
			Event.canonicalUrl,
			Event.channelId,
			Event.eventDate,
			Event.title,
			Event.userId
		FROM
			`$[AC_PROJECT_ID].event` AS Event
		LEFT JOIN EventProperty AS className ON (
			className.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			className.id = Event.id AND
		    className.name = 'className' AND
			className.value = 'com.liferay.document.library.kernel.model.DLFileEntry'
		)
		WHERE
			Event.applicationId = 'Comment' AND
			Event.assetId IS NOT NULL AND
			Event.assetTitle IS NOT NULL AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			Event.eventId = 'posted' AND
			Event.title IS NOT NULL
	),
	DocumentEvent AS (
		SELECT
			Event.assetId,
			Event.assetTitle,
			Event.browserName,
			Event.canonicalUrl,
			Event.channelId,
			Event.city,
			Event.country,
			Event.deviceType,
			Event.eventDate,
			Event.eventId,
			Event.platformName,
			Event.region,
			Event.title,
			Event.userId
		FROM
			`$[AC_PROJECT_ID].event` AS Event
		LEFT JOIN EventProperty AS className ON (
			className.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			className.id = Event.id AND
			className.name = 'className' AND
			className.value = 'com.liferay.document.library.kernel.model.DLFileEntry'
		)
		WHERE
			Event.applicationId = 'Document' AND
			Event.assetId IS NOT NULL AND
			Event.assetTitle IS NOT NULL AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventId IN ('documentDownloaded', 'documentImpressionMade', 'documentPreviewed') AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			Event.title IS NOT NULL
	),
	DocumentComments AS (
		SELECT
			assetId,
			canonicalUrl,
			channelId,
			SUM(1) AS comments,
			TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
			title AS pageTitle,
			userId
		FROM
			CommentEvent
		GROUP BY
			assetId, canonicalUrl, channelId, normalizedEventDate, title, userId
	),
	DocumentDownloadAndImpressions AS (
		SELECT
			assetId,
			assetTitle,
			browserName,
			canonicalUrl,
			channelId,
			city,
			COUNTIF(eventId = 'documentDownloaded') AS downloads,
			country,
			deviceType,
			TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
			platformName,
			COUNTIF(eventId = 'documentImpressionMade' OR eventId = 'documentPreviewed') AS impressions,
			region,
			title AS pageTitle,
			userId
		FROM
			DocumentEvent
		GROUP BY
			assetId, assetTitle, browserName, canonicalUrl, channelId, city,
			country, deviceType, normalizedEventDate, platformName,
			region, title, userId
	),
	RatingsEvent AS (
		SELECT
			Event.assetId,
			Event.canonicalUrl,
			Event.channelId,
			Event.eventDate,
			Event.title,
			CAST(score.value AS FLOAT64) AS score,
			Event.userId
		FROM
			`$[AC_PROJECT_ID].event` AS Event
		LEFT JOIN EventProperty AS className ON (
			className.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			className.id = Event.id AND
		    className.name = 'className' AND
			className.value = 'com.liferay.document.library.kernel.model.DLFileEntry'
		)
		LEFT JOIN EventProperty AS ratingType ON (
			ratingType.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
		    ratingType.id = Event.id AND
		    ratingType.name = 'ratingType' AND
			ratingtype.value = 'stars'
		)
		LEFT JOIN EventProperty AS score ON (
			score.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
		    score.id = Event.id AND
		    score.name = 'score'
		)
		WHERE
			Event.applicationId = 'Ratings' AND
			Event.assetId IS NOT NULL AND
			Event.canonicalUrl IS NOT NULL AND
			Event.eventDate > TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 48 HOUR) AND
			Event.eventId = 'VOTE' AND
			Event.title IS NOT NULL
	),
	DocumentRatings AS (
		SELECT
			assetId,
			canonicalUrl,
			channelId,
			TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
			title AS pageTitle,
			SUM(1) AS ratings,
			score AS ratingsScore,
			userId
		FROM
			RatingsEvent AS RatingsEvent1
		WHERE
			RatingsEvent1.eventDate = (
				SELECT
					MAX(RatingsEvent2.eventDate)
				FROM
					RatingsEvent RatingsEvent2
				WHERE
					RatingsEvent1.assetId = RatingsEvent2.assetId AND
					RatingsEvent1.userid = RatingsEvent2.userid
			) AND score >= 0
		GROUP BY
			assetId, canonicalUrl, channelId, normalizedEventDate, score,
			title, userId
	)
SELECT
	DocumentDownloadAndImpressions.assetId,
	DocumentDownloadAndImpressions.assetTitle,
	DocumentDownloadAndImpressions.browserName,
	DocumentDownloadAndImpressions.canonicalUrl,
	DocumentDownloadAndImpressions.channelId,
	DocumentDownloadAndImpressions.city,
	DocumentComments.comments,
	DocumentDownloadAndImpressions.country,
	DocumentDownloadAndImpressions.deviceType,
	DocumentDownloadAndImpressions.downloads,
	DocumentDownloadAndImpressions.normalizedEventDate AS eventDate,
	DocumentDownloadAndImpressions.pageTitle,
	DocumentDownloadAndImpressions.platformName,
	DocumentDownloadAndImpressions.impressions,
	DocumentRatings.ratings,
	DocumentRatings.ratingsScore,
	DocumentDownloadAndImpressions.region,
	DocumentDownloadAndImpressions.userId
FROM
	DocumentDownloadAndImpressions
LEFT JOIN DocumentRatings ON (
	DocumentDownloadAndImpressions.assetId = DocumentRatings.assetId AND
	DocumentDownloadAndImpressions.canonicalUrl = DocumentRatings.canonicalUrl AND
	DocumentDownloadAndImpressions.channelId = DocumentRatings.channelId AND
	DocumentDownloadAndImpressions.normalizedEventDate = DocumentRatings.normalizedEventDate AND
	DocumentDownloadAndImpressions.pageTitle = DocumentRatings.pageTitle AND
	DocumentDownloadAndImpressions.userId = DocumentRatings.userId
)
LEFT JOIN DocumentComments ON (
	DocumentDownloadAndImpressions.assetId = DocumentComments.assetId AND
	DocumentDownloadAndImpressions.canonicalUrl = DocumentComments.canonicalUrl AND
	DocumentDownloadAndImpressions.channelId = DocumentComments.channelId AND
	DocumentDownloadAndImpressions.normalizedEventDate = DocumentComments.normalizedEventDate AND
	DocumentDownloadAndImpressions.pageTitle = DocumentComments.pageTitle AND
	DocumentDownloadAndImpressions.userId = DocumentComments.userId
)