package by.epam.internetprovider.controller.command.impl.gotopage.client;
import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.internetprovider.controller.command.ICommand;

public class GotoClientDeleteRequest implements ICommand {
	private static final String PAGE = "WEB-INF/jsp/client_delete_request_page.jsp";
	private static final String URL = "Controller?command=goto_client_delete_request";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
