<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="page" scope="session" value="index.jsp" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local"/>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<fmt:message key="local.admin_tariffs_page.deleting_text" var="deleting_text" />
<fmt:message key="local.admin_tariffs_page.warning" var="warning" />
<fmt:message key="local.admin_tariffs_page.delete_button" var="delete_button" />
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
<link rel="stylesheet" href="css/users_search_form.css" type="text/css">
</head>
<body>
	<c:import url="admin_page_header.jsp" />
	<!-- CONTENT -->
	<div class="content">
		<h5>${deleting_text}:</h5>
		<h3>
			<c:out value="${sessionScope.tariff_to_work.title}" />
		</h3>
		<p class="warning">${warning}</p>
		<form action="Controller" method="post" class="reg_form">
			<ul>
				<li>
					<button class="submit" type="submit" name="command" value="goto_ad_tariffs">${back_button}</button>
					<button class="delete" type="submit" name="command" value="admin_del_tariff_process">${delete_button}</button>
				</li>
			</ul>
		</form>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>