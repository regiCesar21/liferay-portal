/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.util;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class Base64 {

	public static byte[] decode(String base64) {
		return _decode(base64, false);
	}

	public static byte[] decodeFromURL(String base64) {
		return _decode(base64, true);
	}

	public static String encode(byte[] raw) {
		return _encode(raw, 0, raw.length, false);
	}

	public static String encodeToURL(byte[] raw) {
		return _encode(raw, 0, raw.length, true);
	}

	private static byte[] _decode(String base64, boolean url) {
		if (Objects.isNull(base64)) {
			return new byte[0];
		}

		int pad = 0;

		for (int i = base64.length() - 1; base64.charAt(i) == '='; i--) {
			pad++;
		}

		int length = ((base64.length() * 6) / 8) - pad;

		byte[] raw = new byte[length];

		int rawindex = 0;

		for (int i = 0; i < base64.length(); i += 4) {
			int block = _getValue(base64.charAt(i), url) << 18;

			block += _getValue(base64.charAt(i + 1), url) << 12;
			block += _getValue(base64.charAt(i + 2), url) << 6;
			block += _getValue(base64.charAt(i + 3), url);

			for (int j = 0; (j < 3) && ((rawindex + j) < raw.length); j++) {
				raw[rawindex + j] = (byte)((block >> (8 * (2 - j))) & 0xff);
			}

			rawindex += 3;
		}

		return raw;
	}

	private static String _encode(
		byte[] raw, int offset, int length, boolean url) {

		int lastIndex = Math.min(raw.length, offset + length);

		StringBuilder sb = new StringBuilder(
			(((lastIndex - offset) / 3) + 1) * 4);

		for (int i = offset; i < lastIndex; i += 3) {
			sb.append(_encodeBlock(raw, i, lastIndex, url));
		}

		return sb.toString();
	}

	private static char[] _encodeBlock(
		byte[] raw, int offset, int lastIndex, boolean url) {

		int block = 0;

		int slack = lastIndex - offset - 1;

		int end = (slack < 2) ? slack : 2;

		for (int i = 0; i <= end; i++) {
			byte b = raw[offset + i];

			int neuter = (b >= 0) ? (int)b : b + 256;

			block += neuter << (8 * (2 - i));
		}

		char[] base64 = new char[4];

		for (int i = 0; i < 4; i++) {
			int sixbit = (block >>> (6 * (3 - i))) & 0x3f;

			base64[i] = _getChar(sixbit, url);
		}

		if (url) {
			if (slack < 1) {
				base64[2] = '*';
			}

			if (slack < 2) {
				base64[3] = '*';
			}
		}
		else {
			if (slack < 1) {
				base64[2] = '=';
			}

			if (slack < 2) {
				base64[3] = '=';
			}
		}

		return base64;
	}

	private static char _getChar(int sixbit, boolean url) {
		if ((sixbit >= 0) && (sixbit <= 25)) {
			return (char)(65 + sixbit);
		}

		if ((sixbit >= 26) && (sixbit <= 51)) {
			return (char)(97 + (sixbit - 26));
		}

		if ((sixbit >= 52) && (sixbit <= 61)) {
			return (char)(48 + (sixbit - 52));
		}

		if (sixbit == 62) {
			if (url) {
				return '-';
			}

			return '+';
		}

		if (sixbit != 63) {
			return '?';
		}

		if (url) {
			return '_';
		}

		return '/';
	}

	private static int _getValue(char c, boolean url) {
		if ((c >= 'A') && (c <= 'Z')) {
			return c - 65;
		}

		if ((c >= 'a') && (c <= 'z')) {
			return (c - 97) + 26;
		}

		if ((c >= '0') && (c <= '9')) {
			return (c - 48) + 52;
		}

		if (url) {
			if (c == '-') {
				return 62;
			}

			if (c == '_') {
				return 63;
			}

			if (c != '*') {
				return -1;
			}
		}
		else {
			if (c == '+') {
				return 62;
			}

			if (c == '/') {
				return 63;
			}

			if (c != '=') {
				return -1;
			}
		}

		return 0;
	}

}