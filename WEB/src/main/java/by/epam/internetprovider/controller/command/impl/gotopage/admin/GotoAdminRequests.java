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

import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminRequests implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_requests_page.jsp";
	private static final String REDIRECT_PAGE = "Controller?command=goto_ad_requests";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CommandUtil.clearSession(request);

		List<Request> requestsList = new ArrayList<>();
		List<User> usersList = new ArrayList<>();
		List<Tariff> tariffsList = new ArrayList<>();
		List<String> adminsNamesList = new ArrayList<>();
		List<String> tariffsNamesList = new ArrayList<>();

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, REDIRECT_PAGE);

		try {

			if (request.getParameter(PARAMETER_IS_PAGE_SEARCHING) != null) {
				requestsList = internetProviderService.getRequestsList(
						CommandUtil.getPlainParametersMap(request.getParameterMap()));
			} else {
				requestsList = internetProviderService.getRequestsList();
			}

			tariffsList = internetProviderService.getTariffsList();

			for (Request req : requestsList) {
				User admin = null;

				if (req.getProcessedBy() != 0) { // if request really proсessed
					admin = internetProviderService.getUserById(req.getProcessedBy());
					adminsNamesList.add(admin.getFirstName() + " " + admin.getLastName());
				} else
					adminsNamesList.add(""); // maybe request was processed by admin, who dosn't
												// exist in system now
				usersList.add(internetProviderService.getUserById(req.getUserId()));
				tariffsNamesList
						.add(internetProviderService.getTariff(req.getTariffId()).getTitle());
			}

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoAdminRequests");
			throw new CommandException("Failed to get data in GotoAdminRequests.", e);
		}

		session.setAttribute(ATTRIBUTE_RESULT_LIST, requestsList);
		session.setAttribute(ATTRIBUTE_TARIFFS_LIST, tariffsList);
		session.setAttribute(ATTRIBUTE_USERS_LIST, usersList);
		session.setAttribute(ATTRIBUTE_ADMINS_NAMES_LIST, adminsNamesList);
		session.setAttribute(ATTRIBUTE_TARIFFS_NAMES_LIST, tariffsNamesList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
