package by.epam.internetprovider.controller.command.impl.gotopage;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoTariffInfo implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/tariff_info_page.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Tariff tariff = null;
		Technology technology;

		if (request.getParameter(PARAMETER_TARIFF_ID) != null) {
			try {
				tariff = internetProviderService
						.getTariff(Integer.parseInt(request.getParameter(PARAMETER_TARIFF_ID)));
				technology = internetProviderService.getTechnology(tariff.getTechnologyId());

			} catch (NumberFormatException e) {
				logger.log(Level.ERROR, "Incorrect tariff ID in command:GotoTariffInfo");
				throw new CommandException("Incorrect tariff ID in command:GotoTariffInfo");
			} catch (ServiceException e) {
				throw new CommandException("Error getting data command:GotoTariffInfo", e);
			}

		} else {
			logger.log(Level.ERROR, "Empty tariff ID in command:GotoTariffInfo");
			throw new CommandException("Empty tariff ID in command:GotoTariffInfo");
		}

		request.setAttribute(PARAMETER_TARIFF, tariff);
		request.setAttribute(PARAMETER_TECHNOLOGY, technology);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
