/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.renderer.FragmentDropZoneRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.layout.taglib.servlet.taglib.RenderFragmentLayoutTag;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentDropZoneRenderer.class)
public class FragmentDropZoneRendererImpl implements FragmentDropZoneRenderer {

	@Override
	public String renderDropZone(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Map<String, Object> fieldValues, long groupId, long plid,
			String mainItemId, String mode, boolean showPreview)
		throws PortalException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		httpServletRequest.setAttribute(
			FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER,
			_fragmentRendererController);

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		try {
			RenderFragmentLayoutTag renderFragmentLayoutTag =
				new RenderFragmentLayoutTag();

			renderFragmentLayoutTag.setFieldValues(fieldValues);
			renderFragmentLayoutTag.setGroupId(groupId);
			renderFragmentLayoutTag.setMainItemId(mainItemId);
			renderFragmentLayoutTag.setMode(mode);
			renderFragmentLayoutTag.setPlid(plid);
			renderFragmentLayoutTag.setShowPreview(showPreview);

			renderFragmentLayoutTag.doTag(
				httpServletRequest, pipingServletResponse);
		}
		catch (Exception exception) {
			throw new FragmentEntryContentException(exception);
		}
		finally {
			if (Objects.equals(mode, FragmentEntryLinkConstants.VIEW)) {
				httpServletRequest.setAttribute(
					WebKeys.SHOW_PORTLET_TOPPER, Boolean.TRUE);
			}
		}

		return unsyncStringWriter.toString();
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

}