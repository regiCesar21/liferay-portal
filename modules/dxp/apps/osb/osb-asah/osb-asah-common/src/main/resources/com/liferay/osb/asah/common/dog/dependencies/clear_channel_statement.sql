BEGIN TRANSACTION;

DELETE FROM BlogDaily WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQEvent WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQIdentityActivitySummary WHERE channelId IN ( ${channel_ids} ) AND firstActivityDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQIdentityInterestPage WHERE channelId IN ( ${channel_ids} );
DELETE FROM BQIdentityInterestScore WHERE channelId IN ( ${channel_ids} ) AND recordedDate < DATE(LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE)));
DELETE FROM BQMembership WHERE channelId IN ( ${channel_ids} ) AND createDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQMembershipChange WHERE channelId IN ( ${channel_ids} ) AND createDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQMembershipIndividual WHERE channelId IN ( ${channel_ids} ) AND modifiedDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQOrder WHERE channelId IN ( ${channel_ids} ) AND createDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQOrder_Raw WHERE channelId IN ( ${channel_ids} ) AND createDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQProduct WHERE channelId IN ( ${channel_ids} ) AND createDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQProduct_Raw WHERE channelId IN ( ${channel_ids} ) AND createDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQSession WHERE channelId IN ( ${channel_ids} ) AND sessionStart < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM BQSessionInterestScore WHERE channelId IN ( ${channel_ids} ) AND recordedDate < DATE(LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE)));
DELETE FROM CustomAssetDaily WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM DocumentLibraryDaily WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM FormDaily WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM JournalDaily WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));
DELETE FROM PageDaily WHERE channelId IN ( ${channel_ids} ) AND eventDate < LEAST(${end_date}, TIMESTAMP_SUB(CURRENT_TIMESTAMP(), INTERVAL 90 MINUTE));

COMMIT TRANSACTION;