<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<title>App信息维护</title>
		<link rel="stylesheet"
			href="<%=path%>/css/jquery.mobile-1.4.2.min.css" />
		<link rel="stylesheet"
			href="<%=path%>/css/app.css" />
		<script src="<%=path%>/js/jquery-1.9.1.min.js"></script>
		<script src="<%=path%>/js/jquery.mobile.router.min.js"></script> 
		<script src="<%=path%>/js/jquery.mobile-1.4.2.min.js"></script>
		<script src="<%=path%>/js/appbase.js"></script>
	</head>
	<body>
		<jsp:include page="include/abList.jsp"></jsp:include>
		<jsp:include page="include/abNew.jsp"></jsp:include>
		<jsp:include page="include/abDetail.jsp"></jsp:include>
	</body>
</html>