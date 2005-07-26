/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.groups;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aetrion.flickr.Transport;
import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.util.XMLUtilities;

/**
 * Interface for working with Flickr Groups.
 *
 * @author Anthony Eden
 */
public class GroupsInterface {

    public static final String METHOD_BROWSE = "flickr.groups.browse";
    public static final String METHOD_GET_ACTIVE_LIST = "flickr.groups.getActiveList";
    public static final String METHOD_GET_INFO = "flickr.groups.getInfo";
    public static final String METHOD_SEARCH = "flickr.groups.search";

    private String apiKey;
    private Transport restInterface;

    public GroupsInterface(String apiKey, Transport restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Browse groups for the given category ID.  If a null value is passed for the category then the root category is
     * used.
     *
     * @param catId The optional category id.  Null value will be ignored.
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Category browse(String catId) throws IOException, SAXException, FlickrException {
        List subcategories = new ArrayList();
        List groups = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_BROWSE));
        parameters.add(new Parameter("api_key", apiKey));

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

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

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

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

        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }

        parameters.add(new Parameter("group_id", groupId));

        RESTResponse response = (RESTResponse) restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element groupElement = (Element) response.getPayload();
            Group group = new Group();
            group.setId(groupElement.getAttribute("id"));

            group.setName(XMLUtilities.getChildValue(groupElement, "name"));
            group.setMembers(XMLUtilities.getChildValue(groupElement, "members"));
            group.setOnline(XMLUtilities.getChildValue(groupElement, "online"));
            group.setPrivacy(XMLUtilities.getChildValue(groupElement, "privacy"));
            group.setChatId(XMLUtilities.getChildValue(groupElement, "chatid"));
            group.setInChat(XMLUtilities.getChildValue(groupElement, "chatcount"));

            return group;
        }
    }

    /**
     * Search for groups.
     *
     * @param text The text
     * @param perPage The number of groups per page
     * @param page The number of pages
     * @return A collection of Group objects
     * @throws IOException A collection of Group objects
     * @throws SAXException
     * @throws FlickrException
     */
    public Collection search(String text, int perPage, int page) throws IOException, SAXException, FlickrException {
        List groups = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_SEARCH));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("text", text));

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
            Element groupsElement = (Element) response.getPayload();
            NodeList groupNodes = groupsElement.getElementsByTagName("group");
            for (int i = 0; i < groupNodes.getLength(); i++) {
                Element groupElement = (Element) groupNodes.item(i);
                Group group = new Group();
                group.setId(groupElement.getAttribute("nsid"));
                group.setName(groupElement.getAttribute("name"));
                group.setEighteenPlus("1".equals(groupElement.getAttribute("eighteenplus")));
                groups.add(group);
            }
            return groups;
        }
    }

}
