package by.epam.internetprovider.service.impl;

import static by.epam.internetprovider.service.impl.util.ServiceConstant.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import by.epam.internetprovider.bean.Ban;
import by.epam.internetprovider.bean.BanReason;
import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.Param.UserRole;
import by.epam.internetprovider.bean.data_object.TariffData;
import by.epam.internetprovider.bean.data_object.UserData;
import by.epam.internetprovider.dao.IBanDAO;
import by.epam.internetprovider.dao.IDataBaseDAO;
import by.epam.internetprovider.dao.IPaymentDAO;
import by.epam.internetprovider.dao.IRequestDAO;
import by.epam.internetprovider.dao.ITariffDAO;
import by.epam.internetprovider.dao.IUserDAO;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.exception.DAORequestNotFoundException;
import by.epam.internetprovider.dao.exception.DAOTariffNotFoundException;
import by.epam.internetprovider.dao.exception.DAOUserNotFoundException;
import by.epam.internetprovider.dao.factory.DAOFactory;
import by.epam.internetprovider.dao.searchfilter.FilterParameter;
import by.epam.internetprovider.dao.searchfilter.SubFilter;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.banreason.BanReasonSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologySearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserSearchFilter;
import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.exception.ServiceCloseDataSourceException;
import by.epam.internetprovider.service.exception.ServiceException;
import by.epam.internetprovider.service.exception.ServiceIncorrectIdException;
import by.epam.internetprovider.service.exception.ServiceOpenDataSourceException;
import by.epam.internetprovider.service.exception.ServiceRequestNotFoundException;
import by.epam.internetprovider.service.exception.ServiceTariffNotFoundException;
import by.epam.internetprovider.service.exception.ServiceUserNotFoundException;
import by.epam.internetprovider.service.impl.util.PasswordComputer;
import by.epam.internetprovider.service.impl.util.PasswordComputer.CannotPerformOperationException;
import by.epam.internetprovider.service.impl.util.PasswordComputer.InvalidHashException;
import by.epam.internetprovider.service.impl.util.Validation;

public class InternetProviderService implements IInternetProviderService {

	private static final String NO_ERRORS = "";
	private static final String LOCAL_ERRORS_BASENAME = "local_errors";
	private static final String ERROR_TARIFF_IS_USED = "add_request.user_already_has_tariff";
	private static final String ERROR_TARIFF_IS_DELETED = "delete_request.already_processed";
	private static final String ERROR_TARIFF_TITLE_IS_BUSY = "add_tariff.title_busy";
	private static final String ERROR_WRONG_CURR_PASSWORD = "edit_user.wrong_current_passwords";
	private static final String ERROR_WRONG_AMOUNT = "make_payment.wront_amount";
	private static final String ERROR_NEG_AMOUNT = "make_payment.negative_amount";

	private static final String LIMITED_TARIFF_FIELD = "no";
	private static final int INITIAL_MONTHLY_DATA = 0;
	private static final int INITIAL_TOTAL_DATA = 0;
	private static final int MONTHLY_DATA_FOR_UNLIM = 0;
	private static final int OVERLOAD_COST_FOR_UNLIM = 0;
	private static final int ZERO_BALLANCE_VALUE = 0;
	private static final BigDecimal INITIAL_ACCOUNT_BALLANCE = new BigDecimal("0");
	private static final int INITIAL_TARIFF_ID = 0;
	private static final int FIRST_LIST_INDEX = 0;
	private static final int FIRST_REAL_DB_INDEX = 1;

	DAOFactory factory = DAOFactory.getIstance();
	IDataBaseDAO dataBaseDAO = factory.getInternetProviderDAO();
	IUserDAO userDAO = factory.getUserDAO();
	ITariffDAO tariffDAO = factory.getTariffDAO();
	IRequestDAO requestDAO = factory.getRequestDAO();
	IBanDAO banDAO = factory.getBanDAO();
	IPaymentDAO paymentDAO = factory.getPaymentDAO();

	@Override
	public void openDataSource() throws ServiceOpenDataSourceException {

		try {
			dataBaseDAO.initDAO();
		} catch (DAOException e) {
			throw new ServiceOpenDataSourceException("Failed to open data source.", e);
		}

	}

