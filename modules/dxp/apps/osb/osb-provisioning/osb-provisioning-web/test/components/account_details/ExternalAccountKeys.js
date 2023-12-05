/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ExternalAccountKeys from '../../../src/main/resources/META-INF/resources/js/components/account_details/ExternalAccountKeys';
import {PermissionsProvider} from '../../../src/main/resources/META-INF/resources/js/hooks/permissions';

function renderExternalAccountKeys(permission = true) {
	return render(
		<PermissionsProvider permissions={{updatePermission: permission}}>
			<ExternalAccountKeys
				details={{
					analyticsCloudGroupId: 'testAnalyticsCloudGroupId',
					dossieraAccountKey: 'testDossieraAccountKey',
					dxpCloudProjectId: 'testDxpCloudProjectId',
					key: '123',
					relatedSalesforceProjectKey:
						'testRelatedSalesforceProjectKey',
					salesforceProjectKey: 'testSalesForceProjectKey',
					updateAnalyticsCloudGroupURL:
						'/update/analytics-cloud/group',
					updateDossieraAccountURL: '/update/dossiera/account',
					updateDxpCloudProjectURL: '/update/dxp-cloud/project',
					updateRelatedSalesforceProjectURL:
						'/update/salesforce/related-project',
					updateSalesforceProjectURL: '/update/salesforce/project'
				}}
			/>
		</PermissionsProvider>
	);
}

describe('ExternalAccountKeys', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = renderExternalAccountKeys();

		expect(container).toBeTruthy();
	});

	it('displays Analytics Cloud Group field with the correct value', () => {
		const {getByText} = renderExternalAccountKeys();

		getByText('analytics-cloud-group');
		getByText('testAnalyticsCloudGroupId');
	});

	it('displays Dossiera Account field with the correct value', () => {
		const {getByText} = renderExternalAccountKeys();

		getByText('dossiera-account');
		getByText('testDossieraAccountKey');
	});

	it('displays DXP Cloud Project field with the correct value', () => {
		const {getByText} = renderExternalAccountKeys();

		getByText('dxp-cloud-project');
		getByText('testDxpCloudProjectId');
	});

	it('displays Related Salesforce Project field with the correct value', () => {
		const {getByText} = renderExternalAccountKeys();

		getByText('related-salesforce-project');
		getByText('testRelatedSalesforceProjectKey');
	});

	it('displays Salesforce Project field with the correct value', () => {
		const {getByText} = renderExternalAccountKeys();

		getByText('salesforce-project');
		getByText('testSalesForceProjectKey');
	});

	describe('ExternalAccountKeys with full editing privilege', () => {
		it('allows the Dossiera Account field to be editable', () => {
			const {getByText} = renderExternalAccountKeys();

			fireEvent.click(getByText('testDossieraAccountKey'));

			getByText('save');
			getByText('cancel');
		});
	});

	describe('ExternalAccountKeys with limited editing privilege', () => {
		it('does not allow the Dossiera Account field to be editable', () => {
			const {getByText, queryByText} = renderExternalAccountKeys(false);

			fireEvent.click(getByText('testDossieraAccountKey'));

			expect(queryByText('save')).toBeFalsy();
			expect(queryByText('cancel')).toBeFalsy();
		});
	});
});
