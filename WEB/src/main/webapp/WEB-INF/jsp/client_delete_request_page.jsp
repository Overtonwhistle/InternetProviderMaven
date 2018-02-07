<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- BASE CLIENT PART -->
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.client_tariffs_page.delete_request_text" var="delete_request_text" />
<fmt:message bundle="${loc}" key="local.client_tariffs_page.delete_request_question"
	var="delete_request_question" />
<fmt:message bundle="${loc}" key="local.client_tariffs_page.button_back" var="button_back" />
<fmt:message bundle="${loc}" key="local.client_tariffs_page.button_delete" var="button_delete" />
<!-- CLIENT BLOCK PART -->
<fmt:message bundle="${loc}" key="local.client_payments.making_payment" var="making_payment" />
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
		<h6>${delete_request_text}</h6>
		<form class="reg_form" name="make_payment_form" method="post" action="Controller">
			<ul>
				<li>
					<h4>${delete_request_question}</h4>
				</li>
				<li>
					<button class="submit" type="submit" name="command" value="goto_client_requests">${button_back}</button>
					<button class="delete" type="submit" name="command" value="client_delete_request_process">${button_delete}</button>
				</li>
			</ul>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>