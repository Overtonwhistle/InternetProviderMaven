package by.epam.internetprovider.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Application error handler. May be used for debugging.
 * 
 * @author Pavel Sorokoletov
 */
public class ErrorHandler extends HttpServlet {

	private static final long serialVersionUID = 9104911192079421487L;
	private static final Logger logger = LogManager.getLogger();

	private static final String DATA_MISSING = "error_handler.error_dara_missing";
	private static final String REQUEST_ATTR_STATUS_CODE = "javax.servlet.error.status_code";
	private static final String REQUEST_ATTR_EXCEPTION = "javax.servlet.error.exception";
	private static final String REQUEST_ATTR_SERVLET_NAME = "javax.servlet.error.servlet_name";
	private static final String REQUEST_ATTR_URI = "javax.servlet.error.request_uri";
	private static final String EMPTY_NAME = "Unknown";
	private static final String ERROR_PAGE = "WEB-INF/jsp/errors-pages/server-error.jsp";
	private static final String SESSION_ATTR_URL = "url";
	private static final String TO_JSP_ATTR_ORIGIN_PAGE = "originPage";
	private static final String TO_JSP_ATTR_STATUS_CODE = "statusCode";
	private static final String TO_JSP_ATTR_STATUS_SERVLET_NAME = "servletName";
	private static final String TO_JSP_ATTR_URI = "uri";
	private static final String TO_JSP_ATTR_ERRORS = "errors";
	private static final String TO_JSP_ATTR_EXCEPTION_TYPE = "exceptionType";
	private static final String TO_JSP_ATTR_MESSAGE = "message";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * Performs errors handling of Commander servlet. Setting are available via
	 * WEB.XML file (deployment descriptor).
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ResourceBundle errorsResource = ResourceBundle.getBundle("local_errors");

		logger.log(Level.INFO, "ErrorHandler working...");
		String page = "";
		String errors = "";

		Integer statusCode = (Integer) request.getAttribute(REQUEST_ATTR_STATUS_CODE);
		Throwable throwable = (Throwable) request.getAttribute(REQUEST_ATTR_EXCEPTION);

		String servletName = (String) request.getAttribute(REQUEST_ATTR_SERVLET_NAME);
		if (servletName == null) {
			servletName = EMPTY_NAME;
		}

		String requestUri = (String) request.getAttribute(REQUEST_ATTR_URI);
		if (requestUri == null) {
			requestUri = EMPTY_NAME;
		}

		HttpSession session = request.getSession(true);
		page = (String) session.getAttribute(SESSION_ATTR_URL);
		request.setAttribute(TO_JSP_ATTR_ORIGIN_PAGE, page);

		if (throwable == null && statusCode == null) {
			errors = errorsResource.getString(DATA_MISSING);
		} else {

			request.setAttribute(TO_JSP_ATTR_STATUS_CODE, statusCode);
			request.setAttribute(TO_JSP_ATTR_STATUS_SERVLET_NAME, servletName);
			request.setAttribute(TO_JSP_ATTR_URI, requestUri);
			request.setAttribute(TO_JSP_ATTR_ERRORS, errors);

			if (throwable != null) {
				request.setAttribute(TO_JSP_ATTR_EXCEPTION_TYPE, throwable.getClass().getName());
				request.setAttribute(TO_JSP_ATTR_MESSAGE, throwable.getMessage());
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
		dispatcher.forward(request, response);
	}
}
