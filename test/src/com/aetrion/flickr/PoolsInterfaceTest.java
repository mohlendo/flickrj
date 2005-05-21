/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.util.Properties;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.util.IOUtilities;
import com.aetrion.flickr.groups.pools.PoolsInterface;
import org.xml.sax.SAXException;
import junit.framework.TestCase;

/**
 * @author Anthony Eden
 */
public class PoolsInterfaceTest extends TestCase {

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

    public void testAddAndRemove() throws FlickrException, IOException, SAXException {
        String photoId = properties.getProperty("photoid");
        String groupId = properties.getProperty("testgroupid");
        PoolsInterface iface = flickr.getPoolsInterface();
        iface.add(photoId, groupId);
        iface.remove(photoId, groupId);
    }

    public void testGetContext() {

    }

    public void testGetGroups() throws FlickrException, IOException, SAXException {
        PoolsInterface iface = flickr.getPoolsInterface();
        Collection groups = iface.getGroups();
        assertNotNull(groups);
        assertEquals(1, groups.size());
    }

    public void testGetPhotos() throws FlickrException, IOException, SAXException {
        String groupId = properties.getProperty("testgroupid");
        PoolsInterface iface = flickr.getPoolsInterface();
        Collection photos = iface.getPhotos(groupId, null, 0, 0);
        assertNotNull(photos);
        assertEquals(4, photos.size());
    }

}
