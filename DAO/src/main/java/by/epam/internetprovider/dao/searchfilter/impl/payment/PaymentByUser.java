package by.epam.internetprovider.dao.searchfilter.impl.payment;

import static by.epam.internetprovider.dao.searchfilter.impl.Utils.trimForOrderByQuery;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.PAYMENT_BYUSER_SUBQUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class PaymentByUser implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		trimForOrderByQuery(query);

		query.append(PAYMENT_BYUSER_SUBQUERY);

	}

}
