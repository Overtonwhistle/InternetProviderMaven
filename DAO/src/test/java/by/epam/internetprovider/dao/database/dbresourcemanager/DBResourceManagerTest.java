package by.epam.internetprovider.dao.database.dbresourcemanager;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.internetprovider.dao.database.dbresourcemanager.DBResourceManager;
import by.epam.internetprovider.dao.database.dbresourcemanager.exception.ResourceManagerException;

public class DBResourceManagerTest {

	DBResourceManager dbResourseManager = DBResourceManager.getInstance();

	@DataProvider
	public Object[][] valideNames() {
		return new Object[][] { { "db.driver", "com.mysql.jdbc.Driver" },
				{ "db.url", "jdbc:mysql://127.0.0.1/internet_provider?useSSL=false" },
				{ "db.user", "root" }, { "db.password", "root" }, { "db.poolsize", "5" }, };
	}

	@Test
	public void getInstance() throws ResourceManagerException {
		Object dbResourseManagerTest = DBResourceManager.getInstance();
		Assert.assertEquals(dbResourseManagerTest.getClass(), DBResourceManager.class);
	}

	@Test(dataProvider = "valideNames")
	public void getValueCorrectValues(String name, String value) throws ResourceManagerException {
		Assert.assertEquals(dbResourseManager.getValue(name), value);

	}

	@Test(expectedExceptions = ResourceManagerException.class)
	public void getValueIncorrect() throws ResourceManagerException {
		dbResourseManager.getValue("wrong value");
	}
}
