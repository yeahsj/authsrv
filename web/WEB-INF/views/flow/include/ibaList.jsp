<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="net.suntec.oauthsrv.dto.AppBase"%>
<%
	List<AppBase> rows = (List<AppBase>) request.getAttribute("rows");
%>
<article id="ibaList" data-role="page" data-title="App列表">
	<div data-role="header" data-position="fixed">
		<h1>
			<spring:message code="indexTitle" />
		</h1>
	</div>
	<div data-role="content">
		<ul id="ibaListCt" data-role="listview" data-inset="true">
			<%
				for (AppBase appBase : rows) {
			%>
			<li><a data-ajax=false href="#"
				onclick="javascript:void(0);">
					<h3>
						<%=appBase.getAppType()%>
					</h3>
					<p>
						scope:
						<%=appBase.getScope()%>
					</p>
					<p>
						oauthVersion:
						<%=appBase.getOauthVersion()%>
					</p></li>
			<%
				}
			%>
		</ul>
	</div>
	<jsp:include page="footer.jsp">
		<jsp:param name="pageName" value="index" />
	</jsp:include>
</article>