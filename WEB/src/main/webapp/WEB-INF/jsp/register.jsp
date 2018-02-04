<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.reg_header" var="header_text" />
<fmt:message bundle="${loc}" key="local.reg_form" var="reg_form" />
<fmt:message bundle="${loc}" key="local.required" var="required" />
<fmt:message bundle="${loc}" key="local.first_name" var="first_name" />
<fmt:message bundle="${loc}" key="local.last_name" var="last_name" />
<fmt:message bundle="${loc}" key="local.passport" var="passport" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.rep_password" var="rep_password" />
<fmt:message bundle="${loc}" key="local.back_button" var="back_button" />
<fmt:message bundle="${loc}" key="local.register_button" var="register_button" />
<fmt:message bundle="${loc}" key="local.first_name_hint" var="first_name_hint" />
<fmt:message bundle="${loc}" key="local.last_name_hint" var="last_name_hint" />
<fmt:message bundle="${loc}" key="local.passport_hint" var="passport_hint" />
<fmt:message bundle="${loc}" key="local.email_hint" var="email_hint" />
<fmt:message bundle="${loc}" key="local.login_hint" var="login_hint" />
<fmt:message bundle="${loc}" key="local.password_hint" var="password_hint" />
<fmt:message bundle="${loc}" key="local.rep_password_hint" var="rep_password_hint" />
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/reg-form.css" type="text/css">
<style>
</style>
</head>
<body>
	<!--  HEADER -->
	<%@ include file="index_page_header.jsp"%>
	<!-- CONTENT -->
	<div class="content">
		<form class="reg_form" action="Controller" method="post" onsubmit="return validateForm()" name="RegForm">
			<ul>
				<li>
					<h2>${reg_form}</h2> <span class="required_notification">* ${required}</span>
				</li>
				<li><label>${first_name}:</label> <input type="text" name="firstname" placeholder="Michael*" /><span
					class="form_hint">${first_name_hint}</span><span class="err" id="err-fname"></span></li>
				<li><label>${last_name}:</label> <input type="text" name="lastname" placeholder="Coleman*" /><span
					class="form_hint">${last_name_hint}</span><span class="err" id="err-lname"></span></li>
				<li><label>${passport}:</label> <input type="text" name="passport" placeholder="ABC1234567890*" /><span
					class="form_hint">${passport_hint}</span><span class="err" id="err-passp"></span></li>
				<li><label>${email}:</label> <input type="text" name="email" placeholder="m.coleman@example.com*" />
					<span class="form_hint">${email_hint}</span><span class="err" id="err-email"></span></li>
				<li><label>${login}:</label> <input type="text" name="login" placeholder="mylogin*" /> <span
					class="form_hint">${login_hint}</span><span class="err" id="err-login"></span></li>
				<li><label>${password}:</label> <input type="password" name="pwd1" placeholder="*"> <span
					class="form_hint">${password_hint}</span><span class="err" id="err-pwd1"></span></li>
				<li><label>${rep_password}:</label> <input type="password" name="pwd2" placeholder="*"><span
					class="form_hint">${rep_password_hint}</span><span class="err" id="err-pwd2"></span></li>
				<li><a href="index.jsp" target='_self' title="${back_button}"> <img src="images/home.png"
						alt="${back_button}"><span>&nbsp;${back_button}</span>
				</a>
					<button style="margin-left: 20%;" class="submit" type="submit" name="command" value="reg_process">${register_button}</button></li>
			</ul>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
 	<c:if test="${local eq 'en'}">
		<script src="js/register_validation_form_en.js"></script>
	</c:if>
	<c:if test="${local eq 'ru'}">
		<script src="js/register_validation_form_ru.js"></script>
	</c:if> 
</body>
</html>