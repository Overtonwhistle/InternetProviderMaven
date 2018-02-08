package by.epam.internetprovider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.impl.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.impl.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.listmaker.IListMaker;

public class CommonMethods {
	private static final Logger logger = LogManager.getLogger();
	static ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	/**
	 * Performs {@link Statement} executeUpdate() .
	 *
	 * @param query {@code String} value of SQL-query
	 * @return {@code true} if operation was successfully completed, else returns
	 *         {@code false}.
	 * @throws DAOException
	 */
	protected static boolean executeUpdate(String query) throws DAOException {

		try (Connection con = pool.getConnection(); Statement st = con.createStatement()) {

			if (st.executeUpdate(query) == 1) {
				return true;
			} else {
				return false;
			}
		}

		catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in getList()", e);
		}

		catch (SQLException e1) {
			throw new DAOException("SQL Error in executeUpdate()", e1);
		}

	}

	/**
	 * Performs {@link PreparedStatement} executeUpdate() .
	 *
	 * @param ps {@link PreparedStatement} то update
	 * @return {@code true} if operation was successfully completed, else returns
	 *         {@code false}.
	 * @throws SQLException
	 */
	protected static boolean getExecuteUpdateResult(PreparedStatement ps) throws SQLException {

		if (ps.executeUpdate() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the {@link List} of generic type <b>T</b>.
	 *
	 * @param <T> the type of elements in this list.
	 * @param query {@code String} value of SQL-query
	 * @param maker {@link IListMaker} object
	 * @return {@link List} of <b>T</b>-objects
	 * @throws DAOException
	 */

	protected static <T> List<T> getList(String query, IListMaker<T> maker) throws DAOException {

		// logger.log(Level.INFO, "DAO getList() query: " + query);

		List<T> list;

		try (Connection con = pool.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)) {

			list = maker.makeList(rs);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in getList()", e);
		} catch (SQLException e1) {
			throw new DAOException("SQL Error in getList()", e1);
		}

		return list;
	}

	/*
	 * protected static void closeConAndStatement(Connection con, Statement st) { if
	 * (st != null) { try { st.close(); } catch (SQLException e) {
	 * logger.log(Level.ERROR, "Error closing statement in closeConAndStatement()");
	 * } } if (con != null) { try { pool.releaseConnection(con); } catch
	 * (SQLException e) { logger.log(Level.ERROR,
	 * "Error closing connection in closeConAndStatement()"); } } }
	 */

	protected static void closeConAndPreStatement(Connection con, PreparedStatement ps)
			throws DAOException {

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR,
						"Error closing prepared statement in closeConAndPreStatement()");
				throw new DAOException(
						"Error closing prepared statement in closeConAndPreStatement()", e);
			}
		}

		if (con != null) {
			try {
				pool.releaseConnection(con);
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing connection in closeConAndPreStatement()");
				throw new DAOException(
						"Error closing prepared connection in closeConAndPreStatement()", e);
			}
		}

	}

}
