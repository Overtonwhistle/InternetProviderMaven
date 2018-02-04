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
	private final static String LOGGED_ATTRIBUTE = "logged";
	private final static String COMMAND_ATTRIBUTE = "command";

	/**
	 * Contains {@code enum} commands-name constants, which are allowed for
	 * unauthorized users.
	 * 
	 */
	private enum validCommand {
		LOG_IN, REGISTER, REG_PROCESS, LOCALIZATION, GOTO_TARIFF_INFO, GOTO_REGISTER_DONE, GOTO_TARIFFS
	}

	private static String redirect_page;
	private String gotoValue;

	/**
	 * Reads initialization value from WEB.XML file. If value is "yes", bad request
	 * will be redirected to specific error page. If value not "no" or missing,
	 * request redirects to the main page.
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {

		gotoValue = fConfig.getInitParameter(ERROR_DESTINATION_INIT_PARAMETER);

		redirect_page = ("yes".equals(gotoValue)) ? AUTHORIZATION_ERROR_PAGE : INDEX_PAGE;
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

		HttpSession session = httpRequest.getSession(true);

		String command = httpRequest.getParameter(COMMAND_ATTRIBUTE);

		boolean isValidCommand = false;

		if (command != null) {
			try {
				validCommand.valueOf(command.toUpperCase());
				isValidCommand = true;
			} catch (IllegalArgumentException e) {
				logger.log(Level.DEBUG, "Invalid command in AuthorizationFilter");
			}
		}

		if (!isValidCommand && (session.getAttribute(LOGGED_ATTRIBUTE) == null)) {
			httpResponse.sendRedirect(redirect_page);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		gotoValue = null;
	}

}
