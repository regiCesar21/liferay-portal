/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import React, {useCallback, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {createCommentQuery} from '../utils/client.es';
import {getContextLink, stripHTML} from '../utils/utils.es';
import Comment from './Comment.es';
import QuestionsEditor from './QuestionsEditor';
import TextLengthValidation from './TextLengthValidation.es';

export default withRouter(
	({
		comments,
		commentsChange,
		editable = true,
		entityId,
		match: {
			params: {questionId, sectionTitle},
		},
		showNewComment,
		showNewCommentChange,
	}) => {
		const [comment, setComment] = useState('');

		const [createComment] = useMutation(createCommentQuery, {
			context: getContextLink(`${sectionTitle}/${questionId}`),
			onCompleted(data) {
				setComment('');
				showNewCommentChange(false);
				commentsChange([
					...comments,
					data.createMessageBoardMessageMessageBoardMessage,
				]);
			},
		});

		const _commentChange = useCallback(
			(comment) => {
				if (commentsChange) {
					return commentsChange([
						...comments.filter((o) => o.id !== comment.id),
					]);
				}

				return null;
			},
			[commentsChange, comments]
		);

		return (
			<div>
				{comments.map((comment) => (
					<Comment
						comment={comment}
						commentChange={_commentChange}
						editable={editable}
						key={comment.id}
					/>
				))}

				{editable && showNewComment && (
					<>
						<ClayForm.Group small>
							<QuestionsEditor
								contents={comment}
								onChange={(event) => {
									setComment(event.editor.getData());
								}}
							/>

							<TextLengthValidation text={comment} />

							<ClayButton.Group className="c-mt-3" spaced>
								<ClayButton
									disabled={stripHTML(comment).length < 15}
									displayType="primary"
									onClick={() => {
										createComment({
											variables: {
												articleBody: comment,
												parentMessageBoardMessageId: entityId,
											},
										});
									}}
								>
									{Liferay.Language.get('reply')}
								</ClayButton>

								<ClayButton
									displayType="secondary"
									onClick={() => showNewCommentChange(false)}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayButton.Group>
						</ClayForm.Group>
					</>
				)}
			</div>
		);
	}
);
