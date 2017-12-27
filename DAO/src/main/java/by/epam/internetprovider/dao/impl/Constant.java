package by.epam.internetprovider.dao.impl;

public class Constant {

	protected static final int FIRST_INDEX = 0;
	protected static final int USER_ROLE_FIELD = 1;
	protected static final int USER_LOGIN_FIELD = 2;
	protected static final int USER_PASSWORD_FIELD = 3;
	protected static final int USER_EMAIL_FIELD = 4;
	protected static final int USER_FIRSTNAME_FIELD = 5;
	protected static final int USER_LASTNAME_FIELD = 6;
	protected static final int USER_PASSPOR_TNUMBER_FIELD = 7;
	protected static final int USER_REGDATE_FIELD = 8;
	protected static final int USER_SET_MONTHLY_DATA_USAGE_FIELD = 9;
	protected static final int USER_TOTAL_DATA_USAGE_FIELD = 10;
	protected static final int USER_ACCOUNT_BALLANCE_FIELD = 11;
	protected static final int USER_TARIFF_FIELD = 12;

	protected static final int TARIFF_TITLE_FIELD = 1;
	protected static final int TARIFF_COST_FIELD = 2;
	protected static final int TARIFF_LIMIT_FIELD = 3;
	protected static final int TARIFF_IS_UNLIM_FIELD = 4;
	protected static final int TARIFF_OVERLOAD_COST_FIELD = 5;
	protected static final int TARIFF_DESCRIPTION_FIELD = 6;
	protected static final int TARIFF_TECH_ID_FIELD = 7;

	protected static final int REQUEST_DATE_FIELD = 1;
	protected static final int REQUEST_CLIENT_ID_FIELD = 2;
	protected static final int REQUEST_TARIFF_ID_FIELD = 3;

	protected static final String USER_CHANGE_TARIFF_QUERY = "UPDATE `internet_provider`.`user` "
			+ "SET `tariff_t_id`=? WHERE `u_id`=?";
	protected static final int USER_CHANGE_TARIFF_QUERY_TARIFF_ID_FIELD = 1;
	protected static final int USER_CHANGE_TARIFF_QUERY_USER_ID_FIELD = 2;

	protected static final String ADD_USER_QUERY = "INSERT INTO `internet_provider`.`user` (`u_role`, `u_login`, `u_password`,"
			+ " `u_email`, `u_first_name`, `u_last_name`, `u_passport_number`, `u_reg_date`,"
			+ " `u_monthly_data_usage`, `u_total_data_usage`, `u_account_ballance`, `tariff_t_id`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	protected static final String EDIT_USER_QUERY = "UPDATE `internet_provider`.`user` SET `u_role`=?,"
			+ " `u_login`=?, `u_password`=?, `u_email`=?, `u_first_name`=?,"
			+ " `u_last_name`=?, `u_passport_number`=?, `u_reg_date`=?,"
			+ " `u_monthly_data_usage`=?, `u_total_data_usage`=?, `u_account_ballance`=?,"
			+ " `tariff_t_id`=? WHERE `u_id`=";

	protected static final String DELETE_USER_QUERY = "DELETE FROM `internet_provider`.`user` WHERE `u_id`=";

	protected static final String BAN_USER_QUERY = "INSERT INTO `internet_provider`.`ban` (`user_u_id`, `ban_reason_br_id`,"
			+ " `b_set_date`, `b_comment`) VALUES (?, ?, ?, ?)";
	protected static final int BAN_USER_QUERY_USER_ID_FIELD = 1;
	protected static final int BAN_USER_QUERY_BAN_REASON_FIELD = 2;
	protected static final int BAN_USER_QUERY_BAN_DATETIME_FIELD = 3;
	protected static final int BAN_USER_QUERY_COMMENT_FIELD = 4;
	protected static final String UNBAN_USER_QUERY = "UPDATE `internet_provider`.`ban` SET `b_reset_date`=? "
			+ "WHERE `user_u_id`= ? AND ISNULL(b_reset_date)";
	protected static final int UNBAN_USER_QUERY_RESET_DATETIME_FIELD = 1;
	protected static final int UNBAN_USER_QUERY_USER_ID_FIELD = 2;

	protected static final String USERS_TO_BAN_QUERY = "SELECT * FROM user WHERE u_account_ballance < 0"
			+ " AND user.u_id NOT IN (SELECT user_u_id FROM ban)";

	protected static final String EDIT_TARIFF_QUERY = "UPDATE `internet_provider`.`tariff` SET `t_title`=?,"
			+ " `t_monthly_cost`=?, `t_monthly_data_limit`=?, `t_is_unlim_traffic`=?, `t_overload_limit_cost`=?,"
			+ " `t_description`=?, `technology_tech_id`=? WHERE `t_id`=";
	protected static final String DELETE_TARIFF_QUERY = "DELETE FROM `internet_provider`.`tariff` WHERE t_id=";

	protected static final String ADD_TARIFF_QUERY = "INSERT INTO `internet_provider`.`tariff` "
			+ "(`t_title`, `t_monthly_cost`, `t_monthly_data_limit`, `t_is_unlim_traffic`, "
			+ "`t_overload_limit_cost`, `t_description`, `technology_tech_id`) VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?);";
	protected static final String ADD_REQUEST_QUERY = "INSERT INTO `internet_provider`.`request`"
			+ " (`r_request_date`, `user_u_id`," + " `tariff_t_id`) VALUES (?, ?, ?);";

	protected static final String COMPLETE_REQUEST_QUERY = "UPDATE `internet_provider`.`request`"
			+ " SET `r_processed_date`=?, `r_processed_by`=? WHERE `r_id`=?;";
	protected static final int COMPLETE_REQUEST_QUERY_DATE_FIELD = 1;
	protected static final int COMPLETE_REQUEST_QUERY_ADMIN_ID_FIELD = 2;
	protected static final int COMPLETE_REQUEST_QUERY_REQUEST_ID_FIELD = 3;
	protected static final String DELETE_REQUEST_QUERY = "DELETE FROM `internet_provider`.`request` WHERE `r_id`=";

	protected static final String ADD_PAYMENT_QUERY = "INSERT INTO `internet_provider`.`payment` "
			+ "(`p_payment_date`, `p_amount`, `user_u_id`) VALUES (?, ?, ?)";
	protected static final int ADD_PAYMENT_DATE_FIELD = 1;
	protected static final int ADD_PAYMENT_AMOUNT_FIELD = 2;
	protected static final int ADD_PAYMENT_USER_ID = 3;

	protected static final String EDIT_CLIENT_BALLANCE_QUERY = "UPDATE `internet_provider`.`user` "
			+ "SET `u_account_ballance`=? WHERE `u_id`=?";
	protected static final int EDIT_CLIENT_BALLANCE_QUERY_AMOUNT_FIELD = 1;
	protected static final int EDIT_CLIENT_BALLANCE_QUERY_USER_ID_FIELD = 2;

}
