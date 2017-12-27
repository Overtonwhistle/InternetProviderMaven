package by.epam.internetprovider.dao.searchfilter.impl.tariff;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.USED_TARIFF_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TariffUsed implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(USED_TARIFF_SUBQUERY);

	}

}
