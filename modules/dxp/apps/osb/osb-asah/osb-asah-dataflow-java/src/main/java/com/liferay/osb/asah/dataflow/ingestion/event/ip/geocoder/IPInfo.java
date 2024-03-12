/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.ingestion.event.ip.geocoder;

import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;

/**
 * @author Inácio Nery
 */
public class IPInfo {

	public static final IPInfo LOCAL_NETWORK = new IPInfo("Local Network");

	public IPInfo(CityResponse cityResponse) {
		City city = cityResponse.getCity();

		_city = city.getName();

		Country country = cityResponse.getCountry();

		_country = country.getName();

		Subdivision subdivision = cityResponse.getMostSpecificSubdivision();

		_region = subdivision.getName();
	}

	public String getCity() {
		return _city;
	}

	public String getCountry() {
		return _country;
	}

	public String getRegion() {
		return _region;
	}

	private IPInfo(String location) {
		_city = location;
		_country = location;
		_region = location;
	}

	private final String _city;
	private final String _country;
	private final String _region;

}