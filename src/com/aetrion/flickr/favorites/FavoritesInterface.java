package com.aetrion.flickr.favorites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Interface for working with Flickr favorites.
 *
 * @author Anthony Eden
 */
public class FavoritesInterface {

    public static final String METHOD_ADD = "flickr.favorites.add";
    public static final String METHOD_GET_LIST = "flickr.favorites.getList";
    public static final String METHOD_GET_PUBLIC_LIST = "flickr.favorites.getPublicList";
    public static final String METHOD_REMOVE = "flickr.favorites.remove";

    private String apiKey;
    private REST restInterface;

    public FavoritesInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Add a photo to the user's favorites.
     *
     * @param photoId The photo ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public void add(String photoId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_ADD));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        parameters.add(new Parameter("photo_id", photoId));

        Response response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

    /**
     * Get the collection of favorites for the calling user or the specified user ID.
     *
     * @param userId The optional user ID.  Null value will be ignored.
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The page to view.  Values <= 0 will be ignored.
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     */
    public Collection getList(String userId, int perPage, int page) throws IOException, SAXException, FlickrException {
        List photos = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        if (userId != null) {
            parameters.add(new Parameter("user_id", userId));
        }
        if (perPage > 0) {
            parameters.add(new Parameter("user_id", new Integer(perPage)));
        }
        if (page > 0) {
            parameters.add(new Parameter("user_id", new Integer(page)));
        }

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element photosElement = (Element) response.getPayload();
            NodeList photoNodes = photosElement.getElementsByTagName("photo");
            for (int i = 0; i < photoNodes.getLength(); i++) {
                Element photoElement = (Element) photoNodes.item(i);
                Photo photo = new Photo();
                photo.setId(photoElement.getAttribute("id"));

                User owner = new User();
                owner.setId(photoElement.getAttribute("owner"));
                photo.setOwner(owner);

                photo.setSecret(photoElement.getAttribute("secret"));
                photo.setServer(photoElement.getAttribute("server"));
                photo.setTitle(photoElement.getAttribute("title"));
                photo.setPublicFlag("1".equals(photoElement.getAttribute("ispublic")));
                photo.setFriendFlag("1".equals(photoElement.getAttribute("isfriend")));
                photo.setFamilyFlag("1".equals(photoElement.getAttribute("isfamily")));

                photos.add(photo);
            }
            return photos;
        }
    }

    /**
     * Get the specified user IDs public contacts.
     *
     * @param userId The user ID
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The optional page to view.  Values <= 0 will be ignored
     * @return A Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Collection getPublicList(String userId, int perPage, int page) throws IOException, SAXException, FlickrException {
        List photos = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_PUBLIC_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        parameters.add(new Parameter("user_id", userId));

        if (perPage > 0) {
            parameters.add(new Parameter("per_page", new Integer(perPage)));
        }
        if (page > 0) {
            parameters.add(new Parameter("page", new Integer(page)));
        }

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element photosElement = (Element) response.getPayload();
            NodeList photoNodes = photosElement.getElementsByTagName("photo");
            for (int i = 0; i < photoNodes.getLength(); i++) {
                Element photoElement = (Element) photoNodes.item(i);
                Photo photo = new Photo();
                photo.setId(photoElement.getAttribute("id"));

                User owner = new User();
                owner.setId(photoElement.getAttribute("owner"));
                photo.setOwner(owner);

                photo.setSecret(photoElement.getAttribute("secret"));
                photo.setServer(photoElement.getAttribute("server"));
                photo.setTitle(photoElement.getAttribute("name"));
                photo.setPublicFlag("1".equals(photoElement.getAttribute("ispublic")));
                photo.setFriendFlag("1".equals(photoElement.getAttribute("isfriend")));
                photo.setFamilyFlag("1".equals(photoElement.getAttribute("isfamily")));

                photos.add(photo);
            }
            return photos;
        }
    }

    /**
     * Remove the specified photo from the user's favorites.
     *
     * @param photoId The photo id
     */
    public void remove(String photoId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_REMOVE));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        parameters.add(new Parameter("photo_id", photoId));

        Response response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

}
