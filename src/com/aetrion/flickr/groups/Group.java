package com.aetrion.flickr.groups;

/**
 * @author Anthony Eden
 */
public class Group {

    private String id;
    private String name;
    private int members;
    private int online;
    private String chatId;
    private int inChat;
    private String privacy;

    public Group() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public void setMembers(String members) {
        if (members != null) setMembers(Integer.parseInt(members));
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setOnline(String online) {
        if (online != null) setOnline(Integer.parseInt(online));
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getInChat() {
        return inChat;
    }

    public void setInChat(int inChat) {
        this.inChat = inChat;
    }

    public void setInChat(String inChat) {
        if (inChat != null) setInChat(Integer.parseInt(inChat));
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

}
