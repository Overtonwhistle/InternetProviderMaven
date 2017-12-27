package by.epam.internetprovider.controller.command.impl.gotopage.client;

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

import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoClientRequests implements Command {

	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/client_requests_page.jsp";
	private static final String URL = "Controller?command=goto_client_requests";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		CommandUtil.clearSession(request);

		List<Request> historyRequestsList = new ArrayList<>();
		List<String> historyTariffsTitlesList = new ArrayList<>();
		User user = (User) session.getAttribute(ATTRIBUTE_USER);
		int userId = user.getId();
		Request activeRequest = null;

		try {

			historyRequestsList = internetProviderService.getUserHistoryRequestsList(userId);
			activeRequest = internetProviderService.getActiveRequest(userId);

			if (activeRequest.getId() != 0) { // if request really exist, it has non-zero Id field
				session.setAttribute(ATTRIBUTE_ACTIVE_REQUEST, activeRequest);
				request.setAttribute(ATTRIBUTE_REQUESTED_TARIFF,
						internetProviderService.getTariff(activeRequest.getTariffId()));
			}

			if (user.getTariffId() > 0) {
				request.setAttribute(ATTRIBUTE_CURRENT_TARIFF,
						internetProviderService.getTariff(user.getTariffId()));
			}
			user = internetProviderService.getUserById(userId);

			for (Request req : historyRequestsList) {
				int tariffId = req.getTariffId();
				historyTariffsTitlesList
						.add(internetProviderService.getTariff(tariffId).getTitle());
			}

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error in command:GotoClientRequests", e);
			throw new CommandException("Error executing command:GotoClientRequests", e);
		}

		session.setAttribute(ATTRIBUTE_URL, URL);
		session.setAttribute(ATTRIBUTE_USER, user); // refreshing USER. Optional?
		request.setAttribute(ATTRIBUTE_HISTORY_REQUESTS_LIST, historyRequestsList);
		request.setAttribute(ATTRIBUTE_HISTORY_TARIFFS_NAMES, historyTariffsTitlesList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);
	}
}
