/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.photos.licenses;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * @author Anthony Eden
 */
public abstract class LicensesInterface {

    public static final String METHOD_GET_INFO = "flickr.photos.licenses.getInfo";

    public static LicensesInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new LicensesInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }

    /**
     * Rotate the specified photo.  The only allowed values for degrees are 90, 180 and 270.
     *
     * @return A collection of License objects
     */
    public abstract Collection getInfo() throws IOException, SAXException, FlickrException;

}
