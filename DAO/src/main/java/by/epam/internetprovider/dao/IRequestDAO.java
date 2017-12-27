
package by.epam.internetprovider.dao;

import java.sql.Timestamp;
import java.util.List;

import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestSearchFilter;

/**
 * Describes Data Access Object layer of application. Provides operations with
 * Request's data source.
 * 
 * @author Pavel Sorokoletov
 */
public interface IRequestDAO {

	/**
	 * Adds the user's request to tariff changing.
	 *
	 * @param request {@link Request} object
	 * @return {@code true} if request was added
	 * @throws DAOException
	 */
	public boolean addRequest(Request request) throws DAOException;

	/**
	 * Returns the user's request by request ID.
	 *
	 * @param requestId the request id
	 * @return {@link Request} object
	 * @throws DAOException
	 */
	public Request getRequest(int requestId) throws DAOException;

	/**
	 * Performs a change of tariff request.
	 *
	 * @param requestId the request id
	 * @param processingDate {@link TimeSTamp} processing date and time
	 * @param adminId ID of the user performing the request
	 * @throws DAOException
	 */
	public void processRequest(int requestId, Timestamp processingDate, int adminId)
			throws DAOException;

	/**
	 * Delete user's request.
	 *
	 * @param requestId the request ID
	 * @return {@code true} if request was deleted
	 * @throws DAOException
	 */
	public boolean deleteRequest(int requestId) throws DAOException;

	/**
	 * Gets list of the requests by specific search condition.
	 *
	 * @param filter {@link RequestSearchFilter} - object, encapsulating searching
	 *            query
	 * @return {@link List} of {@link Request} objects
	 * @throws DAOException
	 */
	public List<Request> getRequestsList(RequestSearchFilter filter) throws DAOException;

}
