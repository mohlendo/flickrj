/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.util.IOUtilities;
import com.aetrion.flickr.urls.UrlsInterface;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class UrlsInterfaceSOAPTest extends TestCase {

    Flickr flickr = null;
    Authentication auth = null;
    Properties properties = null;

    public void setUp() throws ParserConfigurationException, IOException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

            Flickr.debugStream = true;
            SOAP soap = new SOAP(properties.getProperty("host"));
            flickr = new Flickr(properties.getProperty("apiKey"), soap);
            
            auth = new Authentication();
            auth.setEmail(properties.getProperty("email"));
            auth.setPassword(properties.getProperty("password"));
        } finally {
            IOUtilities.close(in);
        }
    }

    public void testGetGroup() throws FlickrException, IOException, SAXException {
        UrlsInterface iface = flickr.getUrlsInterface();
        String url = iface.getGroup(properties.getProperty("groupid"));
        assertEquals("http://www.flickr.com/groups/central/", url);
    }

    public void testGetUserPhotos() throws FlickrException, IOException, SAXException {
        UrlsInterface iface = flickr.getUrlsInterface();
        String url = iface.getUserPhotos(properties.getProperty("nsid"));
        assertEquals("http://www.flickr.com/photos/javatest/", url);
    }

    public void testGetUserProfile() throws FlickrException, IOException, SAXException {
        UrlsInterface iface = flickr.getUrlsInterface();
        String url = iface.getUserProfile(properties.getProperty("nsid"));
        assertEquals("http://www.flickr.com/people/javatest/", url);
    }

    public void testLookupGroup() throws FlickrException, IOException, SAXException {
        UrlsInterface iface = flickr.getUrlsInterface();
        String groupname = iface.lookupGroup("http://www.flickr.com/groups/central/");
        assertEquals("FlickrCentral", groupname);
    }

    public void testLookupUser() throws FlickrException, IOException, SAXException {
        UrlsInterface iface = flickr.getUrlsInterface();
        String username = iface.lookupUser("http://www.flickr.com/people/javatest");
        assertEquals("javatest", username);
    }

}
