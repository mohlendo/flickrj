/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.test;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.Transport;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.people.User;

/**
 * Interface for testing Flickr connectivity.
 *
 * @author Matt Ray
 */
public abstract class TestInterface {
    public static final String METHOD_ECHO = "flickr.test.echo";
    public static final String METHOD_LOGIN = "flickr.test.login";
    
    public static TestInterface getInterface(String apiKey, Transport api) {
        if (api.getTransportType().equals(Transport.REST)) {
            return new TestInterfaceREST(apiKey, (REST)api);
        }
        //put the SOAP version here
        return null;
    }

    

    /**
     * A testing method which echo's all paramaters back in the response.
     *
     * @param params The parameters
     * @return The Collection of echoed elements
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection echo(Collection params) throws IOException, SAXException, FlickrException;

    /**
     * A testing method which checks if the caller is logged in then returns a User object.
     *
     * @return The User object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract User login() throws IOException, SAXException, FlickrException;

}
