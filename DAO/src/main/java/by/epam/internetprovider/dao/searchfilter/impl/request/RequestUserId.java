package by.epam.internetprovider.dao.searchfilter.impl.request;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.REQUEST_USER_ID_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class RequestUserId implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(REQUEST_USER_ID_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
