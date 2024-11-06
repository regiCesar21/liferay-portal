/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.io;

import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.Compression;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.WriteFilesResult;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.transforms.windowing.BoundedWindow;
import org.apache.beam.sdk.transforms.windowing.IntervalWindow;
import org.apache.beam.sdk.transforms.windowing.PaneInfo;
import org.apache.beam.sdk.values.PCollection;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author Marcellus Tavares
 */
public class WriteToText {

	public static class WriteOneFilePerProjectIdPerWindow
		extends PTransform<PCollection<String>, WriteFilesResult<String>> {

		public WriteOneFilePerProjectIdPerWindow(
			String fileNamePrefix, String outputDirectory,
			SerializableFunction<String, String> projectIdMessageMapper) {

			_fileNamePrefix = fileNamePrefix;
			_outputDirectory = outputDirectory;
			_projectIdMessageMapper = projectIdMessageMapper;
		}

		@Override
		public WriteFilesResult<String> expand(
			PCollection<String> pCollection) {

			return pCollection.apply(
				FileIO.<String, String>writeDynamic(
				).by(
					_projectIdMessageMapper
				).via(
					TextIO.sink()
				).withDestinationCoder(
					StringUtf8Coder.of()
				).withNaming(
					projectId -> new PerWindowPartitionedFiles(
						_outputDirectory + "/" + projectId, _fileNamePrefix)
				).withNumShards(
					1
				));
		}

		private final String _fileNamePrefix;
		private final String _outputDirectory;
		private final SerializableFunction<String, String>
			_projectIdMessageMapper;

	}

	protected static class PerWindowPartitionedFiles
		implements FileIO.Write.FileNaming {

		public PerWindowPartitionedFiles(
			String outputFolder, String filePrefix) {

			_outputFolder = outputFolder;
			_filePrefix = filePrefix;
		}

		@Override
		public String getFilename(
			BoundedWindow boundedWindow, PaneInfo paneInfo, int numShards,
			int shardIndex, Compression compression) {

			IntervalWindow intervalWindow = (IntervalWindow)boundedWindow;

			return String.format(
				"%s/%s/%s%s", _outputFolder,
				_dateDateTimeFormatter.print(intervalWindow.start()),
				_getFileNameForWindow(intervalWindow),
				compression.getSuggestedSuffix());
		}

		private String _getFileNameForWindow(IntervalWindow intervalWindow) {
			return String.format(
				"%s-%s-to-%s", _filePrefix,
				_timeDateTimeFormatter.print(intervalWindow.start()),
				_timeDateTimeFormatter.print(intervalWindow.end()));
		}

		private static final DateTimeFormatter _dateDateTimeFormatter =
			ISODateTimeFormat.date(
			).withZoneUTC();
		private static final DateTimeFormatter _timeDateTimeFormatter =
			ISODateTimeFormat.hourMinute(
			).withZoneUTC();

		private final String _filePrefix;
		private final String _outputFolder;

	}

}