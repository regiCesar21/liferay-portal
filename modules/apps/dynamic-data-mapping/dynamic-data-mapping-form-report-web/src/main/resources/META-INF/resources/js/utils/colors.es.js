/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const COLORS = [
	'#4b9bff',
	'#af78ff',
	'#50d2a0',
	'#ffb46e',
	'#ff5f5f',
	'#ff73c3',
	'#9be169',
	'#ffd76e',
	'#5fc8ff',
	'#7785ff',
	'#004ad7',
	'#1c5629',
	'#234584',
	'#863a00',
	'#ab1010',
	'#d3d6e0',
	'#0053f0',
	'#226a33',
	'#294f98',
	'#9f4500',
	'#c31212',
	'#e2e4ea',
	'#0b5fff',
	'#287d3c',
	'#2e5aac',
	'#b95000',
	'#da1414',
	'#f1f2f5',
	'#80acff',
	'#5aca75',
	'#89a7e0',
	'#ff8f39',
	'#f48989',
	'#f7f8f9',
	'#b3cdff',
	'#edf9f0',
	'#eef2fa',
	'#fff4ec',
	'#feefef',
	'#393a4a',
];

const NAMED_COLORS = {
	blueDark: '#272833',
	gray: '#cdced9',
	lightBlue: '#4b9bff',
	white: '#ffffff',
};

export {NAMED_COLORS};

export default (index) => COLORS[index % COLORS.length];
