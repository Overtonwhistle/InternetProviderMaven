<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="page" scope="session" value="index.jsp" />
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.admin_requests.del_request_text" var="del_request_text" />
<fmt:message key="local.admin_requests.proc_request_details" var="proc_request_details" />
<fmt:message key="local.admin_requests.user" var="request_user" />
<fmt:message key="local.admin_requests.ballance" var="ballance" />
<fmt:message key="local.admin_requests.current_tariff" var="current_tariff_text" />
<fmt:message key="local.admin_requests.current_tariff_empty" var="current_tariff_empty" />
<fmt:message key="local.admin_requests.requested_tariff" var="requested_tariff" />
<fmt:message key="local.admin_requests.del_request_warning" var="del_request_warning" />
<fmt:message key="local.button_back" var="button_back" />
<fmt:message key="local.admin_requests.button_delete_do" var="button_delete_do" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- BASE PART -->
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/users_search_form.css" type="text/css">
</head>
<body>
	<c:import url="admin_page_header.jsp" />
	<!-- CONTENT -->
	<div class="content">
		<h2>${del_request_text}</h2>
		<form action="Controller" method="post" class="reg_form">
			<ul>
				<li>
					<h4>${proc_request_details}</h4>
				</li>
				<li><p>
						${request_user}: <strong> <c:out value="${requestScope.user.firstName}" /> <c:out
								value="${requestScope.user.lastName}" />
						</strong>
					</p></li>
				<li><p>
						${ballance}: <strong><c:out value="${requestScope.user.accountBallance}" /></strong>
					</p></li>
				<li>
					<p>
						${current_tariff_text}: <strong> <c:if test="${requestScope.current_tariff eq null}"> 
          ${current_tariff_empty}
            </c:if> <c:out value="${requestScope.current_tariff}" /></strong>
					</p>
				</li>
				<li><p>
						${requested_tariff}:<strong> <c:out value="${requestScope.requested_tariff.title}" /></strong>
					</p></li>
				<li><p class="warning">${del_request_warning}</p></li>
				<li>
					<button class="submit" type="submit" name="command" value="goto_ad_requests">${button_back}</button>
					<button class="delete" type="submit" name="command" value="admin_del_request_process">${button_delete_do}</button>
				</li>
			</ul>
		</form>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>