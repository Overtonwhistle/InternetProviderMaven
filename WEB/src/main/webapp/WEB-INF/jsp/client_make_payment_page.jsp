<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.client_payments.making_payment" var="making_payment" />
<fmt:message key="local.client_payments.curr_ballance" var="curr_ballance" />
<fmt:message key="local.client_payments.enter_text" var="enter_text" />
<fmt:message key="local.client_payments.button_pay" var="button_pay" />
<fmt:message key="local.button_back" var="button_back" />
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
	<c:import url="client_page_header.jsp" />
	<!-- CONTENT -->
	<div class="content">
		<h6>${curr_ballance}:
			<c:out value="${sessionScope.user.accountBallance}" />
		</h6>
		<form class="reg_form" name="make_payment_form" method="post" action="Controller">
			<ul>
				<li>
					<h4>${making_payment}</h4>
				</li>
				<li><label>${enter_text}:</label> <input type="text" name="payment_amount"></li>
				<li>
					<button class="submit" type="submit" name="command" value="goto_client_payments">${button_back}</button>
					<button style="margin-left: 8%;" class="submit" type="submit" name="command"
						value="client_make_payment_process">${button_pay}</button>
				</li>
			</ul>
		</form>
	</div>
	<c:import url="footer.jps" />
</body>
</html>