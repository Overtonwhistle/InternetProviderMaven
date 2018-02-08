package by.epam.internetprovider.controller.command.util;

import java.util.LinkedHashMap;
import java.util.Map;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandUtil {

	public static void clearSession(HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		session.removeAttribute(ATTRIBUTE_ID_TO_WORK);
		session.removeAttribute(ATTRIBUTE_USER_TO_WORK);
		session.removeAttribute(ATTRIBUTE_TARIFF_TO_WORK);
		session.removeAttribute(ATTRIBUTE_REQUEST_ID);
		session.removeAttribute(ATTRIBUTE_ACTIVE_REQUEST);
		session.removeAttribute(ATTRIBUTE_START_INDEX);
		session.removeAttribute(ATTRIBUTE_USERS_LIST);

	}

	public static Map<String, String> getPlainParametersMap(
			Map<String, String[]> httpRequestParametersMap) {

		Map<String, String> parametersMap = new LinkedHashMap<>();

		httpRequestParametersMap.entrySet().stream()
				.forEach(x -> parametersMap.put(x.getKey(), x.getValue()[FIRST_ARRAY_INDEX]));

		return parametersMap;

	}

}
