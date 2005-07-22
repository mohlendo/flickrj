/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.util.XMLUtilities;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Authentication interface.
 *
 * @author Anthony Eden
 */
public class AuthInterface {

    public static final String METHOD_CHECK_TOKEN = "flickr.auth.checkToken";
    public static final String METHOD_GET_FROB = "flickr.auth.getFrob";
    public static final String METHOD_GET_TOKEN = "flickr.auth.getToken";

    private String apiKey;
    private REST restInterface;

    /**
     * Construct the AuthInterface.
     *
     * @param apiKey The API key
     * @param restInterface The REST interface
     */
    public AuthInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
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
    public Auth checkToken(String authToken) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_CHECK_TOKEN));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("auth_token", authToken));

        parameters.add(new Parameter("api_sig", AuthUtilities.getSignature(parameters)));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Auth auth = new Auth();

            Element authElement = (Element) response.getPayload();
            auth.setToken(XMLUtilities.getChildValue(authElement, "token"));
            auth.setPermission(Permission.fromString(XMLUtilities.getChildValue(authElement, "perms")));

            Element userElement = XMLUtilities.getChild(authElement, "user");
            User user = new User();
            user.setId(userElement.getAttribute("nsid"));
            user.setUsername(userElement.getAttribute("username"));
            user.setRealName(userElement.getAttribute("fullname"));
            auth.setUser(user);

            return auth;
        }
    }

    /**
     * Get a frob.
     *
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public String getFrob() throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_CHECK_TOKEN));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("api_sig", AuthUtilities.getSignature(parameters)));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            return XMLUtilities.getValue(response.getPayload());
        }
    }

    /**
     * Get an authentication token for the specific frob.
     *
     * @param frob The frob
     * @return The Auth object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Auth getToken(String frob) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_CHECK_TOKEN));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("frob", frob));

        parameters.add(new Parameter("api_sig", AuthUtilities.getSignature(parameters)));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Auth auth = new Auth();

            Element authElement = (Element) response.getPayload();
            auth.setToken(XMLUtilities.getChildValue(authElement, "token"));
            auth.setPermission(Permission.fromString(XMLUtilities.getChildValue(authElement, "perms")));

            Element userElement = XMLUtilities.getChild(authElement, "user");
            User user = new User();
            user.setId(userElement.getAttribute("nsid"));
            user.setUsername(userElement.getAttribute("username"));
            user.setRealName(userElement.getAttribute("fullname"));
            auth.setUser(user);

            return auth;
        }
    }

}
