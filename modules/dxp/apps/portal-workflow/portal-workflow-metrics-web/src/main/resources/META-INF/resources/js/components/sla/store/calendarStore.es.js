/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {baseURL, headers} from '../../../shared/rest/fetch.es';

class CalendarStore {
	constructor() {
		this.state = {
			calendars: []
		};
	}

	fetchCalendars() {
		return fetch(`${baseURL}/calendars`, {
			headers,
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				this.setState({
					calendars: data.items
				});
			});
	}

	get defaultCalendar() {
		const defaultCalendars = this.state.calendars.filter(
			calendar => calendar.defaultCalendar
		);

		return defaultCalendars.length ? defaultCalendars[0] : {};
	}

	getState() {
		return this.state;
	}

	setState(props) {
		this.state = {...this.getState(), ...props};
	}
}

export default new CalendarStore();
export {CalendarStore};
