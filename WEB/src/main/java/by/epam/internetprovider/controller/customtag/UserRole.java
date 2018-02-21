package by.epam.internetprovider.controller.customtag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import by.epam.internetprovider.bean.User;

@SuppressWarnings("serial")
public class UserRole extends BodyTagSupport {

	private String role;

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if (role.equalsIgnoreCase(user.getRole().toString())) {
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
