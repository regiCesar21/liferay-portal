$[AC_PROJECT_TIME_ZONE_ID_DECLARATION]

WITH IdentityActivity AS (
	SELECT
		*
	FROM
		`$[AC_PROJECT_ID].identityactivitysummary`
	UNION ALL
	SELECT
		COUNT(*) AS activitiesCount,
		Event.channelId,
		Event.dataSourceId,
		Event.eventId,
		MIN(Event.eventDate) AS firstActivityDate,
		Event.userId AS identityId,
		MAX(Identity.individualId) AS individualId,
		MAX(Event.eventDate) AS lastActivityDate
	FROM
		`$[AC_PROJECT_ID].event` AS Event
	LEFT JOIN `$[AC_PROJECT_ID].identity` AS Identity ON (
		Event.userId = Identity.id
	)
	WHERE
		DATE(Event.eventDate, '$[TIME_ZONE_ID]') >= CURRENT_DATE('$[TIME_ZONE_ID]')
	GROUP BY
		Event.channelId,
		Event.dataSourceId,
		Event.eventId,
		Event.userId
)

SELECT
	SUM(activitiesCount) AS activitiesCount,
	channelId,
	dataSourceId,
	eventId,
	MIN(firstActivityDate) AS firstActivityDate,
	identityId,
	MAX(individualId) AS individualId,
	MAX(lastActivityDate) AS lastActivityDate
FROM
	IdentityActivity
GROUP BY
	channelId, dataSourceId, eventId, identityId