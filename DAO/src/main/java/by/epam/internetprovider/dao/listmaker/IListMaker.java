package by.epam.internetprovider.dao.listmaker;
/**
 * Describes a method for obtaining a list of all ResultSet object data
 * 
 * @author Pavel Sorokoletov
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.dao.database.connectionpool.impl.ConnectionPoolOne;

public interface IListMaker <T> {

	static ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	/**
	 * Takes the {@link ResultSet} object, encapsulating the result of a data selection
	 * from the database table, and returns a {@link List} containing the
	 * corresponding objects. The type of the object is defined in a specific
	 * implementation of the interface.
	 *
	 * @param <T> the type of objects in List.
	 * @param rs {@link ResultSet} object, encapsulating the results of query
	 *            execution.
	 * @throws SQLException
	 */
	public List<T> makeList(ResultSet rs) throws SQLException;

}
