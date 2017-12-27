package by.epam.internetprovider.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 9127873411010470109L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}
}
