package by.epam.internetprovider.dao.impl.listmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.bean.Technology;

public class TechnologyListMaker implements ListMaker {

	private static final TechnologyListMaker instance = new TechnologyListMaker();

	private static final int TECH_ID_FIELD = 1;
	private static final int TECH_TITLE_FIELD = 2;
	private static final int TECH_EQUIP_FIELD = 3;

	private TechnologyListMaker() {
	};

	public static TechnologyListMaker getIstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void makeList(ResultSet rs, List<T> list) throws SQLException {

		list.clear();

		while (rs.next()) {
			Technology technology = new Technology();

			technology.setId(rs.getInt(TECH_ID_FIELD));
			technology.setTitle(rs.getString(TECH_TITLE_FIELD));
			technology.setNeedRentEquipment(rs.getBoolean(TECH_EQUIP_FIELD));
			
			list.add((T) technology);
		}

	}

}
