package by.epam.internetprovider.dao.searchfilter.impl.request;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.REQUEST_PROCESSED_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class RequestProcessed implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(REQUEST_PROCESSED_SUBQUERY);

	}

}
