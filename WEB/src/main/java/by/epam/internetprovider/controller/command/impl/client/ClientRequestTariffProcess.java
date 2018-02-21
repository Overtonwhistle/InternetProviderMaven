package by.epam.internetprovider.controller.command.impl.client;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

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
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class ClientRequestTariffProcess implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String DONE_COMMAND = "Controller?command=goto_client_requests";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(ATTRIBUTE_USER);
		int tariffId = 0;

		if (request.getParameter(PARAMETER_TARIFF_ID_SELECTOR) != null) {
			tariffId = Integer.parseInt(request.getParameter(PARAMETER_TARIFF_ID_SELECTOR));

		} else {
			logger.log(Level.ERROR, "Wrong tariff ID in command:ClientRequestTariffProcess");
			throw new CommandException("Wrong tariff ID in command:ClientRequestTariffProcess");
		}

		try {
			internetProviderService.addRequest(user.getId(), tariffId);
		} catch (ServiceException e) {
			logger.log(Level.ERROR,
					"Failed to process request in command:ClientRequestTariffProcess");
			throw new CommandException("Error executing command:ClientRequestTariffProcess", e);
		}

		response.sendRedirect(DONE_COMMAND);

	}

}
