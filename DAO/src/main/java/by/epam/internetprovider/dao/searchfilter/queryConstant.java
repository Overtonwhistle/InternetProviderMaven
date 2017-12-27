package by.epam.internetprovider.dao.searchfilter;

/**
 * Contains string constants for SQL-based queries. Used for SearchFilter
 * mechanism in SQL-DB realisation.
 * 
 * @author Pavel Sorokoletov
 */

public class queryConstant {

	public static final String SQL_WHERE = " where ";
	public static final int SQL_WHERE_LENGTH = SQL_WHERE.length();
	public static final String SQL_AND = " and ";
	public static final int SQL_AND_LENGTH = SQL_AND.length();
	public static final String CLOSING_PARENTHESIS = ")";

	public static final String FIRST_NAME_SUBQUERY = "first_name";
	public static final String LAST_NAME_SUBQUERY = "last_name";

	public static final String FIRST_NAME_ADDITIONAL_QUERY = "user_u_id in "
			+ "(select u_id from internet_provider.user where u_first_name ";
	public static final String LAST_NAME_ADDITIONAL_QUERY = "user_u_id in "
			+ "(select u_id from internet_provider.user where u_last_name ";

	public static final String GET_TECHNOLOGIES_QUERY = "select * from `internet_provider`.`technology`";
	public static final String TECH_ID_SUBFILTER = "tech_id";
	public static final String TECH_TITLE_SUBFILTER = "tech_title";
	public static final String TECH_NEED_EQUIP_SUBFILTER = "tech_rent_equipment";

	public static final String GET_PAYMENTS_QUERY = "select * from `internet_provider`.`payment`";
	public static final String PAYMENT_ID_SUBFILTER = "p_id";
	public static final String PAYMENT_AMOUNT_SUBFILTER = "p_amount";
	public static final String PAYMENT_USER_ID_SUBFILTER = "user_u_id";
	public static final String PAYMENT_DATE_SUBFILTER = "p_payment_date";
	public static final String PAYMENT_BYDATE_SUBQUERY = " order by p_payment_date";
	public static final String PAYMENT_BYUSER_SUBQUERY = " order by user_u_id";
	public static final String PAYMENT_BYAMOUNT_SUBQUERY = " order by p_amount";

	public static final String GET_REQUESTS_QUERY = "select * from `internet_provider`.`request`";
	public static final String REQUEST_ID_SUBFILTER = "r_id";
	public static final String REQUEST_DATE_SUBFILTER = "r_request_date";
	public static final String REQUEST_PROC_DATE_SUBFILTER = "r_processed_date";
	public static final String REQUEST_ACTIVE_SUBQUERY = "ISNULL(r_processed_date)";
	public static final String REQUEST_PROCESSED_SUBQUERY = "NOT ISNULL(r_processed_date)";
	public static final String REQUEST_PROCESSED_BY_SUBFILTER = "r_processed_by";
	public static final String REQUEST_USER_ID_SUBFILTER = "user_u_id";
	public static final String REQUEST_TARIFF_ID_SUBFILTER = "tariff_t_id";
	public static final String REQUEST_BY_DATE_SUBQUERY = "order by r_request_date";
	public static final String REQUEST_BY_USER_SUBQUERY = "order by user_u_id";
	public static final String REQUEST_BY_TARIFF_SUBQUERY = "order by tariff_t_id";

	public static final String GET_TARIFFS_QUERY = "select * from `internet_provider`.`tariff`";
	public static final String TARIFF_ID_SUBFILTER = "t_id";
	public static final String TARIFF_TITLE_SUBFILTER = "t_title";
	public static final String TARIFF_MCOST_SUBFILTER = "t_monthly_cost";
	public static final String TARIFF_MDATA_SUBFILTER = "t_monthly_data_limit";
	public static final String TARIFF_IS_UNLIM_SUBFILTER = "t_is_unlim_traffic";
	public static final String TARIFF_UNLIM_SUBFILTER = "t_is_unlim_traffic=1";
	public static final String TARIFF_LIMITED_SUBFILTER = "t_is_unlim_traffic=0";
	public static final String TARIFF_OVER_COST_SUBFILTER = "t_overload_limit_cost";
	public static final String TARIFF_TECH_ID_SUBFILTER = "technology_tech_id";
	public static final String NOT_USED_TARIFF_SUBQUERY = "t_id not in (select t_id from tariff, "
			+ "user where user.tariff_t_id=tariff.t_id)";
	public static final String USED_TARIFF_SUBQUERY = "t_id in (select t_id from tariff, user "
			+ "where user.tariff_t_id=tariff.t_id)";
	public static final String NO_RENT_TARIFF_SUBQUERY = "technology_tech_id not in"
			+ " (SELECT tech_id FROM internet_provider.technology where tech_rent_equipment=1)";
	public static final String RENT_TARIFF_SUBQUERY = "technology_tech_id in"
			+ " (SELECT tech_id FROM internet_provider.technology where tech_rent_equipment=1)";

	public static final String GET_BANS_QUERY = "select * from `internet_provider`.`ban`";
	public static final String BAN_ID_SUBFILTER = "b_id";
	public static final String BAN_SETDATE_SUBFILTER = "b_set_date";
	public static final String BAN_RESETDATE_SUBFILTER = "b_reset_date";
	public static final String BAN_ACTIVE_SUBQUERY = "ISNULL(b_reset_date)";
	public static final String BAN_BAN_REASON_ID_SUBFILTER = "ban_reason_br_id";
	public static final String BAN_USER_ID_SUBFILTER = "user_u_id";

	public static final String GET_BAN_REASON_QUERY = "select * from `internet_provider`.`ban_reason`";
	public static final String BAN_REASON_ID_SUBFILTER = "br_id";
	public static final String BAN_REASON_TITLE_SUBFILTER = "br_title";

	public static final String GET_USERS_QUERY = "select * from `internet_provider`.`user`";
	public static final String USER_ID_SUBFILTER = "u_id";
	public static final String USER_ROLE_ADMIN_SUBQUERY = "u_role = 'admin'";
	public static final String USER_ROLE_CLIENT_SUBQUERY = "u_role = 'client'";
	public static final String USER_LOGIN_SUBFILTER = "u_login";
	public static final String USER_PASSWORD_SUBFILTER = "u_password";
	public static final String USER_EMAIL_SUBFILTER = "u_email";
	public static final String USER_FNAME_SUBFILTER = "u_first_name";
	public static final String USER_LNAME_SUBFILTER = "u_last_name";
	public static final String USER_PASSPORT_SUBFILTER = "u_passport_number";
	public static final String USER_REGDATE_SUBFILTER = "u_reg_date";
	public static final String USER_MDATA_SUBFILTER = "u_monthly_data_usage";
	public static final String USER_TDATA_SUBFILTER = "u_total_data_usage";
	public static final String USER_BALLANCE_SUBFILTER = "u_account_ballance";
	public static final String USER_BANNED_SUBQUERY = "u_id in (SELECT user_u_id FROM internet_provider.ban "
			+ "where ISNULL(b_reset_date))";
	public static final String USER_NOT_BANNED_SUBQUERY = "u_id not in (SELECT user_u_id FROM internet_provider.ban"
			+ " where ISNULL(b_reset_date))";
	public static final String USER_TARIFF_ID_SUBFILTER = "tariff_t_id";

}
