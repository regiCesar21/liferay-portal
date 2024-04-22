CREATE OR REPLACE TABLE FUNCTION `$[AC_PROJECT_ID].journal_hourly`(endDate TIMESTAMP, startDate TIMESTAMP)
AS (
    WITH
        WebContentEvent AS (
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
                Event.platformName,
                Event.region,
                Event.title,
                Event.userId
            FROM
                `$[AC_PROJECT_ID].event` AS Event
            WHERE
                Event.applicationId = 'WebContent' AND
                Event.assetId IS NOT NULL AND
                Event.assetTitle IS NOT NULL AND
                Event.canonicalUrl IS NOT NULL AND
                Event.channelId IS NOT NULL AND
                Event.eventDate >= startDate AND
                Event.eventDate < endDate AND
                Event.eventId = 'webContentViewed' AND
                Event.title IS NOT NULL
        )
    SELECT
        assetId,
        assetTitle,
        browserName,
        canonicalUrl,
        channelId,
        city,
        country,
        deviceType,
        TIMESTAMP_TRUNC(eventDate, HOUR) AS eventDate,
        platformName,
        region,
        title AS pageTitle,
        userId,
        SUM(1) AS views
    FROM
        WebContentEvent
    GROUP BY
        assetId, assetTitle, browserName, canonicalUrl, channelId, city,
        country, TIMESTAMP_TRUNC(eventDate, HOUR), deviceType, platformName,
        region, title, userId
);