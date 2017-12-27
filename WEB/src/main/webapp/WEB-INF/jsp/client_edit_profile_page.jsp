<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<c:if test="${user.role ne 'CLIENT'}">
	<jsp:forward page="../../index.jsp" />
</c:if>
<%-- <c:set var="url" scope="session" value="Controller?command=goto_client_payments" /> --%>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.client_profile_page.editing_text" var="editing_text" />
<fmt:message bundle="${loc}" key="local.client_profile_page.first_name" var="new_first_name" />
<fmt:message bundle="${loc}" key="local.client_profile_page.first_name_hint" var="first_name_hint" />
<fmt:message bundle="${loc}" key="local.client_profile_page.last_name" var="new_last_name" />
<fmt:message bundle="${loc}" key="local.client_profile_page.last_name_hint" var="last_name_hint" />
<fmt:message bundle="${loc}" key="local.client_profile_page.passport" var="new_passport" />
<fmt:message bundle="${loc}" key="local.client_profile_page.passport_hint" var="passport_hint" />
<fmt:message bundle="${loc}" key="local.client_profile_page.email" var="new_email" />
<fmt:message bundle="${loc}" key="local.client_profile_page.email_hint" var="email_hint" />
<fmt:message bundle="${loc}" key="local.client_profile_page.new_password" var="new_password" />
<fmt:message bundle="${loc}" key="local.client_profile_page.new_password_hint"
	var="new_password_hint" />
<fmt:message bundle="${loc}" key="local.client_profile_page.new_password_ph" var="new_password_ph" />
<fmt:message bundle="${loc}" key="local.client_profile_page.current_password" var="current_password" />
<fmt:message bundle="${loc}" key="local.client_profile_page.current_password_hint"
	var="current_password_hint" />
<fmt:message bundle="${loc}" key="local.client_profile_page.wrong_current_password_text"
	var="wrong_current_password_text" />
<fmt:message bundle="${loc}" key="local.client_profile_page.apply_button" var="apply_button" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/users_search_form.css" type="text/css">
</head>
<body>
	<!--  HEADER -->
	<%@ include file="client_page_header.jsp"%>
	<!-- CONTENT -->
	<div class="content">
		<form class="reg_form" name="editing_profile" method="post" action="Controller">
			<input type="hidden" name=is_page_seacrhing value="true" />
			<ul>
				<li>
					<h4>${editing_text}</h4>
				</li>
				<li><label>${new_first_name}:</label> <input type="text" name="new_firstname"
					value=${sessionScope.user.firstName } /><span class="form_hint">${first_name_hint}</span><span
					class="err" id="err-fname"></span></li>
				<li><label>${new_last_name}:</label> <input type="text" name="new_lastname"
					value=${sessionScope.user.lastName }><span class="form_hint">${last_name_hint}</span><span
					class="err" id="err-lname"></span></li>
				<li><label>${new_passport}:</label> <input type="text" name="new_passport"
					value=${sessionScope.user.passportNumber }><span class="form_hint">${passport_hint}</span><span
					class="err" id="err-passport"></span></li>
				<li><label>${new_email}:</label> <input type="text" name="new_email"
					value=${sessionScope.user.email }><span class="form_hint">${email_hint}</span><span
					class="err" id="err-email"></span></li>
				<li><label>${new_password}:</label> <input type="password" name="new_password"
					placeholder="${new_password_ph}"><span class="form_hint">${new_password_hint}</span><span
					class="err" id="err-pwd1"></span></li>
				<li><label>${current_password}:</label> <input type="password" name="current_password"
					required><span class="form_hint">${current_password_hint}</span></li>
				<c:if test="${requestScope.wrong_current_password eq true}">
					<li><span class="err">${wrong_current_password_text}</span>
				</c:if>
				<c:remove var="wrong_current_password" scope="request" />
				<li>
					<button class="submit" type="submit" name="command" value="client_edit_profile_process">${apply_button}</button>
				</li>
			</ul>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>