/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.blogs;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.photos.Photo;

/**
 * Interface for working with Flickr blog configurations.
 *
 * @author Anthony Eden
 */
public abstract class BlogsInterface {

    public static final String METHOD_GET_LIST = "flickr.blogs.getList";
    public static final String METHOD_POST_PHOTO = "flickr.blogs.postPhoto";

    public static BlogsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new BlogsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    /**
     * Get the collection of configured blogs for the calling user.
     *
     * @return The Collection of configured blogs
     * @throws IOException
     * @throws SAXException
     */
    public abstract Collection getList() throws IOException, SAXException, FlickrException;

    /**
     * Post the specified photo to a blog.
     *
     * @param photo The photo metadata
     * @param blogId The blog ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public void postPhoto(Photo photo, String blogId) throws IOException, SAXException, FlickrException {
        postPhoto(photo, blogId, null);
    }

    /**
     * Post the specified photo to a blog.  Note that the Photo.title and Photo.description are used for the blog entry
     * title and body respectively.
     *
     * @param photo The photo metadata
     * @param blogId The blog ID
     * @param blogPassword The blog password
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void postPhoto(Photo photo, String blogId, String blogPassword) throws IOException, SAXException,
            FlickrException;
}
