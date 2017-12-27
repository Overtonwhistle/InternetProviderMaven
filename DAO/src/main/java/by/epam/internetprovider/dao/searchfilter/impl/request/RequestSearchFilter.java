package by.epam.internetprovider.dao.searchfilter.impl.request;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_REQUESTS_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class RequestSearchFilter extends SearchFilter {

	public String getSearchQuery() {

		return super.getSearchQuery(GET_REQUESTS_QUERY);

	}

}
