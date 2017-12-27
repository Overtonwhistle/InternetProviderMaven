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
<fmt:message bundle="${loc}" key="local.admin_users_page.searching_text" var="searching_text" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_role" var="user_role_field" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_role_all" var="user_role_all" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_role_client" var="user_role_client" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_role_admin" var="user_role_admin" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_fname" var="user_fname" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_fname_ph" var="user_fname_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_lname" var="user_lname" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_lname_ph" var="user_lname_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.pass_number" var="pass_number" />
<fmt:message bundle="${loc}" key="local.admin_users_page.pass_number_ph" var="pass_number_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.reg_date" var="reg_date_field" />
<fmt:message bundle="${loc}" key="local.admin_users_page.reg_date_ph" var="reg_date_field_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.monthly_data" var="monthly_data" />
<fmt:message bundle="${loc}" key="local.admin_users_page.monthly_data_ph" var="monthly_data_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.total_data" var="total_data_field" />
<fmt:message bundle="${loc}" key="local.admin_users_page.total_data_ph" var="total_data_field_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.acc_ballance" var="acc_ballance" />
<fmt:message bundle="${loc}" key="local.admin_users_page.acc_ballance_ph" var="acc_ballance_ph" />
<fmt:message bundle="${loc}" key="local.admin_users_page.block_status" var="block_status" />
<fmt:message bundle="${loc}" key="local.admin_users_page.block_status_all" var="block_status_all" />
<fmt:message bundle="${loc}" key="local.admin_users_page.block_status_blocked"
	var="block_status_blocked" />
<fmt:message bundle="${loc}" key="local.admin_users_page.block_status_not_blocked"
	var="block_status_not_blocked" />
<fmt:message bundle="${loc}" key="local.admin_users_page.reset_button" var="reset_button" />
<fmt:message bundle="${loc}" key="local.admin_users_page.search_button" var="search_button" />
<fmt:message bundle="${loc}" key="local.admin_users_page.user_operation_title"
	var="user_operation_title" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_role" var="table_role" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_name" var="table_name" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_passport" var="table_passport" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_registration"
	var="table_registration" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_m_data" var="table_m_data" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_t_data" var="table_t_data" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_ballance" var="table_ballance" />
<fmt:message bundle="${loc}" key="local.admin_users_page.table_tariff" var="table_tariff" />
<fmt:message bundle="${loc}" key="local.admin_users_page.block_button" var="block_button" />
<fmt:message bundle="${loc}" key="local.admin_users_page.edit_user_button" var="edit_user_button" />
<fmt:message bundle="${loc}" key="local.admin_users_page.delete_user_button"
	var="delete_user_button" />
<fmt:message bundle="${loc}" key="local.admin_users_page.no_data" var="no_data_text" />
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
	<!-- CONTENT ========================================================= -->
	<div class="content">
		<form class="reg_form" name="user_serching" method="post" action="Controller">
			<input type="hidden" name=is_page_seacrhing value="true" />
			<ul>
				<li>
					<h4>${searching_text}</h4>
				</li>
				<li><label>${user_role_field}:</label> <select size="1" name="user_role">
						<option value="all" selected>${user_role_all}</option>
						<option value="client">${user_role_client}</option>
						<option value="admin">${user_role_admin}</option>
				</select></li>
				<li><label> ${user_fname}: </label><input type="text" name="first_name"
					placeholder="${user_fname_ph}"></li>
				<li><label>${user_lname}: </label><input type="text" name="last_name"
					placeholder="${user_lname_ph}"></li>
				<li><label> ${pass_number}:</label> <input type="text" name="passport"
					placeholder="${pass_number_ph}"></li>
				<li><label> ${reg_date_field}: </label><select size="1" name="reg_date_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="reg_date" placeholder="${reg_date_field_ph}"></li>
				<li><label> ${monthly_data}:</label> <select size="1" name="month_data_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="month_data" placeholder="${monthly_data_ph}"></li>
				<li><label> ${total_data_field}:</label> <select size="1" name="total_data_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="total_data" placeholder="${total_data_field_ph}"></li>
				<li><label> ${acc_ballance}:</label> <select size="1" name="ballance_condition">
						<option value="greater">&gt;</option>
						<option value="less" selected>&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="ballance" placeholder="${acc_ballance_ph}"></li>
				<li><label> ${block_status}: </label><select size="1" name="ban_status">
						<option value="All" selected>${block_status_all}</option>
						<option value="not_in_ban">${block_status_not_blocked}</option>
						<option value="in_ban">${block_status_blocked}</option>
				</select></li>
				<li>
					<button class="submit" type="reset">${reset_button}</button>
					<button class="submit" type="submit" name="command" value="goto_ad_users">${search_button}</button>
				</li>
			</ul>
		</form>
		<div>
			<form name="user_operation" method="post" action="Controller">
				<c:if test="${requestScope.users_list[0] eq null}">
					<h4>${no_data_text}</h4>
				</c:if>
				<c:if test="${requestScope.users_list[0] ne null}">
					<h4>${user_operation_title}</h4>
					<table>
						<tr>
							<th>&nbsp;</th>
							<th>${table_role}</th>
							<th>${table_name}</th>
							<th>${table_passport}</th>
							<th>${table_registration}</th>
							<th>${table_m_data}</th>
							<th>${table_t_data}</th>
							<th>${table_ballance}</th>
							<th>${table_tariff}</th>
						</tr>
						<c:forEach items="${requestScope.users_list}" var="user" varStatus="status">
							<c:set var="tariff_id" value="${user.tariffId}" />
							<tr <c:if test="${status.index%2 eq 0}">class="even"</c:if>>
								<td><input type="radio" name="user_id_selector" value="<c:out value="${user.id}"/>"
									required></td>
								<td><c:if test="${user.role eq 'ADMIN'}">
            ${user_role_admin}</c:if> <c:if test="${user.role eq 'CLIENT'}">
            ${user_role_client}
            </c:if></td>
								<td><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></td>
								<td><c:out value="${user.passportNumber}" /></td>
								<td><c:out value="${user.regDate}" /></td>
								<td><c:out value="${user.monthlyDataUsage}" /></td>
								<td><c:out value="${user.totalDataUsage}" /></td>
								<td <c:if test="${user.accountBallance lt 0}">style="font-weight: bold;"</c:if>><c:out
										value="${user.accountBallance}" /></td>
								<td><c:set var="keyString">${tariff_id}</c:set> <c:out
										value="${tariffs_map[tariff_id]}" /> <c:out value="${tariffs_map[keyString]}" /></td>
							</tr>
						</c:forEach>
					</table>
					<p></p>
					<button class="submit" type="submit" name="command" value="goto_ad_ban_user">${block_button}</button>
					<button class="submit" type="submit" name="command" value="goto_ad_edit_user">${edit_user_button}</button>
					<button class="delete" type="submit" name="command" value="goto_ad_delete_user">${delete_user_button}</button>
				</c:if>
			</form>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>