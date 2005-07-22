/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.xml.parsers.ParserConfigurationException;
import javax.swing.JDialog;
import javax.swing.JButton;

import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.util.IOUtilities;
import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.BrowserLauncherRunner;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class AuthInterfaceTest extends TestCase {

    Flickr flickr = null;

    public void setUp() throws ParserConfigurationException, IOException {
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
        } finally {
            IOUtilities.close(in);
        }
    }

    public void testGetFrob() throws FlickrException, IOException, SAXException {
        AuthInterface authInterface = flickr.getAuthInterface();
        String frob = authInterface.getFrob();
        assertNotNull(frob);
    }

    public void testAuthentication() throws FlickrException, IOException, SAXException,
            BrowserLaunchingInitializingException, UnsupportedOperatingSystemException {
        AuthInterface authInterface = flickr.getAuthInterface();
        String frob = authInterface.getFrob();
        Permission permission = Permission.READ;
        URL url = authInterface.buildAuthenticationUrl(permission, frob);

        BrowserLauncher launcher = new BrowserLauncher(null);
        BrowserLauncherRunner runner = new BrowserLauncherRunner(launcher, url.toExternalForm(), null);
        Thread launcherThread = new Thread(runner);
        launcherThread.start();

        // display a dialog
        final JDialog d = new JDialog();
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                d.dispose();
            }
        });
        d.getContentPane().add(continueButton);
        d.setModal(true);
        d.pack();
        d.setVisible(true);

        Auth auth = authInterface.getToken(frob);
//        System.out.println("Token: " + auth.getToken());
//        System.out.println("Permission: " + auth.getPermission());
        assertNotNull(auth.getToken());
        assertEquals(auth.getPermission(), permission);
    }

}
