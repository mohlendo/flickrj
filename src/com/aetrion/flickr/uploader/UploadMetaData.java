/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.uploader;

import java.util.Collection;

/**
 * @author Anthony Eden
 */
public class UploadMetaData {

    private String title;
    private String description;
    private Collection tags;
    private boolean publicFlag;
    private boolean friendFlag;
    private boolean familyFlag;

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

    public Collection getTags() {
        return tags;
    }

    public void setTags(Collection tags) {
        this.tags = tags;
    }

    public boolean isPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(boolean publicFlag) {
        this.publicFlag = publicFlag;
    }

    public boolean isFriendFlag() {
        return friendFlag;
    }

    public void setFriendFlag(boolean friendFlag) {
        this.friendFlag = friendFlag;
    }

    public boolean isFamilyFlag() {
        return familyFlag;
    }

    public void setFamilyFlag(boolean familyFlag) {
        this.familyFlag = familyFlag;
    }

}
