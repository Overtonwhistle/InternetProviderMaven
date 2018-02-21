<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.admin_page.header_text" var="header_text" />
<fmt:message key="local.admin_page.summary_text" var="summary_text" />
<fmt:message key="local.admin_page.data_at" var="data_at" />
<fmt:message key="local.admin_page.total_clients" var="total_clients_info" />
<fmt:message key="local.admin_page.neg_clients" var="neg_clients_info" />
<fmt:message key="local.admin_page.block_clients" var="block_clients_info" />
<fmt:message key="local.admin_page.active_requests" var="active_requests_info" />
<fmt:message key="local.admin_page.total_tariffs" var="total_tariffs_info" />
<fmt:message key="local.admin_page.not_used_tariffs" var="not_used_tariffs_info" />
<fmt:message key="local.admin_page.menu_hint" var="menu_hint" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<!-- END ADMIN PART -->
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
</head>
<body>
	<c:import url="admin_page_header.jsp" />
	<!-- CONTENT -->
	<div class="content">
		<div class="row_of_two">
			<div class="t_one_of_two">
				<h4>${summary_text}</h4>
				<p>
					<i>${data_at}: <ctg:info-time /></i>
				</p>
				<p>
					${total_clients_info}:
					<c:out value=" ${requestScope.clients}" />
				</p>
				<p>${neg_clients_info}:
					<c:out value=" ${requestScope.clients_negative}" />
				</p>
				<p>${block_clients_info}:
					<c:out value=" ${requestScope.in_ban}" />
				</p>
				<p>${active_requests_info}:
					<c:out value=" ${requestScope.active_requests}" />
				</p>
				<p>${total_tariffs_info}:
					<c:out value=" ${requestScope.total_tariffs}" />
				</p>
				<p>${not_used_tariffs_info}:
					<c:out value=" ${requestScope.not_used_tariffs}" />
				</p>
			</div>
			<div class="t_one_of_two">
				<h4>${menu_hint}</h4>
			</div>
		</div>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>
