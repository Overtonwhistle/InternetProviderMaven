/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import by.epam.internetprovider.bean.Ban;
import by.epam.internetprovider.bean.BanReason;
import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.data_object.TariffData;
import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.banreason.BanReasonSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologySearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserSearchFilter;
import by.epam.internetprovider.service.exception.ServiceCloseDataSourceException;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.exception.ServiceIncorrectIdException;
import by.epam.internetprovider.service.exception.ServiceOpenDataSourceException;
import by.epam.internetprovider.service.exception.ServiceUserNotFoundException;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides main buisness logic of WEB-Project
 *
 */
public interface IInternetProviderService {

	/**
	 * Opens data source of application.
	 *
	 * @throws ServiceOpenDataSourceException if data source is not available.
	 */
	public void openDataSource() throws ServiceOpenDataSourceException;

	/**
	 * Сloses data source of application.
	 *
	 * @throws ServiceCloseDataSourceException when closing operation executes with
	 *             an error.
	 */
	public void CloseDataSource() throws ServiceCloseDataSourceException;

	/**
	 * Return {@link User} object by login and password values.
	 *
	 * @param login {@code String} login
	 * @param password {@code String} password
	 * @return {@link User} object
	 * @throws ServiceUserNotFoundException if user with specific login and password
	 *             isn't registred in system
	 * @throws ServiceServerErrorException if an server error occurs
	 */
	public User getUserByLogin(String login, String password) throws ServiceException;

	/**
	 * Return {@link User} object by user ID value.
	 *
	 * @param userId {@code int} user Id
	 * @return {@link User} object
	 * @throws ServiceIncorrectIdException if used id has a negative value.
	 * @throws ServiceUserNotFoundException if user with specific login and password
	 *             isn't registred in system
	 * @throws ServiceException if an server error occurs
	 */
	public User getUserById(int userId) throws ServiceUserNotFoundException, ServiceException;

	/**
	 * Performs user blocking ("bans" user). Actually
	 *
	 * @param {@code int} user Id
	 * @param banReasonId {@code int} value of {@link BanReason} object.
	 * @param comment {@code String} comment of blocking operation (optional)
	 * @throws ServiceException if an server error occurs
	 */
	public void banUser(int userId, int banReasonId, String comment) throws ServiceException;

	/**
	 * Performs user deblocking ("unbans" user).
	 *
	 *
	 * @param userId {@code int} user Id
	 * @throws ServiceException if an server error occurs
	 */
	public void unbanUser(int userId) throws ServiceException;

	/**
	 * Checks if is user banned.
	 *
	 * @param userId {@code int} user Id
	 * @return true, if is user banned, otherwise returns false.
	 * @throws ServiceException if an server error occurs
	 */
	public boolean isUserBlocked(int userId) throws ServiceException;

	/**
	 * Returns an {@link Ban} object, corresponding to the current user bloking
	 * state. If user isn't blocking, returns empty {@link Ban} object.
	 *
	 * @param userId {@code int} user Id
	 * @return {@link Ban} object
	 * @throws ServiceException if an server error occurs
	 */
	public Ban getUserBan(int userId) throws ServiceException;

	/**
	 * Creates the new {@link User} object, and putes it in the appliaction data
	 * source. Performs validation of all user's data before putting it in data
	 * source.
	 *
	 * @param userData {@link UserData} object
	 * @return {@link List} of {@code String} errors, occured during creating
	 *         operation. If no errors occured, returns empty {@code List}.
	 * @throws ServiceException if an error occurs
	 */
	public List<String> createUser(UserData userData) throws ServiceException;

	/**
	 * Edits the all existing users's data, taking new values from
	 * {@link UserData} object. Performs validation of all user's data before
	 * editing.
	 *
	 * @param userData {@link UserData} object, contains new user's data
	 *            values.
	 * @param userId {@code int} user Id
	 * @return {@link List} of {@code String} errors, occured during editing. If no
	 *         errors occured, returns empty {@code List}.
	 * @throws ServiceException if an error occurs
	 */
	public List<String> editUser(UserData userData, int userId) throws ServiceException;

