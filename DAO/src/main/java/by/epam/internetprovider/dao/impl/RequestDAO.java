/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.impl;

import static by.epam.internetprovider.dao.impl.CommonMethods.closeConAndPreStatement;
import static by.epam.internetprovider.dao.impl.CommonMethods.getExecuteUpdateResult;
import static by.epam.internetprovider.dao.impl.CommonMethods.getList;
import static by.epam.internetprovider.dao.impl.Constant.ADD_REQUEST_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.COMPLETE_REQUEST_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.COMPLETE_REQUEST_QUERY_ADMIN_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.COMPLETE_REQUEST_QUERY_DATE_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.COMPLETE_REQUEST_QUERY_REQUEST_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.DELETE_REQUEST_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.FIRST_INDEX;
import static by.epam.internetprovider.dao.impl.Constant.REQUEST_CLIENT_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.REQUEST_DATE_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.REQUEST_TARIFF_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.USER_CHANGE_TARIFF_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.USER_CHANGE_TARIFF_QUERY_TARIFF_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.USER_CHANGE_TARIFF_QUERY_USER_ID_FIELD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.dao.IRequestDAO;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.exception.DAORequestNotFoundException;
import by.epam.internetprovider.dao.impl.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.impl.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.listmaker.impl.RequestListMaker;
import by.epam.internetprovider.dao.searchfilter.FilterParameter;
import by.epam.internetprovider.dao.searchfilter.SubFilter;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestSearchFilter;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides data access methods for MySQL BD
 *
 */

public class RequestDAO implements IRequestDAO {

	private static final Logger logger = LogManager.getLogger();
	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@Override
	public boolean addRequest(Request request) throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(ADD_REQUEST_QUERY)) {

			ps.setTimestamp(REQUEST_DATE_FIELD, request.getRequestDate());
			ps.setInt(REQUEST_CLIENT_ID_FIELD, request.getUserId());
			ps.setInt(REQUEST_TARIFF_ID_FIELD, request.getTariffId());

			return getExecuteUpdateResult(ps);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in addTariff()", e);

		} catch (SQLException e1) {
			throw new DAOException("SQL Error in addTariff()", e1);
		}

	}

	@Override
	public Request getRequest(int requestId) throws DAORequestNotFoundException, DAOException {

		RequestSearchFilter filter = new RequestSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.REQUEST_ID, Integer.toString(requestId)));

		List<Request> requestsList = getRequestsList(filter);

		if (requestsList.isEmpty()) {
			throw new DAORequestNotFoundException("Request not found in getRequest()");
		}

		return requestsList.get(FIRST_INDEX);
	}

	@Override
	public void processRequest(int requestId, Timestamp processingDate, int adminId)
			throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;

		int userId = getRequest(requestId).getUserId();
		int tariffId = getRequest(requestId).getTariffId();

		try
		{
			con = pool.getConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(COMPLETE_REQUEST_QUERY);
			ps.setTimestamp(COMPLETE_REQUEST_QUERY_DATE_FIELD, processingDate);
			ps.setInt(COMPLETE_REQUEST_QUERY_ADMIN_ID_FIELD, adminId);
			ps.setInt(COMPLETE_REQUEST_QUERY_REQUEST_ID_FIELD, requestId);
			ps.executeUpdate();

			ps.close();

			ps = con.prepareStatement(USER_CHANGE_TARIFF_QUERY);
			ps.setInt(USER_CHANGE_TARIFF_QUERY_TARIFF_ID_FIELD, tariffId);
			ps.setInt(USER_CHANGE_TARIFF_QUERY_USER_ID_FIELD, userId);
			ps.executeUpdate();

			con.commit();

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in processRequest()", e);

		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Rollback error!");
				throw new DAOException("Rollback error in processRequest()", e);
			}
			throw new DAOException("SQL Error in processRequest()", e1);

		} finally {
			closeConAndPreStatement(con, ps);
		}
	}

	@Override
	public boolean deleteRequest(int requestId) throws DAOException {

		return CommonMethods.executeUpdate(DELETE_REQUEST_QUERY + requestId);
	}

	@Override
	public List<Request> getRequestsList(RequestSearchFilter filter) throws DAOException {

		return getList(filter.getSearchQuery(), RequestListMaker.getIstance());
	}

}
