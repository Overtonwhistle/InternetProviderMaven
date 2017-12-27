package by.epam.internetprovider.dao.searchfilter.impl.request;

import static by.epam.internetprovider.dao.searchfilter.impl.Utils.trimForOrderByQuery;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.REQUEST_BY_TARIFF_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class RequestByTariff implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		trimForOrderByQuery(query);

		query.append(REQUEST_BY_TARIFF_SUBQUERY);

	}

}
