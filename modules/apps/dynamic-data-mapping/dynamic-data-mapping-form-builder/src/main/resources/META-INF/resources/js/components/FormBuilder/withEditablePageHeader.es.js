/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import Component from 'metal-jsx';

import {sub} from '../../util/strings.es';
import formBuilderProps from './props.es';

const withEditablePageHeader = (ChildComponent) => {
	class EditablePageHeader extends Component {
		getPages() {
			const {pages} = this.props;
			const total = pages.length;
			const visitor = new PagesVisitor(pages);
			let lastPageIndex = total;

			if (pages[pages.length - 1].contentRenderer == 'success') {
				lastPageIndex = total - 1;
			}

			return visitor.mapPages((page, pageIndex) => {
				return {
					...page,
					headerRenderer: 'editable',
					pageIndex,
					pagination: sub(Liferay.Language.get('page-x-of-x'), [
						pageIndex + 1,
						lastPageIndex,
					]),
					placeholder: Liferay.Language.get('page-title'),
					total,
				};
			});
		}

		render() {
			return (
				<div>
					<ChildComponent {...this.props} pages={this.getPages()} />
				</div>
			);
		}
	}

	EditablePageHeader.PROPS = {
		...formBuilderProps,
	};

	return EditablePageHeader;
};

export default withEditablePageHeader;
