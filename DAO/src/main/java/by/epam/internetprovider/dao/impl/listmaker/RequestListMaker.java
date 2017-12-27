package by.epam.internetprovider.dao.impl.listmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.bean.Request;

public class RequestListMaker implements ListMaker {

	private static final RequestListMaker instance = new RequestListMaker();

	private static final int REQUEST_ID_FIELD = 1;
	private static final int REQUEST_DATE_FIELD = 2;
	private static final int REQUEST_PROCESS_DATE_FIELD = 3;
	private static final int REQUEST_PROCESSED_BY_FIELD = 4;
	private static final int REQUEST_USER_ID_FIELD = 5;
	private static final int REQUEST_TARIFF_ID_FIELD = 6;

	private RequestListMaker() {
	};

	public static RequestListMaker getIstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void makeList(ResultSet rs, List<T> list) throws SQLException {

		list.clear();

		while (rs.next()) {
			Request request = new Request();

			request.setId(rs.getInt(REQUEST_ID_FIELD));
			request.setRequestDate(rs.getTimestamp(REQUEST_DATE_FIELD));
			request.setProcessedDate(rs.getTimestamp(REQUEST_PROCESS_DATE_FIELD));
			request.setProcessedBy(rs.getInt(REQUEST_PROCESSED_BY_FIELD));
			request.setUserId(rs.getInt(REQUEST_USER_ID_FIELD));
			request.setTariffId(rs.getInt(REQUEST_TARIFF_ID_FIELD));

			list.add((T) request);
		}

	}

}
