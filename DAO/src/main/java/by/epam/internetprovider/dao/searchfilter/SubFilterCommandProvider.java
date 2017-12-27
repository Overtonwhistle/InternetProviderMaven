/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.searchfilter;

import java.util.EnumMap;
import java.util.Map;

import by.epam.internetprovider.dao.searchfilter.impl.ban.BanActive;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanId;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanReasonId;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanResetDate;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanSetDate;
import by.epam.internetprovider.dao.searchfilter.impl.ban.BanUserId;
import by.epam.internetprovider.dao.searchfilter.impl.banreason.BReasonId;
import by.epam.internetprovider.dao.searchfilter.impl.banreason.BanReasonTitle;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentAmount;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentByAmount;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentByDate;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentByUser;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentDate;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentFirstName;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentId;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentLastName;
import by.epam.internetprovider.dao.searchfilter.impl.payment.PaymentUserId;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestActive;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestByDate;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestByTariff;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestByUser;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestDate;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestId;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestProcBy;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestProcDate;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestProcessed;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestTariffId;
import by.epam.internetprovider.dao.searchfilter.impl.request.RequestUserId;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffId;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffLimited;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffMonthlyCost;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffMonthlyData;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffNeedEquip;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffNoEquip;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffNotUsed;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffOverCost;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffTechnologyId;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffTitle;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffUnlim;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffUsed;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologyEquip;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologyId;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologyTitle;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserBallance;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserBanned;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserEmail;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserFirstName;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserId;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserLastName;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserLogin;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserMonthData;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserNotBanned;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserPassport;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserPassword;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserRegDate;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserRoleAdmin;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserRoleClient;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserTariffId;
import by.epam.internetprovider.dao.searchfilter.impl.user.UserTotalData;

/**
 * Provides storage and issuance of commands for pattern "Command".
 * 
 * 
 * @author Pavel Sorokoletov
 */
public class SubFilterCommandProvider {

	private Map<FilterParameter, ISubFilterCommand> commands = new EnumMap<>(FilterParameter.class);

	public SubFilterCommandProvider() {
		commands.put(FilterParameter.TECHNOLOGY_ID, new TechnologyId());
		commands.put(FilterParameter.TECHNOLOGY_TITLE, new TechnologyTitle());
		commands.put(FilterParameter.TECHNOLOGY_EQUIPMENT, new TechnologyEquip());

		commands.put(FilterParameter.PAYMENT_ID, new PaymentId());
		commands.put(FilterParameter.PAYMENT_AMOUNT, new PaymentAmount());
		commands.put(FilterParameter.PAYMENT_USER_ID, new PaymentUserId());
		commands.put(FilterParameter.PAYMENT_DATE, new PaymentDate());
		commands.put(FilterParameter.PAYMENT_FIRST_NAME, new PaymentFirstName());
		commands.put(FilterParameter.PAYMENT_LAST_NAME, new PaymentLastName());
		commands.put(FilterParameter.PAYMENT_BY_DATE, new PaymentByDate());
		commands.put(FilterParameter.PAYMENT_BY_USER, new PaymentByUser());
		commands.put(FilterParameter.PAYMENT_BY_AMOUNT, new PaymentByAmount());

		commands.put(FilterParameter.REQUEST_ID, new RequestId());
		commands.put(FilterParameter.REQUEST_DATE, new RequestDate());
		commands.put(FilterParameter.REQUEST_PROC_DATE, new RequestProcDate());
		commands.put(FilterParameter.REQUEST_ACTIVE, new RequestActive());
		commands.put(FilterParameter.REQUEST_PROCESSED, new RequestProcessed());
		commands.put(FilterParameter.REQUEST_PROC_BY, new RequestProcBy());
		commands.put(FilterParameter.REQUEST_USER_ID, new RequestUserId());
		commands.put(FilterParameter.REQUEST_TARIFF_ID, new RequestTariffId());
		commands.put(FilterParameter.REQUEST_BY_DATE, new RequestByDate());
		commands.put(FilterParameter.REQUEST_BY_USER, new RequestByUser());
		commands.put(FilterParameter.REQUEST_BY_TARIFF, new RequestByTariff());

		commands.put(FilterParameter.TARIFF_ID, new TariffId());
		commands.put(FilterParameter.TARIFF_NEED_EQUIPMENT, new TariffNeedEquip());
		commands.put(FilterParameter.TARIFF_NO_EQUIPMENT, new TariffNoEquip());
		commands.put(FilterParameter.TARIFF_UNLIMITED, new TariffUnlim());
		commands.put(FilterParameter.TARIFF_LIMITED, new TariffLimited());
		commands.put(FilterParameter.TARIFF_MONTHLY_COST, new TariffMonthlyCost());
		commands.put(FilterParameter.TARIFF_MONTHLY_DATA, new TariffMonthlyData());
		commands.put(FilterParameter.TARIFF_NOT_USED, new TariffNotUsed());
		commands.put(FilterParameter.TARIFF_USED, new TariffUsed());
		commands.put(FilterParameter.TARIFF_OVER_COST, new TariffOverCost());
		commands.put(FilterParameter.TARIFF_TECHNOLOGY_ID, new TariffTechnologyId());
		commands.put(FilterParameter.TARIFF_TITLE, new TariffTitle());

		commands.put(FilterParameter.BAN_ID, new BanId());
		commands.put(FilterParameter.BAN_SET_DATE, new BanSetDate());
		commands.put(FilterParameter.BAN_RESET_DATE, new BanResetDate());
		commands.put(FilterParameter.BAN_ACTIVE, new BanActive());
		commands.put(FilterParameter.BAN_BAN_REASON_ID, new BanReasonId());
		commands.put(FilterParameter.BAN_USER_ID, new BanUserId());

		commands.put(FilterParameter.BAN_REASON_ID, new BReasonId());
		commands.put(FilterParameter.BAN_REASON_TITLE, new BanReasonTitle());

		commands.put(FilterParameter.USER_ID, new UserId());
		commands.put(FilterParameter.USER_ROLE_ADMIN, new UserRoleAdmin());
		commands.put(FilterParameter.USER_ROLE_CLIENT, new UserRoleClient());
		commands.put(FilterParameter.USER_LOGIN, new UserLogin());
		commands.put(FilterParameter.USER_PASSWORD, new UserPassword());
		commands.put(FilterParameter.USER_EMAIL, new UserEmail());
		commands.put(FilterParameter.USER_FIRST_NAME, new UserFirstName());
		commands.put(FilterParameter.USER_LAST_NAME, new UserLastName());
		commands.put(FilterParameter.USER_PASSPORT, new UserPassport());
		commands.put(FilterParameter.USER_REG_DATE, new UserRegDate());
		commands.put(FilterParameter.USER_MONTH_DATA, new UserMonthData());
		commands.put(FilterParameter.USER_TOTAL_DATA, new UserTotalData());
		commands.put(FilterParameter.USER_BALLANCE, new UserBallance());
		commands.put(FilterParameter.USER_BANNED, new UserBanned());
		commands.put(FilterParameter.USER_NOT_BANNED, new UserNotBanned());
		commands.put(FilterParameter.USER_TARIFF_ID, new UserTariffId());

	}

	/**
	 * Returns {@link ISubFilterCommand} interface implementation by search parameter
	 *
	 * @param commandName {@link FilterParameter}
	 * @return {@link ISubFilterCommand} object
	 * @throws DAOWrongSearchFilterException if commandName not present in Provider.
	 */
	public ISubFilterCommand getCommand(FilterParameter commandName) {
		
		return commands.get(commandName);

	}

}
