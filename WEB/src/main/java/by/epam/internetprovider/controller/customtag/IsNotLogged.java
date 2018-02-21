package by.epam.internetprovider.controller.customtag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class IsNotLogged extends BodyTagSupport {
	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession();

		if (session.getAttribute("logged") == null) {
			return EVAL_BODY_INCLUDE;
		} else
			return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
