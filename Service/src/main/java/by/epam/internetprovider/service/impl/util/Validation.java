package by.epam.internetprovider.service.impl.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.internetprovider.bean.data_object.TariffData;
import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.dao.IUserDAO;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.factory.DAOFactory;
import by.epam.internetprovider.dao.searchfilter.FilterParameter;
import by.epam.internetprovider.dao.searchfilter.SubFilter;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserSearchFilter;
import by.epam.internetprovider.service.exception.ServiceException;

public class Validation {
	private final static String NAME_REGEXP = "\\S+";
	private final static String PASSPORT_REGEXP = "^([0-9]|[A-Z]){12,20}$";
	private final static String EMAIL_REGEXP = "^[\\w]+[.]?[\\w]+[@][\\w]+[.][a-zA-Z]{2,4}$";
	private final static String LOGIN_REGEXP = "^[a-zA-z](\\w){4,}$";
	private final static String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
	private final static String TARIFF_TITLE_REGEXP = "^[a-zA-z]+?.*$";

	private final static int MAX_TARIFF_COST = 99;
	private final static int MAX_BALLANCE = 999;

	private static final String LIMITED_TARIFF_FIELD = "no";
	private final static String LOCAL_ERRORS_BASENAME = "local_errors";
	private final static String ERROR_WRONG_PASSWORDS = "edit_user.wrong_passwords";
	private final static String ERROR_WRONG_PASSWORD = "edit_user.wrong_password";
	private final static String ERROR_WRONG_FNAME = "edit_user.wrong_fname";
	private final static String ERROR_WRONG_LNAME = "edit_user.wrong_lname";
	private final static String ERROR_WRONG_PASSPORT = "edit_user.wrong_passport";
	private final static String ERROR_WRONG_EMAIL = "edit_user.wrong_email";
	private final static String ERROR_WRONG_LOGIN = "edit_user.wrong_login";
	private final static String ERROR_LOGIN_BUSY = "edit_user.login_busy";
	private final static String ERROR_WRONG_BALLANCE = "edit_user.wrong_ballance";
	private final static String ERROR_WRONG_DATA = "edit_user.wrong_data";
	private final static String ERROR_WRONG_TITLE = "add_tariff.wrong_title";
	private final static String ERROR_WRONG_COST = "add_tariff.wrong_cost";
	private final static String ERROR_WRONG_LIMIT = "add_tariff.wrong_limit";
	private final static String ERROR_WRONG_OVERCOST = "add_tariff.wrong_overcost";

	static DAOFactory factory = DAOFactory.getIstance();
	static IUserDAO userDAO = factory.getUserDAO();

	public static boolean checkName(String name) {
		return checkToRegexp(name, NAME_REGEXP);
	}

	public static boolean checkPassport(String passport) {
		return checkToRegexp(passport, PASSPORT_REGEXP);
	}

	public static boolean checkEmail(String email) {
		return checkToRegexp(email, EMAIL_REGEXP);
	}

	public static boolean checkLogin(String login) {
		return checkToRegexp(login, LOGIN_REGEXP);
	}

	public static boolean checkPassword(String password) {
		return checkToRegexp(password, PASSWORD_REGEXP);
	}

	public static boolean checkPasswords(String pwd1, String pwd2) {

		if (pwd1 == null || pwd2 == null) {
			return false;
		}

		return pwd1.equals(pwd2);
	}

