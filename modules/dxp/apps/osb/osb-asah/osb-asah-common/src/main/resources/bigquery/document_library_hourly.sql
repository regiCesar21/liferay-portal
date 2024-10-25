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
			Event.eventId IN ('documentDownloaded', 'documentPreviewed', 'posted', 'VOTE') AND
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
			Event.eventId IN ('documentDownloaded', 'documentPreviewed') AND
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
	DocumentDownloadAndPreviews AS (
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
			COUNTIF(eventId = 'documentPreviewed') AS previews,
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
	DocumentDownloadAndPreviews.assetId,
	DocumentDownloadAndPreviews.assetTitle,
	DocumentDownloadAndPreviews.browserName,
	DocumentDownloadAndPreviews.canonicalUrl,
	DocumentDownloadAndPreviews.channelId,
	DocumentDownloadAndPreviews.city,
	DocumentComments.comments,
	DocumentDownloadAndPreviews.country,
	DocumentDownloadAndPreviews.deviceType,
	DocumentDownloadAndPreviews.downloads,
	DocumentDownloadAndPreviews.normalizedEventDate AS eventDate,
	DocumentDownloadAndPreviews.pageTitle,
	DocumentDownloadAndPreviews.platformName,
	DocumentDownloadAndPreviews.previews,
	DocumentRatings.ratings,
	DocumentRatings.ratingsScore,
	DocumentDownloadAndPreviews.region,
	DocumentDownloadAndPreviews.userId
FROM
	DocumentDownloadAndPreviews
LEFT JOIN DocumentRatings ON (
	DocumentDownloadAndPreviews.assetId = DocumentRatings.assetId AND
	DocumentDownloadAndPreviews.canonicalUrl = DocumentRatings.canonicalUrl AND
	DocumentDownloadAndPreviews.channelId = DocumentRatings.channelId AND
	DocumentDownloadAndPreviews.normalizedEventDate = DocumentRatings.normalizedEventDate AND
	DocumentDownloadAndPreviews.pageTitle = DocumentRatings.pageTitle AND
	DocumentDownloadAndPreviews.userId = DocumentRatings.userId
)
LEFT JOIN DocumentComments ON (
	DocumentDownloadAndPreviews.assetId = DocumentComments.assetId AND
	DocumentDownloadAndPreviews.canonicalUrl = DocumentComments.canonicalUrl AND
	DocumentDownloadAndPreviews.channelId = DocumentComments.channelId AND
	DocumentDownloadAndPreviews.normalizedEventDate = DocumentComments.normalizedEventDate AND
	DocumentDownloadAndPreviews.pageTitle = DocumentComments.pageTitle AND
	DocumentDownloadAndPreviews.userId = DocumentComments.userId
)