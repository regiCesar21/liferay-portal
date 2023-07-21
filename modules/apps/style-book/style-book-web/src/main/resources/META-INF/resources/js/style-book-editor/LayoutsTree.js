/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import {globalEval} from 'metal-dom';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {StyleBookContext} from './StyleBookContext';
import {config} from './config';

export default function LayoutsTree({showPrivateLayouts}) {
	const [loading, setLoading] = useState(true);
	const [content, setContent] = useState('');
	const isMounted = useIsMounted();
	const {setPreviewLayout} = useContext(StyleBookContext);

	useEffect(() => {
		setLoading(true);

		Liferay.destroyComponent(`${config.namespace}layoutsTree`);

		const url = new URL(config.layoutsTreeURL);

		url.searchParams.set(
			`${config.namespace}privateLayout`,
			showPrivateLayouts
		);

		fetch(url.href)
			.then((response) => response.text())
			.then((content) => {
				if (isMounted()) {
					setContent(content);
					setLoading(false);
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [isMounted, showPrivateLayouts]);

	return loading ? (
		<ClayLoadingIndicator />
	) : (
		<LayoutsTreeContent content={content} onPageClick={setPreviewLayout} />
	);
}

class LayoutsTreeContent extends React.Component {
	constructor(props) {
		super(props);

		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			globalEval.runScriptsInElement(this._ref.current);

			this._ref.current.addEventListener('change', this._handleOnChange);
		}
	}
	shouldComponentUpdate() {
		return false;
	}

	render() {
		return (
			<div
				className="style-book-editor__page-tree"
				dangerouslySetInnerHTML={{__html: this.props.content}}
				onClick={(event) => {
					const target = event.nativeEvent?.target;

					if (target?.dataset?.label && target?.dataset?.url) {
						this.props.onPageClick({
							layoutName: target.dataset.label,
							layoutURL: target.dataset.url,
						});
					}
				}}
				ref={this._ref}
			/>
		);
	}
}

LayoutsTree.propTypes = {
	showPrivateLayouts: PropTypes.bool.isRequired,
};
