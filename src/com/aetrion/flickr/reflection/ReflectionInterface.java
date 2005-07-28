/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.reflection;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * Interface for working with Flickr tags.
 *
 * @author Anthony Eden
 */
public abstract class ReflectionInterface {

    public static final String METHOD_GET_METHOD_INFO = "flickr.reflection.getMethodInfo";
    public static final String METHOD_GET_METHODS = "flickr.reflection.getMethods";

    public static ReflectionInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new ReflectionInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }

    /**
     * Get the info for the specified method.
     *
     * @param methodName The method name
     * @return The Method object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Method getMethodInfo(String methodName) throws IOException, SAXException, FlickrException;

    /**
     * Get a list of all methods.
     *
     * @return The method names
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getMethods() throws IOException, SAXException, FlickrException;

}
