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

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminDeleteTariff implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String URL = "Controller?command=goto_ad_delete_tariff";
	private static final String DONE_PAGE = "WEB-INF/jsp/admin_del_tariff_page.jsp";
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/del_tariff_error.jsp";

//	private static final String PARAMETER_TARIFF_ID_SELECTOR = "tariff_id_selector";
//	private static final String PARAMETER_ID_TO_WORK = "id_to_work";
//	private static final String PARAMETER_TARIFF_TO_WORK = "tariff_to_work";
//
//	private static final String ATTRIBUTE_URL = "url";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);
		int tariffId;
		String page = DONE_PAGE;

		if (request.getParameter(PARAMETER_TARIFF_ID_SELECTOR) != null) {
			tariffId = Integer.parseInt(request.getParameter(PARAMETER_TARIFF_ID_SELECTOR));
			session.setAttribute(PARAMETER_ID_TO_WORK, tariffId);
		} else {
			tariffId = (int) session.getAttribute(PARAMETER_ID_TO_WORK);
		}

		Tariff tariff = null;

		try {
			tariff = internetProviderService.getTariff(tariffId);
			if (internetProviderService.isTariffUsed(tariffId)) {
				page = ERROR_PAGE;
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error executing command:GotoAdminDeleteTariff");
			throw new CommandException("Error executing command:GotoAdminDeleteTariff", e);
		}

		session.setAttribute(PARAMETER_ID_TO_WORK, tariffId);
		session.setAttribute(PARAMETER_TARIFF_TO_WORK, tariff);

		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}
