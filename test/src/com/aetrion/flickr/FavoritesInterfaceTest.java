/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.favorites.FavoritesInterface;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class FavoritesInterfaceTest extends TestCase {

    Flickr flickr = null;
    Authentication auth = null;

    public void setUp() throws ParserConfigurationException, IOException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            Properties properties = new Properties();
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

    public void testGetList() throws FlickrException, IOException, SAXException {
        FavoritesInterface iface = flickr.getFavoritesInterface();
        Collection favorites = iface.getList(null, 0, 0);
        assertNotNull(favorites);
        assertEquals(1, favorites.size());
    }

    public void testGetPublicList() throws FlickrException, IOException, SAXException {
        FavoritesInterface iface = flickr.getFavoritesInterface();
        Collection favorites = iface.getPublicList("77348956@N00", 0, 0);
        assertNotNull(favorites);
        assertEquals(1, favorites.size());
    }

    public void testAddAndRemove() throws FlickrException, IOException, SAXException {
        String photoId = "2153378";
        FavoritesInterface iface = flickr.getFavoritesInterface();
        iface.add(photoId);

        Photo foundPhoto = null;
        Iterator favorites = iface.getList(null, 0, 0).iterator();
        FIND_LOOP: while (favorites.hasNext()) {
            Photo photo = (Photo) favorites.next();
            if (photo.getId().equals(photoId)) {
                foundPhoto = photo;
                break FIND_LOOP;
            }
        }
        assertNotNull(foundPhoto);
        assertEquals(photoId, foundPhoto.getId());
        iface.remove(photoId);
    }

}
