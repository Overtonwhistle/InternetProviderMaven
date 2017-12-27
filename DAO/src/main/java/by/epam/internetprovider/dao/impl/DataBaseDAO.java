/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.dao.IDataBaseDAO;
import by.epam.internetprovider.dao.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.exception.DAOException;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides data access methods for MySQL BD
 *
 */

public class DataBaseDAO implements IDataBaseDAO {

	private static final Logger logger = LogManager.getLogger();
	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@Override
	public void initDAO() throws DAOException {

		logger.log(Level.DEBUG, "Data source initialization...");
		try {
			pool.initPool();
		} catch (ConnectionPoolException e) {
			throw new DAOException("Error connecting to the data source.", e);
		}
	}

	@Override
	public void dismissDAO() throws DAOException {

		logger.log(Level.DEBUG, "Dismiss data source...");
		try {
			pool.closeAllConnections();
		} catch (ConnectionPoolException e) {
			throw new DAOException("Error closing data source.", e);
		}

	}

}
