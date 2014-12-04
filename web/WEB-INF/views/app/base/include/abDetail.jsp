<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<article id="abDetail" data-role="page" data-title="App基础信息">
<div data-role="header" data-position="fixed">
	<h1>
		App信息
	</h1>
</div>
<div data-role="content" id="abd-form">
	<ul data-role="listview">
		<li data-role="fieldcontain">
			<label for="appType">
				app type:
			</label>
			<input type="text" name="appType" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="clazzName">
				class name:
			</label>
			<input type="text" name="clazzName" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="scope">
				scope:
			</label>
			<input type="text" name="scope" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="ab-oauthVersion">
				oauth version:
			</label>
			<input type="text" name="oauthVersion" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="ab-requestUserInfo">
				is request user info:
			</label>
			<input type="text" name="requestUserInfo" value="" />
		<li class="ui-body ui-body-b">
			<fieldset class="ui-grid-a">
				<div class="ui-block-b">
					<input type="button" data-theme="a" onclick="updateAppBase()"
						value="confirm" />
				</div>
			</fieldset>
		</li>
	</ul>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</article>