/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';

/*eslint-disable no-unused-vars*/
import React, {useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getSectionByMessageQuery} from '../utils/client.es';
import {historyPushWithSlug} from '../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId},
		},
	}) => {
		const context = useContext(AppContext);

		const historyPushParser = historyPushWithSlug(history.push);

		useQuery(getSectionByMessageQuery, {
			onCompleted({messageBoardMessage}) {
				historyPushParser(
					`/questions/${
						context.useTopicNamesInURL
							? messageBoardMessage.messageBoardThread
									.messageBoardSection.title
							: messageBoardMessage.messageBoardThread
									.messageBoardSection.id
					}/${messageBoardMessage.friendlyUrlPath}`
				);
			},
			variables: {
				messageBoardMessageId: questionId,
			},
		});

		return null;
	}
);
