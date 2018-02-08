/**
 * @author Pavel Sorokoletov
 */

package by.epam.internetprovider.dao.impl.database.dbresourcemanager;

import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.dao.impl.database.dbresourcemanager.exception.ResourceManagerException;

/**
 * Provides retrieving database parameters from {@code ResourceBundle}
 * 
 * @author Pavel Sorokoletov
 */
public class DBResourceManager {
	private final static Logger logger = LogManager.getLogger();
	private final static DBResourceManager INSTANCE = new DBResourceManager();
	private final static String BASENAME = "database";
	private ResourceBundle bundle = ResourceBundle.getBundle(BASENAME);

	public static DBResourceManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets the resource value by its string key.
	 *
	 * @param key {@code String} key of resource.
	 * @return {@code String} value
	 * @throws ResourceManagerException the Resource Manager exception
	 */
	public String getValue(String key) throws ResourceManagerException {

		try {
			return bundle.getString(key);
		} catch (java.util.MissingResourceException e) {
			logger.log(Level.ERROR, "DBResourceManager error at getValue() for key:" + key);
			throw new ResourceManagerException(
					"DBResourceManager error at getValue() for key:" + key);
		}
	}

}
