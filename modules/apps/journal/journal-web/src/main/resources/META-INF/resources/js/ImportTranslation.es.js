/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import React, {useEffect, useRef, useState} from 'react';

const VALID_EXTENSIONS = '.xliff,.xlf';

export default function ImportTranslation({
	saveDraftBtnId,
	submitBtnId,
	worflowPending = false,
}) {
	const [importFile, setImportFile] = useState();

	const inputFileRef = useRef();

	useEffect(() => {
		Liferay.Util.toggleDisabled('#' + saveDraftBtnId, !importFile);
		Liferay.Util.toggleDisabled(
			'#' + submitBtnId,
			!importFile || worflowPending
		);
	}, [importFile, saveDraftBtnId, submitBtnId, worflowPending]);

	return (
		<div>
			<h3 className="h4">{Liferay.Language.get('import-file')}</h3>
			<p>
				<span className="text-secondary">
					{Liferay.Language.get(
						'please-upload-your-translation-file'
					)}
				</span>
			</p>

			<div className="mb-5 mt-4">
				<p className="h5">{Liferay.Language.get('file-upload')}</p>

				<ClayInput
					accept={VALID_EXTENSIONS}
					className="d-none"
					name="file"
					onChange={(e) => {
						setImportFile(e.target.files[0]);
					}}
					ref={inputFileRef}
					type="file"
				/>

				{!importFile && (
					<ClayButton
						displayType="secondary"
						onClick={() => {
							inputFileRef.current.click();
						}}
					>
						{Liferay.Language.get('select-file')}
					</ClayButton>
				)}

				{importFile && (
					<>
						<strong>{importFile.name}</strong>

						<ClayButtonWithIcon
							displayType="unstyled"
							onClick={() => {
								setImportFile(null);
							}}
							symbol="times-circle"
							title={Liferay.Language.get('delete')}
						/>
					</>
				)}
			</div>
		</div>
	);
}
