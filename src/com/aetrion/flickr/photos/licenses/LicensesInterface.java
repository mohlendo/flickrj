/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.photos.licenses;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.aetrion.flickr.REST;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.RESTResponse;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Anthony Eden
 */
public class LicensesInterface {

    public static final String METHOD_GET_INFO = "flickr.photos.licenses.getInfo";

    private String apiKey;
    private REST restInterface;

    public LicensesInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Rotate the specified photo.  The only allowed values for degrees are 90, 180 and 270.
     *
     * @return A collection of License objects
     */
    public Collection getInfo() throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_INFO));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            List licenses = new ArrayList();
            Element licensesElement = (Element) response.getPayload();
            NodeList licenseElements = licensesElement.getElementsByTagName("license");
            for (int i = 0; i < licenseElements.getLength(); i++) {
                Element licenseElement = (Element) licenseElements.item(i);
                License license = new License();
                license.setId(licenseElement.getAttribute("id"));
                license.setName(licenseElement.getAttribute("name"));
                license.setUrl(licenseElement.getAttribute("url"));
                licenses.add(license);
            }
            return licenses;
        }
    }

}
