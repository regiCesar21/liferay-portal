/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {deleteMessageQuery} from '../utils/client.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';

export default ({comment, commentChange, editable = true}) => {
	const [deleteMessage] = useMutation(deleteMessageQuery, {
		onCompleted() {
			if (commentChange) {
				commentChange(comment);
			}
		},
	});

	return (
		<div className="c-my-3 questions-reply row">
			<div className="align-items-md-center col-2 col-md-1 d-flex justify-content-end justify-content-md-center">
				<ClayIcon
					className="c-mt-3 c-mt-md-0 questions-reply-icon text-secondary"
					symbol="reply"
				/>
			</div>

			<div className="col-10 col-lg-11">
				<div className="c-mb-0">
					<ArticleBodyRenderer
						{...comment}
						signature={comment.creator.name}
					/>
				</div>

				{editable && comment.actions.delete && (
					<ClayButton
						className="c-mt-3 font-weight-bold text-secondary"
						displayType="unstyled"
						onClick={() => {
							deleteMessage({
								variables: {messageBoardMessageId: comment.id},
							});
						}}
					>
						{Liferay.Language.get('delete')}
					</ClayButton>
				)}
			</div>
		</div>
	);
};
