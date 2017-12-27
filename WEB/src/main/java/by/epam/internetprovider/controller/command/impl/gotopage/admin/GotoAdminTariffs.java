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
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.controller.command.util.CommandUtil;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminTariffs implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_tariffs_page.jsp";
	private static final String REDIRECT_PAGE = "Controller?command=goto_ad_tariffs";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Tariff> tariffsList = new ArrayList<>();
		List<Technology> technologyList = new ArrayList<>();
		List<Boolean> isUsedList = new ArrayList<>();

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, REDIRECT_PAGE);

		CommandUtil.clearSession(request);

		try {
			
			tariffsList = internetProviderService
					.getTariffsList(CommandUtil.getPlainParametersMap(request.getParameterMap()));
			
			for (Tariff tariff : tariffsList) {
				if (internetProviderService.isTariffUsed(tariff.getId()) == true) {
					isUsedList.add(true);
				} else {
					isUsedList.add(false);
				}
			}
			technologyList = internetProviderService.getTechologiesList();
			
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to get data in command:GotoAdminTariffs");
			throw new CommandException("Failed to get data in GotoAdminTariffs.", e);
		}
		
		request.setAttribute(ATTRIBUTE_TARIFFS_LIST, tariffsList);
		request.setAttribute(ATTRIBUTE_TECHNOLOGY_LIST, technologyList);
		request.setAttribute(ATTRIBUTE_IS_USED_LIST, isUsedList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);
	}
}
