package by.epam.internetprovider.dao.searchfilter.impl.payment;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.CLOSING_PARENTHESIS;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.LAST_NAME_ADDITIONAL_QUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class PaymentLastName implements ISubFilterCommand {


	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
		
		query.append(LAST_NAME_ADDITIONAL_QUERY).append(subfilter.condition.getSqlCondition())
		.append(subfilter.value).append(CLOSING_PARENTHESIS);
		
	}

}
