package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USER_ROLE_CLIENT_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class UserRoleClient implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
	
		query.append(USER_ROLE_CLIENT_SUBQUERY);

	}

}
