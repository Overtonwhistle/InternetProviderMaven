package by.epam.internetprovider.dao.impl.listmaker;
/**
 * Describes a method for obtaining a list of all ResultSet object data
 * 
 * @author Pavel Sorokoletov
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ListMaker {

	/**
	 * Adds in the recieved {@link List} all results from the object
	 * {@link ResultSet}.
	 *
	 * @param <T> the type of objects in List.
	 * @param rs {@link ResultSet} object, encapsulating the results of query
	 *            execution.
	 * @param resultList {@link List} for store all results.
	 * @throws SQLException
	 */
	public <T> void makeList(ResultSet rs, List<T> resultList) throws SQLException;

}
