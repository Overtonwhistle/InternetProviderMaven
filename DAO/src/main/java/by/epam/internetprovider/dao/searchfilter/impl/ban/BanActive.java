package by.epam.internetprovider.dao.searchfilter.impl.ban;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.BAN_ACTIVE_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class BanActive implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(BAN_ACTIVE_SUBQUERY);

	}

}
