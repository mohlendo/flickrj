/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.photos.licenses.LicensesInterface;
import com.aetrion.flickr.photos.licenses.License;
import com.aetrion.flickr.util.IOUtilities;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class LicensesInterfaceTest extends TestCase {

    Flickr flickr = null;
    Authentication auth = null;

    public void setUp() throws ParserConfigurationException, IOException {
        Flickr.debugStream = true;
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

    public void testGetInfo() throws FlickrException, IOException, SAXException {
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuthentication(auth);
        LicensesInterface iface = flickr.getLicensesInterface();
        Collection licenses = iface.getInfo();
        assertNotNull(licenses);
        assertTrue(licenses.size() > 0);
        Iterator iter = licenses.iterator();
        while (iter.hasNext()) {
            License license = (License) iter.next();
            assertNotNull(license.getId());
            assertNotNull(license.getName());
            assertNotNull(license.getUrl());
        }
    }

}
