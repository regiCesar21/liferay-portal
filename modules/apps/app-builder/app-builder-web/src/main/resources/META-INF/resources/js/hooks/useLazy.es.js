/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Suspense, lazy} from 'react';

import useLoader from './useLoader.es';

export default function useLazy(hideLoading) {
	const load = useLoader();

	return ({module, props, ...otherProps}) => {
		const Component = lazy(() => load(module));

		return (
			<ErrorBoundary {...otherProps}>
				<Suspense
					fallback={hideLoading ? <></> : <ClayLoadingIndicator />}
				>
					<Component {...props} />
				</Suspense>
			</ErrorBoundary>
		);
	};
}

export class ErrorBoundary extends React.Component {
	constructor(props) {
		super(props);
		this.state = {error: null, hasError: false};
	}

	componentDidCatch(error) {
		this.setState({hasError: true});

		const {onError} = this.props;

		if (onError && typeof onError === 'function') {
			onError(error);
		}
	}

	handleReload() {
		const {onReload} = this.props;

		if (onReload && typeof onReload === 'function') {
			onReload();
		}
		else {
			window.location.reload();
		}
	}

	render() {
		if (this.state.hasError) {
			return (
				<ClayButton
					block
					displayType="secondary"
					onClick={this.handleReload.bind(this)}
				>
					{Liferay.Language.get('refresh')}
				</ClayButton>
			);
		}

		return this.props.children;
	}
}
