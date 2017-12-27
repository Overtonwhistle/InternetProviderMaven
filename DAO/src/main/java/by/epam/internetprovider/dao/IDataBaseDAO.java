
package by.epam.internetprovider.dao;

import by.epam.internetprovider.dao.exception.DAOException;

/**
 * Describes Data Access Object layer of application. Provides operations with
 * data source.
 * 
 * @author Pavel Sorokoletov
 */
public interface IDataBaseDAO {

	/**
	 * Inits (opens) the data source.
	 * 
	 * @throws DAOException
	 */
	public void initDAO() throws DAOException;

	/**
	 * Dismiss (closes) the data source.
	 *
	 * @throws DAOException
	 */
	public void dismissDAO() throws DAOException;

}
