package by.epam.internetprovider.controller.command.impl.gotopage;

import static by.epam.internetprovider.controller.command.util.CommandConstant.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.internetprovider.controller.command.ICommand;

public class GotoPreviousPage implements ICommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		int startIndex = (int) session.getAttribute(ATTRIBUTE_START_INDEX);
		startIndex -= TABLE_ROWS_PER_PAGE;
		session.setAttribute(ATTRIBUTE_START_INDEX, startIndex);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher((String) session.getAttribute(ATTRIBUTE_CURRENT_PAGE));
		dispatcher.forward(request, response);

	}

}
