/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.photos.licenses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Interface for working with copyright licenses.
 *
 * @author Anthony Eden
 */
public class LicensesInterface {

    public static final String METHOD_GET_INFO = "flickr.photos.licenses.getInfo";

    private String apiKey;
    private Transport transport;

    public LicensesInterface(String apiKey, Transport transport) {
        this.apiKey = apiKey;
        this.transport = transport;
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

        Response response = transport.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        List licenses = new ArrayList();
        Element licensesElement = response.getPayload();
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
