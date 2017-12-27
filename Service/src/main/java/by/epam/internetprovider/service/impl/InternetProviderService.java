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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.bean.Ban;
import by.epam.internetprovider.bean.BanReason;
import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.bean.Request;
import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.bean.User;
import by.epam.internetprovider.bean.Param.UserRole;
import by.epam.internetprovider.bean.builder.TariffBuilder;
import by.epam.internetprovider.bean.builder.UserBuilder;
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
import by.epam.internetprovider.service.impl.util.Validation;

public class InternetProviderService implements IInternetProviderService {

	private static final Logger logger = LogManager.getLogger();
	private final static String LOCAL_ERRORS_BASENAME = "local_errors";

	private static final int INITIAL_MONTHLY_DATA = 0;
	private static final int INITIAL_TOTAL_DATA = 0;
	private static final int MONTHLY_DATA_FOR_UNLIM = 0;
	private static final int OVERLOAD_COST_FOR_UNLIM = 0;
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

		if (login == null || password == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getUserByLogin()");
		}

		try {
			return userDAO.getUserByLogin(login, password);
		} catch (DAOUserNotFoundException e) {
			throw new ServiceUserNotFoundException("User " + login + ":" + password + " not found.",
					e);
		} catch (DAOException e) {
			throw new ServiceException("Error getting user from getUserByLogin()", e);
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
			throw new ServiceException("Error getting user from getUserById()", e);
		}
	}

	@Override
	public List<String> createUser(UserBuilder userBuilder) throws ServiceException {

		if (userBuilder == null) {
			logger.log(Level.ERROR, "Null parameter reference in createUser()");
			throw new ServiceException("Null parameter reference in createUser()");
		}

		List<String> errors = Validation.newUserBuilderValidation(userBuilder);

		if (errors.isEmpty()) {

			User user = new User();
			user.setRole(UserRole.CLIENT);
			user.setRegDate(new java.sql.Date(System.currentTimeMillis()));
			user.setMonthlyDataUsage(INITIAL_MONTHLY_DATA);
			user.setTotalDataUsage(INITIAL_TOTAL_DATA);
			user.setAccountBallance(INITIAL_ACCOUNT_BALLANCE);
			user.setTariffId(INITIAL_TARIFF_ID);

			user.setFirstName(userBuilder.getFirstName());
			user.setLastName(userBuilder.getLastName());
			user.setPassportNumber(userBuilder.getPassportNumber());
			user.setEmail(userBuilder.getEmail());
			user.setLogin(userBuilder.getLogin());
			user.setPassword(userBuilder.getPassword());

			try {
				userDAO.addUser(user);
			} catch (DAOException e) {
				throw new ServiceException("Error in registerUser()", e);
			}
		}
		return errors;
	}

	@Override
	public List<String> editUser(UserBuilder userBuilder, int userId) throws ServiceException {

		checkId(userId);

		ArrayList<String> errors = (ArrayList<String>) Validation
				.editUserBuilderValidation(userBuilder);

		if (errors.isEmpty()) {

			User user = new User();
			user.setRole(UserRole.valueOf(userBuilder.getRole()));
			user.setFirstName(userBuilder.getFirstName());
			user.setLastName(userBuilder.getLastName());
			user.setPassportNumber(userBuilder.getPassportNumber());
			user.setEmail(userBuilder.getEmail());
			user.setRegDate(java.sql.Date.valueOf(userBuilder.getRegDate()));
			user.setLogin(userBuilder.getLogin());
			user.setPassword(userBuilder.getPassword());
			user.setMonthlyDataUsage(Long.parseLong(userBuilder.getMonthlyDataUsage()));
			user.setTotalDataUsage(Long.parseLong(userBuilder.getTotalDataUsage()));
			user.setAccountBallance(new BigDecimal(userBuilder.getAccountBallance()));
			user.setTariffId(Integer.parseInt(userBuilder.getTariffId()));

			try {
				userDAO.editUser(userId, user);
			} catch (DAOException e) {
				throw new ServiceException("Error in editUser()", e);
			}
		}
		return errors;
	}

	@Override
	public List<String> editClientProfile(UserBuilder userBuilder, int userId)
			throws ServiceException {

		checkId(userId);

		ArrayList<String> errors = (ArrayList<String>) Validation
				.editClientProfileValidation(userBuilder);

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		User user = getUserById(userId);

		if (!userBuilder.getCurrentPassword().equals(user.getPassword())) {
			errors.add(errorsResource.getString("edit_user.wrong_current_passwords"));
		}

		if (errors.isEmpty()) {

			user.setFirstName(userBuilder.getFirstName());
			user.setLastName(userBuilder.getLastName());
			user.setPassportNumber(userBuilder.getPassportNumber());
			user.setEmail(userBuilder.getEmail());
			user.setPassword(userBuilder.getPassword());

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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getUsersList()");
		}

		try {
			return userDAO.getUsersList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getUsersList()", e);
		}
	}

	@Override
	public List<User> getUsersList(Map<String, String> parameters) throws ServiceException {

		if (parameters == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getUsersList(Map)");
		}

		UserSearchFilter searchFilter = new UserSearchFilter();

		if (parameters.containsKey(PARAMETER_USER_ROLE)) {
			if (parameters.get(PARAMETER_USER_ROLE).equals(PARAMETER_USER_ROLE_ADMIN)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_ROLE_ADMIN));
			} else if (parameters.get(PARAMETER_USER_ROLE).equals(PARAMETER_USER_ROLE_CLIENT)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_ROLE_CLIENT));
			}
		}

		if (parameters.containsKey(PARAMETER_BAN_STATUS)) {
			if (parameters.get(PARAMETER_BAN_STATUS).equals(PARAMETER_BAN_STATUS_IN_BAN)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_BANNED));
			} else if (parameters.get(PARAMETER_BAN_STATUS)
					.equals(PARAMETER_BAN_STATUS_NOT_IN_BAN)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_NOT_BANNED));
			}
		}

		if (parameters.containsKey(PARAMETER_FIRST_NAME)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_FIRST_NAME,
					parameters.get(PARAMETER_FIRST_NAME)));
		}

		if (parameters.containsKey(PARAMETER_LAST_NAME)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_LAST_NAME,
					parameters.get(PARAMETER_LAST_NAME)));
		}

		if (parameters.containsKey(PARAMETER_PASSPORT)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_PASSPORT,
					parameters.get(PARAMETER_PASSPORT)));
		}

		if (parameters.containsKey(PARAMETER_REG_DATE)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_REG_DATE,
					parameters.get(PARAMETER_REG_DATE_CONDITION),
					parameters.get(PARAMETER_REG_DATE)));
		}

		if (parameters.containsKey(PARAMETER_MONTH_DATE)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_MONTH_DATA,
					parameters.get(PARAMETER_MONTH_DATE_CONDITION),
					parameters.get(PARAMETER_MONTH_DATE)));
		}

		if (parameters.containsKey(PARAMETER_TOTAL_DATE)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.USER_TOTAL_DATA,
					parameters.get(PARAMETER_TOTAL_DATE_CONDITION),
					parameters.get(PARAMETER_TOTAL_DATE)));
		}

		if (parameters.containsKey(PARAMETER_BALLANCE)) {
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
		usersFilter.addSubFilter(
				new SubFilter(FilterParameter.USER_BALLANCE, PARAMETER_CONDITION_LESS, 0));
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
			return errorsResource.getString("add_request.user_already_has_tariff");

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
		return "";
	}

	@Override
	public Request getRequest(int requestId) throws ServiceException {

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
		Request request = null;

		request = getRequest(requestId);

		admin = getUserById(adminId);

		if ((admin != null) && (!admin.getRole().equals(UserRole.ADMIN))) {
			throw new ServiceException("Need admin role to process request.");
		}

		try {
			requestDAO.processRequest(requestId, new java.sql.Timestamp(new Date().getTime()),
					adminId);

			User client = getUserById(request.getUserId());

			client.setTariffId(request.getTariffId());
			userDAO.editUser(request.getUserId(), client);

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
				return errorsResource.getString("delete_request.already_processed");
			} else {
				requestDAO.deleteRequest(requestId);
			}
		} catch (DAOException e) {
			throw new ServiceException("Error in deleteRequest()", e);
		}

		return "";
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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getRequestsList()");
		}

		try {
			return requestDAO.getRequestsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getRequestsList()", e);
		}
	}

	@Override
	public List<Request> getRequestsList(Map<String, String> parameters) throws ServiceException {
		RequestSearchFilter searchFilter = new RequestSearchFilter();

		if (parameters == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getRequestsList(Map)");
		}

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
		} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_TARIFF)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.REQUEST_BY_TARIFF));
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

	public List<String> createTariff(TariffBuilder tariffBuilder) throws ServiceException {

		if (tariffBuilder == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in createTariff()");
		}

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		List<String> errors = Validation.tariffBuilderValidation(tariffBuilder);

		if (isTariffTitleBusy(tariffBuilder.getTitle())) {
			errors.add(errorsResource.getString("add_tariff.title_busy"));
		}

		if (errors.isEmpty()) {

			Tariff tariff = new Tariff();
			tariff.setTitle(tariffBuilder.getTitle());
			tariff.setMonthlyCost(new BigDecimal(tariffBuilder.getMonthlyCost()));

			if (tariffBuilder.getUnlimTraffic().equals("no")) {
				tariff.setUnlimTraffic(false);
				tariff.setMonthlyDataLimit(Long.parseLong(tariffBuilder.getMonthlyDataLimit()));
				tariff.setOverloadLimitCost(new BigDecimal(tariffBuilder.getOverloadLimitCost()));

			} else {
				tariff.setUnlimTraffic(true);
				tariff.setMonthlyDataLimit(MONTHLY_DATA_FOR_UNLIM);
				tariff.setOverloadLimitCost(BigDecimal.valueOf(OVERLOAD_COST_FOR_UNLIM));
			}

			tariff.setTechnologyId(Integer.parseInt(tariffBuilder.getTechnologyId()));

			tariff.setDescription(tariffBuilder.getDescription());

			try {
				tariffDAO.addTariff(tariff);
			} catch (DAOException e) {
				throw new ServiceException("Error in addTariff()", e);
			}
		}

		return errors;
	}

	@Override
	public List<String> editTariff(int tariffId, TariffBuilder tariffBuilder)
			throws ServiceException {

		checkId(tariffId);

		if (tariffBuilder == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in editTariff()");
		}

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);

		List<String> errors = Validation.tariffBuilderValidation(tariffBuilder);

		String currentTitle = null;

		try {
			currentTitle = tariffDAO.getTariff(tariffId).getTitle();
		} catch (DAOException e) {
			throw new ServiceException("Error in editTariff()", e);
		}

		if (!currentTitle.equals(tariffBuilder.getTitle())
				&& (tariffBuilder.getTitle().length() > 0))
			try {
				if (isTariffTitleBusy(tariffBuilder.getTitle())) {
					errors.add(errorsResource.getString("add_tariff.title_busy"));
				}
			} catch (ServiceException e1) {
				throw new ServiceException("Error in editTariff()", e1);
			}

		if (errors.isEmpty()) {
			Tariff tariff = new Tariff();
			tariff.setTitle(tariffBuilder.getTitle());
			tariff.setMonthlyCost(new BigDecimal(tariffBuilder.getMonthlyCost()));

			if (tariffBuilder.getUnlimTraffic().equals("no")) {
				tariff.setUnlimTraffic(false);
				tariff.setMonthlyDataLimit(Long.parseLong(tariffBuilder.getMonthlyDataLimit()));
				tariff.setOverloadLimitCost(new BigDecimal(tariffBuilder.getOverloadLimitCost()));

			} else {
				tariff.setUnlimTraffic(true);
				tariff.setMonthlyDataLimit(MONTHLY_DATA_FOR_UNLIM);
				tariff.setOverloadLimitCost(BigDecimal.valueOf(OVERLOAD_COST_FOR_UNLIM));
			}

			tariff.setTechnologyId(Integer.parseInt(tariffBuilder.getTechnologyId()));
			tariff.setDescription(tariffBuilder.getDescription());

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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getTariffsList()");
		}

		try {
			return tariffDAO.getTariffsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getRequestsList()", e);
		}
	}

	@Override
	public List<Tariff> getTariffsList(Map<String, String> parameters) throws ServiceException {

		if (parameters == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getTariffsList(Map)");
		}

		TariffSearchFilter searchFilter = new TariffSearchFilter();

		if (parameters.containsKey(PARAMETER_IS_UNLIM)) {
			if ((parameters.get(PARAMETER_IS_UNLIM)).equals(PARAMETER_YES)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_UNLIMITED));
			} else if ((parameters.get(PARAMETER_IS_UNLIM)).equals(PARAMETER_NO)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_LIMITED));
			}
		}

		if (parameters.containsKey(PARAMETER_NEED_EUQIP)) {
			if ((parameters.get(PARAMETER_NEED_EUQIP)).equals(PARAMETER_YES)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NEED_EQUIPMENT));
			} else if ((parameters.get(PARAMETER_NEED_EUQIP)).equals(PARAMETER_NO)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NO_EQUIPMENT));
			}
		}

		if (parameters.containsKey(PARAMETER_TECHNOLOGY)) {
			if (!(parameters.get(PARAMETER_TECHNOLOGY)).equals(PARAMETER_ALL)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_TECHNOLOGY_ID,
						(parameters.get(PARAMETER_TECHNOLOGY))));
			}
		}

		if (parameters.containsKey(PARAMETER_IS_USED)) {
			if (parameters.get(PARAMETER_IS_USED).equals(PARAMETER_YES)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_USED));
			} else if (parameters.get(PARAMETER_IS_USED).equals(PARAMETER_NO)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_NOT_USED));
			}
		}

		if (parameters.containsKey(PARAMETER_MONTHLY_COST)) {
			if (!(parameters.get(PARAMETER_MONTHLY_COST)).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_MONTHLY_COST,
						(parameters.get(PARAMETER_MONTHLY_COST_CONDITION)),
						parameters.get(PARAMETER_MONTHLY_COST)));
			}
		}

		if (parameters.containsKey(PARAMETER_MONTHLY_LIMIT)) {
			if (!(parameters.get(PARAMETER_MONTHLY_LIMIT)).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_MONTHLY_DATA,
						(parameters.get(PARAMETER_MONTHLY_LIMIT_CONDITION)),
						parameters.get(PARAMETER_MONTHLY_LIMIT)));
			}
		}

		if (parameters.containsKey(PARAMETER_OVER_COST)) {
			if (!(parameters.get(PARAMETER_OVER_COST)).isEmpty()) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.TARIFF_OVER_COST,
						(parameters.get(PARAMETER_OVER_COST_CONDITION)),
						parameters.get(PARAMETER_OVER_COST)));
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
		{
			return tariffsList.stream().min(Comparator.comparing(Tariff::getMonthlyCost)).get()
					.getMonthlyCost();
		}
	}

	@Override
	public List<String> makePayment(int userId, String amount) throws ServiceException {

		checkId(userId);

		if (amount == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in makePayment()");
		}

		ResourceBundle errorsResource = ResourceBundle.getBundle(LOCAL_ERRORS_BASENAME);
		ArrayList<String> errors = new ArrayList<>();
		BigDecimal realAmount = null;
		User user = getUserById(userId);

		try {
			realAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			errors.add(errorsResource.getString("make_payment.wront_amount"));
			return errors;
		}

		if (realAmount.compareTo(new BigDecimal(0)) != 1) {
			errors.add(errorsResource.getString("make_payment.negative_amount"));
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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getPaymentsList()");
		}

		try {
			return paymentDAO.getPaymentsList(filter);
		} catch (DAOException e) {
			throw new ServiceException("Error in getPaymentsList()", e);
		}
	}

	@Override
	public List<Payment> getPaymentsList(Map<String, String> parameters) throws ServiceException {

		if (parameters == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getPaymentsList(Map)");
		}

		PaymentSearchFilter searchFilter = new PaymentSearchFilter();

		if (parameters.containsKey(PARAMETER_FIRST_NAME)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_FIRST_NAME,
					parameters.get(PARAMETER_FIRST_NAME)));
		}

		if (parameters.containsKey(PARAMETER_LAST_NAME)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_LAST_NAME,
					parameters.get(PARAMETER_LAST_NAME)));
		}

		if (parameters.containsKey(PARAMETER_AMOUNT)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_AMOUNT,
					parameters.get(PARAMETER_AMOUNT_CONDITION), parameters.get(PARAMETER_AMOUNT)));
		}

		if (parameters.containsKey(PARAMETER_PAYMENT_DATE)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_DATE,
					parameters.get(PARAMETER_PAYMENT_DATE_CONDITION),
					parameters.get(PARAMETER_PAYMENT_DATE)));
		}

		if (parameters.containsKey(PARAMETER_SORT_BY)) {

			if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_DATE)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_DATE));
			} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_USER)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_USER));
			} else if (parameters.get(PARAMETER_SORT_BY).equals(PARAMETER_SORT_BY_AMOUNT)) {
				searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_AMOUNT));
			}
		} else
			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_BY_DATE));

		return getPaymentsList(searchFilter);
	}

	@Override
	public List<Payment> getUserPaymentsList(int userId, Map<String, String> parameters)
			throws ServiceException {

		checkId(userId);

		if (parameters == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getPaymentsList(Map)");
		}

		PaymentSearchFilter searchFilter = new PaymentSearchFilter();

		searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_USER_ID, userId));

		if (parameters.containsKey(PARAMETER_AMOUNT)) {
			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_AMOUNT,
					parameters.get(PARAMETER_AMOUNT_CONDITION), parameters.get(PARAMETER_AMOUNT)));
		}

		if (parameters.containsKey(PARAMETER_PAYMENT_DATE)) {

			searchFilter.addSubFilter(new SubFilter(FilterParameter.PAYMENT_DATE,
					parameters.get(PARAMETER_PAYMENT_DATE_CONDITION),
					parameters.get(PARAMETER_PAYMENT_DATE)));
		}

		if (parameters.containsKey(PARAMETER_SORT_BY)) {
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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getBansList()");
		}

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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getBanReasonsList()");
		}

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

		if (filter == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getTechologiesList()");
		}

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

	private void checkId(int userId) throws ServiceException {

		if (userId < FIRST_REAL_DB_INDEX) {
			throw new ServiceIncorrectIdException("Incorrect user Id in service method");
		}

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

		if (title == null) {
			logger.log(Level.ERROR, "Null parameter reference.");
			throw new ServiceException("Null parameter reference in getTechologiesList()");
		}

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
