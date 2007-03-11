package com.aetrion.flickr.activity;

import java.util.Collection;
import java.util.Date;

/**
 * <event type="comment" commentid="2534331-103114080-72157594354563260" user="2034
 * 8269@N00" username="x-mago" dateadded="1162339918">Well done!</event>
 * @author mago
 *
 */
public class Event {
    private String id;
    private String type;
    private String user;
    private String username;
    private String value;
    private Date dateadded;

    public Event() {
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public void setDateadded(String dateAdded) {
        if (dateAdded == null || "".equals(dateAdded)) return;
        setDateadded(new Date(Long.parseLong(dateAdded) * (long) 1000));
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
