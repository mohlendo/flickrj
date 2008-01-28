/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.util.IOUtilities;

/**
 * @author Anthony Eden
 * @version $Id: PeopleInterfaceTest.java,v 1.13 2008/01/28 23:01:45 x-mago Exp $
 */
public class PeopleInterfaceTest extends TestCase {

    Flickr flickr = null;
    Properties properties = null;

    public void setUp() throws ParserConfigurationException, IOException, FlickrException, SAXException {
        //Flickr.debugStream = true;

        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

            REST rest = new REST();
            rest.setHost(properties.getProperty("host"));

            flickr = new Flickr(
                properties.getProperty("apiKey"),
                properties.getProperty("secret"),
                rest
            );

            RequestContext requestContext = RequestContext.getRequestContext();

            AuthInterface authInterface = flickr.getAuthInterface();
            Auth auth = authInterface.checkToken(properties.getProperty("token"));
            requestContext.setAuth(auth);
        } finally {
            IOUtilities.close(in);
        }
    }

    public void testFindByEmail() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        User person = iface.findByEmail(properties.getProperty("email"));
        assertNotNull(person);
        assertEquals(person.getId(), properties.getProperty("nsid"));
        assertEquals(person.getUsername(), properties.getProperty("username"));
    }

    public void testFindByUsername() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        User person = iface.findByUsername(properties.getProperty("username"));
        assertNotNull(person);
        assertEquals(properties.getProperty("nsid"), person.getId());
        assertEquals(properties.getProperty("username"), person.getUsername());
        // Do the UrlEcoding is correct?
        person = iface.findByUsername("| K H A L E D |");
        assertNotNull(person);
        assertEquals("14036163@N05", person.getId());
        assertEquals("| K H A L E D |", person.getUsername());
    }

    public void testGetInfo() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        User person = iface.getInfo(properties.getProperty("nsid"));
        assertNotNull(person);
        assertEquals(properties.getProperty("nsid"), person.getId());
        assertEquals(properties.getProperty("username"), person.getUsername());
        assertTrue(person.getBuddyIconUrl().startsWith("http://"));
    }

    public void testGetPublicGroups() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        Collection groups = iface.getPublicGroups(properties.getProperty("nsid"));
        assertNotNull(groups);
        assertTrue(groups.size() == 1);
    }

    public void testGetPublicPhotos() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        PhotoList photos = iface.getPublicPhotos(properties.getProperty("nsid"), 0, 0);
        assertNotNull(photos);
        assertTrue(photos.size() > 3);
    }

    public void testGetUploadStatus() {

    }

}
