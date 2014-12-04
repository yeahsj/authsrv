<%@ page session="false"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.suntec.oauthsrv.dto.AppInfo"%>
<html>
	<head>
		<title>query</title>
	</head>
	<body>
		<%!public String getString(Object str){
			StringBuilder sb = new StringBuilder();
			sb.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;").append(str).append("&nbsp;&nbsp;&nbsp;&nbsp;</td>");
			return sb.toString();
		}%>
		<%
			Object rows = request.getAttribute("rows");
			if (null != rows) {
				List<AppInfo> datas = (ArrayList<AppInfo>) rows;
				out.print("<table>");
				for (AppInfo appInfo : datas) {
					out.print("<tr>");
					out.print( getString(appInfo.getAppId()) );
					out.print( getString(appInfo.getAppSecret()) );
					out.print( getString(appInfo.getAppClientid()) );
					out.print("</tr>");
				}
				out.print("</table>");
			}
		%>
	</body>
</html>