	public static boolean checkUsageData(long month, long total) {

		if ((month < 0) || (total < 0) || (month > total)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkBallance(BigDecimal ballance) {

		if ((ballance == null) || (ballance.doubleValue() < -MAX_BALLANCE)
				|| (ballance.doubleValue() > MAX_BALLANCE)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkTariffTittle(String title) {
		return checkToRegexp(title, TARIFF_TITLE_REGEXP);
	}

	public static boolean checkTariffCost(BigDecimal monthlyCost) {

		if ((monthlyCost == null) || (monthlyCost.doubleValue() <= 0)
				|| (monthlyCost.doubleValue() > MAX_TARIFF_COST)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkTariffDataLimit(long monthlyDataLimit, boolean isUnlim) {

		if ((monthlyDataLimit <= 0 && isUnlim == false)
				|| (monthlyDataLimit > 0 && isUnlim == true)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkOverloadCost(BigDecimal overloadLimitCost, boolean isUnlim) {

		if (overloadLimitCost == null) {
			return false;
		}

		if (overloadLimitCost.doubleValue() <= 0 && !isUnlim) {
			return false;
		} else {
			return true;
		}
	}

	public static List<String> newUserDataValidation(UserData userData) throws ServiceException {

		ResourceBundle errorMessageResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		List<String> errors = new ArrayList<>();

		if (!checkPasswords(userData.getPassword(), userData.getRepeatePassword())) {
			errors.add(errorMessageResource.getString(ERROR_WRONG_PASSWORDS));
		}


		errors.addAll(editClientProfileValidation(userData));

		if (!checkLogin(userData.getLogin())) {
			errors.add(errorMessageResource.getString(ERROR_WRONG_LOGIN));
		}
		else if (isLoginBusy(userData.getLogin())) {
			errors.add(errorMessageResource.getString(ERROR_LOGIN_BUSY));
		}

		return errors;

	}

	public static List<String> editUserDataValidation(UserData userData) throws ServiceException {

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();

		errors.addAll(editClientProfileValidation(userData));

		try {
			BigDecimal ballance = new BigDecimal(userData.getAccountBallance());
			if (!Validation.checkBallance(ballance)) {
				errors.add(errorsResource.getString(ERROR_WRONG_BALLANCE));
			}
		} catch (java.lang.NumberFormatException e) {
			errors.add(errorsResource.getString(ERROR_WRONG_BALLANCE));
		}

		try {
			Long monthlyUsage = Long.parseLong(userData.getMonthlyDataUsage());
			Long totalUsage = Long.parseLong(userData.getTotalDataUsage());

			if (!Validation.checkUsageData(monthlyUsage, totalUsage)) {
				errors.add(errorsResource.getString(ERROR_WRONG_DATA));
			}
		} catch (java.lang.NumberFormatException e) {
			errors.add(errorsResource.getString(ERROR_WRONG_DATA));
		}

		return errors;

	}

	public static List<String> editClientProfileValidation(UserData userData)
			throws ServiceException {

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();

		if (!checkName(userData.getFirstName())) {
			errors.add(errorsResource.getString(ERROR_WRONG_FNAME));
		}
		if (!checkName(userData.getLastName())) {
			errors.add(errorsResource.getString(ERROR_WRONG_LNAME));
		}
		if (!checkPassport(userData.getPassportNumber())) {
			errors.add(errorsResource.getString(ERROR_WRONG_PASSPORT));
		}
		if (!checkEmail(userData.getEmail())) {
			errors.add(errorsResource.getString(ERROR_WRONG_EMAIL));
		}
		if (!checkPassword(userData.getPassword())) {
			errors.add(errorsResource.getString(ERROR_WRONG_PASSWORD));
		}

		return errors;

	}

	public static List<String> tariffDataValidation(TariffData tariffData) {

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();

		if (!Validation.checkTariffTittle(tariffData.getTitle())) {
			errors.add(errorsResource.getString(ERROR_WRONG_TITLE));
		}

		try {
			BigDecimal cost = new BigDecimal(tariffData.getMonthlyCost());
			if (!Validation.checkTariffCost(cost)) {
				errors.add(errorsResource.getString(ERROR_WRONG_COST));
			}
		} catch (java.lang.NumberFormatException e) {
			errors.add(errorsResource.getString(ERROR_WRONG_COST));
		}

		if (tariffData.getUnlimTraffic().equals(LIMITED_TARIFF_FIELD)) {
			try {
				Long limit = Long.parseLong(tariffData.getMonthlyDataLimit());
				if (!Validation.checkTariffDataLimit(limit, false)) {
					errors.add(errorsResource.getString(ERROR_WRONG_LIMIT));
				}
			} catch (java.lang.NumberFormatException e) {
				errors.add(errorsResource.getString(ERROR_WRONG_LIMIT));
			}
			try {
				BigDecimal overloadCost = new BigDecimal(tariffData.getOverloadLimitCost());
				if (!Validation.checkOverloadCost(overloadCost, false)) {
					errors.add(errorsResource.getString(ERROR_WRONG_OVERCOST));
				}
			} catch (java.lang.NumberFormatException e) {
				errors.add(errorsResource.getString(ERROR_WRONG_OVERCOST));
			}
		}

		return errors;
	}

	private static boolean checkToRegexp(String value, String regexp) {
		
		if (value == null) {
			return false;
		}
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(value);
		return m.matches();
	}

	private static boolean isLoginBusy(String login) throws ServiceException {

		UserSearchFilter filter = new UserSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.USER_LOGIN, login));

		try {
			if (userDAO.getUsersList(filter).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (DAOException e) {
			throw new ServiceException("Error in isLoginBusy()", e);
		}

	}

}
