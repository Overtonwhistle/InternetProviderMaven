package by.epam.internetprovider.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This class is a Java Bean entity. Stores information about {@link User} blocking.
 * 
 * @author Pavel Sorokoletov
 *         <p>
 */
public class Ban implements Serializable {

	private static final long serialVersionUID = -7965010461537728770L;
	private int id;
	private int banReason;
	private int userId;
	private String comment;
	private Timestamp banDate;
	private Timestamp unbanDate;

	public Ban() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getBanDate() {
		return banDate;
	}

	public void setBanDate(Timestamp setDate) {
		this.banDate = setDate;
	}

	public Timestamp getUnbanDate() {
		return unbanDate;
	}

	public void setUnbanDate(Timestamp resetDate) {
		this.unbanDate = resetDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getBanReason() {
		return banReason;
	}

	public void setBanReason(int banReason) {
		this.banReason = banReason;
	}

	public int getUser() {
		return userId;
	}

	public void setUser(int user) {
		this.userId = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + banReason;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + id;
		result = prime * result + ((unbanDate == null) ? 0 : unbanDate.hashCode());
		result = prime * result + ((banDate == null) ? 0 : banDate.hashCode());
		result = prime * result + userId;
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
		Ban other = (Ban) obj;
		if (banReason != other.banReason)
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id != other.id)
			return false;
		if (unbanDate == null) {
			if (other.unbanDate != null)
				return false;
		} else if (!unbanDate.equals(other.unbanDate))
			return false;
		if (banDate == null) {
			if (other.banDate != null)
				return false;
		} else if (!banDate.equals(other.banDate))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ban [id=" + id + ", setDate=" + banDate + ", resetDate=" + unbanDate + ", comment="
				+ comment + ", banReason=" + banReason + ", user=" + userId + "]";
	}
}
