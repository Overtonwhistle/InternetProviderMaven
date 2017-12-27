package by.epam.internetprovider.service.exception;

public class ServiceCloseDataSourceException extends ServiceException {

	private static final long serialVersionUID = -7930864762383616688L;

	public ServiceCloseDataSourceException() {
		super();
	}

	public ServiceCloseDataSourceException(String message) {
		super(message);
	}

	public ServiceCloseDataSourceException(Exception e) {
		super(e);
	}

	public ServiceCloseDataSourceException(String message, Exception e) {
		super(message, e);
	}
}
