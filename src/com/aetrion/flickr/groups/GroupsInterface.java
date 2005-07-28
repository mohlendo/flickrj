/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.groups;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Interface for working with Flickr Groups.
 *
 * @author Anthony Eden
 */
public abstract class GroupsInterface {

    public static final String METHOD_BROWSE = "flickr.groups.browse";
    public static final String METHOD_GET_ACTIVE_LIST = "flickr.groups.getActiveList";
    public static final String METHOD_GET_INFO = "flickr.groups.getInfo";
    public static final String METHOD_SEARCH = "flickr.groups.search";

    public static GroupsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new GroupsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }

    /**
     * Browse groups for the given category ID.  If a null value is passed for the category then the root category is
     * used.
     *
     * @param catId The optional category id.  Null value will be ignored.
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Category browse(String catId) throws IOException, SAXException, FlickrException;

    /**
     * Get the current active groups collection.
     *
     * @return A Collection of Group objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getActiveList() throws IOException, SAXException, FlickrException;

    /**
     * Get the info for a specified group.
     *
     * @param groupId The group id
     * @return The Group object
     */
    public abstract Group getInfo(String groupId) throws IOException, SAXException, FlickrException;

    /**
     * Search for groups.
     *
     * @param text The text
     * @param perPage The number of groups per page
     * @param page The number of pages
     * @return A collection of Group objects
     * @throws IOException A collection of Group objects
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection search(String text, int perPage, int page) throws IOException, SAXException, FlickrException;

}
