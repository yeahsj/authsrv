<%@ page session="false"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.suntec.oauthsrv.dto.AppBase"%>
<html>
	<head>
		<title>query</title>
	</head>
	<body>
		<%!public String getString(Object str) {
		StringBuilder sb = new StringBuilder();
		sb.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;").append(str).append("&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		return sb.toString();
	}%>
		<%
			Object rows = request.getAttribute("rows");
			if (null != rows) {
				List<AppBase> datas = (ArrayList<AppBase>) rows;
				out.print("<table>");
				for (AppBase appInfo : datas) {
					out.print("<tr>");
					out.print(getString(appInfo.getAppType()));
					out.print(getString(appInfo.getClazzName()));
					out.print(getString(appInfo.getOauthVersion()));
					out.print(getString(appInfo.getScope()));
					out.print(getString(appInfo.getRequestUserInfo()));
					out.print("</tr>");
				}
				out.print("</table>");
			}
		%>
	</body>
</html>
