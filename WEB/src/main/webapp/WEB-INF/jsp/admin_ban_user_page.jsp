<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="page" scope="session" value="index.jsp" />
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.back_button" var="back_button" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.history_text" var="history_text" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.history_empty" var="history_empty" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.table_reason" var="table_reason" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.table_start" var="table_start" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.table_end" var="table_end" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.table_active" var="table_active" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.table_comment" var="table_comment" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.reason_field" var="reason_field" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.reason_ballance" var="reason_ballance" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.reason_policy" var="reason_policy" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.reason_request" var="reason_request" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.comment_field" var="comment_field" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.comment_ph" var="comment_ph" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.block_user_button" var="block_user_button" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.blocking_text" var="blocking_text" />
<fmt:message bundle="${loc}" key="local.admin_block_users_page.unblock_user_button" var="unblock_user_button" />
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
	<%@ include file="admin_page_header.jsp"%>
	<!-- CONTENT -->
	<div class="content">
		<h4>
			<c:out value="${sessionScope.user_to_work.firstName}" />
			<c:out value="${sessionScope.user_to_work.lastName}" />
			<br>
		</h4>
		<h6>
			<c:if test="${not empty requestScope.user_ban_list}">${history_text} </c:if>
		</h6>
		<div class="h_hint">
			<c:if test="${empty requestScope.user_ban_list}">${history_empty} </c:if>
		</div>
		<c:if test="${not empty requestScope.user_ban_list}">
			<table>
				<tr>
					<th>${table_reason}</th>
					<th>${table_start}</th>
					<th>${table_end}</th>
					<th>${table_comment}</th>
				</tr>
				<c:forEach items="${requestScope.user_ban_list}" var="ban">
					<c:set var="reason_id" value="${ban.banReason}" />
					<tr>
						<td><c:if test="${reason_id eq 1}">
								<c:out value=" ${reason_ballance}" />
							</c:if> <c:if test="${reason_id eq 2}">
								<c:out value=" ${reason_policy}" />
							</c:if> <c:if test="${reason_id eq 3}">
								<c:out value=" ${reason_request}" />
							</c:if></td>
						<td><c:out value="${ban.banDate}" /></td>
						<td><c:out value="${ban.unbanDate}" /> <c:if test="${empty ban.unbanDate}"> 
            ${table_active}
            </c:if></td>
						<td><c:out value="${ban.comment}" /></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<form action="Controller" method="post" class="reg_form">
			<ul>
				<li>
					<h6>${blocking_text}</h6>
				</li>
				<c:if test="${requestScope.is_blocked ne true}">
					<li><label>${reason_field}:</label><select style="margin-left: 0px;" size="1" name="ban_reason">
							<option value="1" selected>${reason_ballance}</option>
							<option value="2">${reason_policy}</option>
							<option value="3">${reason_request}</option>
					</select></li>
					<li><label>${comment_field}:</label> <input type="text" name="ban_comment"
						placeholder="${comment_ph}"></li>
					<li>
						<button class="submit" type="submit" name="command" value="admin_ban_user_process">${block_user_button}</button>
					</li>
				</c:if>
				<c:if test="${requestScope.is_blocked eq true}">
					<li>
						<button class="submit" type="submit" name="command" value="admin_unban_user_process">${unblock_user_button}</button>
					</li>
				</c:if>
				<li>
					<button class="submit" type="submit" name="command" value="goto_ad_users">${back_button}</button>
				</li>
			</ul>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>