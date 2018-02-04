package by.epam.internetprovider.controller.command.impl.client;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class ClientEditProfileProcess implements Command {

	private static final Logger logger = LogManager.getLogger();
	private static String DONE_PAGE = "Controller?command=goto_client_edit_profile";
	private static String WRONG_CURRENT_PASSWORD_PAGE = "Controller?command=goto_client_edit_profile";
	private static String ERROR_PAGE = "WEB-INF/jsp/errors-pages/client_edit_profile_error.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		ArrayList<String> errors = new ArrayList<>();

		User user = (User) session.getAttribute(ATTRIBUTE_USER);
		String currentPassword = request.getParameter(PARAMETER_CURR_PASSWORD);

		if (!currentPassword.equals(user.getPassword())) {
			request.setAttribute(ATTRIBUTE_WRONG_CURR_PASSWORD, true);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(WRONG_CURRENT_PASSWORD_PAGE);
			dispatcher.forward(request, response);
			return;
		}

		UserData userData = new UserData();

		userData.setFirstName(request.getParameter(PARAMETER_NEW_F_NAME));
		userData.setLastName(request.getParameter(PARAMETER_NEW_L_NAME));
		userData.setPassportNumber(request.getParameter(PARAMETER_NEW_PASSPORT));
		userData.setEmail(request.getParameter(PARAMETER_NEW_EMAIL));
		userData.setCurrentPassword(currentPassword);

		if (!request.getParameter(PARAMETER_NEW_PASSWORD).isEmpty()) {
			userData.setPassword(request.getParameter(PARAMETER_NEW_PASSWORD));
		} else {
			userData.setPassword(user.getPassword());
		}

		try {
			errors = (ArrayList<String>) internetProviderService.editClientProfile(userData,
					user.getId());
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to edit user data in command:ClientEditProfileProcess");
			throw new CommandException("Error executing command:ClientEditProfileProcess", e);
		}

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
			dispatcher.forward(request, response);
		}

		else {
			response.sendRedirect(DONE_PAGE);
		}
	}
}
