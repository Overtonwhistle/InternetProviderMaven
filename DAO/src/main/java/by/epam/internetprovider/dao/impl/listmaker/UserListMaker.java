package by.epam.internetprovider.dao.impl.listmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.Param.UserRole;

public class UserListMaker implements ListMaker {

	private static final UserListMaker instance = new UserListMaker();

	private static final int USER_ID_FIELD = 1;
	private static final int USER_ROLE_FIELD = 2;
	private static final int USER_LOGIN_FIELD = 3;
	private static final int USER_PASSWORD_FIELD = 4;
	private static final int USER_EMAIL_FIELD = 5;
	private static final int USER_FIRSTNAME_FIELD = 6;
	private static final int USER_LASTNAME_FIELD = 7;
	private static final int USER_PASSPOR_TNUMBER_FIELD = 8;
	private static final int USER_REGDATE_FIELD = 9;
	private static final int USER_SET_MONTHLY_DATA_USAGE_FIELD = 10;
	private static final int USER_TOTAL_DATA_USAGE_FIELD = 11;
	private static final int USER_ACCOUNT_BALLANCE_FIELD = 12;
	private static final int USER_TARIFF_FIELD = 13;
	
	
	private UserListMaker() {
	};

	public static UserListMaker getIstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void makeList(ResultSet rs, List<T> list) throws SQLException {

		list.clear();

		while (rs.next()) {
			User user = new User();

			user.setId(rs.getInt(USER_ID_FIELD));
			user.setRole(UserRole.valueOf(rs.getString(USER_ROLE_FIELD).toUpperCase()));
			user.setLogin(rs.getString(USER_LOGIN_FIELD));
			user.setPassword(rs.getString(USER_PASSWORD_FIELD));
			user.setEmail(rs.getString(USER_EMAIL_FIELD));
			user.setFirstName(rs.getString(USER_FIRSTNAME_FIELD));
			user.setLastName(rs.getString(USER_LASTNAME_FIELD));
			user.setPassportNumber(rs.getString(USER_PASSPOR_TNUMBER_FIELD));
			user.setRegDate(rs.getDate(USER_REGDATE_FIELD));
			user.setMonthlyDataUsage(rs.getLong(USER_SET_MONTHLY_DATA_USAGE_FIELD));
			user.setTotalDataUsage(rs.getLong(USER_TOTAL_DATA_USAGE_FIELD));
			user.setAccountBallance(rs.getBigDecimal(USER_ACCOUNT_BALLANCE_FIELD));
			user.setTariffId(rs.getInt(USER_TARIFF_FIELD));

			list.add ((T) user);
		}

	}


}
