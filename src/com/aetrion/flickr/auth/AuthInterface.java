/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.auth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Authentication interface.
 *
 * @author Anthony Eden
 */
public abstract class AuthInterface {

    public static final String METHOD_CHECK_TOKEN = "flickr.auth.checkToken";
    public static final String METHOD_GET_FROB = "flickr.auth.getFrob";
    public static final String METHOD_GET_TOKEN = "flickr.auth.getToken";

    public static AuthInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new AuthInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    /**
     * Check the authentication token for validity.
     *
     * @param authToken The authentication token
     * @return The Auth object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Auth checkToken(String authToken) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a frob.
     *
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract String getFrob() throws IOException, SAXException, FlickrException;

    /**
     * Get an authentication token for the specific frob.
     *
     * @param frob The frob
     * @return The Auth object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Auth getToken(String frob) throws IOException, SAXException, FlickrException;

    /**
     * Build the authentication URL using the given permission and frob.
     *
     * @param permission The Permission
     * @param frob The frob returned from getFrob()
     * @return The URL
     * @throws MalformedURLException
     */
    public abstract URL buildAuthenticationUrl(Permission permission, String frob) throws MalformedURLException;

}
