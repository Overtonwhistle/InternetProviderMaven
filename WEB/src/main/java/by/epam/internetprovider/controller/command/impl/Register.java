package by.epam.internetprovider.controller.command.impl;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class Register implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String DONE_PAGE = "Controller?command=goto_register_done";
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/register-error.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> errors;
		UserData userData = new UserData();

		HttpSession session = request.getSession(true);
		session.removeAttribute(ATTRIBUTE_LOGGED);

		userData.setFirstName(request.getParameter(PARAMETER_FIRSTNAME));
		userData.setLastName(request.getParameter(PARAMETER_LASTNAME));
		userData.setPassportNumber(request.getParameter(PARAMETER_PASSPORT));
		userData.setEmail(request.getParameter(PARAMETER_EMAIL));
		userData.setLogin(request.getParameter(PARAMETER_LOGIN));
		userData.setPassword(request.getParameter(PARAMETER_PASSWORD1));
		userData.setRepeatePassword(request.getParameter(PARAMETER_PASSWORD2));

		try {
			errors = internetProviderService.createUser(userData);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error executing command:Register");
			throw new CommandException("Error executing command:Register", e);
		}
	
		RequestDispatcher dispatcher;

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			request.setAttribute(ATTRIBUTE_USER, userData);
			dispatcher = request.getRequestDispatcher(ERROR_PAGE);
		} else {
			request.removeAttribute(ATTRIBUTE_ERRORS);
			dispatcher = request.getRequestDispatcher(DONE_PAGE);
		}
		dispatcher.forward(request, response);

	}

}
