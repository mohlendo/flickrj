package com.aetrion.flickr.groups.pools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.groups.Group;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoContext;
import com.aetrion.flickr.util.StringUtilities;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class PoolsInterface {

    public static final String METHOD_ADD = "flickr.groups.pools.add";
    public static final String METHOD_GET_CONTEXT = "flickr.groups.pools.getContext";
    public static final String METHOD_GET_GROUPS = "flickr.groups.pools.getGroups";
    public static final String METHOD_GET_PHOTOS = "flickr.groups.pools.getPhotos";
    public static final String METHOD_REMOVE = "flickr.groups.pools.remove";

    private String apiKey;
    private REST restInterface;

    public PoolsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Add a photo to a group's pool.
     *
     * @param auth The Authentication object
     * @param photoId The photo ID
     * @param groupId The group ID
     */
    public void add(Authentication auth, String photoId, String groupId) throws IOException, SAXException,
            FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_ADD));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.addAll(auth.getAsParameters());

        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("group_id", groupId));

        RESTResponse response = (RESTResponse) restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {

        }
    }

    public PhotoContext getContext(String photoId, String groupId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_GROUPS));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("group_id", groupId));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Collection payload = response.getPayloadCollection();
            Iterator iter = payload.iterator();
            PhotoContext photoContext = new PhotoContext();
            while (iter.hasNext()) {
                Element element = (Element) iter.next();
                String elementName = element.getTagName();
                if (elementName.equals("prevphoto")) {
                    Photo photo = new Photo();
                    photo.setId(element.getAttribute("id"));
                    photoContext.setPreviousPhoto(photo);
                } else if (elementName.equals("nextphoto")) {
                    Photo photo = new Photo();
                    photo.setId(element.getAttribute("id"));
                    photoContext.setNextPhoto(photo);
                } else {
                    System.err.println("unsupported element name: " + elementName);
                }
            }
            return photoContext;
        }
    }

    public Collection getGroups(Authentication auth) throws IOException, SAXException, FlickrException {
        List groups = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_GROUPS));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.addAll(auth.getAsParameters());

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element groupsElement = (Element) response.getPayload();
            NodeList groupNodes = groupsElement.getElementsByTagName("group");
            for (int i = 0; i < groupNodes.getLength(); i++) {
                Element groupElement = (Element) groupNodes.item(i);
                Group group = new Group();
                group.setId(groupElement.getAttribute("id"));
                group.setName(groupElement.getAttribute("name"));
                group.setAdmin("1".equals(groupElement.getAttribute("admin")));
                group.setPrivacy(groupElement.getAttribute("privacy"));
                group.setPhotoCount(groupElement.getAttribute("photos"));
                groups.add(group);
            }
            return groups;
        }
    }

    public Collection getPhotos(String groupId, String[] tags, int perPage, int page) throws IOException, SAXException,
            FlickrException {
        List photos = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_PHOTOS));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("group_id", groupId));
        if (tags != null) {
            parameters.add(new Parameter("tags", StringUtilities.join(tags, " ")));
        }
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
                owner.setUsername(photoElement.getAttribute("ownername"));
                photo.setOwner(owner);

                photo.setTitle(photoElement.getAttribute("title"));
                photo.setPublicFlag("1".equals(photoElement.getAttribute("ispublic")));
                photo.setFriendFlag("1".equals(photoElement.getAttribute("isfriend")));
                photo.setFamilyFlag("1".equals(photoElement.getAttribute("isfamily")));

                photos.add(photo);
            }

            return photos;
        }
    }

    public void remove(Authentication auth, String photoId, String groupId) throws IOException, SAXException,
            FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_REMOVE));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.addAll(auth.getAsParameters());

        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("group_id", groupId));

        RESTResponse response = (RESTResponse) restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

}
