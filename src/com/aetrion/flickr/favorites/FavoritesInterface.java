/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.favorites;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Interface for working with Flickr favorites.
 *
 * @author Anthony Eden
 */
public abstract class FavoritesInterface {

    public static final String METHOD_ADD = "flickr.favorites.add";
    public static final String METHOD_GET_LIST = "flickr.favorites.getList";
    public static final String METHOD_GET_PUBLIC_LIST = "flickr.favorites.getPublicList";
    public static final String METHOD_REMOVE = "flickr.favorites.remove";

    public static FavoritesInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new FavoritesInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }

    /**
     * Add a photo to the user's favorites.
     *
     * @param photoId The photo ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void add(String photoId) throws IOException, SAXException, FlickrException;

    /**
     * Get the collection of favorites for the calling user or the specified user ID.
     *
     * @param userId The optional user ID.  Null value will be ignored.
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The page to view.  Values <= 0 will be ignored.
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     */
    public Collection getList(String userId, int perPage, int page) throws IOException, SAXException, FlickrException {
        return getList(userId, perPage, page, null);
    }

    /**
     * Get the collection of favorites for the calling user or the specified user ID.
     *
     * @param userId The optional user ID.  Null value will be ignored.
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The page to view.  Values <= 0 will be ignored.
     * @param extras An array of Strings representing extra parameters to send
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     */
    public abstract Collection getList(String userId, int perPage, int page, String[] extras) throws IOException,
            SAXException, FlickrException;

    /**
     * Get the specified user IDs public contacts.
     *
     * @param userId The user ID
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The optional page to view.  Values <= 0 will be ignored
     * @return A Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Collection getPublicList(String userId, int perPage, int page) throws FlickrException, IOException,
            SAXException {
        return getPublicList(userId, perPage, page, null);
    }

    /**
     * Get the specified user IDs public contacts.
     *
     * @param userId The user ID
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The optional page to view.  Values <= 0 will be ignored
     * @param extras A String array of extra parameters to send
     * @return A Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getPublicList(String userId, int perPage, int page, String[] extras) throws IOException, SAXException, FlickrException;

    /**
     * Remove the specified photo from the user's favorites.
     *
     * @param photoId The photo id
     */
    public abstract void remove(String photoId) throws IOException, SAXException, FlickrException;

}
