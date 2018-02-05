package by.epam.internetprovider.dao.searchfilter;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.SQL_AND;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.SQL_WHERE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class, contains {@link SubFilter} objects and provides the formation and
 * issuance of a request corresponding to the available SubFilters.
 * 
 * @author Pavel Sorokoletov
 */

public abstract class SearchFilter {

	private static final SubFilterCommandProvider provider = new SubFilterCommandProvider();
	private static final String EMPTY_VALUE = "''";
	
	private List<SubFilter> filterList = new ArrayList<>();


	/**
	 * Returns the SQL-query to search.
	 *
	 * @param startQuery the primary query to the data source
	 * @return the SQL-query as {@code String}
	 * @throws DAOWrongSearchFilterException
	 */
	protected String getSearchQuery(String startQuery) {

		StringBuilder query = new StringBuilder(startQuery);
		if (!filterList.isEmpty()) {
			query.append(SQL_WHERE);
		}

		Iterator<SubFilter> it = filterList.iterator();

		while (it.hasNext()) {
			SubFilter subFilter = (SubFilter) it.next();
			FilterParameter commandName = subFilter.name;
			ISubFilterCommand command = provider.getCommand(commandName);
			command.execute(query, subFilter);
			if (it.hasNext()) {
				query.append(SQL_AND);
			}
		}

		return query.toString();
	}

	/**
	 * Returns size of {@code} SearchFilter. It equals the number of added
	 * subfilters.
	 *
	 * @return {@code int} size
	 */
	public int size() {
		return filterList.size();
	}

	@Override
	public String toString() {
		return "SearchFilter [Filter=" + filterList + "]";
	}

	/**
	 * Checks if SearchFilter is empty.
	 *
	 * @return true, if is empty.
	 */
	public boolean isEmpty() {
		return filterList.isEmpty();
	}

	/**
	 * Adds the sub filter to SearchFilter.
	 *
	 * @param subFilter the {@link SubFilter} object
	 * @return current {@link SearchFilter} object
	 */
	public void addSubFilter(SubFilter subFilter) {
		if (!subFilter.value.equals(EMPTY_VALUE)) {
			filterList.add(subFilter);
		}
	}

	/**
	 * Clears {@link SearchFilter}, all SubFilters will be deleted.
	 */
	public void clear() {
		filterList.clear();
	}

}
