# Loop Portlet

### Table of Contents
* [Requirements](#requirements)
* [Setup](#setup)

### Requirements
* [Node](https://nodejs.org/en/) (latest version)
* NPM (latest version) *This should be included with your installation of Node*
* [MySQL](https://dev.mysql.com/downloads/) or [MariaDB](https://mariadb.org/download/)

### Setup

1. Create a new database.

1. Download a [Tomcat SP6 bundle](http://mirrors/files.liferay.com/private/ee/portal/7.0.10.6/liferay-dxp-digital-enterprise-tomcat-7.0-sp6-20171010144253003.zip).

1. Download [liferay-fix-pack-de-45-7010.zip](http://mirrors/files.liferay.com/private/ee/fix-packs/7.0.10/de/liferay-fix-pack-de-45-7010.zip) and put the zipped folder inside of `patching-tool/patches` in your bundle directory.
	* [Patching Tool](https://grow.liferay.com/share/Patching+Tool)

1. Download this [hotfix](http://files.liferay.com/private/ee/fix-packs/7.0.10/hotfix/liferay-hotfix-2260-7010.zip) and place it in `patching-tool/patches` as well. (_delete the osgi/state folder in your bundle if hotfix the does not work._)
	* This hotfix changes portal's implementation of the `JSONObject.put(String key, long value)` method in `JSONObjectImpl.java` to store actual long values instead of String converted long values, a change made in DXP which broke Loop APIs.
	* It also lengthens the "name" column of the Repository table, fixing a bug in portal.
	* It also bypasses the `com.liferay.notifications.web.internal.upgrade.v2_1_0.UpgradeUserNotificationEvent` upgrade logic that sets all `delivered` values to true.

1. Run the patching-tool binary and follow the instructions to install the patches.

1. If you are using a MySQL database, download [mysql.jar](https://dev.mysql.com/downloads/connector/j/) and place it in `lib/ext` in your tomcat directory.

1. Add a file called `portal-ext.properties` to your bundle with the following minimum properties. The example below uses a MySQL database.

    ```java
    jdbc.default.driverClassName=com.mysql.cj.jdbc.Driver
    jdbc.default.url=jdbc:mysql://localhost/lportal?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false
    jdbc.default.username=root
    jdbc.default.password=liferay
    setup.wizard.enabled=false

    auth.token.check.enabled=false
    dl.file.max.size=26214400
    dl.file.rank.enabled=false
    javascript.single.page.application.enabled=false
    passwords.encryption.algorithm.legacy=SHA
    ```

    If you are using mariadb, you will need to use a different jdbc driver:

    ```
    jdbc.default.driverClassName=org.mariadb.jdbc.Driver

    ```

1. Deploy all push notification modules __*except*__ for `push-notifications-test` to your bundle. They are located inside `modules/apps/push-notifications` in your portal repository.

1. Deploy all Elasticsearch 6 modules to your bundle. They are located inside `modules/apps/portal-search-elasticsearch6` in your portal repository.

1. Create a `com.liferay.portal.bundle.blacklist.internal.BundleBlacklistConfiguration.config` file with these contents:

	```
	blacklistBundleSymbolicNames=["com.liferay.portal.search.elasticsearch", "com.liferay.portal.search.elasticsearch.shield", "com.liferay.portal.search.elasticsearch.marvel.web"]
	```
	and place it in `osgi/configs` in your bundle directory.

1. Start the bundle.

1. Once the server starts up it should ask for a license; put a Liferay license inside the `deploy` directory of your bundle.

1. Once the license has deployed successfully, refresh the page to see vanilla DXP.

1. Fork and clone [Loop's private repository](https://github.com/liferay/com-liferay-osb-loop-private), and deploy all of its modules to your bundle.

1. Refresh the page to see the Loop login; you're good to go!