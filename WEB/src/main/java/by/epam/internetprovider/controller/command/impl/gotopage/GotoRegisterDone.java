package by.epam.internetprovider.controller.command.impl.gotopage;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.internetprovider.controller.command.ICommand;

public class GotoRegisterDone implements ICommand {
	private static final String PAGE = "WEB-INF/jsp/register-done.jsp";
	private static final String URL = "Controller?command=goto_register_done";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(ATTRIBUTE_URL, URL);

		session.removeAttribute(ATTRIBUTE_USER);
		session.removeAttribute(ATTRIBUTE_LOGGED);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
		dispatcher.forward(request, response);

	}

}
