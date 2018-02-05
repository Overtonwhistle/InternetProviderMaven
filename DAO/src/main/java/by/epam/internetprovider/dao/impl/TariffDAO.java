/**
 * @author Pavel Sorokoletov
 */
package by.epam.internetprovider.dao.impl;

import static by.epam.internetprovider.dao.impl.CommonMethods.executeUpdate;
import static by.epam.internetprovider.dao.impl.CommonMethods.getExecuteUpdateResult;
import static by.epam.internetprovider.dao.impl.CommonMethods.getList;
import static by.epam.internetprovider.dao.impl.Constant.ADD_TARIFF_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.DELETE_TARIFF_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.EDIT_TARIFF_QUERY;
import static by.epam.internetprovider.dao.impl.Constant.FIRST_INDEX;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_COST_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_DESCRIPTION_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_IS_UNLIM_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_LIMIT_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_OVERLOAD_COST_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_TECH_ID_FIELD;
import static by.epam.internetprovider.dao.impl.Constant.TARIFF_TITLE_FIELD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import by.epam.internetprovider.bean.Tariff;
import by.epam.internetprovider.bean.Technology;
import by.epam.internetprovider.dao.ITariffDAO;
import by.epam.internetprovider.dao.database.connectionpool.exception.ConnectionPoolException;
import by.epam.internetprovider.dao.database.connectionpool.impl.ConnectionPoolOne;
import by.epam.internetprovider.dao.exception.DAOException;
import by.epam.internetprovider.dao.exception.DAOTariffNotFoundException;
import by.epam.internetprovider.dao.listmaker.impl.TariffListMaker;
import by.epam.internetprovider.dao.listmaker.impl.TechnologyListMaker;
import by.epam.internetprovider.dao.searchfilter.FilterParameter;
import by.epam.internetprovider.dao.searchfilter.SubFilter;
import by.epam.internetprovider.dao.searchfilter.impl.tariff.TariffSearchFilter;
import by.epam.internetprovider.dao.searchfilter.impl.technology.TechnologySearchFilter;

/**
 * @author Pavel Sorokoletov
 * @version 1.0 Provides data access methods for MySQL BD
 *
 */

public class TariffDAO implements ITariffDAO {

	ConnectionPoolOne pool = ConnectionPoolOne.getInstance();

	@Override
	public boolean addTariff(Tariff tariff) throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(ADD_TARIFF_QUERY)) {

			ps.setString(TARIFF_TITLE_FIELD, tariff.getTitle());
			ps.setBigDecimal(TARIFF_COST_FIELD, tariff.getMonthlyCost());
			ps.setLong(TARIFF_LIMIT_FIELD, tariff.getMonthlyDataLimit());
			ps.setBoolean(TARIFF_IS_UNLIM_FIELD, tariff.isUnlimTraffic());
			ps.setBigDecimal(TARIFF_OVERLOAD_COST_FIELD, tariff.getOverloadLimitCost());
			ps.setString(TARIFF_DESCRIPTION_FIELD, tariff.getDescription());
			ps.setInt(TARIFF_TECH_ID_FIELD, tariff.getTechnologyId());

			return getExecuteUpdateResult(ps);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in addTariff()", e);

		} catch (SQLException e1) {
			throw new DAOException("SQL Error in addTariff()", e1);

		}
	}

	@Override
	public boolean editTariff(int tariffId, Tariff tariff) throws DAOException {

		try (Connection con = pool.getConnection();
				PreparedStatement ps = con.prepareStatement(EDIT_TARIFF_QUERY + tariffId)) {

			ps.setString(TARIFF_TITLE_FIELD, tariff.getTitle());
			ps.setBigDecimal(TARIFF_COST_FIELD, tariff.getMonthlyCost());
			ps.setLong(TARIFF_LIMIT_FIELD, tariff.getMonthlyDataLimit());
			ps.setBoolean(TARIFF_IS_UNLIM_FIELD, tariff.isUnlimTraffic());
			ps.setBigDecimal(TARIFF_OVERLOAD_COST_FIELD, tariff.getOverloadLimitCost());
			ps.setString(TARIFF_DESCRIPTION_FIELD, tariff.getDescription());
			ps.setInt(TARIFF_TECH_ID_FIELD, tariff.getTechnologyId());

			return getExecuteUpdateResult(ps);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Error getting connection in editTariff()", e);

		} catch (SQLException e1) {
			throw new DAOException("SQL Error in editTariff()", e1);
		}

	}

	@Override
	public Tariff getTariff(int tariffId) throws DAOTariffNotFoundException, DAOException {

		TariffSearchFilter filter = new TariffSearchFilter();
		filter.addSubFilter(new SubFilter(FilterParameter.TARIFF_ID, Integer.toString(tariffId)));

		List<Tariff> tariffsList = getTariffsList(filter);

		if (tariffsList.isEmpty()) {
			throw new DAOTariffNotFoundException("Tariff not found in getTariff()");
		}

		return tariffsList.get(FIRST_INDEX);
	}

	@Override
	public boolean deleteTariff(int tariffId) throws DAOException {

		return executeUpdate(DELETE_TARIFF_QUERY + tariffId);

	}

	@Override
	public List<Tariff> getTariffsList(TariffSearchFilter filter) throws DAOException {

		return getList(filter.getSearchQuery(), TariffListMaker.getIstance());
	}

	@Override
	public List<Technology> getTechnologiesList(TechnologySearchFilter filter) throws DAOException {

		return getList(filter.getSearchQuery(), TechnologyListMaker.getIstance());
	}

}
