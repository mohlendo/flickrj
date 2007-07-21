package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.prefs.PrefsInterface;
import com.aetrion.flickr.util.IOUtilities;

/**
 * @author Martin Goebel
 * @version $Id: PrefsInterfaceTest.java,v 1.1 2007/07/21 19:11:27 x-mago Exp $
 */
public class PrefsInterfaceTest extends TestCase {

    Flickr flickr = null;

    public void setUp() throws ParserConfigurationException, IOException, FlickrException, SAXException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            Properties properties = new Properties();
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

    public void testGetContentType() throws FlickrException, IOException, SAXException {
        PrefsInterface iface = flickr.getPrefsInterface();
        String type = iface.getContentType();
        assertTrue(
            type.equals(Flickr.CONTENTTYPE_OTHER)
            || type.equals(Flickr.CONTENTTYPE_PHOTO)
            || type.equals(Flickr.CONTENTTYPE_SCREENSHOT)
        );
    }

    public void testGetSafetyLevel() throws FlickrException, IOException, SAXException {
        PrefsInterface iface = flickr.getPrefsInterface();
        String level = iface.getSafetyLevel();
        assertTrue(
            level.equals(Flickr.SAFETYLEVEL_SAFE)
            || level.equals(Flickr.SAFETYLEVEL_MODERATE)
            || level.equals(Flickr.SAFETYLEVEL_RESTRICTED)
        );
    }

    public void testGetHidden() throws FlickrException, IOException, SAXException {
        PrefsInterface iface = flickr.getPrefsInterface();
        Boolean hidden = iface.getHidden();
    }

}
