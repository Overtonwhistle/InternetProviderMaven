/*
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Provides charset filtration for ServletRequest and ServletResponse. Sets the
 * encoding of Request and Response to value read from the WEB.XML file.
 */
public class CharsetFilter implements Filter {
	private static final String WEB_XML_INIT_VALUE = "characterEncoding";
	private String encoding;

	/**
	 * Reads charset encoding value from WEB.XML file
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter(WEB_XML_INIT_VALUE);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

}
