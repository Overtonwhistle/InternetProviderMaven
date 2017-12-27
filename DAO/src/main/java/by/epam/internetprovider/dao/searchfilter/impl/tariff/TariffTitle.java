package by.epam.internetprovider.dao.searchfilter.impl.tariff;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.TARIFF_TITLE_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TariffTitle implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(TARIFF_TITLE_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
