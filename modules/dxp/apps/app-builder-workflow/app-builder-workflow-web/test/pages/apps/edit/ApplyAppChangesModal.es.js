/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useState} from 'react';

import ApplyAppChangesModal from '../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/ApplyAppChangesModal.es';

const ContainerMock = ({children}) => {
	const [isAppChangesModalVisible, setAppChangesModalVisible] = useState(
		true
	);

	return (
		<EditAppContext.Provider
			value={{isAppChangesModalVisible, setAppChangesModalVisible}}
		>
			{children}
		</EditAppContext.Provider>
	);
};

describe('ApplyChangesModal', () => {
	const mockOnSave = jest.fn().mockImplementation((callback) => callback());

	afterEach(cleanup);

	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('Sets its own visibility on closing', async () => {
		const {getByText} = render(
			<ApplyAppChangesModal onSave={mockOnSave} />,
			{
				wrapper: ContainerMock,
			}
		);

		act(() => {
			jest.runAllTimers();
		});

		const cancelButton = getByText('cancel');

		await fireEvent.click(cancelButton);

		act(() => {
			jest.runAllTimers();
		});

		expect(cancelButton).not.toBeInTheDocument();
	});

	it('Sets its own visibility on closing', async () => {
		const {getByText} = render(
			<ApplyAppChangesModal onSave={mockOnSave} />,
			{
				wrapper: ContainerMock,
			}
		);

		act(() => {
			jest.runAllTimers();
		});

		const saveButton = getByText('save');

		await fireEvent.click(saveButton);

		act(() => {
			jest.runAllTimers();
		});

		expect(saveButton).not.toBeInTheDocument();
	});
});
