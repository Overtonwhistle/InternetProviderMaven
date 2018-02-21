<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="page" scope="session" value="index.jsp" />
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${sessionScope.local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.admin_tariffs_page.add_text" var="add_text" />
<fmt:message key="local.admin_tariffs_page.unlim" var="unlim" />
<fmt:message key="local.admin_tariffs_page.all" var="all" />
<fmt:message key="local.admin_tariffs_page.yes" var="yes" />
<fmt:message key="local.admin_tariffs_page.no" var="no" />
<fmt:message key="local.admin_tariffs_page.m_cost" var="m_cost" />
<fmt:message key="local.admin_tariffs_page.over_cost" var="over_cost" />
<fmt:message key="local.admin_tariffs_page.over_cost_hint" var="over_cost_hint" />
<fmt:message key="local.admin_tariffs_page.table_title" var="table_title" />
<fmt:message key="local.admin_tariffs_page.table_title_hint" var="table_title_hint" />
<fmt:message key="local.admin_tariffs_page.m_cost_hint" var="m_cost_hint" />
<fmt:message key="local.admin_tariffs_page.m_limit" var="m_limit" />
<fmt:message key="local.admin_tariffs_page.m_limit_hint" var="m_limit_hint" />
<fmt:message key="local.admin_tariffs_page.table_m_cost" var="table_m_cost" />
<fmt:message key="local.admin_tariffs_page.table_unlim" var="table_unlim" />
<fmt:message key="local.admin_tariffs_page.table_m_limit" var="table_m_limit" />
<fmt:message key="local.admin_tariffs_page.table_over_cost" var="table_over_cost" />
<fmt:message key="local.admin_tariffs_page.table_technology" var="table_technology" />
<fmt:message key="local.admin_tariffs_page.table_descr" var="table_descr" />
<fmt:message key="local.admin_tariffs_page.table_descr_hint" var="table_descr_hint" />
<fmt:message key="local.admin_tariffs_page.edit_apply" var="edit_apply" />
<fmt:message key="local.admin_tariffs_page.delete_button" var="delete_button" />
<fmt:message key="local.admin_tariffs_page.editing_text" var="editing_text" />
<fmt:message key="local.admin_edit_users_page.back_button" var="back_button" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/reg-form.css" type="text/css">
</head>
<body>
	<c:import url="admin_page_header.jsp" />
	<!-- CONTENT -->
	<div class="content">
		<form action="Controller" method="post" class="reg_form">
			<ul>
				<li>
					<h2>${editing_text}:</h2>
				</li>
				<li><label>${table_title }:</label> <input type="text" name="edit_title" required
					value="<c:out value="${sessionScope.tariff_to_work.title}" />" /></li>
				<li><label>${m_cost}:</label> <input type="text" name="edit_monthly_cost" required
					value="<c:out value="${sessionScope.tariff_to_work.monthlyCost}" />" /></li>
				<li><label>${unlim}:</label> <select size="1" name="edit_unlim">
						<c:if test="${sessionScope.tariff_to_work.unlimTraffic eq true}">
							<option value="yes" selected>${yes}</option>
							<option value="no">${no}</option>
						</c:if>
						<c:if test="${sessionScope.tariff_to_work.unlimTraffic eq false}">
							<option value="yes">${yes}</option>
							<option value="no" selected>${no}</option>
						</c:if>
				</select></li>
				<li><label>${m_limit}:</label> <input type="text" name="edit_limit"
					value="<c:out value="${sessionScope.tariff_to_work.monthlyDataLimit}" />" /></li>
				<li><label>${over_cost}:</label> <input type="text" name="edit_overload_cost"
					value="<c:out value="${sessionScope.tariff_to_work.overloadLimitCost}" />" /></li>
				<li><label> ${table_technology}: </label><select size="1" name="edit_technology">
						<c:forEach items="${requestScope.technology_list}" var="technology">
							<option value="<c:out value="${technology.id}" />"
								<c:if test="${sessionScope.tariff_to_work.technologyId eq technology.id}">selected</c:if>>
								<c:out value="${technology.title}" />
							</option>
						</c:forEach>
				</select></li>
				<li><label>${table_descr}:</label> <input type="text" name="edit_description"
					value="<c:out value="${sessionScope.tariff_to_work.description}" />" /></li>
				<li><button class="submit" type="submit" onclick="history.back();">${back_button}</button>
					<button style="margin-left: 17%;" class="submit" type="submit" name="command"
						value="admin_edit_tariff_process">${edit_apply}</button></li>
			</ul>
		</form>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>