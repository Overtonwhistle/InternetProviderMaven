package by.epam.internetprovider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.dao.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.impl.listmaker.ListMaker;

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
		Connection con = null;
		Statement st = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();

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

		finally {

			closeConAndStatement(con, st);
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
	 * @param maker {@link ListMaker} object
	 * @return {@link List} of <b>T</b>-objects
	 * @throws DAOException
	 */
	protected static <T> List<T> getList(String query, ListMaker maker) throws DAOException {

		logger.log(Level.INFO, "DAO getList() query: " + query);
		List<T> list = new ArrayList<>();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			maker.makeList(rs, list);
		}

		catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in getList()", e);
		}

		catch (SQLException e1) {
			throw new DAOException("SQL Error in getList()", e1);
		}

		finally {
			closeConStatementResultSet(con, st, rs);
		}
		return list;
	}

	protected static void closeConAndStatement(Connection con, Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing statement in closeConAndStatement()");
			}
		}

		if (con != null) {
			try {
				pool.releaseConnection(con);
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing connection in closeConAndStatement()");
			}
		}

	}

	protected static void closeConAndPreStatement(Connection con, PreparedStatement ps) {

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR,
						"Error closing prepared statement in closeConAndPreStatement()");
			}
		}

		if (con != null) {
			try {
				pool.releaseConnection(con);
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing connection in closeConAndPreStatement()");
			}
		}

	}

	protected static void closeConStatementResultSet(Connection con, Statement st, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing resultset in closeConStatementResultSet()");
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing statement in closeConStatementResultSet()");
			}
		}

		if (con != null) {
			try {
				pool.releaseConnection(con);
//				con=null; /////?????
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Error closing connection in closeConStatementResultSet()");
			}
		}

	}

}
