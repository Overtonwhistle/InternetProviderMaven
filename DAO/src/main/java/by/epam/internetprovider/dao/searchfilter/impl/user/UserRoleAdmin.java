package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USER_ROLE_ADMIN_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class UserRoleAdmin implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
	
		query.append(USER_ROLE_ADMIN_SUBQUERY);

	}

}
