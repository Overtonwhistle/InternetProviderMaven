package by.epam.internetprovider.dao.searchfilter.impl.banreason;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_BAN_REASON_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class BanReasonSearchFilter extends SearchFilter {

	public String getSearchQuery() {
		return super.getSearchQuery(GET_BAN_REASON_QUERY);

	}

}
