package by.epam.internetprovider.dao.searchfilter.impl.technology;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_TECHNOLOGIES_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class TechnologySearchFilter extends SearchFilter {

	public String getSearchQuery() {
		return super.getSearchQuery(GET_TECHNOLOGIES_QUERY);

	}

}
