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
			href="<%=path%>/css/jquery.mobile-1.2.0.min.css" />
		<link rel="stylesheet"
			href="<%=path%>/css/app.css" />
		<script src="<%=path%>/js/jquery-1.8.2.min.js"></script>
		<script src="<%=path%>/js/jquery.mobile.router.min.js"></script>
		<script src="<%=path%>/js/jquery.mobile-1.2.0.min.js"></script>
		<script src="<%=path%>/js/app.js"></script>
	</head>
	<body>
		<jsp:include page="include/main.jsp"></jsp:include>
		<jsp:include page="include/ibaList.jsp"></jsp:include>
		<jsp:include page="include/abList.jsp"></jsp:include>
		<jsp:include page="include/abNew.jsp"></jsp:include>
		<article id="ab_help" data-role="page" data-title="应用列表">
		<div data-role="header" data-position="fixed">
			<h1>
				帮助列表
			</h1>
		</div>
		<div data-role="content">
			<ul data-role="listview" data-inset="true">
				<li>
					11212
				</li>
				<li>
					123123
				</li>
				<li>
					123123
				</li>
			</ul>
		</div>
		<nav data-role=footer data-position=fixed>
		<div data-iconpos=bottom data-role=navbar>
			<ul>
				<li>
					<a href=#ab_list class='ui-btn-active ui-state-persist'
						data-transition=fade data-mini=true data-role=button
						data-icon=grid>列表</a>

				</li>
				<li>
					<a href=#ab_help class='ui-btn-active ui-state-persist'
						data-transition=fade data-mini=true data-role=button
						data-icon=grid>help</a>
				</li>
			</ul>
		</div>
		</nav>
		</article>
	</body>
</html>