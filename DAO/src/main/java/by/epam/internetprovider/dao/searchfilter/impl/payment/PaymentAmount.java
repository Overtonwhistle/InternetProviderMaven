package by.epam.internetprovider.dao.searchfilter.impl.payment;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.PAYMENT_AMOUNT_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class PaymentAmount implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(PAYMENT_AMOUNT_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
