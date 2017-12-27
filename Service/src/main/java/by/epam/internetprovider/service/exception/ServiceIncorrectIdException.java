package by.epam.internetprovider.service.exception;

public class ServiceIncorrectIdException extends ServiceException {

	private static final long serialVersionUID = 1289277984620430798L;

	public ServiceIncorrectIdException() {
		super();
	}

	public ServiceIncorrectIdException(String message) {
		super(message);
	}

	public ServiceIncorrectIdException(Exception e) {
		super(e);
	}

	public ServiceIncorrectIdException(String message, Exception e) {
		super(message, e);
	}

}
