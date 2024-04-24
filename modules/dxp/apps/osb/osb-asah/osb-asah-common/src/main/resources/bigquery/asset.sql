WITH NotPageEvent AS (
	SELECT
		CASE
			WHEN
				Event.applicationId = 'Comment'
			THEN
				'Blog'
			ELSE
				Event.applicationId
		END AS applicationId,
		Event.assetId,
		FIRST_VALUE(Event.assetTitle IGNORE NULLS) OVER (
			PARTITION BY
				CASE WHEN
					Event.applicationId = 'Comment'
				THEN
					'Blog'
				ELSE
					Event.applicationId
				END,
				Event.assetId,
				Event.dataSourceId
			ORDER BY eventDate DESC
			ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
		) AS assetTitle,
		Event.canonicalUrl,
		Event.channelId,
		Event.dataSourceId,
		Event.eventDate,
		CASE
			WHEN
				Event.eventId = 'posted'
			THEN
				'commentPosted'
			ELSE
				Event.eventId
		END AS eventId,
		Event.title
	FROM
		`$[AC_PROJECT_ID].event` Event
	WHERE
		(
			Event.applicationId IN (
				'Blog', 'Document', 'Form', 'WebContent'
			) AND
			Event.assetId IS NOT NULL AND
			Event.eventId IN (
				'blogViewed', 'formViewed', 'formSubmitted', 'documentDownloaded',
				'documentPreviewed', 'webContentViewed'
			)
		) OR
		(
			Event.applicationId = 'Comment' AND
			Event.assetId IS NOT NULL AND
			Event.eventId = 'posted' AND
			EXISTS (
				SELECT
					1
				FROM
					UNNEST(Event.properties)
				WHERE
					name = 'className' AND
					SAFE_CAST(value AS STRING) = 'com.liferay.blogs.model.BlogsEntry'
			)
		)
),
PageEvent AS (
	SELECT
		Event.applicationId,
		Event.assetId,
		Event.assetTitle,
		Event.canonicalUrl,
		Event.channelId,
		Event.dataSourceId,
		Event.eventDate,
		Event.eventId,
		Event.title
	FROM
		`$[AC_PROJECT_ID].event` Event
	WHERE
		Event.applicationId = 'Page' AND
		Event.assetId IS NOT NULL AND
		Event.assetTitle IS NOT NULL AND
		Event.eventId = 'pageViewed'
),
AssetEvent AS (
	SELECT
		applicationId,
		assetId,
		assetTitle,
		canonicalUrl,
		channelId,
		dataSourceId,
		eventDate,
		eventId,
		title
	FROM
		NotPageEvent
	UNION ALL
	SELECT
		applicationId,
		assetId,
		assetTitle,
		canonicalUrl,
		channelId,
		dataSourceId,
		eventDate,
		eventId,
		title
	FROM
		PageEvent
)
SELECT
	applicationId,
	TO_HEX(SHA256(assetId)) AS id,
	assetId,
	assetTitle,
	channelId,
	dataSourceId,
	eventId,
	MAX(eventDate) as modifiedDate,
	COUNT(*) as count
FROM
	AssetEvent
GROUP BY
	applicationId,
	assetId,
	assetTitle,
	channelId,
	dataSourceId,
	eventId