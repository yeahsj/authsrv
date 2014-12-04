<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@page import="net.suntec.oauthsrv.dto.AppInfo"%>
<%@page import="net.suntec.oauthsrv.framework.dto.SessionUser"%>
<%@page import="net.suntec.oauthsrv.framework.dto.UserDevice"%>
<article id="ibaList" data-role="page" data-title="device列表">
<div data-role="header" data-position="fixed">
	<h1>
		device列表
	</h1>
</div>
<div data-role="content">
	<ul id="ibaListCt" data-role="listview" data-inset="true">
		<%
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			List<UserDevice> devices = null;
			for (UserDevice userDevice : devices) {
		%>
		<li>
			<a herf="#" onclick="device('<%= userDevice.getDeviceNo() %>');">
				<h3>
					<%=userDevice.getDeviceNo()%>
				</h3> </a>
		</li>
		<%
			}
		%>
	</ul>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</article>