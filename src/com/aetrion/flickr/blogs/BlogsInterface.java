package com.aetrion.flickr.blogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RESTResponse;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Photo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class BlogsInterface {

    public static final String METHOD_GET_LIST = "flickr.blogs.getList";
    public static final String METHOD_POST_PHOTO = "flickr.blogs.postPhoto";

    private String apiKey;
    private REST restInterface;

    public BlogsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    /**
     * Get the collection of configured blogs for the calling user.
     *
     * @param auth The Authentication innformation.
     * @return The Collection of configured blogs
     * @throws IOException
     * @throws SAXException
     */
    public Collection getList(Authentication auth) throws IOException, SAXException, FlickrException {
        List blogs = new ArrayList();

        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_LIST));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.addAll(auth.getAsParameters());

        RESTResponse response = restInterface.get("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } else {
            Element blogsElement = (Element) response.getPayload();
            NodeList blogNodes = blogsElement.getElementsByTagName("blog");
            for (int i = 0; i < blogNodes.getLength(); i++) {
                Element blogElement = (Element) blogNodes.item(i);
                Blog blog = new Blog();
                blog.setId(blogElement.getAttribute("id"));
                blog.setName(blogElement.getAttribute("name"));
                blog.setNeedPassword("1".equals(blogElement.getAttribute("needpassword")));
                blog.setUrl(blogElement.getAttribute("url"));
                blogs.add(blog);
            }
            return blogs;
        }
    }

    public void postPhoto(Authentication auth, Photo photo, String blogId) throws IOException, SAXException, FlickrException {
        postPhoto(auth, photo, blogId, null);
    }

    public void postPhoto(Authentication auth, Photo photo, String blogId, String blogPassword) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_POST_PHOTO));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.addAll(auth.getAsParameters());

        parameters.add(new Parameter("blog_id", blogId));
        parameters.add(new Parameter("photo_id", photo.getId()));
        parameters.add(new Parameter("title", photo.getTitle()));
        parameters.add(new Parameter("description", photo.getDescription()));
        if (blogPassword != null) {
            parameters.add(new Parameter("blog_password", blogPassword));
        }

        RESTResponse response = restInterface.post("/services/rest/", parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

}
