package by.epam.internetprovider.service.exception;

public class ServiceOpenDataSourceException extends ServiceException {

	private static final long serialVersionUID = 6244445949229929966L;

	public ServiceOpenDataSourceException() {
		super();
	}

	public ServiceOpenDataSourceException(String message) {
		super(message);
	}

	public ServiceOpenDataSourceException(Exception e) {
		super(e);
	}

	public ServiceOpenDataSourceException(String message, Exception e) {
		super(message, e);
	}
}
