package by.epam.internetprovider.controller.command.exception;

import javax.servlet.ServletException;

public class CommandException extends ServletException {
	private static final long serialVersionUID = 1L;

	public CommandException() {
		super();
	}

	public CommandException(String message) {
		super(message);
	}

	public CommandException(Exception e) {
		super(e);
	}

	public CommandException(String message, Exception e) {
		super(message, e);
	}
}
