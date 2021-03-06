package by.epam.internetprovider.controller.command.impl.admin;

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

import by.epam.internetprovider.bean.data_object.TariffData;
import by.epam.internetprovider.controller.command.ICommand;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class AdminEditTariffProcess implements ICommand {

	private static final Logger logger = LogManager.getLogger();
	private static final String DONE_PAGE = "Controller?command=goto_ad_tariffs";
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/edit_tariff_error.jsp";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		ArrayList<String> errors = new ArrayList<>();

		int tariffId = (int) session.getAttribute(ATTRIBUTE_ID_TO_WORK);
		TariffData tariffData = (TariffData) session
				.getAttribute(ATTRIBUTE_TARIFF_TO_WORK);

		tariffData.setTitle(request.getParameter(PARAMETER_EDIT_TITLE));
		tariffData.setMonthlyCost(request.getParameter(PARAMETER_EDIT_MONTHLY_COST));
		tariffData.setUnlimTraffic(request.getParameter(PARAMETER_EDIT_UNLIM));
		tariffData.setMonthlyDataLimit(request.getParameter(PARAMETER_EDIT_LIMIT));
		tariffData.setOverloadLimitCost(request.getParameter(PARAMETER_EDIT_OVERLOAD_COST));
		tariffData.setTechnologyId(request.getParameter(PARAMETER_EDIT_TECHNOLOGY));
		tariffData.setDescription(request.getParameter(PARAMETER_EDIT_DESCRIPTION));

		try {
			errors = (ArrayList<String>) internetProviderService.editTariff(tariffId,
					tariffData);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Failed to edit tariff data in command:AdminEditTariffProcess");
			throw new CommandException("Error executing command:AdminEditTariffProcess", e);
		}

		if (!errors.isEmpty()) {
			request.setAttribute(ATTRIBUTE_ERRORS, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
			dispatcher.forward(request, response);
		}

		else {
			session.removeAttribute(ATTRIBUTE_ID_TO_WORK);
			session.removeAttribute(ATTRIBUTE_TARIFF_TO_WORK);
			response.sendRedirect(DONE_PAGE);
		}
	}
}
