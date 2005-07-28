/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.urls;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Interface for testing Flickr connectivity.
 *
 * @author Anthony Eden
 */
public abstract class UrlsInterface {

    public static final String METHOD_GET_GROUP = "flickr.urls.getGroup";
    public static final String METHOD_GET_USER_PHOTOS = "flickr.urls.getUserPhotos";
    public static final String METHOD_GET_USER_PROFILE = "flickr.urls.getUserProfile";
    public static final String METHOD_LOOKUP_GROUP = "flickr.urls.lookupGroup";
    public static final String METHOD_LOOKUP_USER = "flickr.urls.lookupUser";

    public static UrlsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new UrlsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }

    /**
     * Get the group URL for the specified group ID
     *
     * @param groupId The group ID
     * @return The group URL
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract String getGroup(String groupId) throws IOException, SAXException, FlickrException;

    /**
     * Get the URL for the user's photos.
     *
     * @param userId The user ID
     * @return The user photo URL
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract String getUserPhotos(String userId) throws IOException, SAXException, FlickrException;

    /**
     * Get the URL for the user's profile.
     *
     * @param userId The user ID
     * @return The URL
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract String getUserProfile(String userId) throws IOException, SAXException, FlickrException;

    /**
     * Lookup the group name for the specified URL.
     *
     * @param url The url
     * @return The group name
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract String lookupGroup(String url) throws IOException, SAXException, FlickrException;

    /**
     * Lookup the username for the specified User URL.
     *
     * @param url The user profile URL
     * @return The username
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract String lookupUser(String url) throws IOException, SAXException, FlickrException;

}
