<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<c:set var="url" scope="session" value="Controller?command=goto_client_requests" />
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.client_requests.current_tariff_text" var="current_tariff_text" />
<fmt:message bundle="${loc}" key="local.client_requests.tariff_details_hint" var="tariff_details_hint" />
<fmt:message bundle="${loc}" key="local.client_requests.tariff_changing_hint" var="tariff_changing_hint" />
<fmt:message bundle="${loc}" key="local.client_requests.button_change_tariff" var="button_change_tariff" />
<fmt:message bundle="${loc}" key="local.client_requests.active_request_text" var="active_request_text" />
<fmt:message bundle="${loc}" key="local.client_requests.request_date_text" var="request_date_text" />
<fmt:message bundle="${loc}" key="local.client_requests.requested_tariff_text" var="requested_tariff_text" />
<fmt:message bundle="${loc}" key="local.client_requests.delete_request_hint" var="delete_request_hint" />
<fmt:message bundle="${loc}" key="local.client_requests.button_delete_request" var="button_delete_request" />
<fmt:message bundle="${loc}" key="local.client_requests.history_empty_text" var="history_empty_text" />
<fmt:message bundle="${loc}" key="local.client_requests.history_text" var="history_text" />
<fmt:message bundle="${loc}" key="local.client_requests.history_changing_date" var="history_changing_date" />
<fmt:message bundle="${loc}" key="local.client_requests.history_tariff" var="history_tariff" />
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
	<c:if test="${user.role ne 'CLIENT'}">
		<jsp:forward page="../../index.jsp" />
	</c:if>
	<!--  HEADER -->
	<%@ include file="client_page_header.jsp"%>
	<!-- CONTENT -->
	<div class="content">
		<div class="row_of_two">
			<div class="t_one_of_two">
				<h4>
					${current_tariff_text}: <a
						href='Controller?command=goto_tariff_info&tariff_id=<c:out
                    value="${current_tariff.id}"/>'
						title='${tariff_details_hint}'> <c:out value=" ${current_tariff.title}" />
					</a>
				</h4>
				<c:if test="${sessionScope.active_request eq null}">
					<form class="reg_form" name="request_form" method="post" action="Controller">
						<div class="h_hint">${tariff_changing_hint}</div>
						<p>
							<br>
							<button class="submit" type="submit" name="command" value="goto_client_change_tariff">${button_change_tariff}</button>
						</p>
					</form>
				</c:if>
				<c:if test="${sessionScope.active_request ne null}">
					<h6>${active_request_text}</h6>
					<form class="reg_form" name="request_delete" method="post" action="Controller">
						<table class="user_info">
							<tr>
								<td style="width: 40%;">${request_date_text}:</td>
								<td style="width: 60%;"><fmt:formatDate type="both"
										value="${sessionScope.active_request.requestDate}" dateStyle="long" /></td>
							</tr>
							<tr class="even">
								<td>${requested_tariff_text}:</td>
								<td><a
									href='Controller?command=goto_tariff_info&tariff_id=<c:out
                    value="${requested_tariff.id}"/>'
									title='${tariff_details_hint}'> <c:out value=" ${requested_tariff.title}" />
								</a>
							</tr>
						</table>
						<div class="h_hint">${delete_request_hint}</div>
						<br> <input type="hidden" name="request_id_to_delete" value="${requestScope.active_request.id}" />
						<button class="submit" type="submit" name="command" value="goto_client_delete_request">${button_delete_request}</button>
					</form>
				</c:if>
			</div>
			<%--        HISTORY PART ---------------------------------------%>
			<div class="t_one_of_two">
				<c:if test="${requestScope.history_requests_list[0] eq null}">
					<h4>${history_empty_text}</h4>
				</c:if>
				<c:if test="${requestScope.history_requests_list[0] ne null}">
					<h4>${history_text}</h4>
					<table class="user_info">
						<tr>
							<th style="width: 30%;">${request_date_text}</th>
							<th style="width: 30%;">${history_changing_date}</th>
							<th style="width: 30%;">${history_tariff}</th>
						</tr>
						<c:forEach items="${requestScope.history_requests_list}" var="request" varStatus="status">
							<c:set var="tariff_id" value="${request.tariffId}" />
							<tr <c:if test="${status.index%2 eq 0}">class="even"</c:if>>
								<td><fmt:formatDate type="both" value="${request.requestDate}" pattern="yyyy.MM.dd, HH:mm" /></td>
								<td><fmt:formatDate type="both" value="${request.processedDate}" pattern="yyyy.MM.dd, HH:mm" /></td>
								<td><c:out value="${requestScope.history_tariffs_names[status.index]}" /></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>