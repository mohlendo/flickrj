package com.aetrion.flickr.urls;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import com.aetrion.flickr.REST;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.FlickrException;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Interface for testing Flickr connectivity.
 *
 * @author Anthony Eden
 */
public class UrlsInterface {

    public static final String METHOD_GET_GROUP = "flickr.urls.getGroup";
    public static final String METHOD_GET_USER_PHOTOS = "flickr.urls.getUserPhotos";
    public static final String METHOD_GET_USER_PROFILE = "flickr.urls.getUserProfile";
    public static final String METHOD_LOOKUP_GROUP = "flickr.urls.lookupGroup";
    public static final String METHOD_LOOKUP_USER = "flickr.urls.lookupUser";

    private String apiKey;
    private REST restInterface;

    /**
     * Construct a UrlsInterface.
     *
     * @param apiKey The API key
     * @param restInterface The REST interface
     */
    public UrlsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    public String getGroup(String groupId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_GROUP));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("group_id", groupId));

        RESTResponse response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element payload = response.getPayload();
            return payload.getAttribute("url");
        }
    }

    public String getUserPhotos(String userId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_USER_PHOTOS));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("user_id", userId));

        RESTResponse response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element payload = response.getPayload();
            return payload.getAttribute("url");
        }
    }

    public String getUserProfile(String userId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_USER_PROFILE));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("user_id", userId));

        RESTResponse response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element payload = response.getPayload();
            return payload.getAttribute("url");
        }
    }

    public String lookupGroup(String url) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_LOOKUP_GROUP));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("url", url));

        RESTResponse response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element payload = response.getPayload();
            Element groupnameElement = (Element) payload.getElementsByTagName("groupname").item(0);
            return (((Text)groupnameElement.getFirstChild()).getData()).toString();
        }
    }

    public String lookupUser(String url) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_LOOKUP_USER));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("url", url));

        RESTResponse response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element payload = response.getPayload();
            Element groupnameElement = (Element) payload.getElementsByTagName("username").item(0);
            return (((Text)groupnameElement.getFirstChild()).getData()).toString();
        }
    }

}
