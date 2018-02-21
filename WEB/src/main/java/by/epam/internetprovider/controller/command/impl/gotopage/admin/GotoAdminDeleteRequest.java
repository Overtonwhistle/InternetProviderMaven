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

public class GotoAdminDeleteRequest implements ICommand {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_del_request_page.jsp";
	private static final String URL = "Controller?command=goto_ad_delete_request";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		session.setAttribute(ATTRIBUTE_URL, URL);

		int reqId = 0;

		if (request.getParameter(PARAMETER_REQUEST_ID_SELECTOR) != null) {
			reqId = Integer.parseInt(request.getParameter(PARAMETER_REQUEST_ID_SELECTOR));
			session.setAttribute(PARAMETER_ID_TO_WORK, reqId);
		} else {
			reqId = (int) session.getAttribute(PARAMETER_ID_TO_WORK);
		}

		User user = null;
		Request req = null;
		Tariff req_tariff = null;
		String cur_tariff = null;

		try {

			req = internetProviderService.getRequest(reqId);
			user = internetProviderService.getUserById(req.getUserId());
			req_tariff = internetProviderService.getTariff(req.getTariffId());
			if (user.getTariffId() > 0) {
				cur_tariff = internetProviderService.getTariff(user.getTariffId()).getTitle();
			}

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoAdminDeleteRequest");
			throw new CommandException("Failed to get data in command:GotoAdminDeleteRequest", e);
		}

		session.setAttribute(ATTRIBUTE_REQUEST_ID, req.getId());
		request.setAttribute(ATTRIBUTE_USER, user);
		request.setAttribute(ATTRIBUTE_REQUESTED_TARIFF, req_tariff);
		request.setAttribute(ATTRIBUTE_CURRENT_TARIFF, cur_tariff);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
