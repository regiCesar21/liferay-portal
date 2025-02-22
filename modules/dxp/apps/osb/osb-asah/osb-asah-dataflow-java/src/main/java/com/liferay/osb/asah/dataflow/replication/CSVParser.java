/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.replication;

import com.opencsv.CSVParserBuilder;

import java.io.IOException;

/**
 * @author Marcellus Tavares
 */
public class CSVParser {

	public static String[] parseLine(String line) throws IOException {
		return _instance._csvParser.parseLine(line);
	}

	private CSVParser() {
		CSVParserBuilder csvParserBuilder = new CSVParserBuilder();

		csvParserBuilder.withSeparator(';');

		_csvParser = csvParserBuilder.build();
	}

	private static final CSVParser _instance = new CSVParser();

	private final com.opencsv.CSVParser _csvParser;

}