package by.epam.internetprovider.controller.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.User;

/**
 * Provides logging of user log-in and log-off operations
 * 
 * @author Pavel Sorokoletov
 */

public class LoginationListener implements HttpSessionAttributeListener {

	public LoginationListener() {
	}

	private static final Logger logger = LogManager.getLogger();
	private static final String ATTRIBUTE_USER = "user";

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {

		if (event.getName().equals(ATTRIBUTE_USER)) {
			User user = (User) event.getValue();
			logger.log(Level.INFO, "User log in : " + user.getFirstName() + " " + user.getLastName()
					+ " (" + user.getRole() + ")");
		}

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {

		if (event.getName().equals(ATTRIBUTE_USER)) {
			User user = (User) event.getValue();
			logger.log(Level.INFO, "User log off : " + user.getFirstName() + " "
					+ user.getLastName() + " (" + user.getRole() + ")");
		}

	}

}
