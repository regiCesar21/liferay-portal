/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent} from '@testing-library/react';

// import React, {useState} from 'react';

// import {InstanceListContext} from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
// import {ModalContext} from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
// import SingleTransitionModal from '../../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/transition/single/SingleTransitionModal.es';
// import ToasterProvider from '../../../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
// import {MockRouter} from '../../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

// const ContainerMock = ({children}) => {
// 	const selectedInstance = {
// 		assetTitle: 'Blog1',
// 		assetType: 'Blogs Entry',
// 		assignees: [{id: 2, name: 'Test Test'}],
// 		id: 1,
// 		status: 'In Progress',
// 		taskNames: ['Review'],
// 	};
// 	const [singleTransition, setSingleTransition] = useState({
// 		title: 'Test',
// 		transitionName: 'test',
// 	});

// 	return (
// 		<InstanceListContext.Provider
// 			value={{
// 				selectedInstance,
// 			}}
// 		>
// 			<ModalContext.Provider
// 				value={{
// 					setSingleTransition,
// 					singleTransition,
// 					visibleModal: 'singleTransition',
// 				}}
// 			>
// 				<ToasterProvider>{children}</ToasterProvider>
// 			</ModalContext.Provider>
// 		</InstanceListContext.Provider>
// 	);
// };

let getByPlaceholderText, getByText;

// const items = [
// 	{
// 		id: 2,
// 		label: 'Test',
// 		name: 'test',
// 	},
// ];

// const clientMock = {
// 	get: jest.fn().mockResolvedValueOnce({
// 		data: {
// 			items,
// 			totalCount: 1,
// 		},
// 	}),
// 	post: jest
// 		.fn()
// 		.mockRejectedValueOnce(new Error('Request failed'))
// 		.mockResolvedValue({data: {items: []}}),
// };

describe('The SingleTransitionModal component should', () => {

	// beforeAll(() => {
	// 	const renderResult = render(
	// 		<MockRouter client={clientMock}>
	// 			<SingleTransitionModal />
	// 		</MockRouter>,
	// 		{
	// 			wrapper: ContainerMock,
	// 		}
	// 	);

	// 	getByPlaceholderText = renderResult.getByPlaceholderText;
	// 	getByText = renderResult.getByText;

	// 	jest.runAllTimers();
	// });

	test.skip('Be rendered when its attribute visible is "true"', () => {
		const transitionModal = getByText('Test');
		expect(transitionModal).toBeInTheDocument();
	});

	test.skip('Change comment field value, click in "Done" button', () => {
		const commentField = getByPlaceholderText('comment');
		const doneButton = getByText('done');

		fireEvent.change(commentField, {target: {value: 'Comment field test'}});

		expect(commentField).toHaveValue('Comment field test');

		fireEvent.click(doneButton);
	});

	test.skip('Show error alert after failing request and click in "Done" to retry request', () => {
		const alertError = getByText(
			'your-request-has-failed select-done-to-retry'
		);
		const doneButton = getByText('done');

		expect(alertError).toBeTruthy();

		fireEvent.click(doneButton);
	});

	test.skip('Show success alert message after post request success', () => {
		const alertToast = document.querySelector('.alert-dismissible');

		expect(alertToast).toHaveTextContent(
			'the-selected-step-has-transitioned-successfully'
		);
	});
});
