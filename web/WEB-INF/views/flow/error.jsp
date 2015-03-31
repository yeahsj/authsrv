<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<title>error page</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/app.css" />
		<script type="text/javascript" charset="utf-8" src="<%=path%>/js/jq/appframework.min.js"></script>
		<style>
			#afui #backButton{
				left: 10px !important;
			}
			#afui .header h1{
				text-align: left !important;
				left: 50px !important;
			}
			div #content-container{
				position: relative;
				margin-top: 47%;
				margin-left: 20px;
				margin-right: 20px;
				text-align: left;
				vertical-align: middle;
			}
		</style>
		<script type="text/javascript" language="JavaScript">
		    function redirect(){
			    window.demo.clickOnAndroid('redirectLocal');
		    }
		    function init(){
		    	$("#afui").css("height" , ( $(window).height() ) + "px" ); 
		    }
		</script>
	</head>
	<body onload="init();">
		<div id="afui" class="android" >
			<div id="header" class="header">
				<header id="defaultHeader">
				<a href="#" ontouchend="redirect();" id="backButton" class="button" style="visibility: true;"></a>
				<h1 id="pageTitle">
					<spring:message code="Error" />
				</h1>
				</header>
			</div>
			<div id="content" class="bindingapp_page"> 
				<div id="content-container">
					<div style="color: rgb(203, 250, 255);">
						<p style="display:inline;word-break:break-word;">Authentication failed! Please press the "Back" button and try again.
						</p>
					</div>
				</div> 
			</div>
		</div> 
	</body>
</html>
