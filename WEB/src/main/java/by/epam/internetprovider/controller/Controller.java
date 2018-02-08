
package by.epam.internetprovider.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.controller.command.Command;
import by.epam.internetprovider.controller.command.CommandProvider;
import by.epam.internetprovider.controller.command.exception.CommandException;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceCloseDataSourceException;
import by.epam.internetprovider.service.exception.ServiceOpenDataSourceException;
import by.epam.internetprovider.service.factory.ServiceFactory;

/**
 * Main application servlet. Uses a 'COMMAND' patters for processing and
 * executing requests.
 * 
 * @author Pavel Sorokoletov
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 3778903914073650227L;

	private static final Logger logger = LogManager.getLogger(Controller.class);
	private static final CommandProvider provider = new CommandProvider();
	private static final String REQUEST_COMMAND = "command";

	public Controller() {
		super();
	}

	/**
	 * In addition to the servlet initialization, also initializes the data source.
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException, CommandException {

		final ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
		IInternetProviderService internetProviderService = serviceFactoryObject.getServiceImpl();

		try {
			internetProviderService.openDataSource();
		} catch (ServiceOpenDataSourceException e) {
			logger.log(Level.ERROR, "Failed to open data source in Controller::init()");
			throw new CommandException("Error init server's data source.", e);
		} finally {
			super.init();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommandException {

		String commandName = request.getParameter(REQUEST_COMMAND);

		Command command = provider.getCommand(commandName);
		command.execute(request, response);

	}

	@Override
	public void destroy() {

		ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
		IInternetProviderService internetProviderService = serviceFactoryObject.getServiceImpl();

		try {
			internetProviderService.CloseDataSource();
		} catch (ServiceCloseDataSourceException e) {
			logger.log(Level.ERROR, "Failed to close data source, in Controller::destroy()", e);
		} finally {
			super.destroy();
		}
	}

}
