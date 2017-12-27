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
<fmt:message bundle="${loc}" key="local.tariff_info.info_text" var="info_text" />
<fmt:message bundle="${loc}" key="local.tariff_info.m_cost" var="m_cost" />
<fmt:message bundle="${loc}" key="local.tariff_info.unlim" var="unlim" />
<fmt:message bundle="${loc}" key="local.tariff_info.yes" var="yes" />
<fmt:message bundle="${loc}" key="local.tariff_info.no" var="no" />
<fmt:message bundle="${loc}" key="local.tariff_info.m_limit" var="m_limit" />
<fmt:message bundle="${loc}" key="local.tariff_info.over_cost" var="over_cost" />
<fmt:message bundle="${loc}" key="local.tariff_info.tech" var="tech" />
<fmt:message bundle="${loc}" key="local.tariff_info.rent" var="rent" />
<fmt:message bundle="${loc}" key="local.tariff_info.descr" var="descr" />
<fmt:message bundle="${loc}" key="local.button_back" var="button_back" />
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
				<h6>${info_text}</h6>
				<h4>
					<c:out value="${requestScope.tariff.title}" />
				</h4>
				<table class="user_info">
					<tr>
						<td style="width: 40%;">${m_cost}</td>
						<td style="width: 60%;"><c:out value="${requestScope.tariff.monthlyCost}" /></td>
					</tr>
					<tr class="even">
						<td>${unlim }</td>
						<td><c:if test="${requestScope.tariff.unlimTraffic eq true}">${yes}</c:if> <c:if
								test="${requestScope.tariff.unlimTraffic ne true}">${no}</c:if></td>
					</tr>
					<c:if test="${tariff.unlimTraffic ne true}">
						<tr>
							<td>${m_limit}</td>
							<td><c:out value="${requestScope.tariff.monthlyDataLimit}" /></td>
						</tr>
						<tr class="even">
							<td>${over_cost}</td>
							<td><c:out value="${requestScope.tariff.overloadLimitCost}" /></td>
						</tr>
					</c:if>
					<tr>
						<td>${tech}</td>
						<td><a
							href='https://www.google.com/search?q=<c:out
                    value="${requestScope.technology.title}"/>+technology'
							title='Search for technology in Google' target="_blank"><c:out
									value=" ${requestScope.technology.title}" /> </a></td>
					</tr>
					<tr class="even">
						<td>${rent}</td>
						<td><c:if test="${requestScope.technology.needRentEquipment eq true}">Yes</c:if> <c:if
								test="${requestScope.technology.needRentEquipment eq false}">No</c:if></td>
					</tr>
					<tr>
						<td>${descr}</td>
						<td><c:out value="${requestScope.tariff.description}" /></td>
					</tr>
				</table>
				<button class="submit" type="submit" onclick="history.back();">${button_back}</button>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>