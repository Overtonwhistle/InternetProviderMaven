<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="url" scope="session" value="index.jsp" />
<c:if test="${empty sessionScope.locale and not empty cookie.locale}" >
    <c:set var="local" value="${cookie.locale.value}" scope="session" />
</c:if>
<c:if test="${empty sessionScope.locale and empty cookie.locale}" >
	<c:set var="local" scope="session" value="${initParam['start_lang']}" />
</c:if>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.main_message" var="header_text" />
<fmt:message bundle="${loc}" key="local.article_one" var="article_one" />
<fmt:message bundle="${loc}" key="local.link_one" var="link_one" />
<fmt:message bundle="${loc}" key="local.text_one" var="text_one" />
<fmt:message bundle="${loc}" key="local.article_two" var="article_two" />
<fmt:message bundle="${loc}" key="local.link_two" var="link_two" />
<fmt:message bundle="${loc}" key="local.text_two" var="text_two" />
<fmt:message bundle="${loc}" key="local.more" var="more" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<style>
</style>
</head>
<body>
	<!--  HEADER -->
	<%@ include file="WEB-INF/jsp/index_page_header.jsp"%>
	<%-- <c:import url="WEB-INF/jsp/index_page_header.jsp" charEncoding="utf-8" /> --%>
	<!-- CONTENT -->
	<div class="content">
		<div class="row_of_two">
			<div class="t_one_of_two">
				<h4>
					<a href="${link_one}" target="blank">${article_one}</a>
				</h4>
				<p>${text_one}
					<a href="${link_one}" target="blank">${more}. </a>
				</p>
			</div>
			<div class="t_one_of_two">
				<h4>
					<a href="${link_two}" target="blank">${article_two}</a>
				</h4>
				<p>${text_two}
					<a href="${link_two}" target="blank">${more}. </a>
				</p>
			</div>
		</div>
	</div>
	<%@ include file="WEB-INF/jsp/footer.jsp"%>
</body>
</html>