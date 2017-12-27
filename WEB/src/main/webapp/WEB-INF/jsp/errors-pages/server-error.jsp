<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.error_page.server_error_text"
	var="server_error_text" />
<fmt:message bundle="${loc}" key="local.error_page.status_code"
	var="status_code" />
<fmt:message bundle="${loc}" key="local.error_page.servlet_name"
	var="servlet_name" />
<fmt:message bundle="${loc}" key="local.error_page.exc_type" var="exc_type" />
<fmt:message bundle="${loc}" key="local.error_page.exc_message"
	var="exc_message" />
<fmt:message bundle="${loc}" key="local.error_page.go_back" var="go_back" />
<title>Internet Provider</title>
<link rel="stylesheet" href="css/style-404.css" type="text/css">
<link rel="stylesheet" href="css/reg-form.css" type="text/css">
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
</head>
<body>
	<div class="content">
		<img src="images/background/cables.jpg" alt="404 page picture">
		<h2>${server_error_text}</h2>
		<div class="content">
			<div class="row_of_two">
				<c:if test="${sessionScope.user.role eq 'ADMIN'}">
					<p>
						${status_code}:<strong> <c:out value="${statusCode}" /></strong><br>
						URI:<strong> <c:out value="${uri}" /></strong> <br> ${servlet_name}:<strong>
							<c:out value="${servletName}" />
						</strong><br> ${exc_type}:<strong> <c:out value="${exceptionType}" /></strong><br>
						${exc_message}:<strong> <c:out value="${message}" /></strong> <br>
					</p>
				</c:if>
				<button class="submit" type="submit" onclick="history.back();">${go_back}</button>
			</div>
		</div>
	</div>
</body>
</html>