package by.epam.internetprovider.controller.command.impl;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.Param.UserRole;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.exception.ServiceUserNotFoundException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class LogIn implements Command {

	private static final Logger logger = LogManager.getLogger();
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/login-error.jsp";
	private static final String PAGE_GOTO_ADMIN = "Controller?command=goto_admin";
	private static final String PAGE_GOTO_CLIENT = "Controller?command=goto_client";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter(PARAMETER_LOGIN);
		String password = request.getParameter(PARAMETER_PASSWORD);
		String errors = "";
		User user = null;

		HttpSession session = request.getSession(true);
		ResourceBundle res = ResourceBundle.getBundle(BUNDLE_ERRORS);

		try {
			user = internetProviderService.getUserByLogin(login, password);
		} catch (ServiceUserNotFoundException e) {
			errors = res.getString(ERROR_USER_NOT_FOUND);

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "ServiceException", e);
			errors = res.getString(ERROR_SERVER_ERROR);
		}

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
			dispatcher.forward(request, response);
		} else {
			request.removeAttribute(ATTRIBUTE_ERRORS);
			session.setAttribute(ATTRIBUTE_USER, user);
			session.setAttribute(ATTRIBUTE_LOGGED, ATTRIBUTE_LOGGED_VALUE);

			if (user.getRole().equals(UserRole.ADMIN)) {
				response.sendRedirect(PAGE_GOTO_ADMIN);
				return;
			} else {
				response.sendRedirect(PAGE_GOTO_CLIENT);
				return;
			}
		}
	}
}
