package com.aetrion.flickr.people;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.contacts.OnlineStatus;
import com.aetrion.flickr.photos.Photo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class PeopleInterface {

    public static final String METHOD_FIND_BY_EMAIL = "flickr.people.findByEmail";
    public static final String METHOD_FIND_BY_USERNAME = "flickr.people.findByUsername";
    public static final String METHOD_GET_INFO = "flickr.people.getInfo";
    public static final String METHOD_GET_ONLINE_LIST = "flickr.people.getOnlineList";
    public static final String METHOD_GET_PUBLIC_PHOTOS = "flickr.people.getPublicPhotos";

    private String apiKey;
    private REST restInterface;

    public PeopleInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    public User findByEmail(String email) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_FIND_BY_EMAIL));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("find_email", email));

        RESTResponse response = restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element userElement = (Element) response.getPayload();
            User user = new User();
            user.setId(userElement.getAttribute("nsid"));

            Element nameElement = (Element) userElement.getElementsByTagName("username").item(0);
            user.setUsername(((Text) nameElement.getFirstChild()).getData());

            return user;
        }
    }

    public User findByUsername(String username) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_FIND_BY_USERNAME));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("username", username));

        RESTResponse response = restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element userElement = (Element) response.getPayload();
            User user = new User();
            user.setId(userElement.getAttribute("nsid"));

            Element usernameElement = (Element) userElement.getElementsByTagName("username").item(0);
            user.setUsername(((Text) usernameElement.getFirstChild()).getData());

            return user;
        }
    }

    public User getInfo(String userId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_INFO));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("user_id", userId));

        RESTResponse response = restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element userElement = (Element) response.getPayload();
            User user = new User();
            user.setId(userElement.getAttribute("nsid"));
            user.setAdmin("1".equals(userElement.getAttribute("isadmin")));
            user.setPro("1".equals(userElement.getAttribute("ispro")));

            Element usernameElement = (Element) userElement.getElementsByTagName("username").item(0);
            user.setUsername(((Text) usernameElement.getFirstChild()).getData());

            Element realnameElement = (Element) userElement.getElementsByTagName("realname").item(0);
            Text realnameText = (Text) realnameElement.getFirstChild();
            if (realnameText != null)
                user.setRealname(realnameText.getData());

            Element locationElement = (Element) userElement.getElementsByTagName("location").item(0);
            Text locationText = (Text) locationElement.getFirstChild();
            if (locationText != null)
                user.setLocation(locationText.getData());

            Element photosElement = (Element) userElement.getElementsByTagName("photos").item(0);

            Element firstdateElement = (Element) photosElement.getElementsByTagName("firstdate").item(0);
            Text firstdateText = (Text) firstdateElement.getFirstChild();
            if (firstdateText != null)
                user.setPhotosFirstDate(firstdateText.getData());

            Element firstdateTakenElement = (Element) photosElement.getElementsByTagName("firstdatetaken").item(0);
            Text firstdateTakenText = (Text) firstdateTakenElement.getFirstChild();
            if (firstdateTakenText != null)
                user.setPhotosFirstDateTaken(firstdateTakenText.getData());

            Element countElement = (Element) photosElement.getElementsByTagName("count").item(0);
            Text countText = (Text) countElement.getFirstChild();
            if (countElement != null)
                user.setPhotosCount(countText.getData());

            return user;
        }
    }

    public Collection getOnlineList() throws IOException, SAXException, FlickrException {
        List online = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_ONLINE_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        RESTResponse response = restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element onlineElement = (Element) response.getPayload();
            NodeList userNodes = onlineElement.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                User user = new User();
                user.setId(userElement.getAttribute("nsid"));
                user.setUsername(userElement.getAttribute("username"));
                user.setOnline(OnlineStatus.fromType(userElement.getAttribute("online")));
                if (user.getOnline() == OnlineStatus.AWAY) {
                    user.setAwayMessage(((Text) userElement.getFirstChild()).getData());
                }
                online.add(user);
            }
            return online;
        }
    }

    public Collection getPublicPhotos(String userId, int perPage, int page) throws IOException, SAXException, FlickrException {
        List photos = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_PUBLIC_PHOTOS));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("user_id", userId));

        if (perPage > 0) {
            parameters.add(new Parameter("per_page", new Integer(perPage)));
        }
        if (page > 0) {
            parameters.add(new Parameter("page", new Integer(page)));
        }

        RESTResponse response = restInterface.get("/services/rest/", parameters);
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

}
