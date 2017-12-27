<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.button_back" var="button_back" />
<fmt:message bundle="${loc}" key="local.button_reset" var="button_reset" />
<fmt:message bundle="${loc}" key="local.button_search" var="button_search" />
<fmt:message bundle="${loc}" key="local.no_data" var="no_data" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_serching_text"
	var="request_serching_text" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_status" var="request_status" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_status_all" var="request_status_all" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_status_active"
	var="request_status_active" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_status_proc"
	var="request_status_proc" />
<fmt:message bundle="${loc}" key="local.admin_requests.requested_tariff" var="requested_tariff" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_date" var="request_date" />
<fmt:message bundle="${loc}" key="local.admin_requests.request_date_ph" var="request_date_ph" />
<fmt:message bundle="${loc}" key="local.admin_requests.proc_date" var="proc_date" />
<fmt:message bundle="${loc}" key="local.admin_requests.proc_date_ph" var="proc_date_ph" />
<fmt:message bundle="${loc}" key="local.admin_requests.sort_list" var="sort_list" />
<fmt:message bundle="${loc}" key="local.admin_requests.sort_date" var="sort_date" />
<fmt:message bundle="${loc}" key="local.admin_requests.sort_user" var="sort_user" />
<fmt:message bundle="${loc}" key="local.admin_requests.sort_tariff" var="sort_tariff" />
<fmt:message bundle="${loc}" key="local.admin_requests.operations_text" var="operations_text" />
<fmt:message bundle="${loc}" key="local.admin_requests.user_name" var="user_name" />
<fmt:message bundle="${loc}" key="local.admin_requests.ballance" var="ballance" />
<fmt:message bundle="${loc}" key="local.admin_requests.current_tariff" var="current_tariff" />
<fmt:message bundle="${loc}" key="local.admin_requests.requested_tariff" var="requested_tariff" />
<fmt:message bundle="${loc}" key="local.admin_requests.req_date_time" var="req_date_time" />
<fmt:message bundle="${loc}" key="local.admin_requests.proc_date_time" var="proc_date_time" />
<fmt:message bundle="${loc}" key="local.admin_requests.proc_date_active" var="proc_date_active" />
<fmt:message bundle="${loc}" key="local.admin_requests.proc_by" var="proc_by" />
<fmt:message bundle="${loc}" key="local.admin_requests.button_process" var="button_process" />
<fmt:message bundle="${loc}" key="local.admin_requests.button_delete" var="button_delete" />
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
	<c:if test="${user.role ne 'ADMIN'}">
		<jsp:forward page="../../index.jsp" />
	</c:if>
	<!--  HEADER -->
	<%@ include file="admin_page_header.jsp"%>
	<!-- CONTENT -->
	<div class="content">
		<form class="reg_form" name="request_serching" method="post" action="Controller">
			<input type="hidden" name=is_page_seacrhing value="true" />
			<ul>
				<li>
					<h4>${request_serching_text}</h4>
				</li>
				<li><label>${request_status}:</label><select size="1" name="status">
						<option value="all" selected>${request_status_all}</option>
						<option value="active">${request_status_active}</option>
						<option value="processed">${request_status_proc}</option>
				</select></li>
				<li><label> ${requested_tariff}: </label><select size="1" name="tariff">
						<option value="all" selected>${request_status_all}</option>
						<c:forEach items="${requestScope.tariffs_list}" var="tariff">
							<option value="<c:out value="${tariff.id}" />">
								<c:out value="${tariff.title}" />
							</option>
						</c:forEach>
				</select></li>
				<li><label>${request_date}:</label><select size="1" name="req_date_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="req_date" placeholder="${request_date_ph}"></li>
				<li><label>${proc_date}:</label><select size="1" name="proc_date_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="proc_date" placeholder="${proc_date_ph}"></li>
				<li><label>${sort_list}:</label><select size="1" name="sort_by">
						<option value="date" selected>${sort_date}</option>
						<option value="user">${sort_user}</option>
						<option value="tariff">${sort_tariff}</option>
				</select></li>
				<li>
					<button class="submit" type="reset">${button_reset}</button>
					<button class="submit" type="submit" name="command" value="goto_ad_requests">${button_search}</button>
				</li>
			</ul>
		</form>
		<div>
			<form name="request_operation" method="post" action="Controller">
				<c:if test="${requestScope.requests_list[0] eq null}">
					<h4>${no_data}</h4>
				</c:if>
				<c:if test="${requestScope.requests_list[0] ne null}">
					<h4>${operations_text}</h4>
					<table>
						<tr>
							<th style="width: 3%;">&nbsp;</th>
							<th style="width: 14%;">${user_name}</th>
							<th style="width: 9%;">${ballance}</th>
							<th style="width: 13%;">${requested_tariff}</th>
							<th style="width: 15%;">${req_date_time}</th>
							<th style="width: 16%;">${proc_date_time}</th>
							<th style="width: 14%;">${proc_by}</th>
						</tr>
						<c:forEach items="${requestScope.requests_list}" var="request" varStatus="status">
							<c:set var="tariff_id" value="${request.tariffId}" />
							<tr <c:if test="${status.index%2 eq 0}">class="even"</c:if>>
								<td><c:if test="${request.processedDate eq null}">
										<c:set var="active_present" scope="page" value="true" />
										<input type="radio" name="request_id_selector" value="<c:out value="${request.id}" />"
											required>
									</c:if></td>
								<td><c:out value="${requestScope.users_list[status.index].firstName}" /> <c:out
										value="${requestScope.users_list[status.index].lastName}" /></td>
								<td><c:out value="${requestScope.users_list[status.index].accountBallance}" /></td>
								<td><c:out value="${tariffs_names_list[status.index]}" /></td>
								<td><fmt:formatDate type="time" value="${request.requestDate}"
										pattern="yyyy.MM.dd, HH:mm" /></td>
								<td><c:if test="${request.processedDate eq null}">${proc_date_active}</c:if> <fmt:formatDate
										type="time" value="${request.processedDate}" pattern="yyyy.MM.dd, HH:mm" /></td>
								<td><c:out value="${requestScope.admins_names_list[status.index]}" /></td>
							</tr>
						</c:forEach>
					</table>
					<c:if test="${pageScope.active_present eq true}">
						<button class="submit" type="submit" name="command" value="goto_ad_process_request">${button_process}</button>
						<button class="delete" type="submit" name="command" value="goto_ad_delete_request">${button_delete}</button>
					</c:if>
				</c:if>
			</form>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>