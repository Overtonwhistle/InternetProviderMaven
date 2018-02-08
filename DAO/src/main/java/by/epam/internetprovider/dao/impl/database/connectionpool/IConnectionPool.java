package by.epam.internetprovider.dao.impl.database.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

import by.epam.internetprovider.dao.impl.database.connectionpool.exception.ConnectionPoolException;

/**
 * Describes Connection Pool to Data Base. A connection pool is a cache of
 * database connections maintained so that the connections can be reused when
 * future requests to the database are required. Connection pools are used to
 * enhance the performance of executing commands on a database.
 * 
 * @author Pavel Sorokoletov
 */
public interface IConnectionPool {

	/**
	 * Inits the Connection Pool. Creates pool object and initializes its
	 * parameters.
	 *
	 * @throws ConnectionPoolInitException
	 */
	public void initPool() throws ConnectionPoolException;

	/**
	 * Get {@link Connection} from the pool.
	 *
	 * @return {@link Connection} instance.
	 * @throws ConnectionPoolException the connection pool exception
	 */
	public Connection getConnection() throws ConnectionPoolException;

	/**
	 * Returns connection to the pool.
	 * 
	 * @param connection {@link Connection}
	 * @throws SQLException
	 */
	public void releaseConnection(Connection connection) throws SQLException;

	/**
	 * Returns number of available connections in the pool.
	 *
	 * @return {@code int} number of connections.
	 */
	public int getFreeConnections();

	/**
	 * Returns number of busy (issued for use) connections in the pool.
	 *
	 * @return the busy connections.
	 */
	public int getBusyConnections();

	/**
	 * Close all (free and used) connections in pool.
	 *
	 * @throws ConnectionPoolException
	 */
	public void closeAllConnections() throws ConnectionPoolException;

}
