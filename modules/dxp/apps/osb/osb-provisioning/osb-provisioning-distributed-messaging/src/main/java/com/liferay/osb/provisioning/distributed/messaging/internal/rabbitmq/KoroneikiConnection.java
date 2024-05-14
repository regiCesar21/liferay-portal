/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.distributed.messaging.internal.rabbitmq;

import com.liferay.osb.distributed.messaging.rabbitmq.connector.BaseConnection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kyle Bischof
 */
@Component(
	immediate = true,
	property = {"host=", "password=", "port=", "username=", "useSSL="},
	service = KoroneikiConnection.class
)
public class KoroneikiConnection extends BaseConnection {
}