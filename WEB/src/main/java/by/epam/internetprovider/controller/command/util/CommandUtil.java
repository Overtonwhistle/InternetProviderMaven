package by.epam.internetprovider.controller.command.util;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandUtil {

	private static final int FIRST_ARRAY_INDEX = 0;
	private static final String ATTRIBUTE_ID_TO_WORK = "id_to_work";
	private static final String ATTRIBUTE_USER_TO_WORK = "user_to_work";
	private static final String ATTRIBUTE_TARIFF_TO_WORK = "tariff_to_work";
	private static final String ATTRIBUTE_REQUEST_ID = "request_id";
	private static final String ATTRIBUTE_ACTIVE_REQUEST = "active_request";

	public static void clearSession(HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		session.removeAttribute(ATTRIBUTE_ID_TO_WORK);
		session.removeAttribute(ATTRIBUTE_USER_TO_WORK);
		session.removeAttribute(ATTRIBUTE_TARIFF_TO_WORK);
		session.removeAttribute(ATTRIBUTE_REQUEST_ID);
		session.removeAttribute(ATTRIBUTE_ACTIVE_REQUEST);

	}

	public static Map<String, String> getPlainParametersMap(
			Map<String, String[]> httpRequestParametersMap) {

		Map<String, String> parametersMap = new LinkedHashMap<>();

		httpRequestParametersMap.entrySet().stream()
				.forEach(x -> parametersMap.put(x.getKey(), x.getValue()[FIRST_ARRAY_INDEX]));

		return parametersMap;

	}

}
