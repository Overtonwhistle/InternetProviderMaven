<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="by.epam.internetprovider.bean.User"%>
<c:set var="page" scope="session" value="index.jsp" />
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<c:if test="${local eq null}">
  <c:set var="local" scope="session" value="en" />
</c:if>
<c:if test="${user.role ne 'ADMIN'}">
  <jsp:forward page="../../index.jsp" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.edit_text" var="edit_text" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.first_name" var="first_name" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.last_name" var="last_name" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.passport" var="passport" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.email" var="email" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.password" var="password" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.m_data" var="m_data" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.t_data" var="t_data" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.ballance" var="ballance" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.tariff" var="tariff" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.tariff_none" var="tariff_none" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.role" var="role" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.role_client" var="role_client" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.role_admin" var="role_admin" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.back_button" var="back_button" />
<fmt:message bundle="${loc}" key="local.admin_edit_users_page.apply_button" var="apply_button" />
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
  <%@ include file="admin_page_header.jsp"%>
  <!-- CONTENT -->
  <div class="content">
    <form action="Controller" method="post" class="reg_form">
      <ul>
        <li>
          <h2>${edit_text}</h2>
        </li>
        <li><label>${first_name}:</label> <input type="text" name="edit_firstname"
          value="<c:out value="${sessionScope.user_to_work.firstName}" />" /></li>
        <li><label>${last_name}:</label> <input type="text" name="edit_lastname"
          value="<c:out value="${sessionScope.user_to_work.lastName}" />" /></li>
        <li><label>${passport}:</label> <input type="text" name="edit_passport"
          value="<c:out value="${sessionScope.user_to_work.passportNumber}" />" /></li>
        <li><label>${email}:</label> <input type="text" name="edit_email"
          value="<c:out value="${sessionScope.user_to_work.email}" />" /></li>
        <li><label>${password}:</label> <input type="text" name="edit_password"
          value="<c:out value="${sessionScope.user_to_work.password}" />"></li>
        <li><label>${m_data}:</label> <input type="text" name="edit_mon_data"
          value="<c:out value="${sessionScope.user_to_work.monthlyDataUsage}" />"></li>
        <li><label>${t_data}:</label> <input type="text" name="edit_tot_data"
          value="<c:out value="${sessionScope.user_to_work.totalDataUsage}" />"></li>
        <li><label>${ballance}:</label> <input type="text" name="edit_ballance"
          value="<c:out value="${sessionScope.user_to_work.accountBallance}" />"></li>
        <li><label>${tariff}: </label><select size="1" name="edit_tariff">
            <option value="0">${tariff_none}</option>
            <c:forEach items="${requestScope.tariffs_list}" var="tariff">
              <option value="<c:out value="${tariff.id}" />"
                <c:if test="${sessionScope.user_to_work.tariffId eq tariff.id}">selected</c:if>>
                <c:out value="${tariff.title}" />
              </option>
            </c:forEach>
        </select></li>
        <li><label>${role}:</label> <select size="1" name="edit_user_role">
            <c:if test="${sessionScope.user_to_work.role eq 'CLIENT'}">
              <option value="client" selected>${role_client}</option>
              <option value="admin">${role_admin}</option>
            </c:if>
            <c:if test="${sessionScope.user_to_work.role eq 'ADMIN'}">
              <option value="client">${role_client}</option>
              <option value="admin" selected>${role_admin}</option>
            </c:if>
        </select></li>
        <li>
          <button class="submit" type="submit" name="command" value="goto_ad_users">${back_button}</button>
          <button style="margin-left: 17%;" class="submit" type="submit" name="command" value="admin_edit_user_process">${apply_button}</button>
        </li>
      </ul>
    </form>
  </div>
  <%@ include file="footer.jsp"%>
</body>
</html>