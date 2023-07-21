/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "upgrades")
@Meta.OCD(
	id = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	name = "data-cleanup-configuration-name"
)
public interface DataCleanupConfiguration {

	@Meta.AD(
		deflt = "false", name = "clean-up-chat-module-data", required = false
	)
	public boolean cleanUpChatModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-dictionary-module-data",
		required = false
	)
	public boolean cleanUpDictionaryModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-directory-module-data",
		required = false
	)
	public boolean cleanUpDirectoryModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-invitation-module-data",
		required = false
	)
	public boolean cleanUpInvitationModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-mail-reader-module-data",
		required = false
	)
	public boolean cleanUpMailReaderModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-private-messaging-module-data",
		required = false
	)
	public boolean cleanUpPrivateMessagingModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-shopping-module-data",
		required = false
	)
	public boolean cleanUpShoppingModuleData();

	@Meta.AD(
		deflt = "false", name = "clean-up-twitter-module-data", required = false
	)
	public boolean cleanUpTwitterModuleData();

}