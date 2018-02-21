package by.epam.internetprovider.controller.command.impl.gotopage.admin;
import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminProcessRequest implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String URL = "Controller?command=goto_ad_process_request";
	private static final String PAGE = "WEB-INF/jsp/admin_process_request_page.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);
		int requestId;

		if (request.getParameter(PARAMETER_REQUEST_ID_SELECTOR) != null) {
			requestId = Integer.parseInt(request.getParameter(PARAMETER_REQUEST_ID_SELECTOR));
			session.setAttribute(PARAMETER_ID_TO_WORK, requestId);
		} else {
			requestId = (int) session.getAttribute(PARAMETER_ID_TO_WORK);
		}

		User user = null;
		Request userRequest = null;
		Tariff requestedTariff = null;
		String currentTariff = null;

		try {
			userRequest = internetProviderService.getRequest(requestId);
			user = internetProviderService.getUserById(userRequest.getUserId());
			requestedTariff = internetProviderService.getTariff(userRequest.getTariffId());

			if (user.getTariffId() > 0) {
				currentTariff = internetProviderService.getTariff(user.getTariffId()).getTitle();
			}

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoAdminProcessRequest", e);
			throw new CommandException("Failed to get data in command:GotoAdminProcessRequest", e);
		}

		session.setAttribute(ATTRIBUTE_REQUEST_ID, userRequest.getId());
		request.setAttribute(ATTRIBUTE_USER, user);
		request.setAttribute(ATTRIBUTE_REQUESTED_TARIFF, requestedTariff);
		request.setAttribute(ATTRIBUTE_CURRENT_TARIFF, currentTariff);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
