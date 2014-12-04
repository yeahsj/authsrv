<%@ page session="false"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>DETAIL</title>
	</head>
	<body>
		<h1>用户信息: <%=request.getAttribute("id")%></h1>
		<h1>用户信息: <%=request.getAttribute("name")%></h1>
		${userDTO.id}
	</body>
</html>
