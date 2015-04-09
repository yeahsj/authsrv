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
	AppIautoMap record = (AppIautoMap) request.getAttribute("agreeParams");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta HTTP-EQUIV="pragma" CONTENT="no-cache"/> 
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/> 
<meta HTTP-EQUIV="expires" CONTENT="0"/> 
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
	function conUrl(){
		var redUrl = "";
		redUrl += backurl;
		redUrl += "?accessToken=<%=record.getAccessToken()%>";
		redUrl += "&refreshToken=<%=record.getRefreshToken()%>";
		redUrl += "&uid=<%=record.getUid()%>";
		redUrl += "&clientId=<%=record.getClientId()%>";
		redUrl += "&st=" + Math.random();
		return redUrl ;
	}
	
	function init() {
		initPage();
		var myScroller = $("#b-caution-container").scroller({
			refresh : false,
			infinite : false,
			scrollBars : true,
			verticalScroll : false,
			horizontalScroll : false
		});
		$("#afui").css("height", ($(window).height()) + "px");
	}
	
	function close(){
		unbindEvents();
	}

	function disAgree(){
		location.href=conUrl();
	}
	
	function doAgree() {
		var url = "<%=path%>/auth/<%=record.getAppType()%>/agreeAndBindApp";
		var params = "clientId=<%=record.getClientId()%>" + 
					"&accessToken=<%=record.getAccessToken()%>" + 
					"&refreshToken=<%=record.getRefreshToken()%>" + 
					"&loginName=<%=record.getIautoUserId()%>" + 
					"&uid=<%=record.getUid()%>" ;
		
		$.ajax( {
			type :"GET",
			url : url ,
			dataType :"json",
			timeout: 5000, 
			contentType :"application/x-www-form-urlencoded; charset=utf-8",
			async :true,
			data: params,
			success : function(res) {
				if( res.code == 1000 ){
					location.href=conUrl();
				}else{
					console.log(err);
					location.href=conUrl();
				}
			},
			error : function(err) {
				console.log(err);
				location.href=conUrl();
			}
		});
	}
</script>
</head>
<body onload="init();" onunload="close();" onselectstart="return false;">
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
			<%--
					Privacy Statement</br> We promise not to record users' account
					information (user name and password). By default, we will only
					cache users' authorization information in local. We also provide
					the account synchronization function which enables you to use third
					party applications on different devices. If you authorize on one
					device, the third party account can be logged in immediately on
					other devices. At this time, we will bind your iAuto account to the
					third party account. The authorization information will be uploaded
					to our secure cloud server. We will strictly guarantee the data
					safety. Of course, you can delete the authorization information
					saved in the cloud server at any time.</br> Do you agree to the binding
					of your iAuto account and the third party account (Facebook,
					Twitter, Instagram, Soundcloud, Foursquare, Feedly, Pocket, Vine, 500px)?</br>
					--%>
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
