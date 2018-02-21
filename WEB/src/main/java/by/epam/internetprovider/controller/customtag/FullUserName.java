package by.epam.internetprovider.controller.customtag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.epam.internetprovider.bean.User;

@SuppressWarnings("serial")
public class FullUserName extends TagSupport {
	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		String fullName = user.getFirstName() + " " + user.getLastName();
		try {
			JspWriter out = pageContext.getOut();
			out.write(fullName);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
