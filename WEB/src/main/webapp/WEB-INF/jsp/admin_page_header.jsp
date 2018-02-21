<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags"%>

<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.main" var="main" />
<fmt:message key="local.user_menu" var="user_menu" />
<fmt:message key="local.log_off" var="log_off" />
<fmt:message key="local.admin_page" var="admin_page" />
<fmt:message key="local.admin_page.summary" var="summary" />
<fmt:message key="local.admin_page.users" var="users" />
<fmt:message key="local.admin_page.tariffs" var="tariffs" />
<fmt:message key="local.admin_page.requests" var="requests" />
<fmt:message key="local.admin_page.payments" var="payments" />
<fmt:message key="local.admin_page.header_text" var="header_text" />
<!-- END BASE PART -->
<div>
	<div class="menu">
		<div class="navbar">
			<ul>
				<%@ include file="localization_block.jsp"%>
				<li><a href="index.jsp" target='_self'> <img src="images/home.png" alt="${main}"><span>&nbsp;${main}</span>
				</a></li>
				<li><a href="Controller?command=goto_admin" target='_self'> <img src="images/info.png"
						alt="Summary"><span>&nbsp;${summary}</span>
				</a></li>
				<li><a href="Controller?command=goto_ad_users" target='_self'> <img src="images/users.png"
						alt="${users}"><span>&nbsp;${users}</span>
				</a></li>
				<li><a href="Controller?command=goto_ad_tariffs"> <img src="images/notebook.png" alt="${tariffs}"><span>&nbsp;${tariffs}</span>
				</a></li>
				<li><a href="Controller?command=goto_ad_requests" target='_self'> <img
						src="images/calendar_blank.png" alt="${requests}"><span>&nbsp;${requests}</span>
				</a></li>
				<li><a href="Controller?command=goto_ad_payments" target='_self'> <img src="images/dollar.png"
						alt="${payments}"><span>&nbsp;${payments}</span>
				</a>
				<li><a href="#"><img src="images/user.png" alt="${user_menu}"><span>&nbsp;${user_menu}</span></a>
					<ul class="items">
						<li>
							<div class="user-string">
								<ctg:full-name />
							</div>
						</li>
						<li><form class="login-form" action="Controller" method="post">
								<input type="hidden" name="command" value="goto_admin" /> <input class="login_button" type="submit"
									value="${admin_page}" />
							</form></li>
						<li><form class="login-form" action="Controller" method="post">
								<input type="hidden" name="command" value="log_off" /> <input class="login_button" type="submit"
									value="${log_off}" />
							</form></li>
					</ul></li>
			</ul>
		</div>
	</div>
	<div class="headerPictureAd">
		<img src="images/background/home_slide01.jpg" alt="background not found">
		<h1>${header_text}</h1>
	</div>
</div>
