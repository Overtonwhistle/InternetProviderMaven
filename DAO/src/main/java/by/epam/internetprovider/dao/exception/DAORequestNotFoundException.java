/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.exception;

public class DAORequestNotFoundException extends DAOException {

	private static final long serialVersionUID = -4632063746248260901L;

	public DAORequestNotFoundException() {
		super();
	}

	public DAORequestNotFoundException(String message) {
		super(message);
	}

	public DAORequestNotFoundException(Exception e) {
		super(e);
	}

	public DAORequestNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
