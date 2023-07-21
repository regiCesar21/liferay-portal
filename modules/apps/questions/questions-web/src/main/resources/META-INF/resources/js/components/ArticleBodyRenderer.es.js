/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import parser from 'bbcode-to-react';
import React, {useEffect, useState} from 'react';

import Highlight from './Highlight.es';

export default ({
	articleBody,
	compactMode = false,
	encodingFormat,
	id,
	signature,
}) => {
	const [
		articleBodyContainsParagraph,
		setArticleBodyContainsParagraph,
	] = useState(true);

	useEffect(() => {
		setArticleBodyContainsParagraph(articleBody.includes('<p>'));
	}, [articleBody]);

	return (
		<>
			{encodingFormat === 'bbcode' && (
				<div className={`questions-article-body-${id}`}>
					<div>{parser.toReact(articleBody)}</div>
				</div>
			)}
			{encodingFormat !== 'bbcode' && compactMode && (
				<div
					className={`questions-article-body-${id}`}
					dangerouslySetInnerHTML={{__html: articleBody}}
				/>
			)}
			{encodingFormat !== 'bbcode' && !compactMode && (
				<div className={`cke_readonly questions-article-body-${id}`}>
					<Highlight innerHTML={true}>{articleBody}</Highlight>
				</div>
			)}

			{signature && (
				<style
					dangerouslySetInnerHTML={{
						__html: `.questions-article-body-${id} ${
							articleBodyContainsParagraph ? 'p' : 'div'
						}:last-child:after {content: " - ${signature}"; font-weight: bold;}`,
					}}
				/>
			)}
		</>
	);
};
