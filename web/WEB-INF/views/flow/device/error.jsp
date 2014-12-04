<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<script type="text/javascript" charset="utf-8"
			src="<%=path%>/js/jq/appframework.min.js"></script>
		<title>error page</title>
		<script language="javascript" type="text/javascript">
	function init() {
		$("#afui").css("height", ($(window).height()) + "px");
	}
</script>
		<style>
body {
	text-align: center;
	margin:0;
	top:0;
	left:0;
}

.error_page {
	position: relative;
	margin: 0px;
	top:0px;
	left:0px;
	padding:0;
	background-image: url('<%=path%>/css/images/errorbg.png') !important;
}

.error_msg {
	position: absolute;
	font-family: Roboto;
	padding-left: 11%;
	padding-right: 11%;
	font-weight: 300;
	font-size: 32px;
	color: white;
	top: 190px;
}
</style>
	</head>
	<body onload="init();">
		<div id="afui" class="error_page">
			<div class="error_msg">
				Authentication failed,please press "Back" and try again
			</div>
		</div>
	</body>
</html>
