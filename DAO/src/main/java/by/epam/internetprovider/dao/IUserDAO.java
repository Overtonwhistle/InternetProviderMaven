
package by.epam.internetprovider.dao;

import java.math.BigDecimal;
import java.util.List;

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.exception.DAOUserNotFoundException;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserSearchFilter;

/**
 * Describes Data Access Object layer of application. Provides operations with user's
 * data source.
 * 
 * @author Pavel Sorokoletov
 */
public interface IUserDAO {

	/**
	 * Adds the user is application base.
	 *
	 * @param user {@link User}
	 * 
	 * @return {@code true} if user was added.
	 * @throws DAOException
	 */
	public boolean addUser(User user) throws DAOException;

	/**
	 * Edits the user registred in application base.
	 *
	 * @param userId the user's Id field
	 * @param user {@link User} to edit
	 * @return {@code true} if user was edited.
	 * @throws DAOException
	 */
	public boolean editUser(int userId, User user) throws DAOException;

	/**
	 * Returns the user by login and password.
	 *
	 * @param login {@code String} login
	 * @param password {@code String} password
	 * @return {@link User}
	 * @throws DAOException
	 */
	public User getUserByLogin(String login, String password) throws DAOException;

	/**
	 * Returns the user by id.
	 *
	 * @param userId {@code int} user id
	 * @return the user by id
	 * @throws DAOException
	 */
	public User getUserById(int userId) throws DAOUserNotFoundException, DAOException;

	/**
	 * Deletes user by id.
	 *
	 * @param userId {@code int} user id
	 * @return {@code true} if user was edited
	 * @throws DAOException
	 */
	public boolean deleteUserById(int userId) throws DAOException;

	/**
	 * Adds amount to user's account ballance
	 *
	 * @param userId the user id
	 * @param amount {@code BigDecimal} the amount
	 * @throws DAOException
	 */
	public void increaseUserBallance(int userId, BigDecimal amount) throws DAOException;

	/**
	 * Gets list of the users with negative account ballance.
	 *
	 * @return {@link List} of {@link User} objects
	 * @throws DAOException
	 */
	public List<User> getUsersToBan() throws DAOException;

	/**
	 * Gets list of the users by specific search condition.
	 *
	 * @param filter {@link UserSearchFilter} - object, encapsulating searching
	 *            query
	 * @return {@link List} of {@link User} objects
	 * @throws DAOException
	 */
	public List<User> getUsersList(UserSearchFilter filter) throws DAOException;

}
