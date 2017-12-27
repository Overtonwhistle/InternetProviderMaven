package by.epam.internetprovider.dao.searchfilter.impl.ban;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_BANS_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class BanSearchFilter extends SearchFilter {

	public String getSearchQuery() {

		return super.getSearchQuery(GET_BANS_QUERY);

	}

}
