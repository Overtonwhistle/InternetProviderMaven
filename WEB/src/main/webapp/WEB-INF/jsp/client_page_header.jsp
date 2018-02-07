<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:message bundle="${loc}" key="local.user_menu" var="user_menu" />
<fmt:message bundle="${loc}" key="local.main" var="main" />
<fmt:message bundle="${loc}" key="local.log_off" var="log_off" />
<fmt:message bundle="${loc}" key="local.client_page" var="client_page" />
<fmt:message bundle="${loc}" key="local.client_page.summary" var="summary" />
<fmt:message bundle="${loc}" key="local.client_page.tariffs" var="tariffs" />
<fmt:message bundle="${loc}" key="local.client_page.requests" var="requests" />
<fmt:message bundle="${loc}" key="local.client_page.payments" var="payments" />
<fmt:message bundle="${loc}" key="local.client_page.edit_profile" var="edit_profile" />
<fmt:message bundle="${loc}" key="local.client_page.header_text" var="header_text" />
<fmt:message bundle="${loc}" key="local.made_by" var="made_by" />
<!--  HEADER -->
<div>
	<div class="menu">
		<div class="navbar">
			<ul>
				<%@ include file="localization_block.jsp"%>
				<li><a href="index.jsp" target='_self'> <img src="images/home.png" alt="${main}"><span>&nbsp;${main}</span>
				</a></li>
				<li><a href="Controller?command=goto_client" target='_self'> <img src="images/info.png"
						alt="Summary"><span>&nbsp;${summary}</span>
				</a></li>
				<li><a href="Controller?command=goto_client_payments" target='_self'> <img
						src="images/dollar.png" alt="${payments}"><span>&nbsp;${payments}</span>
				</a></li>
				<li><a href="Controller?command=goto_client_requests"> <img src="images/calendar_full.png"
						alt="${requests}"><span>&nbsp;${requests}</span>
				</a></li>
				<li><a href="Controller?command=goto_client_edit_profile" target='_self'> <img
						src="images/edit.png" alt="${edit_profile}"><span>&nbsp;${edit_profile}</span>
				</a>
				<li><a href="#"><img src="images/user.png" alt="${user_menu}"><span>&nbsp;${user_menu}</span></a>
					<ul class="items">
						<li>
							<div class="user-string">
								<c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}" />
							</div>
						</li>
						<li><form class="login-form" action="Controller" method="post">
								<input type="hidden" name="command" value="goto_client" /> <input class="login_button" type="submit"
									value="${client_page}" />
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
		<img src="images/background/user_back.jpg" alt="background not found">
		<h1>${header_text}</h1>
	</div>
</div>
