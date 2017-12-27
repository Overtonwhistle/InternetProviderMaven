
package by.epam.internetprovider.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentSearchFilter;

/**
 * Describes Data Access Object layer of application. Provides operations with
 * data source.
 * 
 * @author Pavel Sorokoletov
 */
public interface IPaymentDAO {

	/**
	 * Adds the user's payment in application base.
	 *
	 * @param userId the user id
	 * @param amount {@link BigDecimal} the amount
	 * @param paymentDateTime {@link Timestamp} date and time of payment
	 * @return {@code true} if payment was added
	 * @throws DAOException
	 */
	public boolean addPayment(int userId, BigDecimal amount, Timestamp paymentDateTime)
			throws DAOException;

	
	
	
	/**
	 * Do payment transaction process.
	 *
	 * @param userId the user ID
	 * @param amount {@link BigDecimal} the amount
	 * @param paymentDateTime {@link Timestamp} date and time of payment
	 * @throws DAOException
	 */
	public void makePayment(User user, BigDecimal amount, Timestamp paymentDateTime)
			throws DAOException;
	
	
	/**
	 * Gets list of the payments by specific search condition.
	 *
	 * @param filter {@link PaymentSearchFilter} - object, encapsulating searching
	 *            query
	 * @return {@link List} of {@link Payment} objects
	 * @throws DAOException
	 */

	public List<Payment> getPaymentsList(PaymentSearchFilter filter) throws DAOException;

}
