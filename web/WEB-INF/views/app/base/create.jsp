<%@ page session="false"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>create</title>
	</head>
	<body>
		<form action="${pageContext.request.contextPath}/user/add" method="post" >
			<input type="text" name="id" value="">
			<input type="text" name="name" value="">
			<input type="text" name="age" value="">
			<input type="submit" value="OK">
		</form>
	</body>
</html>
