package by.epam.internetprovider.dao.searchfilter;

/**
 * Provides a SQL-subquery for searching
 * 
 * @author Pavel Sorokoletov
 */

public interface ISubFilterCommand {

	/**
	 * Adds to query the necessary conditions and parameters obtained from
	 * {@code SubFilter}
	 *
	 * @param query {@code StringBuilder} with base SQL-query
	 * @param subfilter {@link SubFilter} object, determining search conditions.
	 */
	void execute(StringBuilder query, SubFilter subfilter);

}
