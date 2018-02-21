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

import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoClientPayments implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/client_payments_page.jsp";
	private static final String URL = "Controller?command=goto_client_payments";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CommandUtil.clearSession(request);

		HttpSession session = request.getSession(true);

		User user = (User) session.getAttribute(ATTRIBUTE_USER);
		int userId = user.getId();

		List<Payment> paymentsList = new ArrayList<>();

		try {

			paymentsList = internetProviderService.getUserPaymentsList(userId,
					CommandUtil.getPlainParametersMap(request.getParameterMap()));
			user = internetProviderService.getUserById(userId);

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error in command:GotoClient", e);
			throw new CommandException("Error executing command:GotoClient", e);
		}

		session.setAttribute(ATTRIBUTE_URL, URL);
		session.setAttribute(ATTRIBUTE_USER, user); // refreshing USER
		request.setAttribute(ATTRIBUTE_PAYMENTS_LIST, paymentsList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
