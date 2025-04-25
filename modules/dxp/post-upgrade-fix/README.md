# Procedure to Fix Core Upgrade Issues in Liferay 7

In previous Liferay versions, it was common to add upgrade fixes into verify
classes. That wasn't a good practice for two reasons:

- An upgrade fix should be executed just once. A verify process, however,
remains in the code for subsequent Liferay versions to be executed as many times
as necessary.
- It is difficult to maintain verify processes that contain a bunch of upgrade
fixes, especially ones that are old and irrelevant to newer Liferay versions.

To avoid this, we've defined a new procedure to fix upgrade issues in the Core
for Liferay 7 or DXP (and above):

1. Fix the issue in `master`. To do it, add the upgrade fix to the upgrade
process class associated to the next GA release. For example, if the latest
published GA release is 7.0 GA2 (7001), add your upgrade fix to the
`UpgradeProcess_7_0_2` class (create it if doesn't already exist). This step
solves the issue for subsequent GA upgrades.

1. Backport the upgrade fixes to `ee-7.0.x`. The fix pack team includes these
modifications for future fix packs. This solves the issue for new EE customers
who upgrade to DXP after installing this fix pack.

1. If it's a critical issue or your customer needs to solve the problem but
can't repeat the upgrade, create a new module in the
`modules/private/post-upgrade-fix` directory inside the **liferay-portal-ee**
(`ee-7.0.x`) branch. This module must include a Gogo console command which
executes the same modifications as the upgrade process. Create a knowledge base
article with the instructions to execute the process so that other customers can
use it in the future (add the related LPS issue to the article). For initial
review, send a pull request that commits this module to the Upgrade SME (i.e.,
Alberto Chaparro).

1. The module must fulfill the following naming guidelines:

	- **Directory name:**
	`modules/private/post-upgrade-fix/post-upgrade-fix-{LPS_nnnnn}`
	- **Bundle-Name:** Liferay Post Upgrade Fix {LPS_nnnnn}
	- **Bundle-SymbolicName:** `com.liferay.post.upgrade.fix.{LPS_nnnnn}`
	- **Java class:**
	`com.liferay.post.upgrade.fix.{LPS_nnnnn}.osgi.commands.PostUpgradeFixOSGiCommands`
	- **Gogo console scope:** `"postUpgradeFix"`
	- **Gogo console function:** {LPS_nnnnn}

As an example of performing this process, please check the
[LPS-66599](https://issues.liferay.com/browse/LPS-66599) post upgrade fix
module:

- **Directory name:**
`modules/private/post-upgrade-fix/post-upgrade-fix-LPS_66599`
- **Bundle-Name:** Liferay Post Upgrade Fix LPS_66599
- **Bundle-SymbolicName:** `com.liferay.post.upgrade.fix.LPS_66599`
- **Java class:**
`com.liferay.post.upgrade.fix.LPS_66599.osgi.commands.PostUpgradeFixOSGiCommands`
- **Full gogo console command:** `postUpgradeFix:LPS_66599`

Remember, when you deliver this module to a customer, the following
requirements are mandatory:

- Make a database and Document Library backup prior to executing the OSGi
command.
- Execute the command while the portal isn't receiving traffic.