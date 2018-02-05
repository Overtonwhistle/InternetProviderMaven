package by.epam.internetprovider.dao.listmaker.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.internetprovider.bean.Payment;
import by.epam.internetprovider.dao.listmaker.IListMaker;

public class PaymentListMaker implements IListMaker<Payment> {

	private static final PaymentListMaker instance = new PaymentListMaker();

	private static final int PAYMENT_ID_FIELD = 1;
	private static final int PAYMENT_DATE_FIELD = 2;
	private static final int PAYMENT_AMOUNT_FIELD = 3;
	private static final int PAYMENT_USER_ID_FIELD = 4;

	private PaymentListMaker() {
	}

	public static PaymentListMaker getIstance() {
		return instance;
	}

	@Override
	public List<Payment> makeList(ResultSet rs) throws SQLException {

		List<Payment> list = new ArrayList<>();

		while (rs.next()) {
			Payment payment = new Payment();
			payment.setId(rs.getInt(PAYMENT_ID_FIELD));
			payment.setPaymentDate(rs.getTimestamp(PAYMENT_DATE_FIELD));
			payment.setAmount(rs.getBigDecimal(PAYMENT_AMOUNT_FIELD));
			payment.setUserId(rs.getInt(PAYMENT_USER_ID_FIELD));
			list.add(payment);
		}
		return list;
	}

}
