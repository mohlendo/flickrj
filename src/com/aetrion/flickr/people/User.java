/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.people;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

import com.aetrion.flickr.contacts.OnlineStatus;

/**
 * @author Anthony Eden
 * @version $Id: User.java,v 1.11 2007/08/05 13:42:32 x-mago Exp $
 */
public class User implements Serializable {


	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String id;
    private String username;
    private boolean admin;
    private boolean pro;
    private int iconFarm;
    private int iconServer;
    private String realName;
    private String location;
    private Date photosFirstDate;
    private Date photosFirstDateTaken;
    private Date faveDate;
    private int photosCount;
    private OnlineStatus online;
    private String awayMessage;
    private int bandwidthMax;
    private int bandwidthUsed;
    private int filesizeMax;
    private String mbox_sha1sum;

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    public int getIconFarm() {
        return iconFarm;
    }

    public void setIconFarm(int iconFarm) {
        this.iconFarm = iconFarm;
    }

    public void setIconFarm(String iconFarm) {
        if (iconFarm != null) setIconFarm(Integer.parseInt(iconFarm));
    }

    public int getIconServer() {
        return iconServer;
    }

    public void setIconServer(int iconServer) {
        this.iconServer = iconServer;
    }

    public void setIconServer(String iconServer) {
        if (iconServer != null) setIconServer(Integer.parseInt(iconServer));
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Construct the BuddyIconUrl. Either the default, or assembled
     * from farm, iconserver and nsid.
     *
     * @see http://flickr.com/services/api/misc.buddyicons.html
     * @return The BuddyIconUrl
     */
    public String getBuddyIconUrl() {
        /**
         * The default-URL, if the iconServer equals 0.
         */
        String iconUrl = "http://www.flickr.com/images/buddyicon.jpg";
        if (iconServer > 0) {
            iconUrl = "http://farm"
            + iconFarm + ".static.flickr.com/"
            + iconServer + "/buddyicons/"
            + id + ".jpg";
        }
        return iconUrl;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getPhotosFirstDate() {
        return photosFirstDate;
    }

    public void setPhotosFirstDate(Date photosFirstDate) {
        this.photosFirstDate = photosFirstDate;
    }

    public void setPhotosFirstDate(long photosFirstDate) {
        setPhotosFirstDate(new Date(photosFirstDate));
    }

    public void setPhotosFirstDate(String photosFirstDate) {
        if (photosFirstDate != null) {
            setPhotosFirstDate(Long.parseLong(photosFirstDate) * (long) 1000);
        }
    }

    public Date getPhotosFirstDateTaken() {
        return photosFirstDateTaken;
    }

    public void setPhotosFirstDateTaken(Date photosFirstDateTaken) {
        this.photosFirstDateTaken = photosFirstDateTaken;
    }

    public void setPhotosFirstDateTaken(String photosFirstDateTaken) {
        if (photosFirstDateTaken != null) {
            try {
                setPhotosFirstDateTaken(DF.parse(photosFirstDateTaken));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setFaveDate(String faveDate) {
        setFaveDate(Long.parseLong(faveDate) * (long) 1000);
    }

    public void setFaveDate(long faveDate) {
        setFaveDate(new Date(faveDate));
    }

    /**
     * Date when User has faved a Photo.<br>
     * flickr.photos.getFavorites returns person-data where this
     * Date will be set.
     *
     * @param faveDate
     */
    public void setFaveDate(Date faveDate) {
        this.faveDate = faveDate;
    }

    public Date getFaveDate() {
        return faveDate;
    }

    public int getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }

    public void setPhotosCount(String photosCount) {
        if (photosCount != null) {
            setPhotosCount(Integer.parseInt(photosCount));
        }
    }

    public OnlineStatus getOnline() {
        return online;
    }

    public void setOnline(OnlineStatus online) {
        this.online = online;
    }

    public String getAwayMessage() {
        return awayMessage;
    }

    public void setAwayMessage(String awayMessage) {
        this.awayMessage = awayMessage;
    }

    public int getBandwidthMax() {
        return bandwidthMax;
    }

    public void setBandwidthMax(int bandwidthMax) {
        this.bandwidthMax = bandwidthMax;
    }

    public void setBandwidthMax(String bandwidthMax) {
        if (bandwidthMax != null) {
            setBandwidthMax(Integer.parseInt(bandwidthMax));
        }
    }

    public int getBandwidthUsed() {
        return bandwidthUsed;
    }

    public void setBandwidthUsed(int bandwidthUsed) {
        this.bandwidthUsed = bandwidthUsed;
    }

    public void setBandwidthUsed(String bandwidthUsed) {
        if (bandwidthUsed != null) {
            setBandwidthUsed(Integer.parseInt(bandwidthUsed));
        }
    }

    public int getFilesizeMax() {
        return filesizeMax;
    }

    public void setFilesizeMax(int filesizeMax) {
        this.filesizeMax = filesizeMax;
    }

    public void setFilesizeMax(String filesizeMax) {
        if (filesizeMax != null) {
            setFilesizeMax(Integer.parseInt(filesizeMax));
        }
    }

    public void setMbox_sha1sum(String mbox_sha1sum) {
        this.mbox_sha1sum = mbox_sha1sum;
    }

    public String getMbox_sha1sum() {
        return this.mbox_sha1sum;
    }
}
