package by.epam.internetprovider.controller.command;

import java.util.EnumMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.internetprovider.controller.command.impl.LogIn;
import by.epam.internetprovider.controller.command.impl.LogOff;
import by.epam.internetprovider.controller.command.impl.Register;
import by.epam.internetprovider.controller.command.impl.WrongCommand;
import by.epam.internetprovider.controller.command.impl.admin.AdminAddTariffProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminBanUserProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminDeleteRequestProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminDeleteTariffProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminDeleteUserProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminDoRequestProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminEditTariffProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminEditUserProcess;
import by.epam.internetprovider.controller.command.impl.admin.AdminUnbanUserProcess;
import by.epam.internetprovider.controller.command.impl.client.ClientDeleteRequestProcess;
import by.epam.internetprovider.controller.command.impl.client.ClientEditProfileProcess;
import by.epam.internetprovider.controller.command.impl.client.ClientMakePaymentProcess;
import by.epam.internetprovider.controller.command.impl.client.ClientRequestTariffProcess;
import by.epam.internetprovider.controller.command.impl.gotopage.GotoNextPage;
import by.epam.internetprovider.controller.command.impl.gotopage.GotoPreviousPage;
import by.epam.internetprovider.controller.command.impl.gotopage.GotoRegister;
import by.epam.internetprovider.controller.command.impl.gotopage.GotoRegisterDone;
import by.epam.internetprovider.controller.command.impl.gotopage.GotoTariffInfo;
import by.epam.internetprovider.controller.command.impl.gotopage.GotoTariffs;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdmin;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminAddTariff;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminBanUser;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminDeleteRequest;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminDeleteTariff;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminDeleteUser;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminEditTariff;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminEditUser;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminPayments;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminProcessRequest;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminRequests;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminTariffs;
import by.epam.internetprovider.controller.command.impl.gotopage.admin.GotoAdminUsers;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClient;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClientChangeTariff;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClientDeleteRequest;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClientEditProfile;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClientMakePayment;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClientPayments;
import by.epam.internetprovider.controller.command.impl.gotopage.client.GotoClientRequests;
import by.epam.internetprovider.controller.command.impl.localization.Localization;

/**
 * Provides storage and issuance of commands for Controller's pattern "Command".
 * 
 * @author Pavel Sorokoletov
 */
public class CommandProvider {

	private static final Logger logger = LogManager.getLogger();

	private Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);

	public CommandProvider() {

		commands.put(CommandName.LOG_IN, new LogIn());
		commands.put(CommandName.LOG_OFF, new LogOff());
		commands.put(CommandName.LOCALIZATION, new Localization());
		commands.put(CommandName.REGISTER, new GotoRegister());
		commands.put(CommandName.REG_PROCESS, new Register());
		commands.put(CommandName.GOTO_REGISTER_DONE, new GotoRegisterDone());

		commands.put(CommandName.GOTO_TARIFFS, new GotoTariffs());

		commands.put(CommandName.NEXT_RESULT_PAGE, new GotoNextPage());
		commands.put(CommandName.PREVIOUS_RESULT_PAGE, new GotoPreviousPage());

		commands.put(CommandName.GOTO_CLIENT, new GotoClient());
		commands.put(CommandName.GOTO_ADMIN, new GotoAdmin());
		commands.put(CommandName.GOTO_TARIFF_INFO, new GotoTariffInfo());

		commands.put(CommandName.GOTO_CLIENT_PAYMENTS, new GotoClientPayments());
		commands.put(CommandName.GOTO_CLIENT_MAKE_PAYMENT, new GotoClientMakePayment());
		commands.put(CommandName.GOTO_CLIENT_REQUESTS, new GotoClientRequests());
		commands.put(CommandName.GOTO_CLIENT_DELETE_REQUEST, new GotoClientDeleteRequest());
		commands.put(CommandName.GOTO_CLIENT_CHANGE_TARIFF, new GotoClientChangeTariff());
		commands.put(CommandName.GOTO_CLIENT_EDIT_PROFILE, new GotoClientEditProfile());

		commands.put(CommandName.CLIENT_MAKE_PAYMENT_PROCESS, new ClientMakePaymentProcess());
		commands.put(CommandName.CLIENT_REQUEST_TARIFF_PROCESS, new ClientRequestTariffProcess());
		commands.put(CommandName.CLIENT_DELETE_REQUEST_PROCESS, new ClientDeleteRequestProcess());
		commands.put(CommandName.CLIENT_EDIT_PROFILE_PROCESS, new ClientEditProfileProcess());

		commands.put(CommandName.GOTO_AD_USERS, new GotoAdminUsers());
		commands.put(CommandName.GOTO_AD_TARIFFS, new GotoAdminTariffs());
		commands.put(CommandName.GOTO_AD_REQUESTS, new GotoAdminRequests());
		commands.put(CommandName.GOTO_AD_PAYMENTS, new GotoAdminPayments());
		commands.put(CommandName.GOTO_AD_EDIT_USER, new GotoAdminEditUser());
		commands.put(CommandName.GOTO_AD_BAN_USER, new GotoAdminBanUser());
		commands.put(CommandName.GOTO_AD_DELETE_USER, new GotoAdminDeleteUser());
		commands.put(CommandName.GOTO_AD_EDIT_TARIFF, new GotoAdminEditTariff());
		commands.put(CommandName.GOTO_AD_ADD_TARIFF, new GotoAdminAddTariff());
		commands.put(CommandName.GOTO_AD_DELETE_TARIFF, new GotoAdminDeleteTariff());
		commands.put(CommandName.GOTO_AD_PROCESS_REQUEST, new GotoAdminProcessRequest());
		commands.put(CommandName.GOTO_AD_DELETE_REQUEST, new GotoAdminDeleteRequest());

		commands.put(CommandName.ADMIN_EDIT_USER_PROCESS, new AdminEditUserProcess());
		commands.put(CommandName.ADMIN_BAN_USER_PROCESS, new AdminBanUserProcess());
		commands.put(CommandName.ADMIN_UNBAN_USER_PROCESS, new AdminUnbanUserProcess());
		commands.put(CommandName.ADMIN_DEL_USER_PROCESS, new AdminDeleteUserProcess());
		commands.put(CommandName.ADMIN_EDIT_TARIFF_PROCESS, new AdminEditTariffProcess());
		commands.put(CommandName.ADMIN_ADD_TARIFF_PROCESS, new AdminAddTariffProcess());
		commands.put(CommandName.ADMIN_DEL_TARIFF_PROCESS, new AdminDeleteTariffProcess());
		commands.put(CommandName.ADMIN_DO_REQUEST_PROCESS, new AdminDoRequestProcess());
		commands.put(CommandName.ADMIN_DEL_REQUEST_PROCESS, new AdminDeleteRequestProcess());
	}

	/**
	 * Provides a command by its string name.
	 * 
	 * @param name {@code String} presentation name of {@link Command}.
	 * @return corresponding {@link Command}
	 */
	public Command getCommand(String name) {

		if (name != null) {
			try {
				CommandName commandName = CommandName.valueOf(name.toUpperCase());
				return commands.get(commandName);
			} catch (IllegalArgumentException e) {
				logger.log(Level.DEBUG, "Invalid command in AuthorizationFilter");
			}
		}
		return new WrongCommand();
	}
}
