package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USER_FNAME_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class UserFirstName implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(USER_FNAME_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
