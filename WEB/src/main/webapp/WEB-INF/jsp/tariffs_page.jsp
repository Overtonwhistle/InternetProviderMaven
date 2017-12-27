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
<fmt:message bundle="${loc}" key="local.tariffs_page.header_text" var="header_text" />
<fmt:message bundle="${loc}" key="local.tariffs_page.tariff_view_text" var="tariff_view_text" />
<fmt:message bundle="${loc}" key="local.tariffs_page.no_data_text" var="no_data_text" />
<fmt:message bundle="${loc}" key="local.tariffs_page.searching_text" var="searching_text" />
<fmt:message bundle="${loc}" key="local.tariffs_page.unlim" var="unlim" />
<fmt:message bundle="${loc}" key="local.tariffs_page.all" var="all" />
<fmt:message bundle="${loc}" key="local.tariffs_page.yes" var="yes" />
<fmt:message bundle="${loc}" key="local.tariffs_page.no" var="no" />
<fmt:message bundle="${loc}" key="local.tariffs_page.rent" var="rent" />
<fmt:message bundle="${loc}" key="local.tariffs_page.tech" var="tech" />
<fmt:message bundle="${loc}" key="local.tariffs_page.m_cost" var="m_cost" />
<fmt:message bundle="${loc}" key="local.tariffs_page.m_cost_ph" var="m_cost_ph" />
<fmt:message bundle="${loc}" key="local.tariffs_page.m_limit" var="m_limit" />
<fmt:message bundle="${loc}" key="local.tariffs_page.m_limit_ph" var="m_limit_ph" />
<fmt:message bundle="${loc}" key="local.tariffs_page.over_cost" var="over_cost" />
<fmt:message bundle="${loc}" key="local.tariffs_page.over_cost_ph" var="over_cost_ph" />
<fmt:message bundle="${loc}" key="local.tariffs_page.reset_button" var="reset_button" />
<fmt:message bundle="${loc}" key="local.tariffs_page.search_button" var="search_button" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_title" var="table_title" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_m_cost" var="table_m_cost" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_unlim" var="table_unlim" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_m_limit" var="table_m_limit" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_over_cost" var="table_over_cost" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_technology" var="table_technology" />
<fmt:message bundle="${loc}" key="local.tariffs_page.table_descr" var="table_descr" />
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
	<%@ include file="index_page_header.jsp"%>
	<!-- CONTENT -->
	<div class="content">
		<h4>${tariff_view_text}</h4>
		<form class="reg_form" name="tariff_searching" method="post" action="Controller">
			<input type="hidden" name=is_page_seacrhing value="true" />
			<ul>
				<li>
					<h6>${searching_text}</h6>
				</li>
				<li><label>${unlim}: </label><select size="1" name="is_unlim">
						<option value="all" selected>${all}</option>
						<option value="yes">${yes}</option>
						<option value="no">${no}</option>
				</select></li>
				<li><label> ${rent}: </label><select size="1" name="need_rent">
						<option value="all" selected>${all}</option>
						<option value="yes">${yes}</option>
						<option value="no">${no}</option>
				</select></li>
				<li><label> ${tech}: </label><select size="1" name="technology">
						<option value="all" selected>${all}</option>
						<c:forEach items="${requestScope.technology_list}" var="technology">
							<option value="<c:out value="${technology.id}" />"><c:out value="${technology.title}" /></option>
						</c:forEach>
				</select></li>
				<li><label>${m_cost} </label><select size="1" name="monthly_cost_condition">
						<option value="greater">&gt;</option>
						<option value="less" selected>&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="monthly_cost" placeholder="${m_cost_ph}"></li>
				<li><label>${m_limit}: </label><select size="1" name="monthly_data_limit_condition">
						<option value="greater" selected>&gt;</option>
						<option value="less">&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="monthly_data_limit" placeholder="${m_limit_ph}"></li>
				<li><label>${over_cost}: </label><select size="1" name="overload_cost_condition">
						<option value="greater">&gt;</option>
						<option value="less" selected>&lt;</option>
						<option value="equals">=</option>
				</select> <input type="text" name="overload_cost" placeholder="${over_cost_ph}"></li>
				<li>
					<button class="submit" type="reset">${reset_button}</button>
					<button style="margin-left: 2%;" class="submit" type="submit" name="command" value="goto_tariffs">${search_button}</button>
				</li>
			</ul>
		</form>
		<c:if test="${requestScope.tariffs_list[0] eq null}">
			<h4>${no_data_text}</h4>
		</c:if>
		<c:if test="${requestScope.tariffs_list[0] ne null}">
			<div>
				<table>
					<tr>
						<th>${table_title}</th>
						<th style="width: 8%;">${table_m_cost}</th>
						<th style="width: 7%;">${table_unlim}</th>
						<th style="width: 10%;">${table_m_limit}</th>
						<th style="width: 10%;">${table_over_cost}</th>
						<th>${table_technology}</th>
						<th>${table_descr}</th>
					</tr>
					<c:forEach items="${requestScope.tariffs_list}" var="tariff" varStatus="status">
						<c:set var="tech_id" value="${tariff.technologyId}" />
						<tr <c:if test="${status.index%2 eq 0}">class="even"</c:if>>
							<td><c:out value="${tariff.title}" /></td>
							<td><c:out value="${tariff.monthlyCost}" />
							<td><c:if test="${tariff.unlimTraffic eq true}">
            ${yes}</c:if> <c:if test="${tariff.unlimTraffic eq false}">
            ${no}</c:if></td>
							<td><c:if test="${tariff.unlimTraffic eq false}">
									<c:out value="${tariff.monthlyDataLimit}" />
								</c:if></td>
							<td><c:if test="${tariff.unlimTraffic ne true}">
									<c:out value="${tariff.overloadLimitCost}" />
								</c:if></td>
							<td><c:out value="${technology_list[tech_id-1].title}" /></td>
							<td><c:out value="${tariff.description}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
	</div>
	<!-- CONTENT END -->
	<%@ include file="footer.jsp"%>
</body>
</html>