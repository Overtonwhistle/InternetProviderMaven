package by.epam.internetprovider.service.impl.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.internetprovider.bean.builder.TariffBuilder;
import by.epam.internetprovider.bean.builder.UserBuilder;
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

	private final static String LOCAL_ERRORS_BASENAME = "local_errors";

	static DAOFactory factory = DAOFactory.getIstance();
	static IUserDAO userDAO = factory.getUserDAO();

	public static boolean checkName(String name) {

		if (name == null) {
			return false;
		}

		Pattern p = Pattern.compile(NAME_REGEXP);
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public static boolean checkPassport(String passport) {

		if (passport == null) {
			return false;
		}

		Pattern p = Pattern.compile(PASSPORT_REGEXP);
		Matcher m = p.matcher(passport);
		return m.matches();
	}

	public static boolean checkEmail(String email) {

		if (email == null) {
			return false;
		}

		Pattern p = Pattern.compile(EMAIL_REGEXP);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean checkLogin(String login) {

		if (login == null) {
			return false;
		}

		Pattern p = Pattern.compile(LOGIN_REGEXP);
		Matcher m = p.matcher(login);
		return m.matches();
	}

	public static boolean checkPassword(String password) {

		if (password == null) {
			return false;
		}

		Pattern p = Pattern.compile(PASSWORD_REGEXP);
		Matcher m = p.matcher(password);
		return m.matches();
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

		if ((ballance == null) || (ballance.doubleValue() < -999)
				|| (ballance.doubleValue() > 999)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkTariffTittle(String title) {

		if (title == null) {
			return false;
		}

		Pattern p = Pattern.compile(TARIFF_TITLE_REGEXP);
		Matcher m = p.matcher(title);
		return m.matches();
	}

	public static boolean checkTariffCost(BigDecimal monthlyCost) {

		if ((monthlyCost == null) || (monthlyCost.doubleValue() <= 0)
				|| (monthlyCost.doubleValue() > 99)) {
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

	public static boolean checkOverload(BigDecimal overloadLimitCost, boolean isUnlim) {

		if (overloadLimitCost == null) {
			return false;
		}

		if (overloadLimitCost.doubleValue() != 0 && isUnlim == true) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkOverloadCost(BigDecimal overloadLimitCost, boolean isUnlim) {

		if (overloadLimitCost == null) {
			return false;
		}

		if (overloadLimitCost.doubleValue() <= 0 && isUnlim == false) {
			return false;
		} else {
			return true;
		}
	}

	public static List<String> newUserBuilderValidation(UserBuilder userBuilder)
			throws ServiceException {

		ResourceBundle errorMessageResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		List<String> errors = new ArrayList<>();

		if (!checkPasswords(userBuilder.getPassword(), userBuilder.getRepeatePassword())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_passwords"));
		}

		if (!checkName(userBuilder.getFirstName())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_fname"));
		}

		if (!checkName(userBuilder.getLastName())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_lname"));
		}

		if (!checkPassport(userBuilder.getPassportNumber())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_passport"));
		}

		if (!checkEmail(userBuilder.getEmail())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_email"));
		}

		if (!checkLogin(userBuilder.getLogin())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_login"));
		}

		if (!checkPassword(userBuilder.getPassword())) {
			errors.add(errorMessageResource.getString("edit_user.wrong_password"));
		}

		if (isLoginBusy(userBuilder.getLogin())) {
			errors.add(errorMessageResource.getString("edit_user.login_busy"));
		}

		return errors;

	}

	public static List<String> editUserBuilderValidation(UserBuilder userBuilder)
			throws ServiceException {

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();

		if (!checkName(userBuilder.getFirstName())) {
			errors.add(errorsResource.getString("edit_user.wrong_fname"));
		}

		if (!checkName(userBuilder.getLastName())) {
			errors.add(errorsResource.getString("edit_user.wrong_lname"));
		}

		if (!checkPassport(userBuilder.getPassportNumber())) {
			errors.add(errorsResource.getString("edit_user.wrong_passport"));
		}

		if (!checkEmail(userBuilder.getEmail())) {
			errors.add(errorsResource.getString("edit_user.wrong_email"));
		}

		if (!checkLogin(userBuilder.getLogin())) {
			errors.add(errorsResource.getString("edit_user.wrong_login"));
		}

		if (!checkPassword(userBuilder.getPassword())) {
			errors.add(errorsResource.getString("edit_user.wrong_password"));
		}

		try {
			BigDecimal ballance = new BigDecimal(userBuilder.getAccountBallance());
			if (!Validation.checkBallance(ballance)) {
				errors.add(errorsResource.getString("edit_user.wrong_ballance"));
			}
		} catch (java.lang.NumberFormatException e) {
			errors.add(errorsResource.getString("edit_user.wrong_ballance"));
		}

		try {
			Long monthlyUsage = Long.parseLong(userBuilder.getMonthlyDataUsage());
			Long totalUsage = Long.parseLong(userBuilder.getTotalDataUsage());

			if (!Validation.checkUsageData(monthlyUsage, totalUsage)) {
				errors.add(errorsResource.getString("edit_user.wrong_data"));
			}
		} catch (java.lang.NumberFormatException e) {
			errors.add(errorsResource.getString("edit_user.wrong_data"));
		}

		return errors;

	}

	public static List<String> editClientProfileValidation(UserBuilder userBuilder)
			throws ServiceException {

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();

		if (!checkName(userBuilder.getFirstName())) {
			errors.add(errorsResource.getString("edit_user.wrong_fname"));
		}

		if (!checkName(userBuilder.getLastName())) {
			errors.add(errorsResource.getString("edit_user.wrong_lname"));
		}

		if (!checkPassport(userBuilder.getPassportNumber())) {
			errors.add(errorsResource.getString("edit_user.wrong_passport"));
		}

		if (!checkEmail(userBuilder.getEmail())) {
			errors.add(errorsResource.getString("edit_user.wrong_email"));
		}

		if (!checkPassword(userBuilder.getPassword())) {
			errors.add(errorsResource.getString("edit_user.wrong_password"));
		}

		return errors;

	}

//	public static List<String> tariffValidation(Tariff tariff) throws ServiceException {
//
//		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
//		ArrayList<String> errors = new ArrayList<>();
//
//		if (!Validation.checkTariffTittle(tariff.getTitle())) {
//			errors.add(errorsResource.getString("add_tariff.wrong_title") + ", ");
//		}
//
//		if (!Validation.checkTariffCost(tariff.getMonthlyCost())) {
//			errors.add(errorsResource.getString("add_tariff.wrong_cost") + ", ");
//		}
//
//		if (!Validation.checkTariffDataLimit(tariff.getMonthlyDataLimit(),
//				tariff.isUnlimTraffic())) {
//			errors.add(errorsResource.getString("add_tariff.wrong_limit") + ", ");
//		}
//
//		if (!Validation.checkOverload(tariff.getOverloadLimitCost(), tariff.isUnlimTraffic())) {
//			errors.add(errorsResource.getString("add_tariff.wrong_overload") + ", ");
//		}
//
//		if (!Validation.checkOverloadCost(tariff.getOverloadLimitCost(), tariff.isUnlimTraffic())) {
//			errors.add(errorsResource.getString("add_tariff.wrong_overcost") + ", ");
//		}
//
//		return errors;
//
//	}

	public static List<String> tariffBuilderValidation(TariffBuilder tariffBuilder) {

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();

		if (!Validation.checkTariffTittle(tariffBuilder.getTitle())) {
			errors.add(errorsResource.getString("add_tariff.wrong_title"));
		}

		try {
			BigDecimal cost = new BigDecimal(tariffBuilder.getMonthlyCost());
			if (!Validation.checkTariffCost(cost)) {
				errors.add(errorsResource.getString("add_tariff.wrong_cost"));
			}
		} catch (java.lang.NumberFormatException e) {
			errors.add(errorsResource.getString("add_tariff.wrong_cost"));
		}

		if (tariffBuilder.getUnlimTraffic().equals("no")) {

			try {
				Long limit = Long.parseLong(tariffBuilder.getMonthlyDataLimit());
				if (!Validation.checkTariffDataLimit(limit, false)) {
					errors.add(errorsResource.getString("add_tariff.wrong_limit"));
				}
			} catch (java.lang.NumberFormatException e) {
				errors.add(errorsResource.getString("add_tariff.wrong_limit"));
			}

			try {
				BigDecimal overloadCost = new BigDecimal(tariffBuilder.getOverloadLimitCost());
				if (!Validation.checkOverloadCost(overloadCost, false)) {
					errors.add(errorsResource.getString("add_tariff.wrong_overcost"));
				}
			} catch (java.lang.NumberFormatException e) {
				errors.add(errorsResource.getString("add_tariff.wrong_overcost"));
			}

		}
		return errors;
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
