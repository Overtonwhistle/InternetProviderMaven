package by.epam.internetprovider.dao.searchfilter.impl.ban;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.BAN_BAN_REASON_ID_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class BanReasonId implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(BAN_BAN_REASON_ID_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
