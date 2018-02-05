/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.impl;

import static by.epam.internetprovider.dao.impl.CommonMethods.executeUpdate;
import static by.epam.internetprovider.dao.impl.CommonMethods.getExecuteUpdateResult;
import static by.epam.internetprovider.dao.impl.CommonMethods.getList;
import static by.epam.internetprovider.dao.impl.Constant.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.dao.IUserDAO;
import by.epam.internetprovider.dao.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.exception.DAOUserNotFoundException;
import by.epam.internetprovider.dao.listmaker.impl.UserListMaker;
import by.epam.internetprovider.dao.searchfilter.FilterParameter;
import by.epam.internetprovider.dao.searchfilter.SubFilter;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserSearchFilter;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides data access methods for MySQL BD
 *
 */

public class UserDAO implements IUserDAO {

	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@Override
	public boolean addUser(User user) throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(ADD_USER_QUERY)) {

			ps.setString(USER_ROLE_FIELD, user.getRole().name());
			ps.setString(USER_LOGIN_FIELD, user.getLogin());
			ps.setString(USER_PASSWORD_FIELD, user.getPassword());
			ps.setString(USER_EMAIL_FIELD, user.getEmail());
			ps.setString(USER_FIRSTNAME_FIELD, user.getFirstName());
			ps.setString(USER_LASTNAME_FIELD, user.getLastName());
			ps.setString(USER_PASSPOR_TNUMBER_FIELD, user.getPassportNumber());
			ps.setDate(USER_REGDATE_FIELD, user.getRegDate());
			ps.setLong(USER_SET_MONTHLY_DATA_USAGE_FIELD, user.getMonthlyDataUsage());
			ps.setLong(USER_TOTAL_DATA_USAGE_FIELD, user.getTotalDataUsage());
			ps.setBigDecimal(USER_ACCOUNT_BALLANCE_FIELD, user.getAccountBallance());
			ps.setInt(USER_TARIFF_FIELD, user.getTariffId());

			return getExecuteUpdateResult(ps);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in addUser()", e);

		} catch (SQLException e1) {
			throw new DAOException("SQL Error in addUser()", e1);
		}
	}

	@Override
	public boolean editUser(int userId, User user) throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(EDIT_USER_QUERY + userId)) {

			ps.setString(USER_ROLE_FIELD, user.getRole().name());
			ps.setString(USER_LOGIN_FIELD, user.getLogin());
			ps.setString(USER_PASSWORD_FIELD, user.getPassword());
			ps.setString(USER_EMAIL_FIELD, user.getEmail());
			ps.setString(USER_FIRSTNAME_FIELD, user.getFirstName());
			ps.setString(USER_LASTNAME_FIELD, user.getLastName());
			ps.setString(USER_PASSPOR_TNUMBER_FIELD, user.getPassportNumber());
			ps.setDate(USER_REGDATE_FIELD, user.getRegDate());
			ps.setLong(USER_SET_MONTHLY_DATA_USAGE_FIELD, user.getMonthlyDataUsage());
			ps.setLong(USER_TOTAL_DATA_USAGE_FIELD, user.getTotalDataUsage());
			ps.setBigDecimal(USER_ACCOUNT_BALLANCE_FIELD, user.getAccountBallance());
			ps.setInt(USER_TARIFF_FIELD, user.getTariffId());

			return getExecuteUpdateResult(ps);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in editUser()", e);

		} catch (SQLException e1) {
			throw new DAOException("SQL Error in editUser()", e1);
		}

	}

	@Override
	public User getUserById(int userId) throws DAOUserNotFoundException, DAOException {

		UserSearchFilter filter = new UserSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.USER_ID, userId));

		List<User> usersList = getUsersList(filter);

		if (usersList.isEmpty()) {
			throw new DAOUserNotFoundException("User not found in getUserById()");
		}
		return usersList.get(FIRST_INDEX);
	}

	@Override
	public boolean deleteUserById(int userId) throws DAOException {

		return (executeUpdate(DELETE_USER_QUERY + userId));

	}

	@Override
	public User getUserByLogin(String login, String password)
			throws DAOUserNotFoundException, DAOException {

		UserSearchFilter filter = new UserSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.USER_LOGIN, login));
		filter.addSubFilter(new SubFilter(FilterParameter.USER_PASSWORD, password));

		List<User> usersList = getUsersList(filter);

		if (usersList.isEmpty()) {
			throw new DAOUserNotFoundException("User not found in getUserByLogin()");
		}

		return usersList.get(FIRST_INDEX);
	}

	@Override
	public void increaseUserBallance(int userId, BigDecimal amount) throws DAOException {

		User user = getUserById(userId);
		BigDecimal ballance = user.getAccountBallance();
		user.setAccountBallance(ballance.add(amount));
		editUser(userId, user);

	}

	@Override
	public List<User> getUsersToBan() throws DAOException {

		return getList(USERS_TO_BAN_QUERY, UserListMaker.getIstance());
	}

	@Override
	public List<User> getUsersList(UserSearchFilter filter) throws DAOException {

		return getList(filter.getSearchQuery(), UserListMaker.getIstance());
	}

}
