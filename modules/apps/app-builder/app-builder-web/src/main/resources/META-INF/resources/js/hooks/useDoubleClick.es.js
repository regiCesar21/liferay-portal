/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';

const useCancellablePromises = () => {
	const pendingPromises = useRef([]);

	const append = (promise) => {
		pendingPromises.current = [...pendingPromises.current, promise];
	};

	const remove = (promise) => {
		pendingPromises.current = pendingPromises.current.filter(
			(p) => p !== promise
		);
	};

	const clear = () => {
		pendingPromises.current.map((p) => p.cancel());
	};

	return {
		append,
		clear,
		remove,
	};
};

const cancellablePromise = (promise) => {
	let isCanceled = false;

	const wrappedPromise = new Promise((resolve, reject) => {
		promise.then(
			(value) =>
				isCanceled ? reject({isCanceled, value}) : resolve(value),
			(error) => reject({error, isCanceled})
		);
	});

	return {
		cancel: () => {
			isCanceled = true;
		},
		promise: wrappedPromise,
	};
};

export default (onClick, onDoubleClick) => {
	const promises = useCancellablePromises();

	const handleOnClick = (event) => {
		promises.clear();

		const waitForClick = cancellablePromise(
			new Promise((resolve) => setTimeout(resolve, 300))
		);

		promises.append(waitForClick);

		return waitForClick.promise
			.then(() => {
				promises.remove(waitForClick);
				onClick(event);
			})
			.catch((e) => {
				promises.remove(waitForClick);

				if (!e.isCanceled) {
					throw e.error;
				}
			});
	};

	const handleOnDoubleClick = (event) => {
		promises.clear();
		onDoubleClick(event);
	};

	return [handleOnClick, handleOnDoubleClick];
};
