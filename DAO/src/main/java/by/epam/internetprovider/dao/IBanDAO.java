
package by.epam.internetprovider.dao;

import java.sql.Timestamp;
import java.util.List;

import by.epam.internetprovider.bean.Ban;
import by.epam.internetprovider.bean.BanReason;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.banreason.BanReasonSearchFilter;

/**
 * Describes Data Access Object layer of application. Provides operations with
 * data source.
 * 
 * @author Pavel Sorokoletov
 */
public interface IBanDAO {

	/**
	 * Set user to banned state.
	 * 
	 * @param userId {@code int} user id
	 * @param banReason {@link BanReason}
	 * @param setDateTime {@link Timestamp} of date and time of banning
	 * @param comment {@code String} comment
	 * @return {@code true} if user was banned
	 * @throws DAOException
	 */
	public boolean banUser(int userId, int banReason, Timestamp setDateTime, String comment)
			throws DAOException;

	/**
	 * Unbans the user.
	 *
	 * @param userId {@code int} user id
	 * @param resetDateTime {@link Timestamp} of date and time of banning
	 * @return {@code true} if user was unbanned
	 * @throws DAOException
	 */
	public boolean unbanUser(int userId, Timestamp resetDateTime) throws DAOException;

	/**
	 * Gets list of the users with negative account ballance.
	 *
	 * @return {@link List} of {@link User} objects
	 * @throws DAOException
	 */
	public List<User> getUsersToBan() throws DAOException;

	/**
	 * Gets list of the bans by specific search condition.
	 *
	 * @param filter {@link BanSearchFilter} - object, encapsulating searching
	 *            query
	 * @return {@link List} of {@link Ban} objects
	 * @throws DAOException the DAO exception
	 */
	public List<Ban> getBansList(BanSearchFilter filter) throws DAOException;

	/**
	 * Gets list of the ban reasons by specific search condition.
	 *
	 * @param filter {@link BanReasonSearchFilter} - object, encapsulating
	 *            searching query
	 * @return {@link List} of {@link BanReason} objects
	 * @throws DAOException
	 */
	public List<BanReason> getBanReasonsList(BanReasonSearchFilter filter) throws DAOException;

}
