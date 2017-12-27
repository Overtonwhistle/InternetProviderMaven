package by.epam.internetprovider.dao.searchfilter.impl.tariff;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_TARIFFS_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class TariffSearchFilter extends SearchFilter {

	public String getSearchQuery() {

		return super.getSearchQuery(GET_TARIFFS_QUERY);

	}

}
