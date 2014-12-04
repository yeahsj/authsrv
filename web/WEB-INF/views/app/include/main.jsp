<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<article id="main" data-role="page" data-title="首页">
<jsp:include page="header.jsp"><jsp:param value="首页"
		name="title" /></jsp:include>
<div data-role="content">
	<a href="#abList" data-transition="fade" data-role="button"
		data-inline="true" data-mini="true"> <span> <img
				style="width: 80px; height: 40px" src="/css/images/app/selected.png" />
	</span> <br> <span>App基础信息设置</span> </a>
	<a href="#appList" data-transition="fade" data-role="button"
		data-inline="true" data-mini="true"> <span> <img
				style="width: 80px; height: 40px" src="/css/images/app/rank.png" />
	</span> <br> <span>App设置</span> </a>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</article>