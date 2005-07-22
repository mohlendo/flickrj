/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.auth;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.ParameterAlphaComparator;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.util.ByteUtilities;
import com.aetrion.flickr.util.UrlUtilities;

/**
 * Utilities used by the authentication API.
 *
 * @author Anthony Eden
 */
public class AuthUtilities {

    /**
     * Get a signature for a list of parameters using the shared secret from the RequestContext.
     *
     * @param parameters The parameters
     * @return The signature String
     */
    public static String getSignature(List parameters) {
        RequestContext requestContext = RequestContext.getRequestContext();
        return getSignature(requestContext.getSharedSecret(), parameters);
    }

    /**
     * Get a signature for a list of parameters using the given shared secret.
     *
     * @param sharedSecret The shared secret
     * @param params The parameters
     * @return The signature String
     */
    public static String getSignature(String sharedSecret, List params) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(sharedSecret);
        Collections.sort(params, new ParameterAlphaComparator());
        Iterator iter = params.iterator();
        while (iter.hasNext()) {
            Parameter param = (Parameter) iter.next();
            buffer.append(param.getName());
            buffer.append(param.getValue());
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return ByteUtilities.toHexString(md.digest(buffer.toString().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Build the authentication URL which is passed to the client's preferred web browser.
     *
     * @param apiKey The API key
     * @param permission The permission
     * @param frob The frob
     * @return The URL
     * @throws MalformedURLException
     */
    public static URL buildAuthenticationUrl(String apiKey, Permission permission, String frob)
            throws MalformedURLException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("perms", permission.toString()));
        parameters.add(new Parameter("frob", frob));

        parameters.add(new Parameter("api_sig", getSignature(parameters)));

        // these values shouldn't be hardcoded, but they are for now
        // TODO: fix this so it isn't hardcoded
        String host = "flickr.com";
        int port = 80;
        String path = "/services/auth";

        return UrlUtilities.buildUrl(host, port, path, parameters);
    }

}
