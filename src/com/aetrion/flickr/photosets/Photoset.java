/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photosets;

import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;

/**
 * Meta information about a photoset.  To retrieve the photos in the photoset use PhotosetsInterface.getPhotos().
 *
 * @author Anthony Eden
 */
public class Photoset {

    private String id;
    private String url;
    private User owner;
    private Photo primaryPhoto;
    private String secret;
    private String server;
    private int photoCount;
    private String title;
    private String description;

    public Photoset() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Photo getPrimaryPhoto() {
        return primaryPhoto;
    }

    public void setPrimaryPhoto(Photo primaryPhoto) {
        this.primaryPhoto = primaryPhoto;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public void setPhotoCount(String photoCount) {
        if (photoCount != null) {
            setPhotoCount(Integer.parseInt(photoCount));
        }
    }

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

}
