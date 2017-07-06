package im.ligas.permissions.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PermissionsSamplePortletKeys.PORTLET_NAME,
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=actions-to-js-examplet Portlet",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class PermissionsSamplePortlet extends MVCPortlet {

    private static Log LOG = LogFactoryUtil.getLog(PermissionsSamplePortlet.class);

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        renderRequest.setAttribute("permissionsMap", getPermissionsMap(renderRequest));
        super.doView(renderRequest, renderResponse);
    }

    private String getPermissionsMap(RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

        JSONObject permissionsMap = JSONFactoryUtil.createJSONObject();
        for (ActionIDs actionIDs : ActionIDs.values()) {
            permissionsMap.put(actionIDs.toString(), hasPortletPermission(themeDisplay, actionIDs.name()));
        }
        return permissionsMap.toString();
    }

    private boolean hasPortletPermission(ThemeDisplay themeDisplay, String actionId) {

        try {
            return PortletPermissionUtil.contains(
                    themeDisplay.getPermissionChecker(),
                    themeDisplay.getLayout(),
                    PermissionsSamplePortletKeys.PORTLET_NAME,
                    actionId);
        } catch (PortalException e) {
            LOG.warn("Unable ot evaluate permission " + actionId + " for " + PermissionsSamplePortletKeys.PORTLET_NAME, e);
        }
        return false;
    }
}