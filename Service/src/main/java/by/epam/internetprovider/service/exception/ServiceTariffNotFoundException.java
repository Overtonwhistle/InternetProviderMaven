package by.epam.internetprovider.service.exception;

public class ServiceTariffNotFoundException extends ServiceException {

	private static final long serialVersionUID = -7910594359297700502L;

	public ServiceTariffNotFoundException() {
		super();
	}

	public ServiceTariffNotFoundException(String message) {
		super(message);
	}

	public ServiceTariffNotFoundException(Exception e) {
		super(e);
	}

	public ServiceTariffNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
