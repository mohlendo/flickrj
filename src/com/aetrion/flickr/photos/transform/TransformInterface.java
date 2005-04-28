/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */

package com.aetrion.flickr.photos.transform;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import com.aetrion.flickr.REST;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.FlickrException;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class TransformInterface {

    public static final String METHOD_ROTATE = "flickr.photos.transform.rotate";

    private String apiKey;
    private REST restInterface;

    public TransformInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Rotate the specified photo.  The only allowed values for degrees are 90, 180 and 270.
     *
     * @param photoId The photo ID
     * @param degrees The degrees to rotate (90, 170 or 270)
     */
    public void rotate(String photoId, double degrees) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_ROTATE));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("degrees", String.valueOf(degrees)));

        RESTResponse response = (RESTResponse) restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

}
