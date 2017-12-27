package by.epam.internetprovider.dao.searchfilter.impl.banreason;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.BAN_REASON_ID_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class BReasonId implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(BAN_REASON_ID_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
