/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.blogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.photos.Photo;

/**
 * REST Interface for working with Flickr blog configurations.
 *
 * @author Anthony Eden
 */
public class BlogsInterfaceREST extends BlogsInterface{
    
    private String apiKey;
    private REST restInterface;
    
    public BlogsInterfaceREST(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }
    
    /**
     * Get the collection of configured blogs for the calling user.
     *
     * @return The Collection of configured blogs
     * @throws IOException
     * @throws SAXException
     */
    public Collection getList() throws IOException, SAXException, FlickrException {
        List blogs = new ArrayList();
        
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
        } 
        
        Element blogsElement = response.getPayload();
        NodeList blogNodes = blogsElement.getElementsByTagName("blog");
        for (int i = 0; i < blogNodes.getLength(); i++) {
            Element blogElement = (Element) blogNodes.item(i);
            Blog blog = new Blog();
            blog.setId(blogElement.getAttribute("id"));
            blog.setName(blogElement.getAttribute("name"));
            blog.setNeedPassword("1".equals(blogElement.getAttribute("needspassword")));
            blog.setUrl(blogElement.getAttribute("url"));
            blogs.add(blog);
        }
        return blogs;
    }
    
    /**
     * Post the specified photo to a blog.
     *
     * @param photo The photo metadata
     * @param blogId The blog ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public void postPhoto(Photo photo, String blogId) throws IOException, SAXException, FlickrException {
        postPhoto(photo, blogId, null);
    }
    
    /**
     * Post the specified photo to a blog.  Note that the Photo.title and Photo.description are used for the blog entry
     * title and body respectively.
     *
     * @param photo The photo metadata
     * @param blogId The blog ID
     * @param blogPassword The blog password
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public void postPhoto(Photo photo, String blogId, String blogPassword) throws IOException, SAXException,
    FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_POST_PHOTO));
        parameters.add(new Parameter("api_key", apiKey));
        
        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }
        
        parameters.add(new Parameter("blog_id", blogId));
        parameters.add(new Parameter("photo_id", photo.getId()));
        parameters.add(new Parameter("title", photo.getTitle()));
        parameters.add(new Parameter("description", photo.getDescription()));
        if (blogPassword != null) {
            parameters.add(new Parameter("blog_password", blogPassword));
        }
        
        Response response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }
    
}
