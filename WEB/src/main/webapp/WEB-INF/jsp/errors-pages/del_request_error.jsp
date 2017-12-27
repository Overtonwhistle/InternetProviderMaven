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
<fmt:message bundle="${loc}" key="local.error_page.delete_request_header"
	var="delete_request_header" />
<fmt:message bundle="${loc}" key="local.error_page.delete_request_text"
	var="delete_request_text" />
<fmt:message bundle="${loc}" key="local.error_page.try_again" var="try_again" />
<fmt:message bundle="${loc}" key="local.error_page.go_back" var="go_back" />
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/error-style.css" type="text/css">
<link rel="stylesheet" href="css/reg-form.css" type="text/css">
</head>
<body>
	<div class="error-page">
		<img src="images/background/computer-network-errors.jpg"
			alt="background not foung">
		<h1>${delete_request_header}</h1>
		<div class="error-page">
			<div class="content">
				<div class="cont-column">
					<span><c:out value="${errors}" /></span>
					<div class="cont-hint">
						<span>${delete_request_text}</span>
					</div>
				</div>
				<form action="Controller" method="post" class="reg_form">
					<div class="cont-back">
						<button class="submit" type="submit" name="command"
							value="goto_client_requests">${go_back}</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>