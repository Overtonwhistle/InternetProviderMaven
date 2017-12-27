package by.epam.internetprovider.bean;

import java.io.Serializable;

/**
 * This class is a Java Bean entity. Describes {@link Ban} reason.
 * 
 * @author Pavel Sorokoletov
 *         <p>
 */
public class BanReason implements Serializable {

	private static final long serialVersionUID = -3233563711257045957L;
	private int id;
	private String title;

	public BanReason() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		BanReason other = (BanReason) obj;
		if (id != other.id)
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
		return "BanReason [id=" + id + ", title=" + title + "]";
	}

}