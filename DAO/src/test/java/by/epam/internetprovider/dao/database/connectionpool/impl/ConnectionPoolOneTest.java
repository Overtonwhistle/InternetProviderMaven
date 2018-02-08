package by.epam.internetprovider.dao.database.connectionpool.impl;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.epam.internetprovider.dao.impl.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.impl.database.connectionpool.impl.ConnectionPoolOne;

public class ConnectionPoolOneTest {

	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@BeforeMethod
	private void initPoll() throws ConnectionPoolException {
		pool.initPool();
	}

	@AfterMethod
	private void closePool() throws ConnectionPoolException {
		pool.closeAllConnections();
	}

	@Test
	public void getInstance() {
		ConnectionPoolOne poolTestInstanse = ConnectionPoolOne.getInstance();
		Assert.assertEquals(poolTestInstanse.getClass(), ConnectionPoolOne.class);
	}

	@Test
	public void getConnection() throws SQLException {
		java.sql.Connection connection = pool.getConnection();
		Assert.assertTrue(connection instanceof java.sql.Connection);
		Assert.assertFalse(connection.isClosed());

	}

	@Test
	public void getFreeConnections() throws ConnectionPoolException {
		java.sql.Connection connection = pool.getConnection();
		Assert.assertEquals(pool.getFreeConnections(), 4);
	}

	@Test
	public void getBusyConnections() throws ConnectionPoolException {
		java.sql.Connection connection = pool.getConnection();
		Assert.assertEquals(pool.getBusyConnections(), 1);
	}

	@Test
	public void releaseConnection() throws SQLException {
		java.sql.Connection connection1 = pool.getConnection();
		pool.releaseConnection(connection1);
		Assert.assertFalse(connection1.isClosed());
		Assert.assertEquals(pool.getBusyConnections(), 0);
		pool.closeAllConnections();
		Assert.assertTrue(connection1.isClosed());
	}

	@Test(expectedExceptions = ConnectionPoolException.class)
	public void closeClosedconnection() throws SQLException {
		java.sql.Connection connection1 = pool.getConnection();
		pool.closeAllConnections();
		connection1.close();
	}

	@Test
	public void closeAllConnections() throws SQLException {
		java.sql.Connection connection1 = pool.getConnection();
		java.sql.Connection connection2 = pool.getConnection();
		java.sql.Connection connection3 = pool.getConnection();
		java.sql.Connection connection4 = pool.getConnection();
		java.sql.Connection connection5 = pool.getConnection();
		pool.closeAllConnections();
		Assert.assertTrue(connection1.isClosed());
		Assert.assertTrue(connection2.isClosed());
		Assert.assertTrue(connection3.isClosed());
		Assert.assertTrue(connection4.isClosed());
		Assert.assertTrue(connection5.isClosed());

	}

}
