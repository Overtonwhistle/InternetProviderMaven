<%@ page isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.error_page.auth_error" var="header_text" />
<fmt:message bundle="${loc}" key="local.error_page.auth_error_message" var="message" />
<fmt:message bundle="${loc}" key="local.error_page.goto_index_page" var="goto_index_page" />
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico " type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/error-style.css" type="text/css">
</head>
<body>
	<div class="error-page">
		<img src="images/background/computer-network-errors.jpg" alt="background not foung">
		<h1>${header_text}</h1>
		<div class="error-page">
			<div class="content">
				<div class="cont-column">
					<div class="cont-hint">
						<span>${message}</span>
					</div>
				</div>
				<div class="cont-back">
					<a href="index.jsp" class="submit">${goto_index_page} </a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>