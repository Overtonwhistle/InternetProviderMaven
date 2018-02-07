<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<c:set var="url" scope="session" value="Controller?command=goto_client" />
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.client_page.summary_text" var="summary_text" />
<fmt:message bundle="${loc}" key="local.client_page.data_at" var="data_at" />
<fmt:message bundle="${loc}" key="local.client_page.menu_hint" var="menu_hint" />
<fmt:message bundle="${loc}" key="local.client_page.reg_date" var="reg_date" />
<fmt:message bundle="${loc}" key="local.client_page.passport" var="passport" />
<fmt:message bundle="${loc}" key="local.client_page.email" var="email" />
<fmt:message bundle="${loc}" key="local.client_page.m_data" var="m_data" />
<fmt:message bundle="${loc}" key="local.client_page.t_data" var="t_data" />
<fmt:message bundle="${loc}" key="local.client_page.megabytes" var="megabytes" />
<fmt:message bundle="${loc}" key="local.client_page.ballance" var="ballance" />
<fmt:message bundle="${loc}" key="local.client_page.tariff" var="tariff" />
<fmt:message bundle="${loc}" key="local.client_page.tariff_not_set" var="tariff_not_set" />
<fmt:message bundle="${loc}" key="local.client_page.blocked_since" var="blocked_since" />
<fmt:message bundle="${loc}" key="local.client_page.blocking_reason" var="blocking_reason" />
<fmt:message bundle="${loc}" key="local.client_page.blocking_reason_1" var="blocking_reason_1" />
<fmt:message bundle="${loc}" key="local.client_page.blocking_reason_2" var="blocking_reason_2" />
<fmt:message bundle="${loc}" key="local.client_page.blocking_reason_3" var="blocking_reason_3" />
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
		<div class="row_of_two">
			<div class="t_one_of_two">
				<h4>${summary_text}</h4>
				<p>
					<i>${data_at} <fmt:formatDate type="both" value="${now}" dateStyle="long" /></i>
				</p>
				<h4>
					<c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}" />
				</h4>
				<table class="user_info">
					<tr>
						<td style="width: 40%;">${reg_date}</td>
						<td style="width: 60%;"><fmt:formatDate type="date" value="${sessionScope.user.regDate}"
								dateStyle="long" /> <%--  <c:out value=" ${sessionScope.user.regDate}" /> --%></td>
					</tr>
					<tr class="even">
						<td>${passport}</td>
						<td><c:out value=" ${sessionScope.user.passportNumber}" /></td>
					</tr>
					<tr>
						<td>${email}</td>
						<td><c:out value=" ${sessionScope.user.email}" /></td>
					</tr>
					<tr class="even">
						<td>${m_data}</td>
						<td><c:out value=" ${sessionScope.user.monthlyDataUsage}" />&nbsp;${megabytes}</td>
					</tr>
					<tr>
						<td>${t_data}</td>
						<td><c:out value="${sessionScope.user.totalDataUsage}" />&nbsp;${megabytes}</td>
					</tr>
					<tr class="even">
						<td>${ballance}</td>
						<td><c:out value="${sessionScope.user.accountBallance}" /></td>
					</tr>
					<tr>
						<td>${tariff}</td>
						<td><c:if test="${requestScope.current_tariff eq null}"> 
          ${tariff_not_set}
            </c:if> <a
							href='Controller?command=goto_tariff_info&tariff_id=<c:out
                    value="${requestScope.current_tariff.id}"/>'
							title='Click to tariff detail'> <c:out value=" ${requestScope.current_tariff.title}" />
						</a></td>
					</tr>
					<c:if test="${requestScope.is_blocked eq true}">
						<tr class="even">
							<td>${blocked_since}</td>
							<td><fmt:formatDate type="both" value="${requestScope.user_ban.banDate}" dateStyle="long" />
						</tr>
						<tr>
							<td>${blocking_reason}</td>
							<td><c:if test="${requestScope.user_ban.banReason eq 1}">
									<c:out value=" ${blocking_reason_1}" />
								</c:if> <c:if test="${requestScope.user_ban.banReason eq 2}">
									<c:out value=" ${blocking_reason_2}" />
								</c:if> <c:if test="${requestScope.user_ban.banReason eq 3}">
									<c:out value=" ${blocking_reason_3}" />
								</c:if></td>
						</tr>
					</c:if>
				</table>
			</div>
			<div class="t_one_of_two"></div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>