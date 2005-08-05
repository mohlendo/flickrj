/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.people;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Interface for finding Flickr users.
 *
 * @author Anthony Eden
 */
public abstract class PeopleInterface {
    
    public static final String METHOD_FIND_BY_EMAIL = "flickr.people.findByEmail";
    public static final String METHOD_FIND_BY_USERNAME = "flickr.people.findByUsername";
    public static final String METHOD_GET_INFO = "flickr.people.getInfo";
    public static final String METHOD_GET_ONLINE_LIST = "flickr.people.getOnlineList";
    public static final String METHOD_GET_PUBLIC_GROUPS = "flickr.people.getPublicGroups";
    public static final String METHOD_GET_PUBLIC_PHOTOS = "flickr.people.getPublicPhotos";
    public static final String METHOD_GET_UPLOAD_STATUS = "flickr.people.getUploadStatus";
    
    public static PeopleInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new PeopleInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    
    /**
     * Find the user by their email address.
     *
     * @param email The email address
     * @return The User
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract User findByEmail(String email) throws IOException, SAXException, FlickrException;
    
    /**
     * Find a User by the username.
     *
     * @param username The username
     * @return The User object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract User findByUsername(String username) throws IOException, SAXException, FlickrException;
    
    /**
     * Get info about the specified user.
     *
     * @param userId The user ID
     * @return The User object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract User getInfo(String userId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get the list of current online users.
     *
     * @return The list of online users
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     * @deprecated To be removed from the Flickr API
     */
    public abstract Collection getOnlineList() throws IOException, SAXException, FlickrException;
    
    /**
     * Get a collection of public groups for the user.
     *
     * @param userId The user ID
     * @return The public groups
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getPublicGroups(String userId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a collection of public photos for the specified user ID.
     *
     * @param userId The User ID
     * @param perPage The number of photos per page
     * @param page The page offset
     * @return The collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getPublicPhotos(String userId, int perPage, int page) throws IOException, SAXException,
    FlickrException;
    
    /**
     * Get upload status for the currently authenticated user.
     *
     * Note: Requires authentication with 'read' permission using the new authentication API.
     *
     * @return A User object with upload status data fields filled
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract User getUploadStatus() throws IOException, SAXException, FlickrException;
    
}
