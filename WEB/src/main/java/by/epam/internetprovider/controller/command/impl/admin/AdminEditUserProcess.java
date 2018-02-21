package by.epam.internetprovider.controller.command.impl.admin;

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

import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class AdminEditUserProcess implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String DONE_PAGE = "Controller?command=goto_ad_users";
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/edit_user_error.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		ArrayList<String> errors = new ArrayList<>();

		int userId = (int) session.getAttribute(ATTRIBUTE_ID_TO_WORK);

		UserData userData = (UserData) session.getAttribute(ATTRIBUTE_USER_TO_WORK);

		userData.setRole(request.getParameter(PARAMETER_EDIT_USER_ROLE).toUpperCase());
		userData.setFirstName(request.getParameter(PARAMETER_EDIT_FIRSTNAME));
		userData.setLastName(request.getParameter(PARAMETER_EDIT_LASTNAME));
		userData.setPassportNumber(request.getParameter(PARAMETER_EDIT_PASSPORT));
		userData.setEmail(request.getParameter(PARAMETER_EDIT_EMAIL));

		if (!request.getParameter(PARAMETER_EDIT_PASSWORD).isEmpty()) {
			userData.setPassword(request.getParameter(PARAMETER_EDIT_PASSWORD));
		}

		userData.setAccountBallance(request.getParameter(PARAMETER_EDIT_BALLANCE));
		userData.setMonthlyDataUsage(request.getParameter(PARAMETER_EDIT_MONTH_DATA));
		userData.setTotalDataUsage(request.getParameter(PARAMETER_EDIT_TOTAL_DATA));
		userData.setTariffId(request.getParameter(PARAMETER_EDIT_TARIFF));

		try {
			errors = (ArrayList<String>) (internetProviderService.editUser(userData, userId));
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to edit user data in command:AdminEditUserProcess");
			throw new CommandException("Error executing command:AdminEditUserProcess", e);
		}

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
			dispatcher.forward(request, response);
		}

		else {
			session.removeAttribute(ATTRIBUTE_ID_TO_WORK);
			session.removeAttribute(ATTRIBUTE_USER_TO_WORK);
			response.sendRedirect(DONE_PAGE);
		}
	}
}
