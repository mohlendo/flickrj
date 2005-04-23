package com.aetrion.flickr.contacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.util.XMLUtilities;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Interface for working with Flickr contacts.
 *
 * @author Anthony Eden
 */
public class ContactsInterface {

    public static final String METHOD_GET_LIST = "flickr.contacts.getList";
    public static final String METHOD_GET_PUBLIC_LIST = "flickr.contacts.getPublicList";

    private String apiKey;
    private REST restInterface;

    public ContactsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Get the collection of contacts for the calling user.
     *
     * @return The Collection of Contact objects
     * @throws IOException
     * @throws SAXException
     */
    public Collection getList() throws IOException, SAXException, FlickrException {
        List contacts = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_LIST));
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
            Element contactsElement = (Element) response.getPayload();
            NodeList contactNodes = contactsElement.getElementsByTagName("contact");
            for (int i = 0; i < contactNodes.getLength(); i++) {
                Element contactElement = (Element) contactNodes.item(i);
                Contact contact = new Contact();
                contact.setId(contactElement.getAttribute("nsid"));
                contact.setUsername(contactElement.getAttribute("username"));
                contact.setRealName(contactElement.getAttribute("realname"));
                contact.setFriend("1".equals(contactElement.getAttribute("friend")));
                contact.setFamily("1".equals(contactElement.getAttribute("family")));
                contact.setIgnored("1".equals(contactElement.getAttribute("ignored")));
                contact.setOnline(OnlineStatus.fromType(contactElement.getAttribute("online")));
                if (contact.getOnline() == OnlineStatus.AWAY) {
                    contactElement.normalize();
                    contact.setAwayMessage(XMLUtilities.getValue(contactElement));
                }
                contacts.add(contact);
            }
            return contacts;
        }
    }

    /**
     * Get the collection of public contacts for the specified user ID.
     *
     * @param userId The user ID
     * @return The Collection of Contact objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Collection getPublicList(String userId) throws IOException, SAXException, FlickrException {
        List contacts = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_PUBLIC_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        parameters.add(new Parameter("user_id", userId));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element contactsElement = (Element) response.getPayload();
            NodeList contactNodes = contactsElement.getElementsByTagName("contact");
            for (int i = 0; i < contactNodes.getLength(); i++) {
                Element contactElement = (Element) contactNodes.item(i);
                Contact contact = new Contact();
                contact.setId(contactElement.getAttribute("nsid"));
                contact.setUsername(contactElement.getAttribute("username"));
                contact.setIgnored("1".equals(contactElement.getAttribute("ignored")));
                contact.setOnline(OnlineStatus.fromType(contactElement.getAttribute("online")));
                if (contact.getOnline() == OnlineStatus.AWAY) {
                    contactElement.normalize();
                    contact.setAwayMessage(XMLUtilities.getValue(contactElement));
                }
                contacts.add(contact);
            }
            return contacts;
        }
    }

}
