package com.aetrion.flickr.groups;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class GroupsInterface {

    public static final String METHOD_BROWSE = "flickr.groups.browse";
    public static final String METHOD_GET_ACTIVE_LIST = "flickr.groups.getActiveList";
    public static final String METHOD_GET_INFO = "flickr.groups.getInfo";

    private String apiKey;
    private REST restInterface;

    public GroupsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Get the collection of favorites for the calling user or the specified user ID.
     *
     * @param auth The Authentication innformation.
     * @param catId The optional category id.  Null value will be ignored.
     * @return The Collection of Photo objects
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public Category browse(Authentication auth, String catId) throws IOException, SAXException, FlickrException {
        List subcategories = new ArrayList();
        List groups = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_BROWSE));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.addAll(auth.getAsParameters());

        if (catId != null) {
            parameters.add(new Parameter("cat_id", catId));
        }

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element categoryElement = (Element) response.getPayload();

            Category category = new Category();
            category.setName(categoryElement.getAttribute("name"));
            category.setPath(categoryElement.getAttribute("path"));
            category.setPathIds(categoryElement.getAttribute("pathids"));

            NodeList subcatNodes = categoryElement.getElementsByTagName("subcat");
            for (int i = 0; i < subcatNodes.getLength(); i++) {
                Element node = (Element) subcatNodes.item(i);
                Subcategory subcategory = new Subcategory();
                subcategory.setId(Integer.parseInt(node.getAttribute("id")));
                subcategory.setName(node.getAttribute("name"));
                subcategory.setCount(Integer.parseInt(node.getAttribute("count")));

                subcategories.add(subcategory);
            }

            NodeList groupNodes = categoryElement.getElementsByTagName("group");
            for (int i = 0; i < groupNodes.getLength(); i++) {
                Element node = (Element) groupNodes.item(i);
                Group group = new Group();
                group.setId(node.getAttribute("nsid"));
                group.setName(node.getAttribute("name"));
                group.setMembers(node.getAttribute("members"));
                group.setOnline(node.getAttribute("online"));
                group.setChatId(node.getAttribute("chatnsid"));
                group.setInChat(node.getAttribute("inchat"));

                groups.add(group);
            }

            category.setGroups(groups);
            category.setSubcategories(subcategories);

            return category;
        }
    }

    /**
     * Get the current active groups collection.
     *
     * @return A Collection of Group objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Collection getActiveList() throws IOException, SAXException, FlickrException {
        List groups = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_ACTIVE_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element activeGroupsElement = (Element) response.getPayload();
            NodeList groupNodes = activeGroupsElement.getElementsByTagName("group");
            for (int i = 0; i < groupNodes.getLength(); i++) {
                Element groupElement = (Element) groupNodes.item(i);
                Group group = new Group();
                group.setId(groupElement.getAttribute("nsid"));
                group.setName(groupElement.getAttribute("name"));
                group.setMembers(groupElement.getAttribute("members"));
                group.setOnline(groupElement.getAttribute("online"));
                group.setChatId(groupElement.getAttribute("chatnsid"));
                group.setInChat(groupElement.getAttribute("inchat"));

                groups.add(group);
            }
            return groups;
        }
    }

    /**
     * Get the info for a specified group.
     *
     * @param groupId The group id
     * @return The Group object
     */
    public Group getInfo(String groupId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_INFO));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("group_id", groupId));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element groupElement = (Element) response.getPayload();
            Group group = new Group();
            group.setId(groupElement.getAttribute("id"));

            Element nameElement = (Element) groupElement.getElementsByTagName("name").item(0);
            group.setName(((Text) nameElement.getFirstChild()).getData());

            Element membersElement = (Element) groupElement.getElementsByTagName("members").item(0);
            group.setMembers(((Text) membersElement.getFirstChild()).getData());

            Element onlineElement = (Element) groupElement.getElementsByTagName("online").item(0);
            group.setOnline(((Text) onlineElement.getFirstChild()).getData());

            Element privacyElement = (Element) groupElement.getElementsByTagName("privacy").item(0);
            group.setPrivacy(((Text) privacyElement.getFirstChild()).getData());

            Element chatIdElement = (Element) groupElement.getElementsByTagName("chatid").item(0);
            group.setChatId(((Text) chatIdElement.getFirstChild()).getData());

            Element chatCountElement = (Element) groupElement.getElementsByTagName("chatcount").item(0);
            group.setInChat(((Text) chatCountElement.getFirstChild()).getData());

            return group;
        }
    }

}
