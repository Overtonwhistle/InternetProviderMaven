package by.epam.internetprovider.dao.factory;

import by.epam.internetprovider.dao.IBanDAO;
import by.epam.internetprovider.dao.IDataBaseDAO;
import by.epam.internetprovider.dao.IPaymentDAO;
import by.epam.internetprovider.dao.IRequestDAO;
import by.epam.internetprovider.dao.ITariffDAO;
import by.epam.internetprovider.dao.IUserDAO;
import by.epam.internetprovider.dao.impl.BanDAO;
import by.epam.internetprovider.dao.impl.DataBaseDAO;
import by.epam.internetprovider.dao.impl.PaymentDAO;
import by.epam.internetprovider.dao.impl.RequestDAO;
import by.epam.internetprovider.dao.impl.TariffDAO;
import by.epam.internetprovider.dao.impl.UserDAO;

/**
 * Simple singletone-based 'Factory' pattern realisation.
 * 
 * @author Pavel Sorokoletov
 */
public final class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();

	private final IDataBaseDAO dataBaseDAO = new DataBaseDAO();
	private final IUserDAO userDAO = new UserDAO();
	private final ITariffDAO tariffDAO = new TariffDAO();
	private final IRequestDAO requestDAO = new RequestDAO();
	private final IBanDAO banDAO = new BanDAO();
	private final IPaymentDAO paymentDAO = new PaymentDAO();

	private DAOFactory() {
	}

	/**
	 *
	 * @return the istance of {@link DAOFactory}
	 */
	public static DAOFactory getIstance() {
		return instance;
	}

	/**
	 * Gets a specific InternetProviderDAO object, here - MySQL version.
	 *
	 * @return {@link IDataBaseDAO} object
	 */
	public IDataBaseDAO getInternetProviderDAO() {
		return dataBaseDAO;
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public ITariffDAO getTariffDAO() {
		return tariffDAO;
	}

	public IRequestDAO getRequestDAO() {
		return requestDAO;
	}

	public IBanDAO getBanDAO() {
		return banDAO;
	}

	public IPaymentDAO getPaymentDAO() {
		return paymentDAO;
	}

}
