/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

function Author({author: {authorId, name, url}}) {
	return (
		<div className="text-secondary">
			<ClaySticker
				className={classnames('c-mr-2 sticker-user-icon', {
					[`user-icon-color-${parseInt(authorId, 10) % 10}`]: !url,
				})}
				shape="circle"
				size="sm"
			>
				{url ? (
					<img alt={`${name}.`} className="sticker-img" src={url} />
				) : (
					<ClayIcon symbol="user" />
				)}
			</ClaySticker>
			{Liferay.Util.sub(Liferay.Language.get('authored-by-x'), name)}
		</div>
	);
}

function BasicInformation({
	author,
	canonicalURL,
	languageTag,
	publishDate,
	title,
}) {
	const formattedPublishDate = Intl.DateTimeFormat(languageTag, {
		day: 'numeric',
		month: 'long',
		year: 'numeric',
	}).format(new Date(publishDate));

	return (
		<div className="sidebar-section">
			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<ClayTooltipProvider>
						<span
							className="component-title text-truncate-inline"
							data-tooltip-align="top"
							title={title}
						>
							<span className="text-truncate">{title}</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<ClayTooltipProvider>
						<span
							className="text-truncate-inline"
							data-tooltip-align="top"
							title={canonicalURL}
						>
							<span className="c-mb-2 c-mt-1 text-secondary text-truncate text-truncate-reverse">
								{canonicalURL}
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<p className="text-secondary">
						{Liferay.Util.sub(
							Liferay.Language.get('published-on-x'),
							formattedPublishDate
						)}
					</p>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<Author author={author} />
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</div>
	);
}

Author.propTypes = {
	author: PropTypes.object.isRequired,
};

BasicInformation.propTypes = {
	author: PropTypes.object.isRequired,
	canonicalURL: PropTypes.string.isRequired,
	languageTag: PropTypes.string.isRequired,
	publishDate: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};

export default BasicInformation;
