package by.epam.internetprovider.controller.command.impl.gotopage.client;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;
import java.math.BigDecimal;
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
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoClientChangeTariff implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/client_change_tariff_page.jsp";
	private static final String URL = "Controller?command=goto_client_change_tariff";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		List<Tariff> tariffsList = new ArrayList<>();
		List<Technology> technologyList = new ArrayList<>();
		BigDecimal lowestTariffCost;

		try {

			tariffsList = internetProviderService
					.getTariffsList(CommandUtil.getPlainParametersMap(request.getParameterMap()));
			technologyList = internetProviderService.getTechologiesList();

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoClientChangeTariff");
			throw new CommandException("Failed to get data in command:GotoClientChangeTariff.", e);
		}

		if (!tariffsList.isEmpty()) {
			lowestTariffCost = internetProviderService.getLowestTariffCost(tariffsList);
			request.setAttribute(ATTRIBUTE_LOWEST_TARIFF_COST, lowestTariffCost);
			request.setAttribute(ATTRIBUTE_TECHNOLOGY_LIST, technologyList);
		}

		request.setAttribute(ATTRIBUTE_TARIFFS_LIST, tariffsList);

		session.setAttribute(ATTRIBUTE_URL, URL);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
