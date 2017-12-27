package by.epam.internetprovider.dao.searchfilter.impl;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.SQL_AND;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.SQL_AND_LENGTH;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.SQL_WHERE;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.SQL_WHERE_LENGTH;

public class Utils {

	public static void trimForOrderByQuery(StringBuilder query) {
		
		int lastWhereIndex = query.lastIndexOf(SQL_WHERE);

		if (lastWhereIndex == query.length() - SQL_WHERE_LENGTH) {
			query.setLength(lastWhereIndex);
		}

		int lastAndIndex = query.lastIndexOf(SQL_AND);

		if (lastAndIndex == query.length() - SQL_AND_LENGTH) {
			query.setLength(lastAndIndex);
		}
		
	}
}
