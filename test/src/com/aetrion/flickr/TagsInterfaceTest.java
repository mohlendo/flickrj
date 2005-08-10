package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.tags.RelatedTagsList;
import com.aetrion.flickr.tags.Tag;
import com.aetrion.flickr.tags.TagsInterface;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class TagsInterfaceTest extends TestCase {

    Flickr flickr = null;
    Properties properties = null;

    public void setUp() throws ParserConfigurationException, IOException, FlickrException, SAXException {
        Flickr.debugStream = true;
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

            REST rest = new REST();
            rest.setHost(properties.getProperty("host"));

            flickr = new Flickr(properties.getProperty("apiKey"), rest);

            RequestContext requestContext = RequestContext.getRequestContext();
            requestContext.setSharedSecret(properties.getProperty("secret"));

            AuthInterface authInterface = flickr.getAuthInterface();
            Auth auth = authInterface.checkToken(properties.getProperty("token"));
            requestContext.setAuth(auth);
        } finally {
            IOUtilities.close(in);
        }
    }

    public void testGetListPhoto() throws FlickrException, IOException, SAXException {
        TagsInterface iface = flickr.getTagsInterface();
        Photo photo = iface.getListPhoto(properties.getProperty("photoid"));
        assertNotNull(photo);
        assertEquals(properties.getProperty("photoid"), photo.getId());
        assertNotNull(photo.getTags());
        assertEquals(0, photo.getTags().size());
    }

    public void testGetListUser() throws FlickrException, IOException, SAXException {
        TagsInterface iface = flickr.getTagsInterface();
        Collection tags = iface.getListUser(properties.getProperty("nsid"));
        assertNotNull(tags);
        assertEquals(1, tags.size());
    }

    public void testListUserPopular() throws FlickrException, IOException, SAXException {
        TagsInterface iface = flickr.getTagsInterface();
        Collection tags = iface.getListUserPopular(properties.getProperty("nsid"));
        assertNotNull(tags);
        assertEquals(1, tags.size());
        Iterator iter = tags.iterator();
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            assertNotNull(tag.getValue());
            System.out.println(tag.getValue() + ":" + tag.getCount());
        }
    }

    public void testGetRelated() throws FlickrException, IOException, SAXException {
        TagsInterface iface = flickr.getTagsInterface();
        RelatedTagsList relatedTags = iface.getRelated("flower");
        assertNotNull(relatedTags);
        assertEquals("flower", relatedTags.getSource());
        assertTrue("Number of related tags returned was 0", relatedTags.size() > 0);
    }

}
