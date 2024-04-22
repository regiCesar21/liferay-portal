CREATE OR REPLACE TABLE FUNCTION `$[AC_PROJECT_ID].form_hourly`(endDate TIMESTAMP, startDate TIMESTAMP)
AS (
    WITH
        FormEvent AS (
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
            WHERE
                Event.applicationId = 'Form' AND
                Event.assetId IS NOT NULL AND
                Event.canonicalUrl IS NOT NULL AND
                Event.eventDate >= startDate AND
                Event.eventDate < endDate AND
                Event.eventId IN ('formSubmitted', 'formViewed') AND
                Event.title IS NOT NULL
        ),
        FormSubmissionTimes AS (
            SELECT
                assetId,
                browserName,
                canonicalUrl,
                channelId,
                city,
                country,
                deviceType,
                TIMESTAMP_TRUNC(eventDate, HOUR) AS normalizedEventDate,
                platformName,
                region,
                title AS pageTitle,
                userId ,
                SUM(UNIX_SECONDS(eventDate) - UNIX_SECONDS(previousFormViewedEventDate)) * 1000 submissionsTime
            FROM (
                SELECT
                    FormEvent.assetId,
                    FormEvent.browserName,
                    FormEvent.canonicalUrl,
                    FormEvent.channelId,
                    FormEvent.city,
                    FormEvent.country,
                    FormEvent.deviceType,
                    FormEvent.eventDate,
                    FormEvent.eventId,
                    FormEvent.platformName,
                    FormEvent.region,
                    FormEvent.title,
                    FormEvent.userId,
                    MAX(CASE WHEN eventId = 'formViewed' THEN eventDate END)
                    OVER (
                        PARTITION BY
                            assetId, channelId, canonicalUrl, sessionId, title
                        ORDER BY
                            eventDate ASC
                        ROWS UNBOUNDED PRECEDING
                    ) AS previousFormViewedEventDate
                FROM
                    FormEvent
            ) AS TMP
            WHERE
                eventId = 'formSubmitted'
            GROUP BY
                assetId, browserName, canonicalUrl, channelId, city,
                country, deviceType, normalizedEventDate, platformName,
                region, title, userId
        )
    SELECT
        GREATEST(
            0,
            COUNTIF(eventId = 'formViewed' AND Session.id IS NOT NULL) -
            COUNTIF(eventId = 'formSubmitted' AND Session.id IS NOT NULL)
        ) AS abandonments,
        FormEvent.assetId,
        COALESCE(MAX(FormEvent.assetTitle), '') AS assetTitle,
        FormEvent.browserName,
        FormEvent.canonicalUrl,
        CAST(FormEvent.channelId AS INT64) AS channelId,
        FormEvent.city,
        FormEvent.country,
        FormEvent.deviceType,
        TIMESTAMP_TRUNC(eventDate, HOUR) AS eventDate,
        COUNTIF(eventId = 'formViewed' AND Session.id IS NOT NULL
        ) AS finalizedFormViews,
        FormEvent.platformName,
        FormEvent.region,
        FormEvent.title AS pageTitle,
        COUNTIF(eventId = 'formSubmitted') AS submissions,
        MAX(FormSubmissionTimes.submissionsTime) AS submissionsTime,
        FormEvent.userId,
        COUNTIF(eventId = 'formViewed') AS views
    FROM
        FormEvent
    LEFT JOIN FormSubmissionTimes ON (
        FormEvent.assetId = FormSubmissionTimes.assetId AND
        FormEvent.browserName = FormSubmissionTimes.browserName AND
        FormEvent.canonicalUrl = FormSubmissionTimes.canonicalUrl AND
        FormEvent.channelId = FormSubmissionTimes.channelId AND
        FormEvent.city = FormSubmissionTimes.city AND
        FormEvent.country = FormSubmissionTimes.country AND
        FormEvent.deviceType = FormSubmissionTimes.deviceType AND
        TIMESTAMP_TRUNC(eventDate, HOUR) = FormSubmissionTimes.normalizedEventDate AND
        FormEvent.platformName = FormSubmissionTimes.platformName AND
        FormEvent.region = FormSubmissionTimes.region AND
        FormEvent.title = FormSubmissionTimes.pageTitle AND
        FormEvent.userId = FormSubmissionTimes.userId)
    LEFT JOIN `$[AC_PROJECT_ID].session` AS Session ON
        FormEvent.sessionId = Session.id AND
        Session.sessionStart >= startDate AND
        Session.sessionStart < endDate
    GROUP BY
        assetId, browserName, canonicalUrl, channelId, city, country, deviceType,
        eventDate, platformName, region, title, userId
);