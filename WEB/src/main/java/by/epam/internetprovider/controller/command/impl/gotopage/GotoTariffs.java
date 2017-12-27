package by.epam.internetprovider.controller.command.impl.gotopage;

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

import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoTariffs implements Command {

	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/tariffs_page.jsp";
	private static final String URL = "Controller?command=goto_tariffs";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		try {

			request.setAttribute(ATTRIBUTE_TECHNOLOGY_LIST,
					internetProviderService.getTechologiesList());
			request.setAttribute(ATTRIBUTE_TARIFFS_LIST, internetProviderService
					.getTariffsList(CommandUtil.getPlainParametersMap(request.getParameterMap())));

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoClientChangeTariff");
			throw new CommandException("Failed to get data in command:GotoClientChangeTariff.", e);
		}

		session.setAttribute(ATTRIBUTE_URL, URL);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
