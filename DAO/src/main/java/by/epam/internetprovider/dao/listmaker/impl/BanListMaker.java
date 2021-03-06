package by.epam.internetprovider.dao.listmaker.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.internetprovider.bean.Ban;
import by.epam.internetprovider.dao.listmaker.IListMaker;

public class BanListMaker implements IListMaker<Ban> {

	private static final BanListMaker instance = new BanListMaker();

	private static final int BAN_ID_FIELD = 1;
	private static final int BAN_DATE_FIELD = 2;
	private static final int UNBAN_DATE_FIELD = 3;
	private static final int COMMENT_FIELD = 4;
	private static final int BAN_REASON_FIELD = 5;
	private static final int BAN_USER_FIELD = 6;

	private BanListMaker() {
	}

	public static BanListMaker getIstance() {
		return instance;
	}

	@Override
	public List<Ban> makeList(ResultSet rs) throws SQLException {

		List<Ban> list = new ArrayList<>();

		while (rs.next()) {
			Ban ban = new Ban();
			ban.setId(rs.getInt(BAN_ID_FIELD));
			ban.setBanDate(rs.getTimestamp(BAN_DATE_FIELD));
			ban.setUnbanDate(rs.getTimestamp(UNBAN_DATE_FIELD));
			ban.setComment(rs.getString(COMMENT_FIELD));
			ban.setBanReason(rs.getInt(BAN_REASON_FIELD));
			ban.setUser(rs.getInt(BAN_USER_FIELD));
			list.add(ban);
		}
		return list;

	}

}