	/**
	 * Edits only client-available data of User. Taking new values from
	 * {@link UserData} object. Performs validation of all data before editing.
	 *
	 * @param userData {@link UserData} object, contains new user's values
	 *            data.
	 * @param userId {@code int} user Id
	 * @return {@link List} of {@code String} errors, occured during editing. If no
	 *         errors occured, returns empty {@code List}.
	 * @throws ServiceException if an error occurs
	 */
	public List<String> editClientProfile(UserData userData, int userId)
			throws ServiceException;

	/**
	 * Deletes existing user user.
	 *
	 * @param userId {@code int} user Id
	 * @throws ServiceException if an error occurs
	 */
	public void deleteUser(int userId) throws ServiceException;

	/**
	 * Creates the new Tariff. Performs validation of all tariff's data before
	 * putting it in data source.
	 *
	 * @param tariffData {@link TariffData} object, contains new tariff's
	 *            values data.
	 * @return {@link List} of {@code String} errors, occured during editing. If no
	 *         errors occured, returns empty {@code List}.
	 * @throws ServiceAddTariffException the service add tariff exception
	 */
	public List<String> createTariff(TariffData tariffData) throws ServiceException;

	/**
	 * Edits the all existing tariff's data, taking new values from
	 * {@link TariffData} object. Performs validation of all data before editing.
	 *
	 * @param tariffId {@code int} Tariff ID value
	 * @param tariffData {@link TariffData} object, contains new tariff's
	 *            values data.
	 * @return {@link List} of {@code String} errors, occured during editing. If no
	 *         errors occured, returns empty {@code List}.
	 * @throws ServiceException
	 */
	public List<String> editTariff(int tariffId, TariffData tariffData)
			throws ServiceException;

	/**
	 * Returns {@link Tariff} object by ID value.
	 *
	 * @param tariffId {@code int} Tariff ID value
	 * @return {@link Tariff} object
	 * @throws ServiceException
	 */
	public Tariff getTariff(int tariffId) throws ServiceException;

	/**
	 * Returns {@link Technology} object by ID value.
	 *
	 * @param technologyId {@code int} Technology ID value
	 * @return {@link Technology} object
	 * @throws ServiceException
	 */
	public Technology getTechnology(int technologyId) throws ServiceException;

	/**
	 * Checks if is Tariff used.
	 *
	 * @param tariffId {@code int} Tariff ID value
	 * @return true, if tariff is used, otherwise returns false
	 * @throws ServiceException
	 */
	public boolean isTariffUsed(int tariffId) throws ServiceException;

	/**
	 * Deletes tariff by Tariff'd ID.
	 *
	 * @param tariffId {@code int} Tariff ID value
	 * @throws ServiceException
	 */
	public void deleteTariff(int tariffId) throws ServiceException;

	/**
	 * Adds new User's request for tariff change.
	 *
	 * @param userId {@code int} User ID value
	 * @param tariffId {@code int} requested Tariff ID value
	 * @return {@link List} of {@code String} errors, occured during creating
	 *         request. If no errors occured, returns empty {@code List}.
	 * @throws ServiceException
	 */
	public String addRequest(int userId, int tariffId) throws ServiceException;

	/**
	 * Returns Requiest by ID.
	 *
	 * @param requestId {@code int} Request ID value
	 * @return the {@link Request} object
	 * @throws ServiceException
	 */
	public Request getRequest(int requestId) throws ServiceException;

	/**
	 * Returns active User's request.
	 *
	 * @param userId {@code int} User id value
	 * @return the {@link Request} object
	 * @throws ServiceException
	 */
	public Request getActiveRequest(int userId) throws ServiceException;

	/**
	 * Processes user request on behalf of the user whose ID-number is passed as the
	 * method parameter.
	 *
	 * @param requestId {@code int} the request id to process
	 * @param adminId {@code int} User id value. User must have Administrator role.
	 * @throws ServiceException
	 */
	public void processRequest(int requestId, int adminId) throws ServiceException;

	/**
	 * Deletes user's request.
	 *
	 * @param requestId {@code int} the request id to delete
	 * @return the {@code String}-type error, occured during deleting
	 * @throws ServiceException
	 */
	public String deleteRequest(int requestId) throws ServiceException;

