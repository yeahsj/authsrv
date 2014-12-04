<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String pageName = request.getParameter("pageName");
%>

<nav data-role=footer data-position=fixed>
<div data-iconpos=bottom data-role=navbar>
	<ul>
		<% 
		if( pageName.equals("login") ){ 
		%>
		<li>
			<a href=# onclick="toLogin();" 
				data-transition=fade data-mini=true data-role=button data-icon=grid><spring:message code="loginbtn"/></a>

		</li>
		<% } %>
		<% 
		if( pageName.equals("index") || pageName.equals("error") ){ 
		%>
		<li>
			<a href=# onclick="toDevice();" 
				data-transition=fade data-mini=true data-role=button data-icon=grid><spring:message code="indexbtn"/></a>

		</li>
		<% } %>
		
		<% 
		if( pageName.equals("index") ){ 
		%>
		<li>
			<a href=# onclick="logout();" 
				data-transition=fade data-mini=true data-role=button data-icon=grid><spring:message code="logoutbtn"/></a>

		</li>
		<% } %>
	</ul>
</div>
</nav>