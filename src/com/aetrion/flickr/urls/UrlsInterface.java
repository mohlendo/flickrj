package com.aetrion.flickr.urls;

import com.aetrion.flickr.REST;

/**
 * Interface for testing Flickr connectivity.
 *
 * @author Anthony Eden
 */
public class UrlsInterface {

    public static final String METHOD_GET_GROUP = "flickr.urls.getGroup";
    public static final String METHOD_GET_USER_PHOTOS = "flickr.urls.getUserPhotos";
    public static final String METHOD_GET_USER_PROFILE = "flickr.urls.getUserProfile";
    public static final String METHOD_LOOKUP_GROUP = "flickr.urls.lookupGroup";
    public static final String METHOD_LOOKUP_USER = "flickr.urls.lookupUser";

    private String apiKey;
    private REST restInterface;

    /**
     * Construct a UrlsInterface.
     *
     * @param apiKey The API key
     * @param restInterface The REST interface
     */
    public UrlsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

}
