<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="fooBarDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='fooBarDetail.heading'/>"/>
</head>

<s:form id="fooBarForm" action="saveFooBar" method="post" validate="true">
<s:hidden name="fooBar.id" value="%{fooBar.id}"/>

    <s:textfield key="fooBar.name" required="true" cssClass="text medium"/>
    <s:textarea key="fooBar.description" required="false" cssClass="textarea medium"/>

    <li class="buttonBar bottom">
        <s:submit cssClass="button" method="save" key="button.save" theme="simple"/>
        <c:if test="${not empty fooBar.id}"> 
            <s:submit cssClass="button" method="delete" key="button.delete" onclick="return confirmDelete('fooBar')" theme="simple"/>
        </c:if>
        <s:submit cssClass="button" method="cancel" key="button.cancel" theme="simple"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement($("fooBarForm"));
</script>