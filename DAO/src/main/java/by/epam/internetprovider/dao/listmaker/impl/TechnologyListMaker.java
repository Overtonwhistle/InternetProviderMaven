package by.epam.internetprovider.dao.listmaker.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.dao.listmaker.IListMaker;

public class TechnologyListMaker implements IListMaker<Technology> {

	private static final TechnologyListMaker instance = new TechnologyListMaker();

	private static final int TECH_ID_FIELD = 1;
	private static final int TECH_TITLE_FIELD = 2;
	private static final int TECH_EQUIP_FIELD = 3;

	private TechnologyListMaker() {
	}

	public static TechnologyListMaker getIstance() {
		return instance;
	}

	@Override
	public List<Technology> makeList(ResultSet rs) throws SQLException {

		List<Technology> list = new ArrayList<>();

		while (rs.next()) {
			Technology technology = new Technology();
			technology.setId(rs.getInt(TECH_ID_FIELD));
			technology.setTitle(rs.getString(TECH_TITLE_FIELD));
			technology.setNeedRentEquipment(rs.getBoolean(TECH_EQUIP_FIELD));
			list.add(technology);
		}
		return list;

	}

}
