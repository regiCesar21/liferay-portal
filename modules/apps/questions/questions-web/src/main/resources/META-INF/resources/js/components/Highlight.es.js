/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hljs from 'highlight.js';
import React, {useEffect, useRef} from 'react';

function Highlight(props) {
	const {children, element: Element, innerHTML} = props;
	const el = useRef(null);

	hljs.configure({
		languages: [
			'language-java',
			'language-javascript',
			'html',
			'plaintext',
		],
	});

	const highlightCode = () => {
		if (el.current) {
			const nodes = el.current.querySelectorAll('pre code');
			for (let i = 0; i < nodes.length; i++) {
				hljs.highlightBlock(nodes[i]);
			}
		}
	};

	useEffect(highlightCode);
	const elProps = {ref: el};

	if (innerHTML) {
		elProps.dangerouslySetInnerHTML = {__html: children};
		if (Element) {
			return <Element {...elProps} />;
		}

		return <div {...elProps} />;
	}
	if (Element) {
		return <Element {...elProps}>{children}</Element>;
	}

	return (
		<pre ref={el}>
			<code>{children}</code>
		</pre>
	);
}

export default Highlight;
