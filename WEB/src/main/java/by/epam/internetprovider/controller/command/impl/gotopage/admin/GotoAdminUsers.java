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

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminUsers implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_users_page.jsp";
	private static final String REDIRECT_PAGE = "Controller?command=goto_ad_users";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, REDIRECT_PAGE);

		List<User> usersList = new ArrayList<>();

		try {
			usersList = internetProviderService
					.getUsersList(CommandUtil.getPlainParametersMap(request.getParameterMap()));

			request.setAttribute(ATTRIBUTE_USERS_LIST, usersList);
			request.setAttribute(ATTRIBUTE_TARIFFS_LIST, internetProviderService.getTariffsList());
			request.setAttribute(ATTRIBUTE_TARIFFS_MAP, internetProviderService.getTariffsMap());

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoAdminUsers", e);
			throw new CommandException("Failed to get data in GotoAdminUsers.", e);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
