/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.database.connectionpool.impl;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.Driver;

import by.epam.internetprovider.dao.database.connectionpool.IConnectionPool;
import by.epam.internetprovider.dao.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.database.dbresourcemanager.DBParameter;
import by.epam.internetprovider.dao.database.dbresourcemanager.DBResourceManager;
import by.epam.internetprovider.dao.database.dbresourcemanager.exception.ResourceManagerException;

public final class ConnectionPoolOne implements IConnectionPool {

	private static final Logger logger = LogManager.getLogger();

	// private static final ConnectionPoolOne INSTANCE = new ConnectionPoolOne();
	private final static AtomicBoolean CREATE_INSTANCE = new AtomicBoolean(false);
	private static ReentrantLock lock = new ReentrantLock();
	private static ConnectionPoolOne INSTANCE;

	private static final int DEFAULT_POOL_SIZE = 5;
	private int poolSize;
	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConnectionQueue;

	private String driverName;
	private String url;
	private String user;
	private String password;

	// public static ConnectionPoolOne getInstance() {
	// return INSTANCE;
	// }

	public static ConnectionPoolOne getInstance() {
		if (!CREATE_INSTANCE.get()) { // "Double check" initialization way
			lock.lock();
			try {
				if (INSTANCE == null) {
					INSTANCE = new ConnectionPoolOne();
					CREATE_INSTANCE.set(true);
				}
			} finally {
				lock.unlock();
			}
		}
		return INSTANCE;
	}

	private ConnectionPoolOne() {
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Cannot clone the Connection Pool");
	}

	@Override
	public void initPool() throws ConnectionPoolException {

		DBResourceManager dbResourseManager = DBResourceManager.getInstance();

		try {
			// optional, JDBC before 4.0
			this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);

			this.url = dbResourseManager.getValue(DBParameter.DB_URL);
			this.user = dbResourseManager.getValue(DBParameter.DB_USER);
			this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);
			this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE));

		} catch (ResourceManagerException e1) {
			logger.log(Level.ERROR, "Error in Data Base Resource Manager", e1);
			throw new ConnectionPoolException("Error in Data Base Resource Manager.");

		} catch (NumberFormatException e) {
			logger.log(Level.WARN, "Wrong pool size value, will set to default (5).");
			poolSize = DEFAULT_POOL_SIZE;
		}

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // JDBC before 4.0
			// Class.forName(driverName); //JDBC before 4.0
			givenAwayConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(connection); // make proxy
				connectionQueue.add(pooledConnection);
			}

		} catch (SQLException e) {
			throw new ConnectionPoolException("SQLException", e);
			/*
			 * } catch (ClassNotFoundException e) { throw new
			 * ConnectionPoolException("Can't find database driver class.", e);
			 */
		}
		logger.log(Level.INFO, "Pool initialized.");
	}

	@Override
	public void closeAllConnections() throws ConnectionPoolException {
		logger.log(Level.INFO, "Disposing pool...");
		clearConnectionQueue();
	}

	@Override
	public int getFreeConnections() {
		return connectionQueue.size();
	}

	@Override
	public int getBusyConnections() {
		return givenAwayConnectionQueue.size();
	}

	@Override
	public Connection getConnection() throws ConnectionPoolException {

		Connection connection = null;

		try {
			connection = connectionQueue.take();
			givenAwayConnectionQueue.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source.", e);
		}

		return connection;
	}

	@Override
	public void releaseConnection(Connection con) throws ConnectionPoolException {

		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Connection isn't returned to the pool.", e);
		}

	}

	/**
	 * Clears both (free and used) connections queues.
	 *
	 * @throws ConnectionPoolException
	 */
	private void clearConnectionQueue() throws ConnectionPoolException {

		try {
			closeConnectionsQueue(givenAwayConnectionQueue);
			closeConnectionsQueue(connectionQueue);
		} catch (SQLException e) {
			throw new ConnectionPoolException("Error closing the connection.", e);
		}
	}

	/**
	 * Сorrectly closes all connections in the queue.
	 *
	 * @param queue {@code Queue} of connections
	 * @throws SQLException
	 */
	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}
	}

	/**
	 * 
	 * Inner class, realizing {@link Connection} and provides methods for controling
	 * the state of connections. Based of 'PROXY' pattern.
	 */
	private class PooledConnection implements Connection {
		private Connection connection;

		public PooledConnection(Connection c) throws SQLException {
			this.connection = c;
			this.connection.setAutoCommit(true);
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public void close() throws SQLException {

			logger.log(Level.DEBUG, "Overrided close() - connection will be returned in pool.");

			if (connection.isClosed()) {
				throw new ConnectionPoolException("Attempting to close closed connection.");
			}

			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}

			if (!givenAwayConnectionQueue.remove(this)) {
				logger.log(Level.ERROR,
						"Error deleting connection from the given away connections pool.");
				throw new ConnectionPoolException(
						"Error deleting connection from the given away connections pool.");
			}

			if (!connectionQueue.offer(this)) {
				logger.log(Level.ERROR, "Error allocating connection in the pool.");
				throw new ConnectionPoolException("Error allocating connection in the pool.");
			}
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
				throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
				throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames)
				throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,
				int resultSetConcurrency) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor arg0) throws SQLException {
			connection.abort(arg0);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void releaseSavepoint(Savepoint arg0) throws SQLException {
			connection.releaseSavepoint(arg0);
		}

		@Override
		public void rollback(Savepoint arg0) throws SQLException {
			connection.rollback(arg0);
		}

		@Override
		public void setClientInfo(Properties arg0) throws SQLClientInfoException {
			connection.setClientInfo(arg0);
		}

		@Override
		public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
			connection.setNetworkTimeout(arg0, arg1);
		}

		@Override
		public void setSchema(String arg0) throws SQLException {
			connection.setSchema(arg0);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
			connection.setTypeMap(arg0);
		}

	}
}
