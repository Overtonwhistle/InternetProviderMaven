package by.epam.internetprovider.dao.searchfilter.impl.technology;

import static by.epam.internetprovider.dao.searchfilter.queryConstant.TECH_NEED_EQUIP_SUBFILTER;

import by.epam.internetprovider.dao.searchfilter.ISubFilterCommand;
import by.epam.internetprovider.dao.searchfilter.SubFilter;

public class TechnologyEquip implements ISubFilterCommand {

	@Override
	public void execute(StringBuilder query, SubFilter subfilter) {
		
		query.append(TECH_NEED_EQUIP_SUBFILTER).append(subfilter.value);

	}

}
