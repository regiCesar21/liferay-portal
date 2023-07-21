/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const ButtonInfo = ({items}) => {
	return (
		<small className="button-info">
			{items.map(
				({label, name}, index) =>
					name && (
						<div key={index}>
							<span className="font-weight-bold">{`${label}: `}</span>
							{name}
						</div>
					)
			)}
		</small>
	);
};

export default ButtonInfo;
