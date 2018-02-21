package by.epam.internetprovider.controller.command.impl.client;

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
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class ClientDeleteRequestProcess implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String DONE_PAGE = "Controller?command=goto_client_requests";
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/del_request_error.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String errors;
		Request activeRequest = (Request) session.getAttribute(ATTRIBUTE_ACTIVE_REQUEST);

		try {
			errors = internetProviderService.deleteRequest(activeRequest.getId());
		} catch (ServiceException e) {
			logger.log(Level.ERROR,
					"Failed to process request in command:ClientDeleteRequestProcess");
			throw new CommandException("Error executing command:ClientDeleteRequestProcess", e);
		}

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
			dispatcher.forward(request, response);
		} else {
			session.removeAttribute(ATTRIBUTE_ACTIVE_REQUEST);
			response.sendRedirect(DONE_PAGE);
		}
	}

}
