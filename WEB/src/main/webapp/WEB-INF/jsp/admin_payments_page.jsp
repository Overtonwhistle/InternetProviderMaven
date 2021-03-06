<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local"/>
<fmt:message key="local.no_data" var="no_data" />
<fmt:message key="local.admin_payments.pay_searching" var="pay_searching" />
<fmt:message key="local.admin_payments.fname" var="fname" />
<fmt:message key="local.admin_payments.fname_ph" var="fname_ph" />
<fmt:message key="local.admin_payments.lname" var="lname" />
<fmt:message key="local.admin_payments.lname_ph" var="lname_ph" />
<fmt:message key="local.admin_payments.pay_date" var="pay_date" />
<fmt:message key="local.admin_payments.pay_date_time" var="pay_date_time" />
<fmt:message key="local.admin_payments.pay_date_ph" var="pay_date_ph" />
<fmt:message key="local.admin_payments.pay_amount" var="pay_amount" />
<fmt:message key="local.admin_payments.pay_amount_ph" var="pay_amount_ph" />
<fmt:message key="local.admin_payments.sort_by" var="sort_by" />
<fmt:message key="local.admin_payments.sort_by_date" var="sort_by_date" />
<fmt:message key="local.admin_payments.sort_by_user" var="sort_by_user" />
<fmt:message key="local.admin_payments.sort_by_amount" var="sort_by_amount" />
<fmt:message key="local.admin_payments.button_reset" var="button_reset" />
<fmt:message key="local.admin_payments.button_search" var="button_search" />
<fmt:message key="local.admin_payments.user_name" var="user_name" />
<fmt:message key="local.admin_payments.ballance" var="ballance" />
<fmt:message key="local.admin_payments.amount" var="amount" />
<fmt:message key="local.page_navigation.next_page" var="next_page" />
<fmt:message key="local.page_navigation.previous_page" var="previous_page" />

<c:set var="current_page" scope="session" value="WEB-INF/jsp/admin_payments_page.jsp" />
<c:set var="num_rows" scope="page" value="${14}" />
<c:set var="res_rows" scope="page" value="${fn:length(sessionScope.result_list)}" />

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
	<c:import url="admin_page_header.jsp" />

	<!-- CONTENT ========================================================= -->
	<div class="content">
		<form class="reg_form" name="payment_serching" method="post" action="Controller">
			<input type="hidden" name=is_page_seacrhing value="true" />
			<ul>
				<li>
					<h4>${pay_searching}</h4>
				</li>
				<li><label>${fname}: </label><input type="text" name="first_name" placeholder="${fname_ph}"></li>
				<li><label>${lname}: </label><input type="text" name="last_name" placeholder="${lname_ph}"></li>

				<li><label>${pay_date}:</label><select size="1" name="payment_date_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="payment_date" placeholder="${pay_date_ph}"></li>

				<li><label>${pay_amount}:</label> <select size="1" name="amount_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="amount" placeholder="${pay_amount_ph}"></li>

				<li><label>${sort_by}:</label><select size="1" name="sort_by">
						<option value="date" selected>${sort_by_date}</option>
						<option value="user">${sort_by_user}</option>
						<option value="amount">${sort_by_amount}</option>
				</select></li>
				<li>
					<button class="submit" type="reset">${button_reset }</button>
					<button class="submit" type="submit" name="command" value="goto_ad_payments">${button_search}</button>
				</li>
			</ul>
		</form>
		<div>
			<form name="payment_operation" method="post" action="Controller">
				<c:if test="${sessionScope.result_list[0] eq null}">
					<h4>${no_data}</h4>
				</c:if>

				<c:if test="${sessionScope.result_list[0] ne null}">
					<table style="width: 70%;">
						<tr>
							<th style="width: 2%;">№</th>
							<th style="width: 14%;">${user_name}</th>
							<th style="width: 13%;">${amount}</th>
							<th style="width: 13%;">${pay_date_time}</th>
						</tr>

						<c:forEach items="${sessionScope.result_list}" var="payment" varStatus="status"
							begin="${sessionScope.start_index}" end="${sessionScope.start_index+num_rows}">
							<c:set var="tariff_id" value="${request.tariffId}" />
							<tr <c:if test="${status.index%2 eq 0}">class="even"</c:if>>
								<td><c:out value="${status.index+1}" /></td>
								<td><c:out value="${sessionScope.users_list[status.index].firstName}" /> <c:out
										value="${sessionScope.users_list[status.index].lastName}" /></td>
								<td><c:out value="${payment.amount}" /></td>
								<td><fmt:formatDate type="time" value="${payment.paymentDate}" pattern="yyyy.MM.dd, HH:mm" /></td>
							</tr>
						</c:forEach>

					</table>
				</c:if>
			</form>

			<%-- PREVIOUS / NEXT block --%>

			<form class="reg_form" name="requests_serching" method="post" action="Controller">
				<c:if test="${sessionScope.start_index gt 0}">
					<button class="submit" type="submit" name="command" value="previous_result_page">${previous_page}</button>
				</c:if>
				<c:if test="${(res_rows - sessionScope.start_index) gt num_rows}">
					<button class="submit" type="submit" name="command" value="next_result_page">${next_page}</button>
				</c:if>
			</form>

		</div>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>