package by.epam.internetprovider.dao.searchfilter;

/**
 * Contains constants for filter parameters, are the names of the parameters to search for.
 * 
 * @author Pavel Sorokoletov
 */

public enum FilterParameter { 

	TECHNOLOGY_ID, TECHNOLOGY_TITLE, TECHNOLOGY_EQUIPMENT,

	PAYMENT_ID, PAYMENT_AMOUNT, PAYMENT_USER_ID, PAYMENT_DATE, PAYMENT_FIRST_NAME, PAYMENT_LAST_NAME, PAYMENT_BY_DATE, PAYMENT_BY_USER, PAYMENT_BY_AMOUNT,

	REQUEST_ID, REQUEST_DATE, REQUEST_PROC_DATE, REQUEST_ACTIVE, REQUEST_PROCESSED, REQUEST_PROC_BY, REQUEST_USER_ID, REQUEST_TARIFF_ID, REQUEST_BY_DATE, REQUEST_BY_USER, REQUEST_BY_TARIFF,

	TARIFF_ID, TARIFF_NEED_EQUIPMENT, TARIFF_NO_EQUIPMENT, TARIFF_UNLIMITED, TARIFF_LIMITED, TARIFF_MONTHLY_COST, TARIFF_MONTHLY_DATA, TARIFF_NOT_USED, TARIFF_USED, TARIFF_OVER_COST, TARIFF_TECHNOLOGY_ID, TARIFF_TITLE,

	BAN_ID, BAN_SET_DATE, BAN_RESET_DATE, BAN_ACTIVE, BAN_BAN_REASON_ID, BAN_USER_ID,

	BAN_REASON_ID, BAN_REASON_TITLE,

	USER_ID, USER_ROLE_ADMIN, USER_ROLE_CLIENT, USER_LOGIN, USER_PASSWORD, USER_EMAIL, USER_FIRST_NAME, USER_LAST_NAME, USER_PASSPORT, USER_REG_DATE, USER_MONTH_DATA, USER_TOTAL_DATA, USER_BALLANCE, USER_BANNED, USER_NOT_BANNED, USER_TARIFF_ID
}