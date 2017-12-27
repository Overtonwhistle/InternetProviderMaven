package by.epam.internetprovider.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.internetprovider.controller.command.Command;

public class WrongCommand implements Command {
	private final static String REDIRECT_PAGE = "index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.sendRedirect(REDIRECT_PAGE);
	}

}
