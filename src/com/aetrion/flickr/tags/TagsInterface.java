/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.tags;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.photos.Photo;

/**
 * Interface for working with Flickr tags.
 *
 * @author Anthony Eden
 */
public abstract class TagsInterface {
    
    public static final String METHOD_GET_LIST_PHOTO = "flickr.tags.getListPhoto";
    public static final String METHOD_GET_LIST_USER = "flickr.tags.getListUser";
    public static final String METHOD_GET_LIST_USER_POPULAR = "flickr.tags.getListUserPopular";
    public static final String METHOD_GET_RELATED = "flickr.tags.getRelated";
    
    public static TagsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new TagsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    /**
     * Get a list of tags for the specified photo.
     *
     * @param photoId The photo ID
     * @return The collection of Tag objects
     */
    public abstract Photo getListPhoto(String photoId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a collection of tags used by the specified user.
     *
     * @param userId The User ID
     * @return The User object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getListUser(String userId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a list of the user's popular tags.
     *
     * @param userId The user ID
     * @return The collection of Tag objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getListUserPopular(String userId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get the related tags.
     *
     * @param tag The source tag
     * @return A RelatedTagsList object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract RelatedTagsList getRelated(String tag) throws IOException, SAXException, FlickrException;
    
}
