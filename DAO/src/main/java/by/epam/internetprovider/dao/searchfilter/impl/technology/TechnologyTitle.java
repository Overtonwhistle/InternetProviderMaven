package by.epam.internetprovider.dao.searchfilter.impl.technology;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.TECH_TITLE_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TechnologyTitle implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {

		query.append(TECH_TITLE_SUBFILTER).append(subfilter.condition.getSqlCondition())
				.append(subfilter.value);

	}

}
