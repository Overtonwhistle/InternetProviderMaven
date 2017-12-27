package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_USERS_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class UserSearchFilter extends SearchFilter {

	public String getSearchQuery() {

		return super.getSearchQuery(GET_USERS_QUERY);

	}

}
