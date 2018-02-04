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

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.bean.data_object.TariffData;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminEditTariff implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_edit_tariff_page.jsp";
	private static final String URL = "Controller?command=goto_ad_edit_tariff";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);
		int tariffId;

		if (request.getParameter(PARAMETER_TARIFF_ID_SELECTOR) != null) {
			tariffId = Integer.parseInt(request.getParameter(PARAMETER_TARIFF_ID_SELECTOR));
			session.setAttribute(PARAMETER_ID_TO_WORK, tariffId);
		} else {
			tariffId = (int) session.getAttribute(PARAMETER_ID_TO_WORK);
		}

		Tariff tariff = null;
		List<Technology> technologysList = new ArrayList<>();

		try {
			tariff = internetProviderService.getTariff(tariffId);
			technologysList = internetProviderService.getTechologiesList();
			request.setAttribute(PARAMETER_TARRIFS_LIST, technologysList);
			request.setAttribute(PARAMETER_TECHNOLOGY_LIST, technologysList);

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error getUserById() in command:GotoAdminEditTariff", e);
			throw new CommandException("Error executing command:GotoAdminEditTariff", e);
		}

		TariffData tariffData = new TariffData(tariff);

		session.setAttribute(PARAMETER_TARIFF_TO_WORK, tariffData);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