	@Override
	public void CloseDataSource() throws ServiceCloseDataSourceException {

		try {
			dataBaseDAO.dismissDAO();
		} catch (DAOException e) {
			throw new ServiceCloseDataSourceException("Failed to close data source.", e);
		}

	}

	@Override
	public User getUserByLogin(String login, String password) throws ServiceException {

		checkForNull(login);
		checkForNull(password);

		User user;

		try {
			user = userDAO.getUserByLogin(login);
		} catch (DAOUserNotFoundException e) {
			throw new ServiceUserNotFoundException("User with login " + login + " not found.", e);
		} catch (DAOException e) {
			throw new ServiceException("Data source error in getUserByLogin()", e);
		}

		boolean isPasswordCorrect = false;

		try {
			isPasswordCorrect = PasswordComputer.verifyPassword(password, user.getPassword());
		} catch (CannotPerformOperationException | InvalidHashException e) {
			throw new ServiceException("Service error in getUserByLogin()", e);
		}

		if (isPasswordCorrect) {
			return user;
		} else {
			throw new ServiceUserNotFoundException(
					"User with login " + login + ":" + password + " not found.");
		}
	}

	@Override
	public User getUserById(int userId) throws ServiceUserNotFoundException, ServiceException {

		checkId(userId);

		try {
			return userDAO.getUserById(userId);
		} catch (DAOUserNotFoundException e) {
			throw new ServiceUserNotFoundException("User:" + userId + " not found.", e);
		} catch (DAOException e) {
			throw new ServiceException("Data source error in getUserById()", e);
		}
	}

	@Override
	public List<String> createUser(UserData userData) throws ServiceException {

		checkForNull(userData);

		List<String> errors = Validation.newUserDataValidation(userData);

		if (errors.isEmpty()) {
			User user = new User();
			user.setRole(UserRole.CLIENT);
			user.setRegDate(new java.sql.Date(System.currentTimeMillis()));
			user.setMonthlyDataUsage(INITIAL_MONTHLY_DATA);
			user.setTotalDataUsage(INITIAL_TOTAL_DATA);
			user.setAccountBallance(INITIAL_ACCOUNT_BALLANCE);
			user.setTariffId(INITIAL_TARIFF_ID);

			try {
				setUserData(user, userData);
				userDAO.addUser(user);
			} catch (DAOException | CannotPerformOperationException e) {
				throw new ServiceException("Error in registerUser()", e);
			}
		}
		return errors;
	}

	@Override
	public List<String> editUser(UserData userData, int userId) throws ServiceException {

		checkId(userId);
		checkForNull(userData);

		ArrayList<String> errors = (ArrayList<String>) Validation.editUserDataValidation(userData);

		if (errors.isEmpty()) {
			User user = new User();
			try {
				setUserData(user, userData);
				userDAO.editUser(userId, user);
			} catch (DAOException | CannotPerformOperationException e) {
				throw new ServiceException("Error in editUser()", e);
			}

		}
		return errors;
	}

	@Override
	public List<String> editClientProfile(UserData userData, int userId) throws ServiceException {

		checkId(userId);
		checkForNull(userData);
		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		ArrayList<String> errors = (ArrayList<String>) Validation
				.editClientProfileValidation(userData);

		User user = getUserById(userId);

		try {
			if (!PasswordComputer.verifyPassword(userData.getCurrentPassword(),
					user.getPassword())) {
				errors.add(errorsResource.getString(ERROR_WRONG_CURR_PASSWORD));
			}
		} catch (CannotPerformOperationException | InvalidHashException e1) {
			throw new ServiceException("Error in editClientProfile()", e1);
		}

		if (errors.isEmpty()) {

			user.setFirstName(userData.getFirstName());
			user.setLastName(userData.getLastName());
			user.setPassportNumber(userData.getPassportNumber());
			user.setEmail(userData.getEmail());

			if (userData.getPassword() != null) {
				try {
					user.setPassword(PasswordComputer.createHash(userData.getPassword()));
				} catch (CannotPerformOperationException e) {
					throw new ServiceException("Error in editClientProfile()", e);
				}
			}

			try {
				userDAO.editUser(userId, user);
			} catch (DAOException e) {
				throw new ServiceException("Error in editClientProfile()", e);
			}
		}
		return errors;
	}

