<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:if test="${local eq null}">
	<c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message  key="local.admin_tariffs_page.searching_text" var="searching_text" />
<fmt:message  key="local.admin_tariffs_page.no_data_text" var="no_data_text" />
<fmt:message  key="local.admin_tariffs_page.unlim" var="unlim" />
<fmt:message  key="local.admin_tariffs_page.all" var="all" />
<fmt:message  key="local.admin_tariffs_page.yes" var="yes" />
<fmt:message  key="local.admin_tariffs_page.no" var="no" />
<fmt:message  key="local.admin_tariffs_page.rent" var="rent" />
<fmt:message  key="local.admin_tariffs_page.tech" var="tech" />
<fmt:message  key="local.admin_tariffs_page.m_cost" var="m_cost" />
<fmt:message  key="local.admin_tariffs_page.m_cost_ph" var="m_cost_ph" />
<fmt:message  key="local.admin_tariffs_page.m_limit" var="m_limit" />
<fmt:message  key="local.admin_tariffs_page.m_limit_ph" var="m_limit_ph" />
<fmt:message  key="local.admin_tariffs_page.over_cost" var="over_cost" />
<fmt:message  key="local.admin_tariffs_page.over_cost_ph" var="over_cost_ph" />
<fmt:message  key="local.admin_tariffs_page.is_used" var="is_used" />
<fmt:message  key="local.admin_tariffs_page.reset_button" var="reset_button" />
<fmt:message  key="local.admin_tariffs_page.search_button" var="search_button" />
<fmt:message  key="local.admin_tariffs_page.operation_text" var="operation_text" />
<fmt:message  key="local.admin_tariffs_page.operation_hint" var="operation_hint" />
<fmt:message  key="local.admin_tariffs_page.table_title" var="table_title" />
<fmt:message  key="local.admin_tariffs_page.table_m_cost" var="table_m_cost" />
<fmt:message  key="local.admin_tariffs_page.table_unlim" var="table_unlim" />
<fmt:message  key="local.admin_tariffs_page.table_m_limit" var="table_m_limit" />
<fmt:message  key="local.admin_tariffs_page.table_over_cost" var="table_over_cost" />
<fmt:message  key="local.admin_tariffs_page.table_technology" var="table_technology" />
<fmt:message  key="local.admin_tariffs_page.table_descr" var="table_descr" />
<fmt:message  key="local.admin_tariffs_page.edit_button" var="edit_button" />
<fmt:message  key="local.admin_tariffs_page.delete_button" var="delete_button" />
<fmt:message  key="local.admin_tariffs_page.add_button" var="add_button" />
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
		<form class="reg_form" name="tariff_serching" method="post" action="Controller">
			<input type="hidden" name=is_page_seacrhing value="true" />
			<ul>
				<li>
					<h4>${searching_text}</h4>
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
							<option value="<c:out value="${technology.id}" />"><c:out
									value="${technology.title}" /></option>
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
				<li><label>${is_used}: </label><select size="1" name="is_used">
						<option value="all" selected>${all}</option>
						<option value="yes">${yes}</option>
						<option value="no">${no}</option>
				</select></li>
				<li>
					<button class="submit" type="reset">${reset_button}</button>
					<button class="submit" type="submit" name="command" value="goto_ad_tariffs">${search_button}</button>
				</li>
			</ul>
		</form>
		<div>
			<form method="post" action="Controller">
				<c:if test="${requestScope.tariffs_list[0] eq null}">
					<h4>${no_data_text}</h4>
				</c:if>
				<c:if test="${requestScope.tariffs_list[0] ne null}">
					<h4>${operation_text}</h4>
					<div class="h_hint">*${operation_hint}</div>
					<table>
						<tr>
							<th>&nbsp;</th>
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
							<tr
								<c:if test="${requestScope.is_used_list[status.index] eq false}">style="color: grey; "</c:if>
								<c:if test="${status.index%2 eq 0}">class="even"</c:if>>
								<td><input type="radio" name="tariff_id_selector"
									value="<c:out value="${tariff.id}" />" required></td>
								<td><c:out value="${tariff.title}" /></td>
								<td><c:out value="${tariff.monthlyCost}" />
								<td><c:if test="${tariff.unlimTraffic eq true}">
            ${yes}
              </c:if></td>
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
					<button class="submit" type="submit" name="command" value="goto_ad_edit_tariff">${edit_button}</button>
					<button class="delete" type="submit" name="command" value="goto_ad_delete_tariff">${delete_button}</button>
				</c:if>
			</form>
			<form method="post" action="Controller">
				<br>
				<button class="submit" type="submit" name="command" value="goto_ad_add_tariff">${add_button}</button>
			</form>
		</div>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>