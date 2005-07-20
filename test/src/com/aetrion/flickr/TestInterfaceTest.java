/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.people.User;
import com.aetrion.flickr.test.TestInterface;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class TestInterfaceTest extends TestCase {

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

    public void testEcho() throws FlickrException, IOException, SAXException {
        TestInterface iface = flickr.getTestInterface();
        List params = new ArrayList();
        params.add(new Parameter("test", "test"));
        Collection results = iface.echo(params);
        assertNotNull(results);
    }

    public void testLogin() throws FlickrException, IOException, SAXException {
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuthentication(auth);
        TestInterface iface = flickr.getTestInterface();
        User user = iface.login();
        assertNotNull(user);
    }

}
