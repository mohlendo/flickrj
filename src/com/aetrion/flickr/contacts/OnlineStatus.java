package com.aetrion.flickr.contacts;

/**
 * @author Anthony Eden
 */
public class OnlineStatus {

    public static final int OFFLINE_TYPE = 0;
    public static final int AWAY_TYPE = 1;
    public static final int ONLINE_TYPE = 2;

    public static final OnlineStatus OFFLINE = new OnlineStatus(OFFLINE_TYPE);
    public static final OnlineStatus AWAY = new OnlineStatus(AWAY_TYPE);
    public static final OnlineStatus ONLINE = new OnlineStatus(ONLINE_TYPE);

    private int type;

    private OnlineStatus(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static OnlineStatus fromType(int type) {
        switch (type) {
            case OFFLINE_TYPE:
                return OFFLINE;
            case AWAY_TYPE:
                return AWAY;
            case ONLINE_TYPE:
                return ONLINE;
            default:
                throw new IllegalArgumentException("Unsupported online type: " + type);
        }
    }

    public static OnlineStatus fromType(String type) {
        return fromType(Integer.parseInt(type));
    }
}
