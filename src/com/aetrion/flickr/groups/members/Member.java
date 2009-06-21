package com.aetrion.flickr.groups.members;

/**
 * Group-member.
 *
 * @author mago
 * @version $Id: Member.java,v 1.1 2009/06/21 19:55:15 x-mago Exp $
 */

public class Member {
    private String id;
    private String userName;
    private int iconFarm;
    private int iconServer;
    private String memberType;

    public static final String TYPE_MEMBER = "2";
    public static final String TYPE_MODERATOR = "3";
    public static final String TYPE_ADMIN = "4";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIconFarm() {
        return iconFarm;
    }

    public void setIconFarm(String iconFarm) {
        if (iconFarm != null) setIconFarm(Integer.parseInt(iconFarm));
    }

    public void setIconFarm(int iconFarm) {
        this.iconFarm = iconFarm;
    }

    public int getIconServer() {
        return iconServer;
    }

    public void setIconServer(String iconServer) {
        if (iconServer != null) setIconServer(Integer.parseInt(iconServer));
    }

    public void setIconServer(int iconServer) {
        this.iconServer = iconServer;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
