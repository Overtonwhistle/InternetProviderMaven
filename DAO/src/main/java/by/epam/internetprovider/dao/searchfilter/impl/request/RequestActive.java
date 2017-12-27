package by.epam.internetprovider.dao.searchfilter.impl.request;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.REQUEST_ACTIVE_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class RequestActive implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(REQUEST_ACTIVE_SUBQUERY);

	}

}
