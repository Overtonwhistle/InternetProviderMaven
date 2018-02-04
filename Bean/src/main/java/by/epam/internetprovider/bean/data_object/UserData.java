package by.epam.internetprovider.bean.data_object;

import java.io.Serializable;

import by.epam.internetprovider.bean.User;

/**
 * This class is a simply Data Transer Object entity. It is uses for data
 * storage of {@link User} object, all fields presents as {@code String} values.
 * 
 * @author Pavel Sorokoletov
 */

public class UserData implements Serializable {

	private static final long serialVersionUID = -3124956375266837181L;
	private String role;
	private String id;
	private String tariffId;
	private String monthlyDataUsage;
	private String totalDataUsage;
	private String accountBallance;
	private String login;
	private String password;
	private String repeatePassword;
	private String currentPassword;
	private String email;
	private String firstName;
	private String lastName;
	private String passportNumber;
	private String regDate;

	public UserData() {
	}

	/**
	 * Constructor, accepting as a parameter a {@link User} object. Used for editing
	 * an existing and creating new {@link User} object.
	 * 
	 * @param user {@code User} object.
	 */
	public UserData(User user) {
		this.role = String.valueOf(user.getRole());
		this.id = String.valueOf(user.getId());
		this.tariffId = String.valueOf(user.getTariffId());
		this.monthlyDataUsage = String.valueOf(user.getMonthlyDataUsage());
		this.totalDataUsage = String.valueOf(user.getTotalDataUsage());
		this.accountBallance = String.valueOf(user.getAccountBallance());
		this.login = user.getLogin();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.passportNumber = user.getPassportNumber();
		this.regDate = String.valueOf(user.getRegDate());
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTariffId() {
		return tariffId;
	}

	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}

	public String getMonthlyDataUsage() {
		return monthlyDataUsage;
	}

	public void setMonthlyDataUsage(String monthlyDataUsage) {
		this.monthlyDataUsage = monthlyDataUsage;
	}

	public String getTotalDataUsage() {
		return totalDataUsage;
	}

	public void setTotalDataUsage(String totalDataUsage) {
		this.totalDataUsage = totalDataUsage;
	}

	public String getAccountBallance() {
		return accountBallance;
	}

	public void setAccountBallance(String accountBallance) {
		this.accountBallance = accountBallance;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatePassword() {
		return password;
	}

	public void setRepeatePassword(String password) {
		this.password = password;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserData [role=" + role + ", id=" + id + ", tariffId=" + tariffId
				+ ", monthlyDataUsage=" + monthlyDataUsage + ", totalDataUsage=" + totalDataUsage
				+ ", accountBallance=" + accountBallance + ", login=" + login + ", password="
				+ password + ", repeatePassword=" + repeatePassword + ", currentPassword="
				+ currentPassword + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", passportNumber=" + passportNumber + ", regDate=" + regDate + "]";
	}

}
