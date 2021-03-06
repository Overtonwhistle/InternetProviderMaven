package by.epam.internetprovider.controller.command;

/**
 * Contains {@code enum} constants, used in "Command" application pattern.
 * 
 * @author Pavel Sorokoletov
 */
public enum CommandName {
	WRONG_COMMAND,

	LOG_IN, LOG_OFF, REGISTER, LOCALIZATION, GOTO_TARIFF_INFO, GOTO_REGISTER_DONE, GOTO_TARIFFS,
	
	NEXT_RESULT_PAGE, PREVIOUS_RESULT_PAGE,

	GOTO_CLIENT, GOTO_CLIENT_PAYMENTS, GOTO_CLIENT_REQUESTS, GOTO_CLIENT_EDIT_PROFILE, GOTO_CLIENT_MAKE_PAYMENT, GOTO_CLIENT_DELETE_REQUEST, GOTO_CLIENT_CHANGE_TARIFF,

	CLIENT_MAKE_PAYMENT_PROCESS, CLIENT_DELETE_REQUEST_PROCESS, CLIENT_REQUEST_TARIFF_PROCESS, CLIENT_EDIT_PROFILE_PROCESS,

	GOTO_ADMIN, GOTO_AD_USERS, GOTO_AD_TARIFFS, GOTO_AD_REQUESTS, GOTO_AD_PAYMENTS,

	GOTO_AD_EDIT_USER, GOTO_AD_BAN_USER, GOTO_AD_DELETE_USER, GOTO_AD_EDIT_TARIFF, GOTO_AD_ADD_TARIFF, GOTO_AD_DELETE_TARIFF, GOTO_AD_PROCESS_REQUEST, GOTO_AD_DELETE_REQUEST,

	ADMIN_EDIT_USER_PROCESS, REG_PROCESS, ADMIN_BAN_USER_PROCESS, ADMIN_UNBAN_USER_PROCESS, ADMIN_DEL_USER_PROCESS, ADMIN_EDIT_TARIFF_PROCESS, ADMIN_ADD_TARIFF_PROCESS, ADMIN_DEL_TARIFF_PROCESS, ADMIN_DEL_REQUEST_PROCESS, ADMIN_DO_REQUEST_PROCESS
}
