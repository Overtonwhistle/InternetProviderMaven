package by.epam.internetprovider.dao.listmaker.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.internetprovider.bean.BanReason;
import by.epam.internetprovider.dao.listmaker.IListMaker;

public class BanReasonListMaker implements IListMaker<BanReason> {

	private static final BanReasonListMaker instance = new BanReasonListMaker();

	private static final int BAN_REASON_ID_FIELD = 1;
	private static final int BAN_REASON_TITLE_FIELD = 2;

	private BanReasonListMaker() {
	}

	public static BanReasonListMaker getIstance() {
		return instance;
	}

	@Override
	public List<BanReason> makeList(ResultSet rs) throws SQLException {
		List<BanReason> list = new ArrayList<>();

		while (rs.next()) {
			BanReason banReason = new BanReason();
			banReason.setId(rs.getInt(BAN_REASON_ID_FIELD));
			banReason.setTitle(rs.getString(BAN_REASON_TITLE_FIELD));
			list.add(banReason);
		}
		return list;
	}

}
