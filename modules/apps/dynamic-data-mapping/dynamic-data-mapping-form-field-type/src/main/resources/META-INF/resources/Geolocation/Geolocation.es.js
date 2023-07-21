/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'leaflet/dist/leaflet.css';
import React from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {MAP_PROVIDER, useGeolocation} from './useGeolocation.es';

const geolocateTitle = Liferay.Language.get('geolocate');
const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		return <div {...this.props} />;
	}
}

const Geolocation = ({
	disabled,
	googleMapsAPIKey,
	instanceId,
	mapProviderKey,
	name,
	onChange,
	value,
	viewMode,
	...otherProps
}) => {
	useGeolocation({
		disabled,
		googleMapsAPIKey,
		instanceId,
		mapProviderKey,
		name,
		onChange,
		value,
		viewMode,
	});

	return (
		<div {...otherProps} className="ddm-geolocation field-labels-inline">
			{!disabled || viewMode ? (
				<dl>
					<dt className="text-capitalize"></dt>
					<dd>
						<NoRender
							className="lfr-map"
							id={`map_${instanceId}`}
							style={{height: '280px'}}
						/>
						<input
							id={`input_value_${instanceId}`}
							name={name}
							type="hidden"
						/>
					</dd>
				</dl>
			) : (
				<img
					alt={pathThemeImages}
					src={`${pathThemeImages}/common/geolocation.png`}
					title={geolocateTitle}
				/>
			)}
		</div>
	);
};

const Main = ({
	googleMapsAPIKey,
	instanceId,
	mapProviderKey = MAP_PROVIDER.openStreetMap,
	name,
	onChange,
	readOnly,
	value,
	viewMode,
	...otherProps
}) => (
	<FieldBase {...otherProps} name={name} style={null}>
		<Geolocation
			disabled={readOnly}
			googleMapsAPIKey={googleMapsAPIKey}
			instanceId={instanceId}
			mapProviderKey={mapProviderKey}
			name={name}
			onChange={(value) => onChange({}, value)}
			value={value}
			viewMode={viewMode}
		/>
	</FieldBase>
);

Main.displayName = 'Geolocation';

export default Main;
