package by.epam.internetprovider.service.exception;

public class ServiceRequestNotFoundException extends ServiceException {

	private static final long serialVersionUID = 6952597837788538459L;

	public ServiceRequestNotFoundException() {
		super();
	}

	public ServiceRequestNotFoundException(String message) {
		super(message);
	}

	public ServiceRequestNotFoundException(Exception e) {
		super(e);
	}

	public ServiceRequestNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
