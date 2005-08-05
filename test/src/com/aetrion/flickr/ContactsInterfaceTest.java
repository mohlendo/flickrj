/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.contacts.ContactsInterface;
import com.aetrion.flickr.util.IOUtilities;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class ContactsInterfaceTest extends TestCase {

    Flickr flickr = null;
    Properties properties = null;

    public void setUp() throws ParserConfigurationException, IOException, FlickrException, SAXException {
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

    public void testGetList() throws FlickrException, IOException, SAXException {
        ContactsInterface iface = flickr.getContactsInterface();
        Collection contacts = iface.getList();
        assertNotNull(contacts);
        assertEquals(1, contacts.size());
    }

    public void testGetPublicList() throws FlickrException, IOException, SAXException {
        ContactsInterface iface = flickr.getContactsInterface();
        Collection contacts = iface.getPublicList(properties.getProperty("nsid"));
        assertNotNull(contacts);
        assertEquals(1, contacts.size());
    }

}
