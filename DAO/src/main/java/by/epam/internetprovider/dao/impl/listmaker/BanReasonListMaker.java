package by.epam.internetprovider.dao.impl.listmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.bean.BanReason;

public class BanReasonListMaker implements ListMaker {

	private static final BanReasonListMaker instance = new BanReasonListMaker();

	private static final int BAN_REASON_ID_FIELD = 1;
	private static final int BAN_REASON_TITLE_FIELD = 2;

	private BanReasonListMaker() {
	};

	public static BanReasonListMaker getIstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void makeList(ResultSet rs, List<T> list) throws SQLException {

		list.clear();

		while (rs.next()) {
			BanReason banReason = new BanReason();

			banReason.setId(rs.getInt(BAN_REASON_ID_FIELD));
			banReason.setTitle(rs.getString(BAN_REASON_TITLE_FIELD));

			list.add((T) banReason);
		}

	}

}
