<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
		<title></title>
		<jsp:include page="../flow/include/resource.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
		/**
		 * @param request_url
		 *            请求路径
		 * @param method
		 *            请求方法
		 * @param data
		 *            请求数据
		 * @description 从服务器中获取 Auth Header,用于认证
		 * @return {string}
		 */
		var getTokenHeader = function(request_url, method, data) {
			var tokenUrl = "http://localhost:8080/auth/twitter/header"
					+ "?iautoLoginName=demo1" + "&method=" + method + "&requestUrl="
					+ window.encodeURIComponent(request_url);
			var returnVal = "";

			$.ajax({
						type : method,
						async : false,
						url : tokenUrl,
						data : data,
						success : function(msg) {
							returnVal = msg;
						},
						error : function(xhr, textStatus, errorThrown) {
							returnVal = null;
						}
					});
			// console.log("returnVal: " + returnVal);
			return returnVal;
		};
		/**
		 * @function
		 * @description 构建Auth Header,并放入request header中. 用于Twitter oauth认证
		 */
		var createTokenHeader = function(xhr, request_url, method, data) {
			// console.log("createTokenHeader.....");
			var tokenHeader = getTokenHeader(request_url, method, data);
			console.log("tokenHeader: " + tokenHeader);
			xhr.setRequestHeader('Authorization', tokenHeader);
		};
		var accessTwitterApi = function(request_url, method, data,
				responseAccessSuccessAction, responseAccessErrorAction) {
			var result = null;
			// console.log("request_url: " + request_url);
			// console.log("method: " + method);
			$.ajax({
						type : method,
						url : request_url,
						async : false,
						data : data,
						dataType : 'json',
						// jsonp:'callback',
						crossDomain : true,
						beforeSend : function(xhr) {
							// console.log("beforeSend.....");
							// console.log("request_url: " + request_url);
							createTokenHeader(xhr, request_url, method, data);
						},
						success : function(res, textStatus) {
							console.log("success ........ " + textStatus);
							if( responseAccessSuccessAction ){
								result = responseAccessSuccessAction(res);
							}
								// eval(responseAccessSuccessAction)(res);
						},
						error : function(xhr, textStatus, errorThrown) {
							result = null;
						}
					});
			return result;
		};
		
			function accessApi(){
				try{
				var url = $("#url").val();
					var method = $("#method").val();
					console.log( url );
					accessTwitterApi( url , method , null , function(res){ console.log(res); });
				}catch(ex){
					console.log(ex);
				}
			}


			
		</script>
	</head>
	<body>
		<div data-role="header" data-position="fixed">
			<h1>
				twitter test
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
						<label for="url">
							url:
						</label>
						<input type="text" name="url" id="url"
							value="https://api.twitter.com/1.1/statuses/home_timeline.json" />
					</li>
					<li data-role="fieldcontain">
						<label for="method">
							method:
						</label>
						<input type="text" name="method" id="method" value="" />
					</li>
					<li class="ui-body ui-body-b">
						<fieldset class="ui-grid-a">
							<div class="ui-block-b">
								<input type="button" data-theme="a" onclick="accessApi();"
									value="test" />
							</div>
						</fieldset>
					</li>
				</ul>
			</form>
		</div>
	</body>
</html>
