package by.epam.internetprovider.controller.command.impl.localization;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.internetprovider.controller.command.Command;

public class Localization implements Command {
	private static final String DEFAULT_PAGE = "index.jsp";
	private static final String COOCKIE_LOCALE_NAME = "locale";
	private static final String PARAMETER_LOCAL = "local";
	private static final String ATTRIBUTE_LOCAL = "local";
	private static final String ATTRIBUTE_RU = "ru";
	private static final String ATTRIBUTE_EN = "en";
	private static final String ATTRIBUTE_URL = "url";
	private static final String ATTRIBUTE_RUSSIAN_LANGUAGE = "ru";
	private static final String ATTRIBUTE_RUSSIAN_COUNTRY = "RU";
	private static final String ATTRIBUTE_ENGLISH_LANGUAGE = "en";
	private static final String ATTRIBUTE_ENGLISH_COUNTRY = "US";
	private static final String ATTRIBUTE_DEFAULT_LANGUAGE = "en";
	private static final String ATTRIBUTE_DEFAULT_COUNTRY = "US";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession(true);

		String local = request.getParameter(PARAMETER_LOCAL);
		session.setAttribute(ATTRIBUTE_LOCAL, local);

		switch (local) {
		case ATTRIBUTE_RU:
			Locale.setDefault(new Locale(ATTRIBUTE_RUSSIAN_LANGUAGE, ATTRIBUTE_RUSSIAN_COUNTRY));
			break;

		case ATTRIBUTE_EN:
			Locale.setDefault(new Locale(ATTRIBUTE_ENGLISH_LANGUAGE, ATTRIBUTE_ENGLISH_COUNTRY));
			break;

		default:
			Locale.setDefault(new Locale(ATTRIBUTE_DEFAULT_LANGUAGE, ATTRIBUTE_DEFAULT_COUNTRY));
		}

		Cookie cookie = new Cookie(COOCKIE_LOCALE_NAME, local);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days
		cookie.setComment("User's language cookie");
		response.addCookie(cookie);

		String url = (String) session.getAttribute(ATTRIBUTE_URL);

		if (url != null) {
			response.sendRedirect(url);

		} else {
			response.sendRedirect(DEFAULT_PAGE);
		}

	}

}
