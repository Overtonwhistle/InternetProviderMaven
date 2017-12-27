/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.impl;

import static by.epam.internetprovider.dao.impl.CommonMethods.closeConAndPreStatement;
import static by.epam.internetprovider.dao.impl.CommonMethods.getExecuteUpdateResult;
import static by.epam.internetprovider.dao.impl.CommonMethods.getList;
import static by.epam.internetprovider.dao.impl.Constant.ADD_PAYMENT_AMOUNT_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.ADD_PAYMENT_DATE_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.ADD_PAYMENT_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.ADD_PAYMENT_USER_ID;
import static by.epam.internetprovider.dao.impl.Constant.EDIT_CLIENT_BALLANCE_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.EDIT_CLIENT_BALLANCE_QUERY_AMOUNT_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.EDIT_CLIENT_BALLANCE_QUERY_USER_ID_FIELD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.dao.IPaymentDAO;
import by.epam.internetprovider.dao.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.impl.listmaker.PaymentListMaker;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentSearchFilter;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides data access methods for MySQL BD
 *
 */

public class PaymentDAO implements IPaymentDAO {

	private static final Logger logger = LogManager.getLogger();
	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@Override
	public boolean addPayment(int userId, BigDecimal amount, Timestamp paymentDateTime)
			throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(ADD_PAYMENT_QUERY)) {
			ps.setTimestamp(ADD_PAYMENT_DATE_FIELD, paymentDateTime);
			ps.setBigDecimal(ADD_PAYMENT_AMOUNT_FIELD, amount);
			ps.setInt(ADD_PAYMENT_USER_ID, userId);

			return getExecuteUpdateResult(ps);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in addPayment()", e);

		} catch (SQLException e1) {
			throw new DAOException("SQL Error in addPayment()", e1);
		}
	}

	@Override
	public void makePayment(User user, BigDecimal amount, Timestamp paymentDateTime)
			throws DAOException {

		BigDecimal oldBallance = user.getAccountBallance();
		BigDecimal newBallance = (oldBallance.add(amount));

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(ADD_PAYMENT_QUERY);
			ps.setTimestamp(ADD_PAYMENT_DATE_FIELD, paymentDateTime);
			ps.setBigDecimal(ADD_PAYMENT_AMOUNT_FIELD, amount);
			ps.setInt(ADD_PAYMENT_USER_ID, user.getId());
			ps.executeUpdate();

			ps.close();

			ps = con.prepareStatement(EDIT_CLIENT_BALLANCE_QUERY);
			ps.setBigDecimal(EDIT_CLIENT_BALLANCE_QUERY_AMOUNT_FIELD, newBallance);
			ps.setInt(EDIT_CLIENT_BALLANCE_QUERY_USER_ID_FIELD, user.getId());
			ps.executeUpdate();

			con.commit();

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in addPayment()", e);

		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Rollback error!");
				throw new DAOException("Rollback error in addPayment()", e);
			}
			throw new DAOException("SQL Error in addPayment()", e1);

		} finally {
			closeConAndPreStatement(con, ps);
		}
	}

	@Override
	public List<Payment> getPaymentsList(PaymentSearchFilter filter) throws DAOException {

		return getList(filter.getSearchQuery(), PaymentListMaker.getIstance());

	}

}
