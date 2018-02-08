package by.epam.internetprovider.dao.impl.database.connectionpool.exception;

import java.sql.SQLException;

public class ConnectionPoolException extends SQLException {
	private static final long serialVersionUID = 1L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Exception e) {
		super(e);
	}

	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}
}
