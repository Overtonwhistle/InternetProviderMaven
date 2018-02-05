package by.epam.internetprovider.dao.listmaker.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.dao.listmaker.IListMaker;

public class TariffListMaker implements IListMaker<Tariff> {

	private static final TariffListMaker instance = new TariffListMaker();

	private static final int TARIFF_ID_FIELD = 1;
	private static final int TARIFF_TITLE_FIELD = 2;
	private static final int TARIFF_MONTHLY_COST_FIELD = 3;
	private static final int TARIFF_MONTHLY_LIMIT_FIELD = 4;
	private static final int TARIFF_IS_UNLIM_FIELD = 5;
	private static final int TARIFF_OVERLOAD_COST_FIELD = 6;
	private static final int TARIFF_DESCRIPTION_FIELD = 7;
	private static final int TARIFF_TECH_ID_FIELD = 8;

	private TariffListMaker() {
	}

	public static TariffListMaker getIstance() {
		return instance;
	}

	@Override
	public List<Tariff> makeList(ResultSet rs) throws SQLException {

		List<Tariff> list = new ArrayList<>();

		while (rs.next()) {
			Tariff tariff = new Tariff();
			tariff.setId(rs.getInt(TARIFF_ID_FIELD));
			tariff.setTitle(rs.getString(TARIFF_TITLE_FIELD));
			tariff.setMonthlyCost(rs.getBigDecimal(TARIFF_MONTHLY_COST_FIELD));
			tariff.setMonthlyDataLimit(rs.getLong(TARIFF_MONTHLY_LIMIT_FIELD));
			tariff.setUnlimTraffic(rs.getBoolean(TARIFF_IS_UNLIM_FIELD));
			tariff.setOverloadLimitCost(rs.getBigDecimal(TARIFF_OVERLOAD_COST_FIELD));
			tariff.setDescription(rs.getString(TARIFF_DESCRIPTION_FIELD));
			tariff.setTechnologyId(rs.getInt(TARIFF_TECH_ID_FIELD));
			list.add(tariff);
		}
		return list;
	}

}
