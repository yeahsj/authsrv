<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<article id="abNew" data-role="page" data-title="App基础信息">
<div data-role="header" data-position="fixed">
	<h1>
		新增信息
	</h1>
</div>
<div data-role="content" id="ab-form">
	<ul data-role="listview">
		<li data-role="fieldcontain">
			<label for="ab-appType">
				app type:
			</label>
			<input type="text" name="appType" id="ab-appType" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="ab-clazzName">
				class name:
			</label>
			<input type="text" name="clazzName" id="ab-clazzName" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="ab-scope">
				scope:
			</label>
			<input type="text" name="scope" id="ab-scope" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="ab-oauthVersion">
				oauth version:
			</label>
			<input type="text" name="oauthVersion" id="ab-oauthVersion" value="" />
		</li>
		<li data-role="fieldcontain">
			<label for="ab-requestUserInfo">
				is request user info:
			</label>
			<input type="text" name="requestUserInfo" id="ab-requestUserInfo"
				value="" />
		<li class="ui-body ui-body-b">
			<fieldset class="ui-grid-a">
				<div class="ui-block-b">
					<input type="button" data-theme="a" onclick="saveAppBase()"
						value="confirm" />
				</div>
			</fieldset>
		</li>
	</ul>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</article>