/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class PeopleInterfaceTest extends TestCase {

    Flickr flickr = null;
    Authentication auth = null;
    Properties properties = null;

    public void setUp() throws ParserConfigurationException, IOException {
        Flickr.debugStream = true;

        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

            REST rest = new REST();
            rest.setHost(properties.getProperty("host"));

            flickr = new Flickr(properties.getProperty("apiKey"), rest);

            auth = new Authentication();
            auth.setEmail(properties.getProperty("email"));
            auth.setPassword(properties.getProperty("password"));
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
    }

    public void testGetInfo() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        User person = iface.getInfo(properties.getProperty("nsid"));
        assertNotNull(person);
        assertEquals(properties.getProperty("nsid"), person.getId());
        assertEquals(properties.getProperty("username"), person.getUsername());
    }

    public void testGetOnlineList() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        Collection onlineList = iface.getOnlineList();
        assertNotNull(onlineList);
    }

    public void testGetPublicPhotos() throws FlickrException, IOException, SAXException {
        PeopleInterface iface = flickr.getPeopleInterface();
        Collection photos = iface.getPublicPhotos(properties.getProperty("nsid"), 0, 0);
        assertNotNull(photos);
        assertEquals(3, photos.size());
    }

}
