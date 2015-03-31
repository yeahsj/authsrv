<%@page import="net.suntec.oauthsrv.dto.AppIautoMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl = (String) request.getAttribute("backurl");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes" />
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/js/jq/appframework.min.js"></script>
	<script type="text/javascript" charset="utf-8"
	src="<%=path%>/js/jq/appframework.ui.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/js/bindcaution.js"></script>
<link rel="stylesheet" href="<%=path%>/css/bindcaution.css" />
<title>bind caution page</title>
<script language="javascript" type="text/javascript">
	var backurl = "<%=backurl%>";
	function init() {
		initPage();
		var myScroller = $("#b-caution-container").scroller({
			refresh : false,
			infinite : false,
			scrollBars : true,
			verticalScroll : false,
			horizontalScroll : false
		});
		//$("#afui").css("height", ($(window).height()) + "px");
	}

	function close(){
		unbindEvents();
	}
	
	function doAgree() {
	}
</script>
</head>
<body onload="init();" onunload="close();">
	<div id="afui">
		<div id="b-caution-main"
			style="background-image: url(<%=path%>/css/images/caution_bg.png)">
			<div id="b-caution-title">Caution</div>
			<div id="b-caution-container"
				style="background-image: url(<%=path%>/css/images/caution_content_bg.png)">
				<div id="b-caution-content">
					<spring:message code="MSG_BIND"/>
					</br>
				</div>
			</div>
			<div id="b-caution-line">
				<!-- normal button1 -->
				<div id="b-caution-button-1" class="b-caution-button"
					style="background-image: url(<%=path%>/css/images/caution_btn_m.png)">
					<div class="b-caution-button-text">Not Agree</div>
				</div>
				<!-- pressed button1 -->
				<div id="b-caution-button-1-pressed" class="b-caution-button"
					style="background-image: url(<%=path%>/css/images/caution_btn_m_p.png)">
					<div class="b-caution-button-text">Not Agree</div>
				</div>
				<!-- normal button2 -->
				<div id="b-caution-button-2" class="b-caution-button"
					style="background-image: url(<%=path%>/css/images/caution_btn_m.png)">
					<div class="b-caution-button-text">Agree</div>
				</div>
				<!-- pressed button2 -->
				<div id="b-caution-button-2-pressed" class="b-caution-button"
					style="background-image: url(<%=path%>/css/images/caution_btn_m_p.png)">
					<div class="b-caution-button-text">Agree</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
