package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoContext;
import com.aetrion.flickr.photos.SearchParameters;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photosets.Photoset;
import com.aetrion.flickr.photosets.Photosets;
import com.aetrion.flickr.photosets.PhotosetsInterface;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class PhotosetsInterfaceTest extends TestCase {

    Flickr flickr = null;
    Authentication auth = null;
    Properties properties = null;

    public void setUp() throws ParserConfigurationException, IOException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

//            REST rest = new REST();
//            rest.setHost(properties.getProperty("host"));

            flickr = new Flickr(properties.getProperty("apiKey"));

            auth = new Authentication();
            auth.setEmail(properties.getProperty("email"));
            auth.setPassword(properties.getProperty("password"));
        } finally {
            IOUtilities.close(in);
        }
    }

    public void testCreateAndDelete() throws FlickrException, IOException, SAXException {
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuthentication(auth);
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        Photoset photoset = iface.create("test", "A test photoset", properties.getProperty("photoid"));
        assertNotNull(photoset);
        assertNotNull(photoset.getId());
        assertNotNull(photoset.getUrl());
        iface.delete(photoset.getId());
    }

    public void testEditMeta() {

    }

    public void testEditPhotos() {

    }

    public void testGetContext() throws FlickrException, IOException, SAXException {
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuthentication(auth);
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        PhotoContext photoContext = iface.getContext(properties.getProperty("photoid"), properties.getProperty("photosetid"));
        Photo previousPhoto = photoContext.getPreviousPhoto();
        Photo nextPhoto = photoContext.getNextPhoto();
        assertNotNull(previousPhoto);
        assertNotNull(nextPhoto);
    }

    public void testGetInfo() throws FlickrException, IOException, SAXException {
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        Photoset photoset = iface.getInfo(properties.getProperty("photosetid"));
        assertNotNull(photoset);
        assertEquals(3, photoset.getPhotoCount());
    }

    public void testGetList() throws FlickrException, IOException, SAXException {
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        Photosets photosets = iface.getList(properties.getProperty("nsid"));
        assertNotNull(photosets);
        assertEquals(1, photosets.getPhotosets().size());
    }

    public void testGetList2() throws FlickrException, IOException, SAXException {
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        Photosets photosets = iface.getList("26095690@N00");
        assertNotNull(photosets);
    }

    public void testGetPhotos() throws FlickrException, IOException, SAXException {
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        Collection photos = iface.getPhotos(properties.getProperty("photosetid"));
        assertNotNull(photos);
        assertEquals(3, photos.size());
    }

    public void testOrderSets() throws FlickrException, IOException, SAXException {
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuthentication(auth);
        PhotosetsInterface iface = flickr.getPhotosetsInterface();
        String[] photosetIds = {properties.getProperty("photosetid")};
        iface.orderSets(photosetIds);
    }

}
