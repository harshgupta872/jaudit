<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="fooBarList.title"/></title>
    <meta name="heading" content="<fmt:message key='fooBarList.heading'/>"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editFooBar.html"/>'"
        value="<fmt:message key="button.add"/>"/>
    
    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<s:set name="fooBars" value="fooBars" scope="request"/>
<display:table name="fooBars" class="table" requestURI="" id="fooBarList" export="true" pagesize="25">
    <display:column property="id" sortable="true" href="editFooBar.html" 
        paramId="id" paramProperty="id" titleKey="fooBar.id"/>
    <display:column property="name" sortable="true" titleKey="fooBar.name"/>
    <display:column property="description" sortable="true" titleKey="fooBar.description"/>

    <display:setProperty name="paging.banner.item_name" value="fooBar"/>
    <display:setProperty name="paging.banner.items_name" value="people"/>

    <display:setProperty name="export.excel.filename" value="FooBar List.xls"/>
    <display:setProperty name="export.csv.filename" value="FooBar List.csv"/>
    <display:setProperty name="export.pdf.filename" value="FooBar List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("fooBarList");
</script>