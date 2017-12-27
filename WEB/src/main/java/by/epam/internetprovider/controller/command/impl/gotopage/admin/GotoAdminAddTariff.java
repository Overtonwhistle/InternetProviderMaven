package by.epam.internetprovider.controller.command.impl.gotopage.admin;

import static by.epam.internetprovider.controller.command.util.CommandConstant.ATTRIBUTE_TARIFF_TO_WORK;
import static by.epam.internetprovider.controller.command.util.CommandConstant.ATTRIBUTE_TECHNOLOGY_LIST;
import static by.epam.internetprovider.controller.command.util.CommandConstant.ATTRIBUTE_URL;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.builder.TariffBuilder;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminAddTariff implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_add_tariff_page.jsp";
	private static final String URL = "Controller?command=goto_ad_add_tariff";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// List<Technology> technologysList = new ArrayList<>();
		// TariffBuilder tariffBuilder = new TariffBuilder();

		try {
			// technologysList = internetProviderService.getTechologiesList();
			request.setAttribute(ATTRIBUTE_TECHNOLOGY_LIST,
					internetProviderService.getTechologiesList());

		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error getUserById() in command:GotoAdminEditTariff", e);
			throw new CommandException("Error executing command:GotoAdminEditTariff", e);
		}

		session.setAttribute(ATTRIBUTE_URL, URL);
		session.setAttribute(ATTRIBUTE_TARIFF_TO_WORK, new TariffBuilder());

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
