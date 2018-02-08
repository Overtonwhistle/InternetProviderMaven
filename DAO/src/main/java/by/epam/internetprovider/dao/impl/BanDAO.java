/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.impl;

import static by.epam.internetprovider.dao.impl.CommonMethods.getExecuteUpdateResult;
import static by.epam.internetprovider.dao.impl.CommonMethods.getList;
import static by.epam.internetprovider.dao.impl.Constant.BAN_USER_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.BAN_USER_QUERY_BAN_DATETIME_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.BAN_USER_QUERY_BAN_REASON_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.BAN_USER_QUERY_COMMENT_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.BAN_USER_QUERY_USER_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.UNBAN_USER_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.UNBAN_USER_QUERY_RESET_DATETIME_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.UNBAN_USER_QUERY_USER_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.USERS_TO_BAN_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import by.epam.internetprovider.bean.Ban;
import by.epam.internetprovider.bean.BanReason;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.dao.IBanDAO;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.impl.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.listmaker.impl.BanListMaker;
import by.epam.internetprovider.dao.listmaker.impl.BanReasonListMaker;
import by.epam.internetprovider.dao.listmaker.impl.UserListMaker;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.banreason.BanReasonSearchFilter;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides data access methods for MySQL BD
 *
 */

public class BanDAO implements IBanDAO {

	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@Override
	public boolean banUser(int userId, int banReason, Timestamp setBanDateTime, String comment)
			throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(BAN_USER_QUERY)) {

			ps.setInt(BAN_USER_QUERY_USER_ID_FIELD, userId);
			ps.setInt(BAN_USER_QUERY_BAN_REASON_FIELD, banReason);
			ps.setTimestamp(BAN_USER_QUERY_BAN_DATETIME_FIELD, setBanDateTime);
			ps.setString(BAN_USER_QUERY_COMMENT_FIELD, comment);

			return CommonMethods.getExecuteUpdateResult(ps);

		} catch (SQLException e) {
			throw new DAOException("SQLException in banUser()", e);
		}

	}

	@Override
	public boolean unbanUser(int userId, Timestamp resetDateTime) throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(UNBAN_USER_QUERY)) {
			ps.setTimestamp(UNBAN_USER_QUERY_RESET_DATETIME_FIELD, resetDateTime);
			ps.setInt(UNBAN_USER_QUERY_USER_ID_FIELD, userId);

			return getExecuteUpdateResult(ps);

		} catch (SQLException e) {
			throw new DAOException("SQLException from unbanUser()", e);
		}

	}

	@Override
	public List<User> getUsersToBan() throws DAOException {

		return getList(USERS_TO_BAN_QUERY, UserListMaker.getIstance());
	}

	@Override
	public List<Ban> getBansList(BanSearchFilter filter) throws DAOException {


		return getList(filter.getSearchQuery(), BanListMaker.getIstance());

	}

	@Override
	public List<BanReason> getBanReasonsList(BanReasonSearchFilter filter) throws DAOException {

		return getList(filter.getSearchQuery(), BanReasonListMaker.getIstance());
	}

}
