/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ExtendedObjectClassDefinition(
	category = "search", factoryInstanceLabelAttribute = "connectionId",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration",
	localization = "content/Language",
	name = "elasticsearch-connection-configuration-name"
)
@ProviderType
public interface ElasticsearchConnectionConfiguration {

	@Meta.AD(
		deflt = "false", description = "active-help", name = "active",
		required = false
	)
	public boolean active();

	@Meta.AD(
		description = "connection-id-help", name = "connection-id",
		required = false
	)
	public String connectionId();

	@Meta.AD(
		deflt = "http://localhost:9200",
		description = "network-host-addresses-help",
		name = "network-host-addresses", required = false
	)
	public String[] networkHostAddresses();

	@Meta.AD(
		deflt = "false", description = "authentication-enabled-help",
		name = "authentication-enabled", required = false
	)
	public boolean authenticationEnabled();

	@Meta.AD(
		deflt = "elastic", description = "username-help", name = "username",
		required = false
	)
	public String username();

	@Meta.AD(
		description = "password-help", name = "password", required = false,
		type = Meta.Type.Password
	)
	public String password();

	@Meta.AD(
		deflt = "false", description = "http-ssl-enabled-help",
		name = "http-ssl-enabled", required = false
	)
	public boolean httpSSLEnabled();

	@Meta.AD(
		deflt = "pkcs12", description = "truststore-type-help",
		name = "truststore-type", required = false
	)
	public String truststoreType();

	@Meta.AD(
		deflt = "/path/to/localhost.p12", description = "truststore-path-help",
		name = "truststore-path", required = false
	)
	public String truststorePath();

	@Meta.AD(
		description = "truststore-password-help", name = "truststore-password",
		required = false, type = Meta.Type.Password
	)
	public String truststorePassword();

	@Meta.AD(
		description = "proxy-host-help", name = "proxy-host", required = false
	)
	public String proxyHost();

	@Meta.AD(
		deflt = "0", description = "proxy-port-help", name = "proxy-port",
		required = false
	)
	public int proxyPort();

	@Meta.AD(
		description = "proxy-username-help", name = "proxy-username",
		required = false
	)
	public String proxyUserName();

	@Meta.AD(
		description = "proxy-password-help", name = "proxy-password",
		required = false, type = Meta.Type.Password
	)
	public String proxyPassword();

}