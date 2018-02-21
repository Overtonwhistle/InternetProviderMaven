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

import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdmin implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_page.jsp";
	private static final String URL = "Controller?command=goto_admin";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CommandUtil.clearSession(request);

		try {

			request.setAttribute(ATTRIBUTE_CLIENTS,
					internetProviderService.getClientsList().size());

			request.setAttribute(ATTRIBUTE_CLIENTS_NEGATIVE,
					internetProviderService.getUsersWithNegBallance().size());

			request.setAttribute(ATTRIBUTE_IS_USER_IN_BAN,
					internetProviderService.getUsersInBan().size());

			request.setAttribute(ATTRIBUTE_ACTIVE_REQUESTS,
					internetProviderService.getActiveRequestsList().size());

			request.setAttribute(ATTRIBUTE_TOTAL_TARIFFS,
					internetProviderService.getTariffsList().size());

			request.setAttribute(ATTRIBUTE_NOT_USED_TARIFFS,
					internetProviderService.getNotUsedTariffsList().size());

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoAdmin");
			throw new CommandException("Failed to get data in GotoAdmin.", e);
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
