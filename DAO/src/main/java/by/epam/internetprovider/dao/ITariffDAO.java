
package by.epam.internetprovider.dao;

import java.util.List;

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologySearchFilter;

/**
 * Describes Data Access Object layer of application. Provides operations with
 * Tariff's data source.
 * 
 * @author Pavel Sorokoletov
 */
public interface ITariffDAO {

	/**
	 * Adds the new tariff.
	 *
	 * @param tariff {@link Tariff}
	 * @return {@code true} if tariff was added
	 * @throws DAOException
	 */
	public boolean addTariff(Tariff tariff) throws DAOException;

	/**
	 * Edits the existing tariff.
	 *
	 * @param tariffId the Tariff ID
	 * @param tariff the edited Tariff
	 * @return {@code true} if tariff was edited
	 * @throws DAOException
	 */
	public boolean editTariff(int tariffId, Tariff tariff) throws DAOException;

	/**
	 * Delete the existing tariff.
	 *
	 * @param tariffId the Tariff ID
	 * @return {@code true} if tariff was deleted
	 * @throws DAOException
	 */
	public boolean deleteTariff(int tariffId) throws DAOException;

	/**
	 * Gets the by Tariff ID.
	 *
	 * @param tariffId the Tariff ID
	 * @return {@link Tariff} object
	 * @throws DAOException
	 */
	public Tariff getTariff(int tariffId) throws DAOException;

	/**
	 * Gets list of the tariffs by specific search condition.
	 *
	 * @param filter {@link TariffSearchFilter} - object, encapsulating searching
	 *            query
	 * @return {@link List} of {@link Tariff} objects
	 * @throws DAOException
	 */
	public List<Tariff> getTariffsList(TariffSearchFilter filter) throws DAOException;

	/**
	 * Gets list of the technologies by specific search condition.
	 *
	 * @param filter {@link TechnologySearchFilter} - object, encapsulating
	 *            searching query
	 * @return {@link List} of {@link Technology} objects
	 * @throws DAOException
	 */
	public List<Technology> getTechnologiesList(TechnologySearchFilter filter) throws DAOException;

}
