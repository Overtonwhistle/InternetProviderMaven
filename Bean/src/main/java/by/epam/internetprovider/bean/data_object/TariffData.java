package by.epam.internetprovider.bean.data_object;

import java.io.Serializable;

import by.epam.internetprovider.bean.Tariff;

/**
 * This class is a simply Data Transer Object entity. It is uses for data
 * storage of {@link Tariff} object, all fields presents as String values.
 * 
 * @author Pavel Sorokoletov
 */

public class TariffData implements Serializable {

	private static final long serialVersionUID = -2568293733642879638L;

	private static final String DEFAULT_TARIFF_TITLE = "New Tariff";
	private static final String DEFAULT_TARIFF_DESCRIPTION = "New tariff description";
	private static final String DEFAULT_TARIFF_M_COST = "0";
	private static final String DEFAULT_TARIFF_OVERLIMIT_COST = "0";
	private static final String DEFAULT_M_LIMIT = "0";

	private String id;
	private String technologyId;
	private String unlimTraffic;
	private String monthlyDataLimit;
	private String monthlyCost;
	private String overloadLimitCost;
	private String title;
	private String description;

	/**
	 * Default constructor. Initializes some fields with default values, used for
	 * creating new, or editing a {@link Tariff} object.
	 */
	public TariffData() {

		this.setTitle(DEFAULT_TARIFF_TITLE);
		this.setDescription(DEFAULT_TARIFF_DESCRIPTION);
		this.setMonthlyCost(DEFAULT_TARIFF_M_COST);
		this.setOverloadLimitCost(DEFAULT_TARIFF_OVERLIMIT_COST);
		this.setMonthlyDataLimit(DEFAULT_M_LIMIT);
	}

	/**
	 * Constructor, accepting as a parameter a {@link Tariff} object. Used for
	 * editing an existing {@link Tariff} object.
	 * 
	 * @param tariff
	 *            {@code Tariff} object.
	 */
	public TariffData(Tariff tariff) {

		this.id = String.valueOf(tariff.getId());
		this.technologyId = String.valueOf(tariff.getTechnologyId());
		this.unlimTraffic = String.valueOf(tariff.isUnlimTraffic());
		this.monthlyDataLimit = String.valueOf(tariff.getMonthlyDataLimit());
		this.monthlyCost = String.valueOf(tariff.getMonthlyCost());
		this.overloadLimitCost = String.valueOf(tariff.getOverloadLimitCost());
		this.title = tariff.getTitle();
		this.description = tariff.getDescription();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(String technologyId) {
		this.technologyId = technologyId;
	}

	public String getUnlimTraffic() {
		return unlimTraffic;
	}

	public void setUnlimTraffic(String unlimTraffic) {
		this.unlimTraffic = unlimTraffic;
	}

	public String getMonthlyDataLimit() {
		return monthlyDataLimit;
	}

	public void setMonthlyDataLimit(String monthlyDataLimit) {
		this.monthlyDataLimit = monthlyDataLimit;
	}

	public String getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(String monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public String getOverloadLimitCost() {
		return overloadLimitCost;
	}

	public void setOverloadLimitCost(String overloadLimitCost) {
		this.overloadLimitCost = overloadLimitCost;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TariffData [id=" + id + ", technologyId=" + technologyId + ", unlimTraffic="
				+ unlimTraffic + ", monthlyDataLimit=" + monthlyDataLimit + ", monthlyCost="
				+ monthlyCost + ", overloadLimitCost=" + overloadLimitCost + ", title=" + title
				+ ", description=" + description + "]";
	}

}
