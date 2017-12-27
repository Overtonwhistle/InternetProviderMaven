package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USER_BANNED_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class UserBanned implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
	
		query.append(USER_BANNED_SUBQUERY);

	}

}