	@Override
	public boolean isUserBlocked(int userId) throws ServiceException {

		checkId(userId);

		UserSearchFilter filter = new UserSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.USER_ID, userId));
		filter.addSubFilter(new SubFilter(FilterParameter.USER_BANNED));

		try {
			if (userDAO.getUsersList(filter).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (DAOException e) {
			throw new ServiceException("Error in isUserBanned()", e);
		}

	}

	@Override
	public Ban getUserBan(int userId) throws ServiceException {

		checkId(userId);

		Ban ban = new Ban();

		BanSearchFilter filter = new BanSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.BAN_USER_ID, userId));
		filter.addSubFilter(new SubFilter(FilterParameter.BAN_ACTIVE));

		ban = getBansList(filter).get(FIRST_LIST_INDEX);

		return ban;
	}

	@Override
	public void banUser(int userId, int banReason, String comment) throws ServiceException {

		checkId(userId);

		try {
			banDAO.banUser(userId, banReason, new java.sql.Timestamp(System.currentTimeMillis()),
					comment);
		} catch (DAOException e) {
			throw new ServiceException("Error in banUser()", e);
		}

	}

	@Override
	public void unbanUser(int userId) throws ServiceException {

		checkId(userId);

		try {
			banDAO.unbanUser(userId, new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (DAOException e) {
			throw new ServiceException("Error in unbanUser()", e);
		}

	}

	@Override
	public void deleteUser(int userId) throws ServiceException {

		checkId(userId);

		try {
			userDAO.deleteUserById(userId);
		} catch (DAOException e) {
			throw new ServiceException("Error in deleteUser()", e);
		}
	}

	@Override
	public List<User> getUsersList(UserSearchFilter filter) throws ServiceException {

		checkForNull(filter);

		try {
			return userDAO.getUsersList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getUsersList()", e);
		}
	}

	@Override
	public List<User> getUsersList(Map<String, String> parameters) throws ServiceException {

		checkForNull(parameters);

		UserSearchFilter searchFilter = new UserSearchFilter();

		if (parameters.containsKey(PARAMETER_IS_PAGE_SEARCHING)) {

			if (parameters.get(PARAMETER_USER_ROLE).equals(PARAMETER_USER_ROLE_ADMIN)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_ROLE_ADMIN));
			} else if (parameters.get(PARAMETER_USER_ROLE).equals(PARAMETER_USER_ROLE_CLIENT)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_ROLE_CLIENT));
			}

			if (parameters.get(PARAMETER_BAN_STATUS).equals(PARAMETER_BAN_STATUS_IN_BAN)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_BANNED));
			} else if (parameters.get(PARAMETER_BAN_STATUS)
					.equals(PARAMETER_BAN_STATUS_NOT_IN_BAN)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_NOT_BANNED));
			}

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_FIRST_NAME,
					parameters.get(PARAMETER_FIRST_NAME)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_LAST_NAME,
					parameters.get(PARAMETER_LAST_NAME)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_PASSPORT,
					parameters.get(PARAMETER_PASSPORT)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_REG_DATE,
					parameters.get(PARAMETER_REG_DATE_CONDITION),
					parameters.get(PARAMETER_REG_DATE)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_MONTH_DATA,
					parameters.get(PARAMETER_MONTH_DATE_CONDITION),
					parameters.get(PARAMETER_MONTH_DATE)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_TOTAL_DATA,
					parameters.get(PARAMETER_TOTAL_DATE_CONDITION),
					parameters.get(PARAMETER_TOTAL_DATE)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_BALLANCE,
					parameters.get(PARAMETER_BALLANCE_CONDITION),
					parameters.get(PARAMETER_BALLANCE)));
		}

		return getUsersList(searchFilter);
	}

	@Override
	public List<User> getUsersList() throws ServiceException {

		return getUsersList(new UserSearchFilter());
	}

	@Override
	public List<User> getClientsList() throws ServiceException {

		UserSearchFilter usersFilter = new UserSearchFilter();
		usersFilter.addSubFilter(new SubFilter(FilterParameter.USER_ROLE_CLIENT));
		return getUsersList(usersFilter);

	}

	@Override
	public List<User> getUsersWithNegBallance() throws ServiceException {

		UserSearchFilter usersFilter = new UserSearchFilter();
		usersFilter.addSubFilter(new SubFilter(FilterParameter.USER_BALLANCE,
				PARAMETER_CONDITION_LESS, ZERO_BALLANCE_VALUE));
		return getUsersList(usersFilter);
	}

	@Override
	public List<User> getUsersInBan() throws ServiceException {

		UserSearchFilter usersFilter = new UserSearchFilter();
		usersFilter.addSubFilter(new SubFilter(FilterParameter.USER_BANNED));
		return getUsersList(usersFilter);
	}

	@Override
	public List<Ban> getUserBansList(int userId) throws ServiceException {

		checkId(userId);

		BanSearchFilter filter = new BanSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.BAN_USER_ID, userId));

		return getBansList(filter);

	}

	@Override
	public String addRequest(int userId, int tariffId) throws ServiceException {

		checkId(userId);
		checkId(tariffId);

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		if (isUserHasTariff(userId, tariffId)) {
			return errorsResource.getString(ERROR_TARIFF_IS_USED);
		} else {
			try {
				Request request = new Request();
				request.setRequestDate(new java.sql.Timestamp(new Date().getTime()));
				request.setUserId(userId);
				request.setTariffId(tariffId);
				requestDAO.addRequest(request);
			} catch (DAOException e) {
				throw new ServiceException("Error in addRequest()", e);
			}
		}
		return NO_ERRORS;
	}

	@Override
	public Request getRequest(int requestId) throws ServiceException {

		System.out.println("service::get request:" + requestId);

		checkId(requestId);

		try {
			return requestDAO.getRequest(requestId);
		} catch (DAORequestNotFoundException e) {
			throw new ServiceRequestNotFoundException("Request not found in getRequest()", e);
		} catch (DAOException e) {
			throw new ServiceException("Error in getRequest()", e);
		}

	}

	@Override
	public void processRequest(int requestId, int adminId) throws ServiceException {

		checkId(adminId);
		checkId(requestId);

		User admin = null;

		admin = getUserById(adminId);

		if ((admin != null) && (!admin.getRole().equals(UserRole.ADMIN))) {
			throw new ServiceException("Need admin role to process request.");
		}

		try {
			requestDAO.processRequest(requestId, new java.sql.Timestamp(new Date().getTime()),
					adminId);

		} catch (DAOException e) {
			throw new ServiceException("Error in processRequest()", e);
		}

	}

	@Override
	public String deleteRequest(int requestId) throws ServiceException {

		checkId(requestId);

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		try {
			Request request = requestDAO.getRequest(requestId);
			if (request.getProcessedDate() != null) {
				return errorsResource.getString(ERROR_TARIFF_IS_DELETED);
			} else {
				requestDAO.deleteRequest(requestId);
			}
		} catch (DAOException e) {
			throw new ServiceException("Error in deleteRequest()", e);
		}

		return NO_ERRORS;
	}

	@Override
	public Request getActiveRequest(int userId) throws ServiceException {

		checkId(userId);

		List<Request> activeRequests = new ArrayList<>();
		Request activeRequest = new Request();

		RequestSearchFilter searchActiveFilter = new RequestSearchFilter();
		searchActiveFilter.addSubFilter(
				new SubFilter(FilterParameter.REQUEST_USER_ID, Integer.toString(userId)));
		searchActiveFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_ACTIVE));

		activeRequests = getRequestsList(searchActiveFilter);

		if (!activeRequests.isEmpty()) {
			activeRequest = activeRequests.get(FIRST_LIST_INDEX);
		}

		return activeRequest;
	}

	@Override
	public List<Request> getRequestsList(RequestSearchFilter filter) throws ServiceException {

		checkForNull(filter);

		try {
			return requestDAO.getRequestsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getRequestsList()", e);
		}
	}

	@Override
	public List<Request> getRequestsList(Map<String, String> parameters) throws ServiceException {

		checkForNull(parameters);
		RequestSearchFilter searchFilter = new RequestSearchFilter();

		if (parameters.containsKey(PARAMETER_IS_PAGE_SEARCHING)) {

			if (parameters.get(PARAMETER_STATUS).equals(PARAMETER_STATUS_ACTIVE)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_ACTIVE));
			} else if (parameters.get(PARAMETER_STATUS).equals(PARAMETER_STATUS_PROCESSED)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_PROCESSED));
			}

			if (!parameters.get(PARAMETER_TARIFF).equals(PARAMETER_TARIFF_ALL)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_TARIFF_ID,
						parameters.get(PARAMETER_TARIFF)));
			}

			if (!parameters.get(PARAMETER_REQUEST_DATE).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_DATE,
						parameters.get(PARAMETER_REQUEST_DATE_CONDITION),
						parameters.get(PARAMETER_REQUEST_DATE)));
			}

			if (!parameters.get(PARAMETER_PROCESS_DATE).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_PROC_DATE,
						parameters.get(PARAMETER_PROCESS_DATE_CONDITION),
						parameters.get(PARAMETER_PROCESS_DATE)));
			}

			if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_DATE)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_BY_DATE));
			} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_USER)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_BY_USER));
			} else {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_BY_TARIFF));
			}
		}

		return getRequestsList(searchFilter);
	}

	@Override
	public List<Request> getRequestsList() throws ServiceException {
		RequestSearchFilter searchFilter = new RequestSearchFilter();
		searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_BY_DATE));
		return getRequestsList(searchFilter);
	}

	@Override
	public List<Request> getActiveRequestsList() throws ServiceException {
		RequestSearchFilter requestFilter = new RequestSearchFilter();
		requestFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_ACTIVE));
		return getRequestsList(requestFilter);
	}

	@Override
	public List<Request> getUserHistoryRequestsList(int userId) throws ServiceException {
		RequestSearchFilter requestFilter = new RequestSearchFilter();
		requestFilter.addSubFilter(
				new SubFilter(FilterParameter.REQUEST_USER_ID, Integer.toString(userId)));
		requestFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_PROCESSED));
		return getRequestsList(requestFilter);
	}

	@Override
	public Tariff getTariff(int tariffId)
			throws ServiceIncorrectIdException, ServiceTariffNotFoundException, ServiceException {

		checkId(tariffId);

		try {
			return tariffDAO.getTariff(tariffId);
		} catch (DAOTariffNotFoundException e) {
			throw new ServiceTariffNotFoundException("Tariff not found.", e);
		} catch (DAOException e) {
			throw new ServiceException("Error in getTariff()", e);
		}
	}

	public List<String> createTariff(TariffData tariffData) throws ServiceException {

		checkForNull(tariffData);

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		List<String> errors = Validation.tariffDataValidation(tariffData);

		if (isTariffTitleBusy(tariffData.getTitle())) {
			errors.add(errorsResource.getString(ERROR_TARIFF_TITLE_IS_BUSY));
		}

		if (errors.isEmpty()) {

			Tariff tariff = new Tariff();
			tariff.setTitle(tariffData.getTitle());
			tariff.setMonthlyCost(new BigDecimal(tariffData.getMonthlyCost()));

			if (tariffData.getUnlimTraffic().equals(LIMITED_TARIFF_FIELD)) {
				tariff.setUnlimTraffic(false);
				tariff.setMonthlyDataLimit(Long.parseLong(tariffData.getMonthlyDataLimit()));
				tariff.setOverloadLimitCost(new BigDecimal(tariffData.getOverloadLimitCost()));

			} else {
				tariff.setUnlimTraffic(true);
				tariff.setMonthlyDataLimit(MONTHLY_DATA_FOR_UNLIM);
				tariff.setOverloadLimitCost(BigDecimal.valueOf(OVERLOAD_COST_FOR_UNLIM));
			}

			tariff.setTechnologyId(Integer.parseInt(tariffData.getTechnologyId()));
			tariff.setDescription(tariffData.getDescription());

			try {
				tariffDAO.addTariff(tariff);
			} catch (DAOException e) {
				throw new ServiceException("Error in addTariff()", e);
			}
		}

		return errors;
	}

	@Override
	public List<String> editTariff(int tariffId, TariffData tariffData) throws ServiceException {

		checkId(tariffId);
		checkForNull(tariffData);
		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		List<String> errors = Validation.tariffDataValidation(tariffData);

		String currentTitle = null;

		try {
			currentTitle = tariffDAO.getTariff(tariffId).getTitle();
		} catch (DAOException e) {
			throw new ServiceException("Error in editTariff()", e);
		}

		if (!currentTitle.equals(tariffData.getTitle()) && (tariffData.getTitle().length() > 0))
			if (isTariffTitleBusy(tariffData.getTitle())) {
				errors.add(errorsResource.getString(ERROR_TARIFF_TITLE_IS_BUSY));
			}

		if (errors.isEmpty()) {

			Tariff tariff = new Tariff();
			tariff.setTitle(tariffData.getTitle());
			tariff.setMonthlyCost(new BigDecimal(tariffData.getMonthlyCost()));

			if (tariffData.getUnlimTraffic().equals(LIMITED_TARIFF_FIELD)) {
				tariff.setUnlimTraffic(false);
				tariff.setMonthlyDataLimit(Long.parseLong(tariffData.getMonthlyDataLimit()));
				tariff.setOverloadLimitCost(new BigDecimal(tariffData.getOverloadLimitCost()));

			} else {
				tariff.setUnlimTraffic(true);
				tariff.setMonthlyDataLimit(MONTHLY_DATA_FOR_UNLIM);
				tariff.setOverloadLimitCost(BigDecimal.valueOf(OVERLOAD_COST_FOR_UNLIM));
			}

			tariff.setTechnologyId(Integer.parseInt(tariffData.getTechnologyId()));
			tariff.setDescription(tariffData.getDescription());

			try {
				tariffDAO.editTariff(tariffId, tariff);
			} catch (DAOException e) {
				throw new ServiceException("Error in editTariff()", e);
			}

		}

		return errors;

	}

	@Override
	public boolean isTariffUsed(int tariffId) throws ServiceException {

		checkId(tariffId);

		TariffSearchFilter filter = new TariffSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.TARIFF_ID, Integer.toString(tariffId)));
		filter.addSubFilter(new SubFilter(FilterParameter.TARIFF_USED));

		try {
			if (tariffDAO.getTariffsList(filter).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (DAOException e) {
			throw new ServiceException("Error in isTariffUsed()", e);
		}

	}

	@Override
	public void deleteTariff(int tariffId) throws ServiceException {

		checkId(tariffId);

		try {
			tariffDAO.deleteTariff(tariffId);
		} catch (DAOException e) {
			throw new ServiceException("Error in deleteTariff()", e);
		}
	}

	@Override
	public List<Tariff> getTariffsList(TariffSearchFilter filter) throws ServiceException {

		checkForNull(filter);

		try {
			return tariffDAO.getTariffsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getRequestsList()", e);
		}
	}

	@Override
	public List<Tariff> getTariffsList(Map<String, String> parameters) throws ServiceException {

		checkForNull(parameters);

		TariffSearchFilter searchFilter = new TariffSearchFilter();

		if (parameters.containsKey(PARAMETER_IS_PAGE_SEARCHING)) {

			if ((parameters.get(PARAMETER_IS_UNLIM)).equals(PARAMETER_YES)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_UNLIMITED));
			} else if ((parameters.get(PARAMETER_IS_UNLIM)).equals(PARAMETER_NO)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_LIMITED));
			}

			if ((parameters.get(PARAMETER_NEED_EUQIP)).equals(PARAMETER_YES)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NEED_EQUIPMENT));
			} else if ((parameters.get(PARAMETER_NEED_EUQIP)).equals(PARAMETER_NO)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NO_EQUIPMENT));
			}

			if (!(parameters.get(PARAMETER_TECHNOLOGY)).equals(PARAMETER_ALL)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_TECHNOLOGY_ID,
						(parameters.get(PARAMETER_TECHNOLOGY))));
			}

			if (!(parameters.get(PARAMETER_MONTHLY_COST)).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_MONTHLY_COST,
						(parameters.get(PARAMETER_MONTHLY_COST_CONDITION)),
						parameters.get(PARAMETER_MONTHLY_COST)));
			}

			if (!(parameters.get(PARAMETER_MONTHLY_LIMIT)).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_MONTHLY_DATA,
						(parameters.get(PARAMETER_MONTHLY_LIMIT_CONDITION)),
						parameters.get(PARAMETER_MONTHLY_LIMIT)));
			}

			if (!(parameters.get(PARAMETER_OVER_COST)).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_OVER_COST,
						(parameters.get(PARAMETER_OVER_COST_CONDITION)),
						parameters.get(PARAMETER_OVER_COST)));
			}

			if (parameters.containsKey(PARAMETER_IS_USED)) {
				if (parameters.get(PARAMETER_IS_USED).equals(PARAMETER_YES)) {
					searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_USED));
				} else if (parameters.get(PARAMETER_IS_USED).equals(PARAMETER_NO)) {
					searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NOT_USED));
				}
			}
		}

		return getTariffsList(searchFilter);
	}

	@Override
	public List<Tariff> getTariffsList() throws ServiceException {
		return getTariffsList(new TariffSearchFilter());
	}

	@Override
	public List<Tariff> getNotUsedTariffsList() throws ServiceException {
		TariffSearchFilter tariffNotUsedFilter = new TariffSearchFilter();
		tariffNotUsedFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NOT_USED));
		return getTariffsList(tariffNotUsedFilter);

	}

	@Override
	public Map<Integer, String> getTariffsMap() throws ServiceException {

		List<Tariff> list = getTariffsList();

		return list.stream().collect(Collectors.toMap(Tariff::getId, Tariff::getTitle));
	}

	@Override
	public BigDecimal getLowestTariffCost(List<Tariff> tariffsList) {
		return tariffsList.stream().min(Comparator.comparing(Tariff::getMonthlyCost)).get()
				.getMonthlyCost();
	}

	@Override
	public List<String> makePayment(int userId, String amount) throws ServiceException {

		checkId(userId);
		checkForNull(amount);

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();
		BigDecimal realAmount = null;
		User user = getUserById(userId);

		try {
			realAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			errors.add(errorsResource.getString(ERROR_WRONG_AMOUNT));
			return errors;
		}

		if (realAmount.compareTo(new BigDecimal(0)) != 1) {
			errors.add(errorsResource.getString(ERROR_NEG_AMOUNT));
		} else
			try {
				paymentDAO.makePayment(user, realAmount,
						new java.sql.Timestamp(new Date().getTime()));
			} catch (DAOException e) {
				throw new ServiceException("Error in makePayment()", e);
			}

		return errors;
	}

	@Override
	public List<Payment> getPaymentsList(PaymentSearchFilter filter) throws ServiceException {

		checkForNull(filter);

		try {
			return paymentDAO.getPaymentsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getPaymentsList()", e);
		}
	}

	@Override
	public List<Payment> getPaymentsList(Map<String, String> parameters) throws ServiceException {

		checkForNull(parameters);

		PaymentSearchFilter searchFilter = new PaymentSearchFilter();

		if (parameters.containsKey(PARAMETER_IS_PAGE_SEARCHING)) {

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_FIRST_NAME,
					parameters.get(PARAMETER_FIRST_NAME)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_LAST_NAME,
					parameters.get(PARAMETER_LAST_NAME)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_AMOUNT,
					parameters.get(PARAMETER_AMOUNT_CONDITION), parameters.get(PARAMETER_AMOUNT)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_DATE,
					parameters.get(PARAMETER_PAYMENT_DATE_CONDITION),
					parameters.get(PARAMETER_PAYMENT_DATE)));

			if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_DATE)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_DATE));
			} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_USER)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_USER));
			} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_AMOUNT)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_AMOUNT));
			}
		}
		return getPaymentsList(searchFilter);
	}

	@Override
	public List<Payment> getUserPaymentsList(int userId, Map<String, String> parameters)
			throws ServiceException {

		checkId(userId);
		checkForNull(parameters);

		PaymentSearchFilter searchFilter = new PaymentSearchFilter();
		searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_USER_ID, userId));

		if (parameters.containsKey(PARAMETER_IS_PAGE_SEARCHING)) {

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_AMOUNT,
					parameters.get(PARAMETER_AMOUNT_CONDITION), parameters.get(PARAMETER_AMOUNT)));

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_DATE,
					parameters.get(PARAMETER_PAYMENT_DATE_CONDITION),
					parameters.get(PARAMETER_PAYMENT_DATE)));

			if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_DATE)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_DATE));
			} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_AMOUNT)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_AMOUNT));
			}
		}
		return getPaymentsList(searchFilter);
	}

	@Override
	public List<Payment> getUserPaymentsList(int userId) throws ServiceException {

		checkId(userId);

		PaymentSearchFilter searchFilter = new PaymentSearchFilter();
		searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_USER_ID, userId));

		return getPaymentsList(searchFilter);
	}

	@Override
	public List<Payment> getPaymentsList() throws ServiceException {

		PaymentSearchFilter searchFilter = new PaymentSearchFilter();
		searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_DATE));

		return getPaymentsList(searchFilter);
	}

	@Override
	public List<Ban> getBansList(BanSearchFilter filter) throws ServiceException {

		checkForNull(filter);

		try {
			return banDAO.getBansList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getBansList()", e);
		}
	}

	@Override
	public List<Ban> getBansList() throws ServiceException {
		return getBansList(new BanSearchFilter());
	}

	@Override
	public List<BanReason> getBanReasonsList(BanReasonSearchFilter filter) throws ServiceException {

		checkForNull(filter);

		try {
			return banDAO.getBanReasonsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getBanReasonsList()", e);
		}
	}

	@Override
	public List<BanReason> getBanReasonsList() throws ServiceException {
		return getBanReasonsList(new BanReasonSearchFilter());
	}

	@Override
	public List<Technology> getTechologiesList(TechnologySearchFilter filter)
			throws ServiceException {

		checkForNull(filter);

		try {
			return tariffDAO.getTechnologiesList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getTechologiesList()", e);
		}
	}

	@Override
	public List<Technology> getTechologiesList() throws ServiceException {

		return getTechologiesList(new TechnologySearchFilter());
	}

	@Override
	public Technology getTechnology(int technologyId) throws ServiceException {

		checkId(technologyId);

		TechnologySearchFilter filter = new TechnologySearchFilter();

		filter.addSubFilter(
				new SubFilter(FilterParameter.TECHNOLOGY_ID, Integer.toString(technologyId)));

		return getTechologiesList(filter).get(FIRST_LIST_INDEX);

	}

	private void checkId(int iD) throws ServiceException {

		if (iD < FIRST_REAL_DB_INDEX) {
			throw new ServiceException("Incorrect ID=" + iD + " in service method");
		}

	}

	private void checkForNull(Object referenceToCheck) throws ServiceException {

		if (referenceToCheck == null) {
			throw new ServiceException("Null reference!");
		}
	}

	private static void setUserData(User user, UserData userData)
			throws CannotPerformOperationException {

		if (userData.getRole() != null)
			user.setRole(UserRole.valueOf(userData.getRole()));

		if (userData.getFirstName() != null)
			user.setFirstName(userData.getFirstName());

		if (userData.getFirstName() != null)
			user.setLastName(userData.getLastName());

		if (userData.getPassportNumber() != null)
			user.setPassportNumber(userData.getPassportNumber());

		if (userData.getEmail() != null)
			user.setEmail(userData.getEmail());

		if (userData.getLogin() != null)
			user.setLogin(userData.getLogin());

		if (userData.getRegDate() != null)
			user.setRegDate(java.sql.Date.valueOf(userData.getRegDate()));

		if (userData.getLogin() != null)
			user.setLogin(userData.getLogin());

		if (userData.getPassword() != null)
			user.setPassword(PasswordComputer.createHash(userData.getPassword()));

		if (userData.getMonthlyDataUsage() != null)
			user.setMonthlyDataUsage(Long.parseLong(userData.getMonthlyDataUsage()));

		if (userData.getTotalDataUsage() != null)
			user.setTotalDataUsage(Long.parseLong(userData.getTotalDataUsage()));

		if (userData.getAccountBallance() != null)
			user.setAccountBallance(new BigDecimal(userData.getAccountBallance()));

		if (userData.getTariffId() != null)
			user.setTariffId(Integer.parseInt(userData.getTariffId()));
	}

	private boolean isUserHasTariff(int userId, int tariffId) throws ServiceException {

		checkId(userId);
		checkId(tariffId);

		UserSearchFilter filter = new UserSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.USER_ID, userId));
		filter.addSubFilter(new SubFilter(FilterParameter.USER_TARIFF_ID, tariffId));

		try {
			if (userDAO.getUsersList(filter).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (DAOException e) {
			throw new ServiceException("Error in isUserHasTariff()", e);
		}
	}

	private boolean isTariffTitleBusy(String title) throws ServiceException {

		checkForNull(title);

		List<Tariff> list;

		TariffSearchFilter filter = new TariffSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.TARIFF_TITLE, title));

		try {
			list = tariffDAO.getTariffsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in isTariffTitleBusy()", e);
		}

		if (list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
