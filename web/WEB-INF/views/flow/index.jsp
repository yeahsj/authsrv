<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.suntec.oauthsrv.framework.ResourceConfig"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<title><%= ResourceConfig.getInstance().getServerConfig().getAppName() %></title>
		<jsp:include page="include/resource.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
			$(document).ready( function() {
				console.log("start init");
				fillIbaList();
			});
		</script>
	</head>
	<body>  
		<jsp:include page="include/appList.jsp"></jsp:include>
	</body>
</html>