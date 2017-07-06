<%@ include file="init.jsp" %>

<p>
    <b><liferay-ui:message key="actions_to_js_examplet_PermissionsSample.caption"/></b>
</p>

<div class="js-view-permission">
    You have view permissions!
</div>

<div class="js-delete-permission">
    You have delete permissions!
</div>

<c:out value="${permissionsMap}"/>

<aui:script>
    (function($){
        var permissionsMap = JSON.parse('${permissionsMap}');

        if(permissionsMap.view){
            $(".js-view-permission").show()
        }else {
            $(".js-view-permission").hide()
        }


        if(permissionsMap.delete){
            $(".js-delete-permission").show()
        }else {
            $(".js-delete-permission").hide()
        }
    })($)


</aui:script>