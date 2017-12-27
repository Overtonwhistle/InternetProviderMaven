/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.exception;

public class DAOTariffNotFoundException extends DAOException {

	private static final long serialVersionUID = -4277215078055635449L;

	public DAOTariffNotFoundException() {
		super();
	}

	public DAOTariffNotFoundException(String message) {
		super(message);
	}

	public DAOTariffNotFoundException(Exception e) {
		super(e);
	}

	public DAOTariffNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
