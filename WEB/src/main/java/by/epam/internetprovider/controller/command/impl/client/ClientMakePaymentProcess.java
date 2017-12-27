package by.epam.internetprovider.controller.command.impl.client;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class ClientMakePaymentProcess implements Command {

	private static final Logger logger = LogManager.getLogger();
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/make_payment_error.jsp";
	private static final String DONE_PAGE = "Controller?command=goto_client_payments";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		ArrayList<String> errors = new ArrayList<>();

		User user = (User) session.getAttribute(ATTRIBUTE_USER);

		String amount = request.getParameter(PARAMETER_PAYMENT_AMOUNT);

		try {
			errors = (ArrayList<String>) internetProviderService.makePayment(user.getId(), amount);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to make a payment in command:AdminEditTariffProcess");
			throw new CommandException("Failed to make a payment in command:AdminEditTariffProcess",
					e);
		}

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
			dispatcher.forward(request, response);
		}

		else {
			response.sendRedirect(DONE_PAGE);
		}
	}

}
