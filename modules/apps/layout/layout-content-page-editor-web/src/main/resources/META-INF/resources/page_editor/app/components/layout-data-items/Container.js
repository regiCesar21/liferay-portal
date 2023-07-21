/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import selectLanguageId from '../../selectors/selectLanguageId';
import InfoItemService from '../../services/InfoItemService';
import {useSelector} from '../../store/index';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import loadBackgroundImage from '../../utils/loadBackgroundImage';

const Container = React.forwardRef(
	({children, className, data, item, withinTopper = false}, ref) => {
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const itemConfig = getResponsiveConfig(
			item.config,
			selectedViewportSize
		);

		const {
			backgroundColor,
			backgroundImage,
			borderColor,
			borderRadius,
			borderWidth,
			fontFamily,
			fontSize,
			fontWeight,
			height,
			marginBottom,
			marginLeft,
			marginRight,
			marginTop,
			maxHeight,
			maxWidth,
			minHeight,
			minWidth,
			opacity,
			overflow,
			paddingBottom,
			paddingLeft,
			paddingRight,
			paddingTop,
			shadow,
			textAlign,
			textColor,
			width,
		} = itemConfig.styles;

		const {widthType} = itemConfig;

		const languageId = useSelector(selectLanguageId);
		const [backgroundImageValue, setBackgroundImageValue] = useState('');
		const [link, setLink] = useState(null);

		useEffect(() => {
			loadBackgroundImage(backgroundImage).then(setBackgroundImageValue);
		}, [backgroundImage]);

		useEffect(() => {
			if (!itemConfig.link) {
				return;
			}

			if (itemConfig.link.href) {
				setLink(itemConfig.link);
			}
			else if (itemConfig.link.fieldId) {
				InfoItemService.getInfoItemFieldValue({
					...itemConfig.link,
					languageId,
					onNetworkStatus: () => {},
				}).then(({fieldValue}) => {
					setLink({
						href: fieldValue,
						target: itemConfig.link.target,
					});
				});
			}
		}, [itemConfig.link, languageId]);

		const style = {
			boxSizing: 'border-box',
		};

		style.backgroundColor = getFrontendTokenValue(backgroundColor);
		style.border = `solid ${borderWidth}px`;
		style.borderColor = getFrontendTokenValue(borderColor);
		style.borderRadius = getFrontendTokenValue(borderRadius);
		style.color = getFrontendTokenValue(textColor);
		style.fontFamily = getFrontendTokenValue(fontFamily);
		style.fontSize = getFrontendTokenValue(fontSize);
		style.fontWeight = getFrontendTokenValue(fontWeight);
		style.height = height;
		style.maxHeight = maxHeight;
		style.minHeight = minHeight;
		style.opacity = opacity ? opacity / 100 : null;
		style.overflow = overflow;

		if (!withinTopper) {
			style.boxShadow = getFrontendTokenValue(shadow);
			style.maxWidth = maxWidth;
			style.minWidth = minWidth;
			style.width = width;
		}

		if (backgroundImageValue) {
			style.backgroundImage = `url(${backgroundImageValue})`;
			style.backgroundPosition = '50% 50%';
			style.backgroundRepeat = 'no-repeat';
			style.backgroundSize = 'cover';
		}

		const content = (
			<div
				{...(link ? {} : data)}
				className={classNames(
					className,
					`mb-${marginBottom || 0}`,
					`mt-${marginTop || 0}`,
					`pb-${paddingBottom || 0}`,
					`pl-${paddingLeft || 0}`,
					`pr-${paddingRight || 0}`,
					`pt-${paddingTop || 0}`,
					{
						container: widthType === 'fixed',
						empty: !item.children.length && !height,
						[`bg-${backgroundColor}`]:
							backgroundColor && !backgroundColor.startsWith('#'),
						[`ml-${marginLeft || 0}`]:
							widthType !== 'fixed' && !withinTopper,
						[`mr-${marginRight || 0}`]:
							widthType !== 'fixed' && !withinTopper,
						[textAlign
							? textAlign.startsWith('text-')
								? textAlign
								: `text-${textAlign}`
							: '']: textAlign,
					}
				)}
				ref={ref}
				style={style}
			>
				{children}
			</div>
		);

		return link ? (
			<a
				{...data}
				href={link.href}
				style={{color: 'inherit', textDecoration: 'none'}}
				target={link.target}
			>
				{content}
			</a>
		) : (
			content
		);
	}
);

Container.displayName = 'Container';

Container.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({}),
	}).isRequired,
};

export default Container;