	/**
	 * Make payment. Registers a user's payment with the current date and time.
	 * Returns {@link List} of errors.
	 *
	 * @param userId {@code int} user id value
	 * @param amount {@code String} amount the amount of Payment
	 * @return {@link List} of {@code String} error
	 * @throws ServiceException the service exception
	 */
	public List<String> makePayment(int userId, String amount) throws ServiceException;

	/**
	 * Returns {@link List} of {@link User} objects.
	 *
	 * @param filter {@link UserSearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link User} objects
	 * @throws ServiceException
	 */
	public List<User> getUsersList(UserSearchFilter filter) throws ServiceException;

	/**
	 * Returns {@link List} of {@link User} objects.
	 *
	 * @param parameters {@link Map} of parameters for searching. Key of Map
	 *            contains {@code String} parameter's name, value - {@code String}
	 *            parameter's value.
	 * 
	 * @return {@link List} of {@link User} objects
	 * @throws ServiceException
	 */
	public List<User> getUsersList(Map<String, String> parameters) throws ServiceException;

	/**
	 * Returns {@link List} of {@link User} objects, containing all users in data
	 * source of the application.
	 *
	 * @return {@link List} of {@link User} objects
	 * @throws ServiceException
	 */
	public List<User> getUsersList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link User} objects, containing users with role
	 * CLIENT (clients of application) in data source of the application.
	 *
	 * @return {@link List} of {@link User} objects
	 * @throws ServiceException
	 */
	public List<User> getClientsList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link User} objects, containing users with negative
	 * account ballance in data source of the application.
	 *
	 * @return {@link List} of {@link User} objects
	 * @throws ServiceException
	 */
	public List<User> getUsersWithNegBallance() throws ServiceException;

	/**
	 * Returns {@link List} of {@link User} objects, containing blocked users in
	 * data source of the application.
	 *
	 * @return {@link List} of {@link User} objects
	 * @throws ServiceException
	 */
	public List<User> getUsersInBan() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Request} objects.
	 *
	 * @param filter {@link RequestSearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link Request} objects
	 * @throws ServiceException
	 */
	public List<Request> getRequestsList(RequestSearchFilter filter) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Request} objects.
	 *
	 * @param parameters {@link Map} of parameters for searching. Key of Map
	 *            contains {@code String} parameter's name, value - {@code String}
	 *            parameter's value.
	 * 
	 * @return {@link List} of {@link Tariff} objects
	 * @throws ServiceException
	 */
	public List<Request> getRequestsList(Map<String, String> parameters) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Request} objects, containing all requests in
	 * data source of the application.
	 *
	 * @return {@link List} of {@link Request} objects
	 * @throws ServiceException
	 */
	public List<Request> getRequestsList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Request} objects, containing all active
	 * requests in data source of the application.
	 *
	 * @return {@link List} of {@link Request} objects
	 * @throws ServiceException
	 */
	public List<Request> getActiveRequestsList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Request} objects, containing all proccessed
	 * requests for user in data source of the application.
	 *
	 * @param userId the User's Id value
	 * @return {@link List} of {@link Request} objects
	 * @throws ServiceException the service exception
	 */
	public List<Request> getUserHistoryRequestsList(int userId) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Tariff} objects.
	 *
	 * @param filter {@link TariffSearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link Tariff} objects
	 * @throws ServiceException
	 */
	public List<Tariff> getTariffsList(TariffSearchFilter filter) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Tariff} objects.
	 *
	 * @param parameters {@link Map} of parameters for searching. Key of Map
	 *            contains {@code String} parameter's name, value - {@code String}
	 *            parameter's value.
	 * 
	 * @return {@link List} of {@link Tariff} objects
	 * @throws ServiceException
	 */
	public List<Tariff> getTariffsList(Map<String, String> parameters) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Tariff} objects, containing all tariffs in
	 * data source of the application.
	 *
	 * @return {@link List} of {@link Tariff} objects
	 * @throws ServiceException
	 */
	public List<Tariff> getTariffsList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Tariff} objects, containing not used tariffs
	 * in data source of the application.
	 *
	 * @return {@link List} of {@link Tariff} objects
	 * @throws ServiceException
	 */
	public List<Tariff> getNotUsedTariffsList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Ban} objects.
	 *
	 * @param filter {@link BanSearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link Ban} objects
	 * @throws ServiceException
	 */
	public List<Ban> getBansList(BanSearchFilter filter) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Ban} objects, containing all user blocking
	 * states in data source of the application.
	 *
	 * @return {@link List} of {@link Ban} objects
	 * @throws ServiceException
	 */
	public List<Ban> getBansList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Ban} objects - all User's bans, i.e. history
	 * of blocking.
	 *
	 * @param userId {@code int} the user id
	 * @return {@link List} of {@link Ban} objects
	 * @throws ServiceException
	 */
	public List<Ban> getUserBansList(int userId) throws ServiceException;

	/**
	 * Returns {@link List} of {@link BanReason} objects.
	 *
	 * @param filter {@link BanReasonSearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link BanReason} objects
	 * @throws ServiceException
	 */
	public List<BanReason> getBanReasonsList(BanReasonSearchFilter filter) throws ServiceException;

	/**
	 * Returns {@link List} of {@link BanReason} objects, containing all available
	 * blocking reasons in data source of the application.
	 *
	 * @return {@link List} of {@link BanReason} objects
	 * @throws ServiceException
	 */
	public List<BanReason> getBanReasonsList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Technology} objects.
	 *
	 * @param filter {@link TechnologySearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link Technology} objects
	 * @throws ServiceException
	 */
	public List<Technology> getTechologiesList(TechnologySearchFilter filter)
			throws ServiceException;

	/**
	 * Returns {@link List} of {@link Technology} objects, containing all available
	 * technologies in data source of the application.
	 *
	 * @return {@link List} of {@link Technology} objects
	 * @throws ServiceException
	 */
	public List<Technology> getTechologiesList() throws ServiceException;

	/**
	 * Returns {@link List} of {@link Payment} objects.
	 *
	 * @param filter {@link PaymentSearchFilter} object, determining the search
	 *            conditions for compiling the list.
	 * @return {@link List} of {@link Payment} objects
	 * @throws ServiceException
	 */
	public List<Payment> getPaymentsList(PaymentSearchFilter searchFilter) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Payment} objects.
	 *
	 * @param parameters {@link Map} of parameters for searching. Key of Map
	 *            contains {@code String} parameter's name, value - {@code String}
	 *            parameter's value.
	 * 
	 * @return {@link List} of {@link Payment} objects
	 * @throws ServiceException
	 */
	public List<Payment> getPaymentsList(Map<String, String> parameters) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Payment} objects for specified user.
	 *
	 * @param userId the User id
	 * @param parameters {@link Map} of parameters for searching. Key of Map
	 *            contains {@code String} parameter's name, value - {@code String}
	 *            parameter's value.
	 * @return {@link List} of {@link Payment} objects
	 * @throws ServiceException the service exception
	 */
	public List<Payment> getUserPaymentsList(int userId) throws ServiceException;

	/**
	 * Returns {@link List} of {@link Payment} objects for specified user.
	 *
	 * @param userId the User id
	 * @return {@link List} of {@link Payment} objects
	 * @throws ServiceException the service exception
	 */
	public List<Payment> getUserPaymentsList(int userId, Map<String, String> parameters)
			throws ServiceException;

	/**
	 * Returns {@link List} of {@link Payment} objects, containing all payments in
	 * data source of the application.
	 *
	 * @return {@link List} of {@link Payment} objects
	 * @throws ServiceException
	 */
	public List<Payment> getPaymentsList() throws ServiceException;

	/**
	 * Returns {@link Map} of some Tariff's data. Key of Map - Tariff Id, value of
	 * Map - Tariff title.
	 *
	 * @return the {@link Map} of Integer:String.
	 * @throws ServiceException
	 */
	public Map<Integer, String> getTariffsMap() throws ServiceException;

	/**
	 * Returns {@code BigDecimal} Tariff cost value in all Tariffs from parameter
	 * tariffsList.
	 *
	 * @param tariffsList {@link List} of {@link Tariff} objects
	 * @return {@code BigDecimal} price
	 */

	public BigDecimal getLowestTariffCost(List<Tariff> tariffsList);

}
