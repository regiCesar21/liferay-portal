/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getRelatedThreadsQuery} from '../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../utils/utils.es';
import Link from './Link.es';
import QuestionBadge from './QuestionsBadge.es';
import SectionLabel from './SectionLabel.es';
import UserIcon from './UserIcon.es';

export default withRouter(
	({
		match: {
			params: {sectionTitle},
		},
		question,
	}) => {
		const context = useContext(AppContext);

		const {data} = useQuery(getRelatedThreadsQuery, {
			variables: {
				search: question && question.headline,
				siteKey: context.siteKey,
			},
		});

		return (
			<>
				{data && !!data.messageBoardThreads.items.length && (
					<>
						<h2 className="c-mt-5 font-weight-light h3 text-secondary">
							Related Questions
						</h2>

						<hr />

						<div className="row">
							{data.messageBoardThreads.items
								.filter(
									(otherQuestion) =>
										otherQuestion.id !== question.id
								)
								.map((relatedQuestion) => (
									<div
										className="col-lg-3 col-md-4 col-sm-6 p-3 position-relative"
										key={relatedQuestion.id}
									>
										<div className="align-items-center d-flex justify-content-between stretched-link-layer">
											<SectionLabel
												section={
													relatedQuestion.messageBoardSection
												}
											/>

											<QuestionBadge
												className="text-secondary"
												symbol={
													normalizeRating(
														question.aggregateRating
													) < 0
														? 'caret-bottom'
														: 'caret-top'
												}
												value={normalizeRating(
													relatedQuestion.aggregateRating
												)}
											/>
										</div>

										<Link
											className="c-mt-2 d-block questions-title stretched-link text-reset"
											to={`/questions/${sectionTitle}/${relatedQuestion.friendlyUrlPath}`}
										>
											<h3
												className={classNames(
													'h2',
													'stretched-link-layer',
													{
														'question-seen':
															relatedQuestion.seen,
													}
												)}
											>
												{relatedQuestion.headline}

												{!!relatedQuestion.locked && (
													<span className="c-ml-2">
														<ClayIcon symbol="lock" />
													</span>
												)}
											</h3>
										</Link>

										<div className="c-mt-3 small stretched-link-layer">
											<UserIcon
												fullName={
													relatedQuestion.creator.name
												}
												portraitURL={
													relatedQuestion.creator
														.image
												}
												size="sm"
												userId={String(
													relatedQuestion.creator.id
												)}
											/>

											<span className="c-ml-2 font-weight-bold">
												{relatedQuestion.creator.name}
											</span>

											<span className="text-secondary">
												{' - ' +
													dateToInternationalHuman(
														relatedQuestion.dateModified
													)}
											</span>
										</div>
									</div>
								))}
						</div>
					</>
				)}
			</>
		);
	}
);
