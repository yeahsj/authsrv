<%@page import="com.openjava.core.util.StrUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String errMsg = StrUtil.nullToString( request.getAttribute("errMsg") );
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
		console.log( "error message: <%=errMsg%>" );
		var pageHeight = $(window).height();
		if( pageHeight > 480 ){
			$("#afui").css("height", "480px");
		}else{
			$("#afui").css("height", pageHeight + "px");
		}
		console.log( $("#afui").height() );
		if( $("#afui").height() > 480 ){
			$("#afui").css("height", "480px");
		}
	}
	
</script>
		<style>
body {
	text-align: center;
	margin:0;
	top:0;
	left:0;
}

#pageTitle{
	margin:0px;	
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
	<body onload="init();" onselectstart="return false;">
		<div id="afui" class="error_page">
			<div class="error_msg">
				Authentication failed! Please press the "Back" button and try again.
			</div>
		</div>
	</body>
</html>
