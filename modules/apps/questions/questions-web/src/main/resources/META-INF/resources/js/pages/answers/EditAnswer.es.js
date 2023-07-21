/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLazyQuery, useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import QuestionsEditor from '../../components/QuestionsEditor';
import TextLengthValidation from '../../components/TextLengthValidation.es';
import {getMessageQuery, updateMessageQuery} from '../../utils/client.es';
import {getContextLink, stripHTML} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {answerId, questionId, sectionTitle},
		},
	}) => {
		const context = useContext(AppContext);

		const [getMessage, {data}] = useLazyQuery(getMessageQuery, {
			fetchPolicy: 'network-only',
			variables: {friendlyUrlPath: answerId, siteKey: context.siteKey},
		});

		const [articleBody, setArticleBody] = useState('');
		const [id, setId] = useState('');

		useEffect(() => {
			setId((data && data.messageBoardMessageByFriendlyUrlPath.id) || '');
		}, [data]);

		const [addUpdateMessage] = useMutation(updateMessageQuery, {
			context: getContextLink(`${sectionTitle}/${questionId}`),
			onCompleted() {
				history.goBack();
			},
			update(proxy) {
				proxy.evict(`MessageBoardMessage:${id}`);
				proxy.gc();
			},
		});

		return (
			<section className="c-mt-5 questions-section questions-sections-answer">
				<div className="questions-container">
					<div className="row">
						<div className="c-mx-auto col-xl-10">
							<h1>{Liferay.Language.get('edit-answer')}</h1>

							<ClayForm>
								<ClayForm.Group className="c-mt-4">
									<label htmlFor="basicInput">
										{Liferay.Language.get('answer')}

										<span className="c-ml-2 reference-mark">
											<ClayIcon symbol="asterisk" />
										</span>
									</label>

									<QuestionsEditor
										contents={
											data &&
											data
												.messageBoardMessageByFriendlyUrlPath
												.articleBody
										}
										onChange={(event) =>
											setArticleBody(
												event.editor.getData()
											)
										}
										onInstanceReady={() => getMessage()}
									/>

									<ClayForm.FeedbackGroup>
										<ClayForm.FeedbackItem>
											<TextLengthValidation
												text={articleBody}
											/>
										</ClayForm.FeedbackItem>
									</ClayForm.FeedbackGroup>
								</ClayForm.Group>
							</ClayForm>

							<div className="c-mt-4 d-flex flex-column-reverse flex-sm-row">
								<ClayButton
									className="c-mt-4 c-mt-sm-0"
									disabled={
										!articleBody ||
										stripHTML(articleBody).length < 15
									}
									displayType="primary"
									onClick={() => {
										addUpdateMessage({
											variables: {
												articleBody,
												messageBoardMessageId:
													data
														.messageBoardMessageByFriendlyUrlPath
														.id,
											},
										});
									}}
								>
									{Liferay.Language.get('update-your-answer')}
								</ClayButton>

								<ClayButton
									className="c-ml-sm-3"
									displayType="secondary"
									onClick={() => history.goBack()}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</div>
						</div>
					</div>
				</div>
			</section>
		);
	}
);
