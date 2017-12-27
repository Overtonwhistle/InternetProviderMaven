package by.epam.internetprovider.dao.searchfilter.impl.payment;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.GET_PAYMENTS_QUERY;

import by.epam.internetprovider.dao.searchfilter.SearchFilter;

public class PaymentSearchFilter extends SearchFilter {

	public String getSearchQuery() {

		return super.getSearchQuery(GET_PAYMENTS_QUERY);

	}

}
