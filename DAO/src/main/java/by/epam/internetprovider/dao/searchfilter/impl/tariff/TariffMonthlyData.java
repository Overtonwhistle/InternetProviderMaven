package by.epam.internetprovider.dao.searchfilter.impl.tariff;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.TARIFF_MDATA_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TariffMonthlyData implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(TARIFF_MDATA_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
