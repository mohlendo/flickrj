/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.groups.pools;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.photos.PhotoContext;

/**
 * @author Anthony Eden
 */
public abstract class PoolsInterface {
    
    public static final String METHOD_ADD = "flickr.groups.pools.add";
    public static final String METHOD_GET_CONTEXT = "flickr.groups.pools.getContext";
    public static final String METHOD_GET_GROUPS = "flickr.groups.pools.getGroups";
    public static final String METHOD_GET_PHOTOS = "flickr.groups.pools.getPhotos";
    public static final String METHOD_REMOVE = "flickr.groups.pools.remove";
    
    public static PoolsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new PoolsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    /**
     * Add a photo to a group's pool.
     *
     * @param photoId The photo ID
     * @param groupId The group ID
     */
    public abstract void add(String photoId, String groupId) throws IOException, SAXException,
    FlickrException ;
    
    /**
     * Get the context for a photo in the group pool.
     *
     * @param photoId The photo ID
     * @param groupId The group ID
     * @return The PhotoContext
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract PhotoContext getContext(String photoId, String groupId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a collection of all of the user's groups.
     *
     * @return A Collection of Group objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getGroups() throws IOException, SAXException, FlickrException;
    
    /**
     * Get the photos for the specified group pool, optionally filtering by taf.
     *
     * @param groupId The group ID
     * @param tags The optional tags (may be null)
     * @param perPage The number of photos per page (0 to ignore)
     * @param page The page offset (0 to ignore)
     * @return A Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getPhotos(String groupId, String[] tags, int perPage, int page) throws IOException, SAXException,
    FlickrException;
    
    /**
     * Remove the specified photo from the group.
     *
     * @param photoId The photo ID
     * @param groupId The group ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void remove(String photoId, String groupId) throws IOException, SAXException,
    FlickrException;
    
}
