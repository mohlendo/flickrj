/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.reflection.Method;
import com.aetrion.flickr.reflection.ReflectionInterface;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class ReflectionInterfaceTest extends TestCase {

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

    public void testGetMethodInfo() throws FlickrException, IOException, SAXException {
        String methodName = "flickr.urls.lookupGroup";
        ReflectionInterface reflectionInterface = flickr.getReflectionInterface();
        Method method = reflectionInterface.getMethodInfo(methodName);
        assertNotNull(method);
        assertEquals(methodName, method.getName());
    }

    public void testGetMethods() throws FlickrException, IOException, SAXException {
        ReflectionInterface reflectionInterface = flickr.getReflectionInterface();
        Collection methods = reflectionInterface.getMethods();
        assertNotNull(methods);
        assertTrue("There are no methods in the method list", methods.size() > 0);
    }

}
