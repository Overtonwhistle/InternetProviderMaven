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

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

public class GotoAdminDeleteUser implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String PAGE = "WEB-INF/jsp/admin_del_user_page.jsp";
	private static final String URL = "Controller?command=goto_ad_delete_user";

//	private static final String PARAMETER_USER_ID_SELECTOR = "user_id_selector";
//	private static final String PARAMETER_ID_TO_WORK = "id_to_work";
//
//	private static final String ATTRIBUTE_ID_TO_WORK = "id_to_work";
//	private static final String ATTRIBUTE_USER_TO_WORK = "user_to_work";
//	private static final String ATTRIBUTE_URL = "url";

	private static final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
	private static final IInternetProviderService internetProviderService = serviceFactoryObject
			.getServiceImpl();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		int userId = 0;

		if (request.getParameter(PARAMETER_USER_ID_SELECTOR) != null) {
			userId = Integer.parseInt(request.getParameter(PARAMETER_USER_ID_SELECTOR));
			session.setAttribute(PARAMETER_ID_TO_WORK, userId);
		} else
			userId = (int) session.getAttribute(PARAMETER_ID_TO_WORK);

		User user = null;

		try {
			user = internetProviderService.getUserById(userId);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error executing command:GotoAdminDeleteUser");
			throw new CommandException("Error executing command:GotoAdminDeleteUser", e);
		}

		session.setAttribute(ATTRIBUTE_URL, URL);
		session.setAttribute(ATTRIBUTE_ID_TO_WORK, userId);
		session.setAttribute(ATTRIBUTE_USER_TO_WORK, user);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
