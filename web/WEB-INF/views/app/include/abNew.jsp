<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<article id="abNew" data-role="page" data-title="App基础信息">
<div data-role="header" data-position="fixed">
	<a href="#abList">返回</a>
	<h1>
		新增信息
	</h1>
	<a href="#" data-role="button" onclick="saveAppBase();">确定</a>
</div>
<div data-role="content" id="ab-form">
	<label for="ab-appType">app type:</label>
    <input type="text" name="appType" id="ab-appType" value="" />
    <label for="ab-clazzName">class name:</label>
    <input type="text" name="clazzName" id="ab-clazzName" value="" />
    <label for="ab-scope">scope:</label>
    <input type="text" name="scope" id="ab-scope" value="" />
    <label for="ab-oauthVersion">oauth version:</label>
    <input type="text" name="oauthVersion" id="ab-oauthVersion" value="" />
    <label for="ab-requestUserInfo">is request user info:</label>
    <input type="text" name="requestUserInfo" id="ab-requestUserInfo" value="" />
</div>
<jsp:include page="footer.jsp"></jsp:include>
</article>