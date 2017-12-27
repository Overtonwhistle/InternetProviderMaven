<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locbutton_ru" var="ru_button" />
<fmt:message bundle="${loc}" key="locbutton_en" var="en_button" />
<fmt:message bundle="${loc}" key="local.english_hint" var="english_hint" />
<fmt:message bundle="${loc}" key="local.russian_hint" var="russian_hint" />

<!-- locale changing part -->
<li class="locale">
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="localization" />
		<c:if test="${local eq 'ru'}">
			<input type="image" src="images/Russia.png" alt="${ru_button}" name="local" title="${russian_hint}"
				value="ru" />
			<input type="hidden" name="command" value="localization" />
			<input type="image" src="images/United-States_n.png" alt="${en_button}" name="local"
				title="${english_hint}" value="en" />
		</c:if>
		<c:if test="${local eq 'en' }">
			<input type="image" src="images/Russia_n.png" alt="${ru_button}" name="local" title="${russian_hint}"
				value="ru" />
			<input type="hidden" name="command" value="localization" />
			<input type="image" src="images/United-States.png" alt="${en_button}" name="local" title="${english_hint}"
				value="en" />
		</c:if>
		<c:if test="${local ne 'en' and local ne 'ru' }">
s			 <input type="image" src="images/Russia_n.png" alt="${ru_button}" name="local" title="${russian_hint}"
				value="ru" />
			<input type="hidden" name="command" value="localization" />
			<input type="image" src="images/United-States_n.png" alt="${en_button}" name="local"
				title="${english_hint}" value="en" />
		</c:if>
	</form>
</li>
<!-- end locale changing part -->
