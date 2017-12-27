package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USER_LNAME_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class UserLastName implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(USER_LNAME_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
