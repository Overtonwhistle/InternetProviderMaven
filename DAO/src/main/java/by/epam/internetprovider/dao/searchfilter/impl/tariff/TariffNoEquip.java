package by.epam.internetprovider.dao.searchfilter.impl.tariff;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.NO_RENT_TARIFF_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TariffNoEquip implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(NO_RENT_TARIFF_SUBQUERY);

	}

}
