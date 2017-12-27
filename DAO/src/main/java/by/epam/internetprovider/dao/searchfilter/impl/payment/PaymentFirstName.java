package by.epam.internetprovider.dao.searchfilter.impl.payment;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.CLOSING_PARENTHESIS;
import static by.epam.internetprovider.dao.searchfilter.queryConstant.FIRST_NAME_ADDITIONAL_QUERY;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class PaymentFirstName implements ISubFilterCommand {


	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
		
		query.append(FIRST_NAME_ADDITIONAL_QUERY).append(subfilter.condition.getSqlCondition())
		.append(subfilter.value).append(CLOSING_PARENTHESIS);
		
	}

}
