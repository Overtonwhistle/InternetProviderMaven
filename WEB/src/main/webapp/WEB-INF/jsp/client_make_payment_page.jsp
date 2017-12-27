<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- BASE CLIENT PART -->
<c:if test="${local eq null}">
  <c:set var="local" scope="session" value="en" />
</c:if>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<!-- CLIENT BLOCK PART -->
<fmt:message bundle="${loc}" key="local.client_payments.making_payment" var="making_payment" />
<fmt:message bundle="${loc}" key="local.client_payments.curr_ballance" var="curr_ballance" />
<fmt:message bundle="${loc}" key="local.client_payments.enter_text" var="enter_text" />
<fmt:message bundle="${loc}" key="local.client_payments.button_pay" var="button_pay" />
<fmt:message bundle="${loc}" key="local.button_back" var="button_back" />
<!-- END ADMIN PART -->
<title>Internet Provider</title>
<link rel="shortcut icon" href="images/icons/16.ico" type="image/x-icon">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/users_search_form.css" type="text/css">
</head>
<body>
  <c:if test="${user.role ne 'CLIENT'}">
    <jsp:forward page="../../index.jsp" />
  </c:if>
  <!--  HEADER -->
  <%@ include file="client_page_header.jsp"%>
  <!-- CONTENT -->
  <div class="content">
    <h6>${curr_ballance}:
      <c:out value="${sessionScope.user.accountBallance}" />
    </h6>
    <form class="reg_form" name="make_payment_form" method="post" action="Controller">
      <ul>
        <li>
          <h4>${making_payment}</h4>
        </li>
        <li><label>${enter_text}:</label> <input type="text" name="payment_amount"></li>
        <li>
          <button class="submit" type="submit" name="command" value="goto_client_payments">${button_back}</button>
          <button style="margin-left: 8%;" class="submit" type="submit" name="command"
            value="client_make_payment_process">${button_pay}</button>
        </li>
      </ul>
    </form>
  </div>
  <%@ include file="footer.jsp"%>
</body>
</html>