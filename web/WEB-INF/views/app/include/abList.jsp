<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<article id="abList" data-role="page" data-title="App基础信息列表">
<div data-role="header" data-position="fixed">
	<a href="#main" >返回</a>
	<h1>
		App基础信息列表
	</h1>
	<a href="#abNew" >新建</a>
</div>
<div data-role="content">
	<ul id="abListCt" data-role="listview" data-inset="true">
	</ul>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</article>