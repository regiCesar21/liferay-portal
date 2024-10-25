CREATE OR REPLACE TABLE FUNCTION `$[AC_PROJECT_ID].blog_hourly`(endDate TIMESTAMP, startDate TIMESTAMP)
AS (
	WITH
        BlogEventProperty AS (
            SELECT
                Event.eventDate,
                Event.id,
                EventProperty.name,
                EventProperty.value
            FROM
                `$[AC_PROJECT_ID].event` AS Event,
            	UNNEST(Event.properties) AS EventProperty
            WHERE
                (
                    (
                        Event.applicationId = 'Blog' AND
                        Event.eventId IN ('blogClicked', 'blogDepthReached', 'blogViewed')
                    ) OR
                    (
                        Event.applicationId = 'Ratings'
                    )
                ) AND
                Event.assetId IS NOT NULL AND
                Event.assetTitle IS NOT NULL AND
                Event.canonicalUrl IS NOT NULL AND
                Event.eventDate >= startDate AND
                Event.eventDate < endDate AND
                Event.title IS NOT NULL
        ),
        BlogEvent AS (
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
                Event.sessionId,
                Event.title,
                Event.userId
            FROM
                `$[AC_PROJECT_ID].event` AS Event
            LEFT JOIN BlogEventProperty AS className ON (
                className.eventDate >= startDate AND
                className.eventDate < endDate AND
                className.id = Event.id AND
                className.name = 'className' AND
                className.value = 'com.liferay.blogs.model.BlogsEntry'
            )
            WHERE
                (
                    (
                        Event.applicationId = 'Blog' AND
                        Event.eventId IN ('blogClicked', 'blogDepthReached', 'blogViewed')
                    ) OR
                    (
                        Event.applicationId = 'Ratings' AND
                        className.value IS NOT NULL
                    )
                ) AND
                Event.assetId IS NOT NULL AND
                Event.assetTitle IS NOT NULL AND
                Event.canonicalUrl IS NOT NULL AND
                Event.eventDate >= startDate AND
                Event.eventDate < endDate AND
                Event.title IS NOT NULL
        ),
        BlogFinalizedEvent AS (
            SELECT
                BlogEvent.assetId,
                BlogEvent.assetTitle,
                BlogEvent.canonicalUrl,
                BlogEvent.channelId,
                BlogEvent.eventDate,
                BlogEvent.eventId,
                BlogEvent.sessionId,
                BlogEvent.title,
                BlogEvent.userId
            FROM
                BlogEvent
            INNER JOIN `$[AC_PROJECT_ID].session` AS Session ON
                BlogEvent.sessionId = Session.id
            WHERE
                Session.sessionStart >= startDate AND
                Session.sessionStart < endDate
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
            LEFT JOIN BlogEventProperty AS className ON (
                className.eventDate >= startDate AND
                className.eventDate < endDate AND
                className.id = Event.id AND
                className.name = 'className' AND
                className.value = 'com.liferay.blogs.model.BlogsEntry'
            )
            WHERE
                Event.applicationId = 'Comment' AND
                Event.assetId IS NOT NULL AND
                Event.canonicalUrl IS NOT NULL AND
                Event.eventDate >= startDate AND
                Event.eventDate < endDate AND
                Event.eventId = 'posted' AND
                Event.title IS NOT NULL
        ),
        BlogComments AS (
            SELECT
                assetId,
                canonicalUrl,
                SUM(1) AS comments,
                channelId,
                TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
                title AS pageTitle,
                userId
            FROM
                CommentEvent
            GROUP BY
                assetId, canonicalUrl, channelId, normalizedEventDate, title, userId
        ),
        RatingsEvent AS (
            SELECT
                Event.assetId,
                Event.canonicalUrl,
                Event.channelId,
                Event.eventDate,
                CAST(score.value AS FLOAT64) AS score,
                Event.title,
                Event.userId
            FROM
                `$[AC_PROJECT_ID].event` AS Event
            LEFT JOIN BlogEventProperty AS className ON (
                className.eventDate >= startDate AND
                className.eventDate < endDate AND
                className.id = Event.id AND
                className.value = 'com.liferay.blogs.model.BlogsEntry' AND
                className.name = 'className'
            )
            LEFT JOIN BlogEventProperty AS ratingType ON (
                ratingType.eventDate >= startDate AND
                ratingType.eventDate < endDate AND
                ratingType.id = Event.id AND
                ratingType.value = 'stars' AND
                ratingType.name = 'ratingType'
            )
            LEFT JOIN BlogEventProperty AS score ON (
                score.eventDate >= startDate AND
                score.eventDate < endDate AND
                score.id = Event.id AND
                score.name = 'score'
            )
            WHERE
                Event.applicationId = 'Ratings' AND
                Event.assetId IS NOT NULL AND
                Event.canonicalUrl IS NOT NULL AND
                Event.eventDate >= startDate AND
                Event.eventDate < endDate AND
                Event.eventId = 'VOTE' AND
                Event.title IS NOT NULL
        ),
        BlogRatings AS (
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
        ),
        BlogReadTimes AS (
            SELECT
                assetId,
                assetTitle,
                canonicalUrl,
                channelId,
                TIMESTAMP_TRUNC(maxEventDate, HOUR) AS normalizedEventDate,
                title AS pageTitle,
                SUM(readTime) AS readTime,
                userId
            FROM
                (
                    SELECT
                        assetId,
                        assetTitle,
                        canonicalUrl,
                        channelId,
                        MAX(CASE WHEN eventId != 'blogViewed' THEN eventDate END) AS maxEventDate,
                        (
                            UNIX_SECONDS(MAX(CASE WHEN eventId != 'blogViewed' THEN eventDate END)) -
                            UNIX_SECONDS(MIN(eventDate))
                        ) AS readTime,
                        sessionId,
                        title,
                        userId
                    FROM
                        BlogFinalizedEvent
                    GROUP BY
                        assetId, assetTitle, canonicalUrl, channelId, sessionId,
                        title, userId
                ) AS TMP
            WHERE
                maxEventDate IS NOT NULL
            GROUP BY
                assetId, assetTitle, canonicalUrl, channelId, normalizedEventDate,
                title, userId
        ),
        BlogViewsAndClicks AS (
            SELECT
                assetId,
                assetTitle,
                browserName,
                canonicalUrl,
                channelId,
                COUNTIF(eventId = 'blogClicked') AS clicks,
                city,
                country,
                TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
                deviceType,
                platformName,
                region,
                COUNT(DISTINCT(sessionId)) AS sessions,
                title AS pageTitle,
                userId,
                COUNTIF(eventId = 'blogViewed') AS views
            FROM
                BlogEvent
            GROUP BY
                assetId, assetTitle, browserName, canonicalUrl, channelId, city,
                country, normalizedEventDate, deviceType, platformName,
                region, title, userId
        )
    SELECT
        BlogViewsAndClicks.assetId,
        BlogViewsAndClicks.assetTitle,
        BlogViewsAndClicks.browserName,
        BlogViewsAndClicks.canonicalUrl,
        BlogViewsAndClicks.channelId,
        BlogViewsAndClicks.city,
        COALESCE(BlogViewsAndClicks.clicks, 0) AS clicks,
        COALESCE(BlogComments.comments, 0) AS comments,
        BlogViewsAndClicks.country,
        BlogViewsAndClicks.deviceType,
        BlogViewsAndClicks.normalizedEventDate AS eventDate,
        BlogViewsAndClicks.pageTitle,
        BlogViewsAndClicks.platformName,
        BlogRatings.ratings,
        BlogRatings.ratingsScore,
        BlogReadTimes.readTime * 1000 AS readTime,
        BlogViewsAndClicks.region,
        BlogViewsAndClicks.sessions,
        BlogViewsAndClicks.userId,
        COALESCE(BlogViewsAndClicks.views, 0) AS views
    FROM
        BlogViewsAndClicks
    LEFT JOIN BlogComments ON (
        BlogViewsAndClicks.assetId = BlogComments.assetId AND
        BlogViewsAndClicks.canonicalUrl = BlogComments.canonicalUrl AND
        BlogViewsAndClicks.channelId = BlogComments.channelId AND
        BlogViewsAndClicks.normalizedEventDate = BlogComments.normalizedEventDate AND
        BlogViewsAndClicks.pageTitle = BlogComments.pageTitle AND
        BlogViewsAndClicks.userId = BlogComments.userId
    )
    LEFT JOIN BlogRatings ON (
        BlogViewsAndClicks.assetId = BlogRatings.assetId AND
        BlogViewsAndClicks.canonicalUrl = BlogRatings.canonicalUrl AND
        BlogViewsAndClicks.channelId = BlogRatings.channelId AND
        BlogViewsAndClicks.normalizedEventDate = BlogRatings.normalizedEventDate AND
        BlogViewsAndClicks.pageTitle = BlogRatings.pageTitle AND
        BlogViewsAndClicks.userId = BlogRatings.userId
    )
    LEFT JOIN BlogReadTimes ON (
        BlogViewsAndClicks.assetId = BlogReadTimes.assetId AND
        BlogViewsAndClicks.assetTitle = BlogReadTimes.assetTitle AND
        BlogViewsAndClicks.canonicalUrl = BlogReadTimes.canonicalUrl AND
        BlogViewsAndClicks.channelId = BlogReadTimes.channelId AND
        BlogViewsAndClicks.normalizedEventDate = BlogReadTimes.normalizedEventDate AND
        BlogViewsAndClicks.pageTitle = BlogReadTimes.pageTitle AND
        BlogViewsAndClicks.userId = BlogReadTimes.userId
    )
);