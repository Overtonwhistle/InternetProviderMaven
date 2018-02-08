package by.epam.internetprovider.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Param.UserRole;
import by.epam.internetprovider.bean.User;

/**
 * Used for filtering requests to the main servlet-Controller, not allowing
 * access to resources that must be available only to authorized users.
 * 
 * @author Pavel Sorokoletov
 */
public class AuthorizationFilter implements Filter {

	private final static Logger logger = LogManager.getLogger();
	private final static String ERROR_DESTINATION_INIT_PARAMETER = "goto-auth-error-page";
	private final static String INDEX_PAGE = "index.jsp";
	private final static String AUTHORIZATION_ERROR_PAGE = "auth-error.jsp";
	private final static String COMMAND_ATTRIBUTE = "command";

	/**
	 * Contains {@code enum} commands-name constants, which are allowed for
	 * unauthorized users (guest mode).
	 * 
	 */
	private enum ValidGuestCommand {
		LOG_IN, REGISTER, REG_PROCESS, LOCALIZATION, GOTO_TARIFF_INFO, GOTO_REGISTER_DONE, GOTO_TARIFFS
	}

	/**
	 * Contains {@code enum} commands-name constants, which are allowed for
	 * administrators.
	 * 
	 */
	private enum AdminCommand {
		NEXT_RESULT_PAGE, PREVIOUS_RESULT_PAGE, LOG_OFF, GOTO_ADMIN, GOTO_AD_ADD_TARIFF, GOTO_AD_BAN_USER, GOTO_AD_DELETE_REQUEST, GOTO_AD_DELETE_TARIFF, GOTO_AD_DELETE_USER, GOTO_AD_EDIT_TARIFF, GOTO_AD_EDIT_USER, GOTO_AD_PAYMENTS, GOTO_AD_PROCESS_REQUEST, GOTO_AD_REQUESTS, GOTO_AD_TARIFFS, GOTO_AD_USERS, ADMIN_ADD_TARIFF_PROCESS, ADMIN_BAN_USER_PROCESS, ADMIN_UNBAN_USER_PROCESS, ADMIN_DEL_REQUEST_PROCESS, ADMIN_DEL_TARIFF_PROCESS, ADMIN_DEL_USER_PROCESS, ADMIN_EDIT_TARIFF_PROCESS, ADMIN_EDIT_USER_PROCESS, ADMIN_DO_REQUEST_PROCESS, LOCALIZATION, GOTO_TARIFF_INFO, GOTO_TARIFFS
	}

	/**
	 * Contains {@code enum} commands-name constants, which are allowed for clients.
	 * 
	 */
	private enum ClientCommand {
		NEXT_RESULT_PAGE, PREVIOUS_RESULT_PAGE, LOG_OFF, GOTO_CLIENT, GOTO_CLIENT_CHANGE_TARIFF, GOTO_CLIENT_DELETE_REQUEST, GOTO_CLIENT_EDIT_PROFILE, GOTO_CLIENT_MAKE_PAYMENT, GOTO_CLIENT_PAYMENTS, GOTO_CLIENT_REQUESTS, CLIENT_REQUEST_TARIFF_PROCESS, CLIENT_DELETE_REQUEST_PROCESS, CLIENT_EDIT_PROFILE_PROCESS, CLIENT_MAKE_PAYMENT_PROCESS, LOCALIZATION, GOTO_TARIFF_INFO, GOTO_TARIFFS
	}

	private static String redirect_page;
	private String gotoDestination;
	private User user;

	/**
	 * Reads initialization value from WEB.XML file. If value is "yes", bad request
	 * will be redirected to specific error page. If value not "no" or missing,
	 * request redirects to the main page.
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {

		gotoDestination = fConfig.getInitParameter(ERROR_DESTINATION_INIT_PARAMETER);
		redirect_page = ("yes".equals(gotoDestination)) ? AUTHORIZATION_ERROR_PAGE : INDEX_PAGE;
	}

	/**
	 * Performs filtering of commands
	 * 
	 * @param request {@link ServletRequest}
	 * @param response {@link ServletResponse}
	 * @param chain {@link FilterChain}
	 * 
	 * @exception IOException, ServletException
	 */

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String command;
		boolean isValidCommand = false;

		if (httpRequest.getParameter(COMMAND_ATTRIBUTE) != null) { // if request contains a command
			command = httpRequest.getParameter(COMMAND_ATTRIBUTE);

			if (session.getAttribute("user") != null) { // logged state
				user = (User) session.getAttribute("user");

				try {
					if (user.getRole() == UserRole.ADMIN) { // admin is logged
						AdminCommand.valueOf(command.toUpperCase());
					} else { // client here
						ClientCommand.valueOf(command.toUpperCase());
					}
					isValidCommand = true;
				} catch (IllegalArgumentException e) {
					logger.log(Level.DEBUG,
							"Invalid user command:" + command + " in AuthorizationFilter");
				}

			}

			else // not logged - guest state, only base commands are available
				try {
					ValidGuestCommand.valueOf(command.toUpperCase());
					isValidCommand = true;
				} catch (IllegalArgumentException e) {
					logger.log(Level.DEBUG,
							"Invalid guest command:" + command + " in AuthorizationFilter");
				}

		}

		if (isValidCommand) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(redirect_page);
		}
	}

	public void destroy() {
		gotoDestination = null;
		user = null;
	}

}
