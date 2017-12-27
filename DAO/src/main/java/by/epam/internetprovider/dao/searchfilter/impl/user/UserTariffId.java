package by.epam.internetprovider.dao.searchfilter.impl.user;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USER_TARIFF_ID_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class UserTariffId implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(USER_TARIFF_ID_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
