/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	PortletBase,
	addParams,
	navigate,
	openSelectionModal,
} from 'frontend-js-web';

class SharedAssets extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this._selectAssetTypeURL = config.selectAssetTypeURL;
		this._viewAssetTypeURL = config.viewAssetTypeURL;
	}

	attached() {
		Liferay.on('sharing:changed', () =>
			Liferay.Portlet.refresh('#p_p_id' + this.namespace)
		);
	}

	handleFilterItemClicked(event) {
		const itemData = event.data.item.data;
		const namespace = this.namespace;
		const viewAssetTypeURL = this._viewAssetTypeURL;

		if (itemData && itemData.action === 'openAssetTypesSelector') {
			openSelectionModal({
				onSelect: (selectedItem) => {
					if (selectedItem) {
						let uri = viewAssetTypeURL;

						uri = addParams(
							namespace + 'className=' + selectedItem.value,
							uri
						);

						navigate(uri);
					}
				},
				selectEventName: namespace + 'selectAssetType',
				title: Liferay.Language.get('select-asset-type'),
				url: this._selectAssetTypeURL,
			});
		}
	}
}

export default SharedAssets;
