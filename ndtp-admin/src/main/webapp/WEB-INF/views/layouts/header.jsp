<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ndtp.domain.Key"%>
<%@page import="ndtp.domain.UserSession"%>
<%@page import="ndtp.domain.CacheManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<header class="site-header">
	<div class="row">
		<div class="container">
			<h2 class="site-title u-pull-left" style="font-size:28px; font-family:Lousianne; color:#573592"><a href="/main/index.do">mago3D</a></h2>
			<aside class="site-aside-menu u-pull-right">
				<ul>
					<li>
						<a href="#">
							<span class="icon-glyph glyph-qmark-circle"></span>
							<span class="icon-text"><spring:message code='help'/></span>
						</a>
					</li>
					<li>
<%
	UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
	if(userSession != null && userSession.getUserId() != null && !"".equals(userSession.getUserId())) {
%>
						<a href="/signout/signout">
							<span class="icon-glyph glyph-out"></span>
							<span class="icon-text"><spring:message code='signout'/></span>
						</a>
<%
	} else {
%>
						<a href="/signin/signin">
							<span class="icon-glyph glyph-on"></span>
							<span class="icon-text"><spring:message code='signin'/></span>
						</a>
<%
	}
%>					
					</li>
				</ul>
			</aside>
		</div>
	</div>
</header>