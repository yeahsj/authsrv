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
		<jsp:include page="../flow/include/resource.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
			function login() {
				document.getElementById("loginFrom").submit();
			}
		</script>
	</head>
	<body>
		<div id="afui" class="android">
			<div id="header">
			</div>
			<div id="content" class="bindingapp_page">
				<div data-title="manager" id="main" class="bindingapp_page panel"
					data-footer="none" selected="true">
					<form action="<%= request.getContextPath() %>/config/reloadConfig" id="loginFrom" method="post">
						<ul class="list bindingapp__content__wrap">
							<li class="bindingapp__content__item" style="color: red;height:30px;">
								${errmsg}
							</li>
							<li class="bindingapp__content__item">
								<label for="lg-username">
									<spring:message code="username" />
									:
								</label>
								<input type="text" name="userName" id="lg-username" value="" />
							</li>
							<li class="bindingapp__content__item">
								<label for="lg-password">
									<spring:message code="password" />
									:
								</label>
								<input type="text" name="password" id="lg-password" value="" />
							</li>
							<li class="bindingapp__content__item">
								<a onclick="login()" class="button"><spring:message code="loginbtn"/></a>
							</li>
						</ul>
					</form>
				</div>
			</div>
	</body>
</html>