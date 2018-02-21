<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags"%>

<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.main" var="main" />
<fmt:message key="local.news" var="news" />
<fmt:message key="local.tariffs" var="tariffs" />
<fmt:message key="local.offers" var="offers" />
<fmt:message key="local.about" var="about" />
<fmt:message key="local.user_menu" var="user_menu" />
<fmt:message key="local.login" var="login" />
<fmt:message key="local.password" var="password" />
<fmt:message key="local.log_in" var="log_in" />
<fmt:message key="local.log_off" var="log_off" />
<fmt:message key="local.new_user" var="new_user" />
<fmt:message key="local.admin_page" var="admin_page" />
<fmt:message key="local.client_page" var="client_page" />
<fmt:message key="local.article_one" var="article_one" />
<fmt:message key="local.link_one" var="link_one" />
<fmt:message key="local.text_one" var="text_one" />
<fmt:message key="local.article_two" var="article_two" />
<fmt:message key="local.link_two" var="link_two" />
<fmt:message key="local.text_two" var="text_two" />
<!--  HEADER -->
<div>
	<div class="menu">
		<div class="navbar">
			<ul>
				<%@ include file="localization_block.jsp"%>
				<li><a href="index.jsp" target='_self'> <img src="images/home.png" alt="${main}"><span>&nbsp;${main}</span>
				</a></li>

				<li><a href="404.html" target='_self'> <img src="images/comments.png" alt="${news}"><span>&nbsp;${news}</span>
				</a></li>
				<li><a href="Controller?command=goto_tariffs" target='_self'> <img src="images/notebook.png"
						alt="${tariffs}"><span>&nbsp;${tariffs}</span>
				</a></li>
				<li><a href="404.html" target='_self'> <img src="images/favorite.png" alt="${offers}"><span>&nbsp;${offers}</span>
				</a></li>
				<li><a href="https://careers.epam.by/company" target='_self'> <img src="images/comment.png"
						alt="${about}"><span>&nbsp;${about}</span>
				</a>
				<li><a href="#"><img src="images/user.png" alt="${user_menu}"><span>&nbsp;${user_menu}</span></a>
					<ul class="items">

						<ctg:logged>
							<li>
								<div class="user-string">
									<ctg:full-name />
								</div>
							</li>
						</ctg:logged>
						<ctg:not-logged>
							<li>
								<form class="login-form" action="Controller" method="post">
									<input type="hidden" name="command" value="log_in" /> <input type="text" name="login" value=""
										placeholder="${login}" required> <input type="password" name="password" value=""
										placeholder="${password}" required /> <input type="submit" value="${log_in}" class="login_button" />
								</form>
							</li>
						</ctg:not-logged>
						<ctg:logged>
							<ctg:user-role role="admin">
								<li><form class="login-form" action="Controller" method="post">
										<input type="hidden" name="command" value="goto_admin" /> <input class="login_button" type="submit"
											value="${admin_page}" />
									</form></li>
							</ctg:user-role>
							<ctg:user-role role="client">
								<li><form class="login-form" action="Controller" method="post">
										<input type="hidden" name="command" value="goto_client" /> <input class="login_button"
											type="submit" value="${client_page}" />
									</form></li>
							</ctg:user-role>
							<li><form class="login-form" action="Controller" method="post">
									<input type="hidden" name="command" value="log_off" /> <input class="login_button" type="submit"
										value="${log_off}" />
								</form></li>
						</ctg:logged>
						<ctg:not-logged>
							<li>
								<form class="login-form" action="Controller" method="post">
									<input type="hidden" name="command" value="register" /> <input type="submit" value="${new_user}"
										class="login_button" />
								</form>
							</li>
						</ctg:not-logged>
					</ul></li>
			</ul>
		</div>
	</div>
	<div class="headerPictureAd">
		<img src="images/background/connectors.jpg" alt="background not found">
		<h1>
			<c:out value="${header_text}" />
		</h1>
	</div>
</div>