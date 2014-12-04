<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.suntec.oauthsrv.framework.ResourceConfig"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<title><spring:message code="title" /></title>
		<jsp:include page="include/resource.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
			function keydownLogin(){
				if( event.keyCode == 13 ){
					login();
				}
			}
			function login() {
				document.getElementById("loginFrom").submit();
			}
		</script>
	</head>
	<body>
		<div data-role="header" data-position="fixed">
			<h1>
				<spring:message code="loginTitle" />
			</h1>
			<!-- 
			<a href="#" onclick="login();" data-icon="action" class="ui-btn-right">login</a> -->
		</div>
		<div data-role="content" id="login_form">
			<form action="/flow/doLogin" id="loginFrom" method="post">
				<ul data-role="listview">
					<li style="color: red">
						${errmsg}
					</li>
					<li data-role="fieldcontain">
						<label for="lg-username">
							<spring:message code="username" />
							:
						</label>
						<input type="text" name="userName" id="lg-username" value=""
							onkeydown="keydownLogin()" />
					</li>
					<li data-role="fieldcontain">
						<label for="lg-password">
							<spring:message code="password" />
							:
						</label>
						<input type="password" name="password" id="lg-password" value=""
							onkeydown="keydownLogin()" />
					</li>
					<li class="ui-body ui-body-b">
						<fieldset class="ui-grid-a">
							<div class="ui-block-b">
								<input type="button" data-theme="a" onclick="login()"
									value="<spring:message code="loginbtn"/>" />
							</div>
						</fieldset>
					</li>
				</ul>
			</form>
		</div>
	</body>
</html>