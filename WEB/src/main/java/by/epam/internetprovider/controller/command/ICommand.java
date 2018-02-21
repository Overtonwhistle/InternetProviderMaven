package by.epam.internetprovider.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides pattern "Command" for main application.
 * 
 * @author Pavel Sorokoletov
 *         <p>
 */
public interface ICommand {
	/**
	 * Describes a method for executing commands in main Controller servlet.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @exception IOException
	 * @exception ServletException
	 * 
	 */
	void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;

}
