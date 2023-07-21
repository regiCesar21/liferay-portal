/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import colors from '../../utils/colors.es';

const MAX_LABELS = 10;

const Label = ({active, color, index, label, onMouseOut, onMouseOver}) => (
	<li className={active ? '' : 'dim'} data-item={index}>
		<svg height="20" width="20">
			<circle
				cx="10"
				cy="10"
				fill={color}
				onMouseOut={onMouseOut}
				onMouseOver={onMouseOver}
				r="9"
				strokeWidth="3"
			/>
		</svg>

		<p onMouseOut={onMouseOut} onMouseOver={onMouseOver} x="0em" y="1em">
			<span dy="2em" x="2.5em">
				{label}
			</span>
		</p>
	</li>
);

const ShowAll = ({expand, expanded, labelsLength}) => {
	if (labelsLength <= MAX_LABELS) {
		return null;
	}

	const toggleExpanded = () => {
		expand(!expanded);
	};

	return (
		<div className="show-more-action">
			<svg height="20" width="20">
				<g>
					<circle
						cx="10"
						cy="10"
						fill={'#F0F5FF'}
						r="9"
						strokeWidth="3"
					/>

					<ClayIcon
						symbol={expanded ? 'caret-top-l' : 'caret-bottom-l'}
					/>
				</g>
			</svg>

			<ClayButton
				className="show-more-button"
				displayType="unstyled"
				onClick={() => toggleExpanded()}
			>
				{expanded
					? Liferay.Language.get('show-less')
					: Liferay.Language.get('show-all')}
			</ClayButton>
		</div>
	);
};

export default ({activeIndex, labels, onMouseOut, onMouseOver}) => {
	const [isShowAllExpanded, expandShowAll] = useState(false);

	const handleOnMouseOver = ({currentTarget}) => {
		const item = currentTarget.closest('li').dataset.item;
		onMouseOver(parseInt(item, 10));
	};

	return (
		<div className="legend-container well well-lg">
			<div className="legend-list">
				<ul>
					{labels
						.slice(
							0,
							isShowAllExpanded ? labels.length : MAX_LABELS
						)
						.map((label, index) => (
							<Label
								active={
									activeIndex === null ||
									activeIndex === index
								}
								color={colors(index)}
								index={index}
								key={`item-${index}`}
								label={label}
								onMouseOut={onMouseOut}
								onMouseOver={handleOnMouseOver}
							/>
						))}

					<ShowAll
						expand={expandShowAll}
						expanded={isShowAllExpanded}
						labelsLength={labels.length}
					/>
				</ul>
			</div>
		</div>
	);
};
