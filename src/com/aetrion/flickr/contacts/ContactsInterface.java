/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.contacts;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Interface for working with Flickr contacts.
 *
 * @author Anthony Eden
 */
public abstract class ContactsInterface {

    public static final String METHOD_GET_LIST = "flickr.contacts.getList";
    public static final String METHOD_GET_PUBLIC_LIST = "flickr.contacts.getPublicList";

    public static ContactsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new ContactsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }

    /**
     * Get the collection of contacts for the calling user.
     *
     * @return The Collection of Contact objects
     * @throws IOException
     * @throws SAXException
     */
    public abstract Collection getList() throws IOException, SAXException, FlickrException;

    /**
     * Get the collection of public contacts for the specified user ID.
     *
     * @param userId The user ID
     * @return The Collection of Contact objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getPublicList(String userId) throws IOException, SAXException, FlickrException;

}
