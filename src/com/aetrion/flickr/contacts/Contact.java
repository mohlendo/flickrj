package com.aetrion.flickr.contacts;

/**
 * Class representing a Flickr contact.
 *
 * @author Anthony Eden
 */
public class Contact {

    private String id;
    private String username;
    private String realName;
    private boolean friend;
    private boolean family;
    private boolean ignored;
    private OnlineStatus online;
    private String awayMessage;

    public Contact() {

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public boolean isFamily() {
        return family;
    }

    public void setFamily(boolean family) {
        this.family = family;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public OnlineStatus getOnline() {
        return online;
    }

    public void setOnline(OnlineStatus online) {
        this.online = online;
    }

    /**
     * Get the contact's away message.  This method may return null if the contact online status is not 'away'.
     *
     * @return The away message or null
     */
    public String getAwayMessage() {
        return awayMessage;
    }

    public void setAwayMessage(String awayMessage) {
        this.awayMessage = awayMessage;
    }

}
