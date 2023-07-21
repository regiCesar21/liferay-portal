/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Link from '../../../../src/main/resources/META-INF/resources/admin/js/components/ShareFormModal/Link.es';

const props = {
	spritemap: 'spritemap',
	url: 'publish/url',
};

describe('Link', () => {
	let component;

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders the default markup', () => {
		component = new Link(props);
		expect(component).toMatchSnapshot();
	});

	it("copies the sharable URL to user's clipboard", () => {
		component = new Link(props);
		component._clipboard.emit('success');

		jest.runAllTimers();

		expect(component.state.success).toBeTruthy();
		expect(component).toMatchSnapshot();

		document.querySelector('.ddm-copy-clipboard').click();
	});
});
