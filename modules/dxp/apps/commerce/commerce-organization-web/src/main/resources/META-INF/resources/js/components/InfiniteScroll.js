/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {bindAll} from '../utils/utils';
import w from '../utils/window';

class InfiniteScroll extends Component {
	constructor(props) {
		super(props);

		bindAll(this, 'handleScroll_', 'shouldScroll_');

		this.debouncedScrollHandler_ = debounce(this.handleScroll_, props.wait);
	}

	componentDidMount() {
		this.attachScrollHandler_();

		if (this.props.hasMoreResults) {
			this.debouncedScrollHandler_();
		}
	}

	componentWillUnmount() {
		if (this._request) {
			this._request.cancel();
		}

		this.debouncedScrollHandler_.cancel();

		this.detachScrollHandler_();
	}

	attachScrollHandler_(forceAttach) {
		const {attachToElement, hasMoreResults} = this.props;

		if (hasMoreResults || forceAttach) {
			attachToElement().addEventListener(
				'scroll',
				this.debouncedScrollHandler_
			);
		}
	}

	detachScrollHandler_() {
		this.props
			.attachToElement()
			.removeEventListener('scroll', this.debouncedScrollHandler_);
	}

	handleScroll_() {
		const {onScrollEnd} = this.props;

		if (!this.state.loading_ && onScrollEnd && this.shouldScroll_()) {
			this.setState({loading_: true});

			this._request = onScrollEnd().then(() => {
				this.setState({loading_: false});
			});
		}
	}

	shouldScroll_() {
		let shouldScroll = false;

		const scrollContainer = this.element;

		if (scrollContainer && scrollContainer.offsetParent) {
			shouldScroll =
				scrollContainer.getBoundingClientRect().bottom -
					w.innerHeight -
					this.props.scrollOffset <
				0;
		}

		return shouldScroll;
	}

	syncHasMoreResults(value) {
		if (value) {
			this.attachScrollHandler_(value);
		}
		else {
			this.debouncedScrollHandler_.cancel();

			this.detachScrollHandler_();
		}
	}

	render() {
		return <div>{this.props.children}</div>;
	}
}

InfiniteScroll.defaultProps = {
	attachToElement: () => w,
	hasMoreResults: true,
	loading_: false,
	scrollOffset: 0,
	wait: 100,
};

InfiniteScroll.propTypes = {
	attachToElement: PropTypes.func,
	hasMoreResults: PropTypes.bool,
	onScrollEnd: PropTypes.func,
	scrollOffset: PropTypes.number,
	wait: PropTypes.number,
};

export default InfiniteScroll;
