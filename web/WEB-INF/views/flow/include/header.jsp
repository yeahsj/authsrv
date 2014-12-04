<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String title = request.getParameter("title");
%>

<div data-role="header" data-position="fixed">
	<h1>
		<%=title%>
	</h1>
</div>