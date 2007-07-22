/*
 * Copyright (c) 2007 Martin Goebel
 */
package com.aetrion.flickr.prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;

/**
 * Requesting preferences for the current authenticated user.
 *
 * @author Martin Goebel
 * @version $Id: PrefsInterface.java,v 1.3 2007/07/22 21:28:38 x-mago Exp $
 */
public class PrefsInterface {
    public static final String METHOD_GET_CONTENT_TYPE = "flickr.prefs.getContentType";
    public static final String METHOD_GET_HIDDEN = "flickr.prefs.getHidden";
    public static final String METHOD_GET_SAFETY_LEVEL = "flickr.prefs.getSafetyLevel";

    private String apiKey;
    private Transport transportAPI;

    /**
     * Construct a PrefsInterface.
     *
     * @param apiKey The API key
     * @param transport The Transport interface
     */
    public PrefsInterface(String apiKey, Transport transport) {
        this.apiKey = apiKey;
        this.transportAPI = transport;
    }

    /**
     * Returns the default content type preference for the user.
     *
     * @see com.aetrion.flickr.Flickr#CONTENTTYPE_OTHER
     * @see com.aetrion.flickr.Flickr#CONTENTTYPE_PHOTO
     * @see com.aetrion.flickr.Flickr#CONTENTTYPE_SCREENSHOT
     * @return The content-type
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public String getContentType() throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_CONTENT_TYPE));
        parameters.add(new Parameter("api_key", apiKey));

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }

        Element personElement = response.getPayload();
        return personElement.getAttribute("content_type");
    }

    /**
     * Returns the default hidden preference for the user.
     *
     * @return boolean hidden or not
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public boolean getHidden() throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_HIDDEN));
        parameters.add(new Parameter("api_key", apiKey));

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }

        Element personElement = response.getPayload();
        return personElement.getAttribute("hidden").equals("1") ? true : false ;
    }

    /**
     * Returns the default safety level preference for the user.
     *
     * @see com.aetrion.flickr.Flickr#SAFETYLEVEL_MODERATE
     * @see com.aetrion.flickr.Flickr#SAFETYLEVEL_RESTRICTED
     * @see com.aetrion.flickr.Flickr#SAFETYLEVEL_SAFE
     * @return The current users safety-level
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public String getSafetyLevel() throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_SAFETY_LEVEL));
        parameters.add(new Parameter("api_key", apiKey));

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }

        Element personElement = response.getPayload();
        return personElement.getAttribute("safety_level");
    }
}
