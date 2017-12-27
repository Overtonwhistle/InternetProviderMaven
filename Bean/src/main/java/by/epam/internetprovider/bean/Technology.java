package by.epam.internetprovider.bean;

import java.io.Serializable;

/**
 * This class is a Java Bean entity. Describes technology, used in
 * {@link Tariff}.
 * 
 * @author Pavel Sorokoletov
 *         <p>
 */
public class Technology implements Serializable {

	private static final long serialVersionUID = -7586674858246499706L;
	private int id;
	private boolean needRentEquipment;
	private String title;

	public Technology() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isNeedRentEquipment() {
		return needRentEquipment;
	}

	public void setNeedRentEquipment(boolean needRentEquipment) {
		this.needRentEquipment = needRentEquipment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (needRentEquipment ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Technology other = (Technology) obj;
		if (id != other.id)
			return false;
		if (needRentEquipment != other.needRentEquipment)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Technology [id=" + id + ", title=" + title + ", needRentEquipment="
				+ needRentEquipment + "]";
	}
}
