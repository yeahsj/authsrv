<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="net.suntec.oauthsrv.dto.AppBase"%>

<article id="ibaList" data-role="page" data-title="App列表">
<div data-role="header" data-position="fixed">
	<h1>
		<spring:message code="indexTitle"/>
	</h1>
</div>
<div data-role="content">
	<ul id="ibaListCt" data-role="listview" data-inset="true">
	</ul>
</div>
<jsp:include page="footer.jsp">
	<jsp:param name="pageName" value="index" />
</jsp:include>
</article>