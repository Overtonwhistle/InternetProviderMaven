<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:requestEncoding value="utf-8" />
<fmt:message bundle="${loc}" key="local.main" var="main" />
<fmt:message bundle="${loc}" key="local.user_menu" var="user_menu" />
<fmt:message bundle="${loc}" key="local.log_off" var="log_off" />
<fmt:message bundle="${loc}" key="local.admin_page" var="admin_page" />
<fmt:message bundle="${loc}" key="local.admin_page.summary" var="summary" />
<fmt:message bundle="${loc}" key="local.admin_page.users" var="users" />
<fmt:message bundle="${loc}" key="local.admin_page.tariffs" var="tariffs" />
<fmt:message bundle="${loc}" key="local.admin_page.requests" var="requests" />
<fmt:message bundle="${loc}" key="local.admin_page.payments" var="payments" />
<fmt:message bundle="${loc}" key="local.admin_page.header_text" var="header_text" />
<fmt:message bundle="${loc}" key="local.made_by" var="made_by" />
<!-- END BASE PART -->
<div>
  <div class="menu">
    <div class="navbar">
      <ul>
        <!-- locale changing part -->
        <%@ include file="localization_block.jsp"%>
        <!-- end locale changing part -->
        <li><a href="index.jsp" target='_self'> <img src="images/home.png" alt="${main}"><span>&nbsp;${main}</span>
        </a></li>
        <li><a href="Controller?command=goto_admin" target='_self'> <img src="images/info.png" alt="Summary"><span>&nbsp;${summary}</span>
        </a></li>
        <li><a href="Controller?command=goto_ad_users" target='_self'> <img src="images/users.png"
            alt="${users}"><span>&nbsp;${users}</span>
        </a></li>
        <li><a href="Controller?command=goto_ad_tariffs"> <img src="images/notebook.png" alt="${tariffs}"><span>&nbsp;${tariffs}</span>
        </a></li>
        <li><a href="Controller?command=goto_ad_requests" target='_self'> <img src="images/calendar_blank.png"
            alt="${requests}"><span>&nbsp;${requests}</span>
        </a></li>
        <li><a href="Controller?command=goto_ad_payments" target='_self'> <img src="images/dollar.png"
            alt="${payments}"><span>&nbsp;${payments}</span>
        </a>
        <li><a href="#"><img src="images/user.png" alt="${user_menu}"><span>&nbsp;${user_menu}</span></a>
          <ul class="items">
            <c:if test="${sessionScope.logged eq true}">
              <li>
                <div class="user-string">
                  <c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}" />
                </div>
              </li>
            </c:if>
            <c:if test="${sessionScope.logged ne true}">
              <li>
                <form class="login-form" action="Controller" method="post">
                  <input type="hidden" name="command" value="log_in" /> <input type="text" name="login" value=""
                    placeholder="${login}" required> <input type="password" name="password" value=""
                    placeholder="${password}" required /> <input type="submit" value="${log_in}" class="login_button" />
                </form>
              </li>
            </c:if>
            <c:if test="${sessionScope.logged eq true}">
              <c:if test="${user.role eq 'ADMIN'}">
                <li><form class="login-form" action="Controller" method="post">
                    <input type="hidden" name="command" value="goto_admin" /> <input class="login_button" type="submit"
                      value="${admin_page}" />
                  </form></li>
              </c:if>
              <c:if test="${sessionScope.user.role eq 'CLIENT'}">
                <li><form class="login-form" action="Controller" method="post">
                    <input type="hidden" name="command" value="goto_client" /> <input class="login_button"
                      type="submit" value="${client_page}" />
                  </form></li>
              </c:if>
              <li><form class="login-form" action="Controller" method="post">
                  <input type="hidden" name="command" value="log_off" /> <input class="login_button" type="submit"
                    value="${log_off}" />
                </form></li>
            </c:if>
            <c:if test="${sessionScope.logged ne true}">
              <li>
                <form class="login-form" action="Controller" method="post">
                  <input type="hidden" name="command" value="register" /> <input type="submit" value="${new_user}"
                    class="login_button" />
                </form>
              </li>
            </c:if>
          </ul></li>
      </ul>
    </div>
  </div>
  <div class="headerPictureAd">
    <img src="images/background/home_slide01.jpg" alt="background not found">
    <h1>${header_text}</h1>
  </div>
</div>
