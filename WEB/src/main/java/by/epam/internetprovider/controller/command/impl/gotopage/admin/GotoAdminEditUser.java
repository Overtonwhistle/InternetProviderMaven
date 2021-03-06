package by.epam.internetprovider.controller.command.impl.gotopage.admin;
import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminEditUser implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_edit_user_page.jsp";
	private static final String URL = "Controller?command=goto_ad_edit_user";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);
		int userId;

		if (request.getParameter(PARAMETER_USER_ID_SELECTOR) != null) {
			userId = Integer.parseInt(request.getParameter(PARAMETER_USER_ID_SELECTOR));
			session.setAttribute(PARAMETER_ID_TO_WORK, userId);
		} else {
			userId = (int) session.getAttribute(PARAMETER_ID_TO_WORK);
		}

		User user = null;
		List<Tariff> tariffsList = new ArrayList<>();

		try {
			user = internetProviderService.getUserById(userId);
			tariffsList = internetProviderService.getTariffsList();
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error in command:GotoAdminEditUser", e);
			throw new CommandException("Error executing command:GotoAdminEditUser", e);
		}

		UserData userData = new UserData(user);

		session.setAttribute(ATTRIBUTE_USER_TO_WORK, userData);
		request.setAttribute(ATTRIBUTE_TARIFFS_LIST, tariffsList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
