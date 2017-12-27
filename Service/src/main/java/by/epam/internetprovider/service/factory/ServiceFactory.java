package by.epam.internetprovider.service.factory;

import by.epam.internetprovider.service.IInternetProviderService;
import by.epam.internetprovider.service.impl.InternetProviderService;

/**
 * Simple singletone-based 'Factory' pattern realisation.
 * 
 * @author Pavel Sorokoletov
 */
public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();

	private final IInternetProviderService internetProviderService = new InternetProviderService();

	/**
	 *
	 * @return the istance of {@link ServiceFactory}
	 */
	public static ServiceFactory getInstance() {
		return instance;
	}
	/**
	 * Gets a specific InternetProviderDAO object.
	 *
	 * @return {@link IInternetProviderService} object
	 */
	public IInternetProviderService getServiceImpl() {
		return internetProviderService;
	}

}
