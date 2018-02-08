package by.epam.internetprovider.dao.impl.database.dbresourcemanager.exception;

import java.sql.SQLException;

public class ResourceManagerException extends SQLException {

	private static final long serialVersionUID = -7324419689895500333L;

	public ResourceManagerException() {
		super();
	}

	public ResourceManagerException(String message) {
		super(message);
	}

	public ResourceManagerException(Exception e) {
		super(e);
	}

	public ResourceManagerException(String message, Exception e) {
		super(message, e);
	}
}
