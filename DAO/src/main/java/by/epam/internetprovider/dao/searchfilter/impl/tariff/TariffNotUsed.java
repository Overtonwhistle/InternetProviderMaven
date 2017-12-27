package by.epam.internetprovider.dao.searchfilter.impl.tariff;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.NOT_USED_TARIFF_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TariffNotUsed implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(NOT_USED_TARIFF_SUBQUERY);

	}

}
