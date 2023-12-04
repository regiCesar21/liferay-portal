/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useCallback, useContext, useMemo, useState} from 'react';

import ModalWithSteps from '../../../../shared/components/modal-with-steps/ModalWithSteps.es';
import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {usePatch} from '../../../../shared/hooks/usePatch.es';
import {InstanceListContext} from '../../InstanceListPageProvider.es';
import {ModalContext} from '../ModalProvider.es';
import SelectTasksStep from '../shared/select-tasks-step/SelectTasksStep.es';
import {useFetchTasks} from '../shared/select-tasks-step/hooks/useFetchTasks.es';
import UpdateDueDateStep from './UpdateDueDateStep.es';

const BulkUpdateDueDateModal = () => {
	const {setSelectAll, setSelectedItems} = useContext(InstanceListContext);
	const {
		closeModal,
		selectTasks: {selectAll, tasks},
		setSelectTasks,
		setUpdateDueDate,
		updateDueDate: {comment, dueDate},
		visibleModal,
	} = useContext(ModalContext);
	const [currentStep, setCurrentStep] = useState('selectTasks');
	const [errorToast, setErrorToast] = useState(null);
	const [fetching, setFetching] = useState(false);
	const [updating, setUpdating] = useState(false);
	const toaster = useToaster();

	const {clearFilters, fetchTasks} = useFetchTasks({
		callback: ({items}) => {
			setCurrentStep('selectDueDate');
			setFetching(false);
			setSelectTasks({selectAll, tasks: items});
		},
	});

	const clearContext = useCallback(() => {
		setUpdateDueDate({
			comment: undefined,
			dueDate: undefined,
		});
	}, [setUpdateDueDate]);

	const onCloseModal = (refetch) => {
		clearContext();
		clearFilters();
		closeModal(refetch);
		setSelectTasks({selectAll: false, tasks: []});
		setCurrentStep('selectTasks');
		setErrorToast(false);
	};

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const body = useMemo(() => {
		if (dueDate) {
			return tasks.map(({id: workflowTaskId}) => ({
				comment,
				dueDate,
				workflowTaskId,
			}));
		}

		return [];
	}, [comment, dueDate, tasks]);

	const {patchData} = usePatch({
		admin: true,
		body,
		callback: () => {
			setUpdating(false);

			toaster.success(
				tasks.length > 1
					? Liferay.Language.get(
							'the-due-dates-for-these-tasks-have-been-updated'
					  )
					: Liferay.Language.get(
							'the-due-date-for-this-task-has-been-updated'
					  )
			);

			onCloseModal(true);
			setSelectedItems([]);
			setSelectAll(false);
		},
		url: '/workflow-tasks/update-due-date',
	});

	const handleDone = useCallback(() => {
		setUpdating(true);

		patchData().catch(({response}) => {
			const errorMessage = `${Liferay.Language.get(
				'your-request-has-failed'
			)} ${Liferay.Language.get('select-done-to-retry')}`;

			setErrorToast(response?.data?.title ?? errorMessage);
			setUpdating(false);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dueDate, tasks]);

	const handleNext = useCallback(() => {
		if (selectAll) {
			setFetching(true);

			fetchTasks().catch(() => {
				setErrorToast(
					`${Liferay.Language.get('your-request-has-failed')}`
				);
				setFetching(false);
			});
		}
		else {
			setCurrentStep('selectDueDate');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectAll]);

	const handlePrevious = useCallback(() => {
		clearContext();
		setCurrentStep('selectTasks');
		setErrorToast(false);
	}, [clearContext]);

	const getStep = useCallback(
		(step) => {
			const steps = {
				selectDueDate: {
					cancelBtn: {
						disabled: updating,
						handle: onClose,
					},
					component: UpdateDueDateStep,
					nextBtn: {
						disabled: !dueDate || updating,
						handle: handleDone,
						text: Liferay.Language.get('done'),
					},
					order: 2,
					previousBtn: {
						disabled: updating,
						handle: handlePrevious,
					},
					props: {
						className: 'fixed-height modal-metrics-content',
					},
					subtitle: Liferay.Language.get('update-due-date'),
					title: Liferay.Language.get('update-tasks-due-dates'),
				},
				selectTasks: {
					cancelBtn: {
						disabled: fetching,
						handle: onClose,
					},
					component: SelectTasksStep,
					nextBtn: {
						disabled: tasks.length === 0 || fetching,
						handle: handleNext,
						text: Liferay.Language.get('next'),
					},
					order: 1,
					previousBtn: false,
					props: {setErrorToast},
					subtitle: Liferay.Language.get('select-tasks'),
					title: Liferay.Language.get('select-tasks-to-update'),
				},
			};

			return steps[step];
		},
		[
			dueDate,
			fetching,
			handleDone,
			handleNext,
			handlePrevious,
			onClose,
			updating,
			tasks.length,
		]
	);

	const step = useMemo(() => getStep(currentStep), [currentStep, getStep]);

	return (
		<ModalWithSteps
			error={errorToast}
			observer={observer}
			step={step}
			visible={visibleModal === 'bulkUpdateDueDate'}
		/>
	);
};

export default BulkUpdateDueDateModal;
