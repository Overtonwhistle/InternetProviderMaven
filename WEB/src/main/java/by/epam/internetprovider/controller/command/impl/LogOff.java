package by.epam.internetprovider.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.util.CommandUtil;

public class LogOff implements Command {

	private final static String REDIRECT_PAGE = "index.jsp";
	private static final String ATTRIBUTE_USER = "user";
	private static final String ATTRIBUTE_LOGGED = "logged";
	private static final String ATTRIBUTE_PAGE = "page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		CommandUtil.clearSession(request);

		session.removeAttribute(ATTRIBUTE_LOGGED);
		session.removeAttribute(ATTRIBUTE_USER);
		session.removeAttribute(ATTRIBUTE_PAGE);

		response.sendRedirect(REDIRECT_PAGE);

	}

}
