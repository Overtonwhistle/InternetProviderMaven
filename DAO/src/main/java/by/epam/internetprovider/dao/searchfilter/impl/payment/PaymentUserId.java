package by.epam.internetprovider.dao.searchfilter.impl.payment;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.PAYMENT_USER_ID_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class PaymentUserId implements ISubFilterCommand {


	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
		
		query.append(PAYMENT_USER_ID_SUBFILTER).append(subfilter.condition.getSqlCondition())
		.append(subfilter.value);
		
	}

}
