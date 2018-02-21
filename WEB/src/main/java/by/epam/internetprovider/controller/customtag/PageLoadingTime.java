package by.epam.internetprovider.controller.customtag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class PageLoadingTime extends BodyTagSupport {
	private static final String TIME_ATTRIBUTE = "loading_time";
	long time;

	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession();

		if (session.getAttribute(TIME_ATTRIBUTE) == null) {
			session.setAttribute(TIME_ATTRIBUTE, (long) System.currentTimeMillis());

			// Path path = Paths.get(request.getRequestURI());
			// String fileName = path.getFileName().toString();
			//
			// try {
			// pageContext.forward(fileName);
			// } catch (ServletException e) {
			// e.printStackTrace();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			try {
				pageContext.forward((String) session.getAttribute("url"));
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			time = (long) System.currentTimeMillis() - (long) session.getAttribute(TIME_ATTRIBUTE);
			try {
				JspWriter out = pageContext.getOut();
				out.write("<b>" + time + "</b>");
			} catch (IOException e) {
				throw new JspException(e.getMessage());
			}
			session.removeAttribute(TIME_ATTRIBUTE);
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
