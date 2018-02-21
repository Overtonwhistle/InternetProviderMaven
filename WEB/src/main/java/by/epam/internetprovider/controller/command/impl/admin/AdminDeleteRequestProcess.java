package by.epam.internetprovider.controller.command.impl.admin;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class AdminDeleteRequestProcess implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String DONE_COMMAND = "Controller?command=goto_ad_requests";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);

		try {
			internetProviderService.deleteRequest((int) session.getAttribute(ATTRIBUTE_REQUEST_ID));
		} catch (ServiceException e) {
			logger.log(Level.ERROR,
					"Failed to process request in command:AdminDeleteRequestProcess");
			throw new CommandException("Error executing command:AdminDeleteRequestProcess", e);
		}

		session.removeAttribute(ATTRIBUTE_REQUEST_ID);

		response.sendRedirect(DONE_COMMAND);

	}

}
