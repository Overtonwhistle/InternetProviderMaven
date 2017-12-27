/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.exception;

public class DAOUserNotFoundException extends DAOException {

	private static final long serialVersionUID = 1504440348852770038L;

	public DAOUserNotFoundException() {
		super();
	}

	public DAOUserNotFoundException(String message) {
		super(message);
	}

	public DAOUserNotFoundException(Exception e) {
		super(e);
	}

	public DAOUserNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
