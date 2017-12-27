package by.epam.internetprovider.service.impl.util;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.internetprovider.service.impl.util.Validation;

public class ValidationTest {

	@DataProvider
	public Object[] validBallance() {
		return new Object[] { new BigDecimal("0"), new BigDecimal("100.5"), new BigDecimal("998.67"),
				new BigDecimal("-80.9"), new BigDecimal("-666.666"), new BigDecimal("-999") };
	}

	@DataProvider
	public Object[] invalidBallance() {
		return new Object[] { new BigDecimal("1000"), new BigDecimal("-999.9"), null };
	}

	@Test(dataProvider = "validBallance")
	public void checkValidBallance(BigDecimal ballance) {
		Assert.assertTrue(Validation.checkBallance(ballance));
	}

	@Test(dataProvider = "invalidBallance")
	public void checkInvalidBallance(BigDecimal ballance) {
		Assert.assertFalse(Validation.checkBallance(ballance));
	}

	@DataProvider
	public Object[] validmail() {
		return new Object[] { "testmail@test.com", "more_test@mail.ru", "sample.adress@usa.de" };
	}

	@DataProvider
	public Object[] invalidmail() {
		return new Object[] { null, "testmail@test", "more-test@mail.ru", "sample$.adress@usa.de",
				"bad@adress" };
	}

	@Test(dataProvider = "validmail")
	public void checkValidmail(String email) {
		Assert.assertTrue(Validation.checkEmail(email));
	}

	@Test(dataProvider = "invalidmail")
	public void checkInvalidMail(String email) {
		Assert.assertFalse(Validation.checkEmail(email));
	}

	@DataProvider
	public Object[] validLogin() {
		return new Object[] { "qwErty", "_user1", "john_sm" };
	}

	@DataProvider
	public Object[] invalidLogin() {
		return new Object[] { "1qwErty", "&user1", "john", null };
	}

	@Test(dataProvider = "validLogin")
	public void checkvalidLogin(String login) {
		Assert.assertTrue(Validation.checkLogin(login));
	}

	@Test(dataProvider = "invalidLogin")
	public void checkInvalidLogin(String login) {
		Assert.assertFalse(Validation.checkLogin(login));
	}

	@DataProvider
	public Object[] validName() {
		return new Object[] { "qwErty", "_user1", "john_sm", "11242-34" };
	}

	@DataProvider
	public Object[] invalidName() {
		return new Object[] { "", "na me", "  ", null };
	}

	@Test(dataProvider = "validName")
	public void checkvalidName(String name) {
		Assert.assertTrue(Validation.checkName(name));
	}

	@Test(dataProvider = "invalidName")
	public void checkInvalidName(String name) {
		Assert.assertFalse(Validation.checkName(name));
	}

	}
