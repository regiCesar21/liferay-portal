BEGIN TRANSACTION;

DELETE FROM BlogDaily WHERE channelId IN ( ${channel_ids} );
DELETE FROM CustomAssetDaily WHERE channelId IN ( ${channel_ids} );
DELETE FROM DocumentLibraryDaily WHERE channelId IN ( ${channel_ids} );
DELETE FROM FormDaily WHERE channelId IN ( ${channel_ids} );
DELETE FROM JournalDaily WHERE channelId IN ( ${channel_ids} );
DELETE FROM PageDaily WHERE channelId IN ( ${channel_ids} );

COMMIT TRANSACTION;