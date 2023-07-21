/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.ntlm.internal.msrpc;

import jcifs.dcerpc.DcerpcMessage;
import jcifs.dcerpc.ndr.NdrBuffer;
import jcifs.dcerpc.ndr.NdrException;

/**
 * @author Marcellus Tavares
 */
public class NetrLogonSamLogon extends DcerpcMessage {

	public NetrLogonSamLogon(
		String logonServer, String computerName,
		NetlogonAuthenticator netlogonAuthenticator,
		NetlogonAuthenticator returnNetlogonAuthenticator, int logonLevel,
		NetlogonNetworkInfo netlogonNetworkInfo, int validationLevel,
		NetlogonValidationSamInfo netlogonValidationSamInfo,
		int authoritative) {

		_logonServer = logonServer;
		_computerName = computerName;
		_netlogonAuthenticator = netlogonAuthenticator;
		_returnNetlogonAuthenticator = returnNetlogonAuthenticator;
		_logonLevel = (short)logonLevel;
		_netlogonNetworkInfo = netlogonNetworkInfo;
		_validationLevel = (short)validationLevel;
		_netlogonValidationSamInfo = netlogonValidationSamInfo;
		_authoritative = (byte)authoritative;

		ptype = 0;
		flags = DCERPC_FIRST_FRAG | DCERPC_LAST_FRAG;
	}

	@Override
	public void decode_out(NdrBuffer ndrBuffer) throws NdrException {
		int returnAuthenticator = ndrBuffer.dec_ndr_long();

		if (returnAuthenticator > 0) {
			_returnNetlogonAuthenticator.decode(ndrBuffer);
		}

		ndrBuffer.dec_ndr_short();

		int validationInformation = ndrBuffer.dec_ndr_long();

		if (validationInformation > 0) {
			ndrBuffer = ndrBuffer.deferred;

			_netlogonValidationSamInfo.decode(ndrBuffer);
		}

		_authoritative = (byte)ndrBuffer.dec_ndr_small();
		_status = ndrBuffer.dec_ndr_long();
	}

	@Override
	public void encode_in(NdrBuffer ndrBuffer) {
		ndrBuffer.enc_ndr_referent(_logonServer, 1);
		ndrBuffer.enc_ndr_string(_logonServer);

		ndrBuffer.enc_ndr_referent(_computerName, 1);
		ndrBuffer.enc_ndr_string(_computerName);

		ndrBuffer.enc_ndr_referent(_netlogonAuthenticator, 1);

		_netlogonAuthenticator.encode(ndrBuffer);

		ndrBuffer.enc_ndr_referent(_returnNetlogonAuthenticator, 1);

		_returnNetlogonAuthenticator.encode(ndrBuffer);

		ndrBuffer.enc_ndr_short(_logonLevel);
		ndrBuffer.enc_ndr_short(_logonLevel);

		ndrBuffer.enc_ndr_referent(_netlogonNetworkInfo, 1);

		_netlogonNetworkInfo.encode(ndrBuffer);

		ndrBuffer.enc_ndr_short(_validationLevel);
	}

	public NetlogonValidationSamInfo getNetlogonValidationSamInfo() {
		return _netlogonValidationSamInfo;
	}

	@Override
	public int getOpnum() {
		return 2;
	}

	public int getStatus() {
		return _status;
	}

	@SuppressWarnings("unused")
	private byte _authoritative;

	private final String _computerName;
	private final short _logonLevel;
	private final String _logonServer;
	private final NetlogonAuthenticator _netlogonAuthenticator;
	private final NetlogonNetworkInfo _netlogonNetworkInfo;
	private final NetlogonValidationSamInfo _netlogonValidationSamInfo;
	private final NetlogonAuthenticator _returnNetlogonAuthenticator;
	private int _status;
	private final short _validationLevel;

}