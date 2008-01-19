package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.places.Location;
import com.aetrion.flickr.places.Place;
import com.aetrion.flickr.places.PlacesInterface;
import com.aetrion.flickr.places.PlacesList;
import com.aetrion.flickr.util.IOUtilities;

/**
 * Tests for the PlacesInterface.
 *
 * @author mago
 * @version $Id: PlacesInterfaceTest.java,v 1.2 2008/01/19 22:53:56 x-mago Exp $
 */
public class PlacesInterfaceTest extends TestCase {

    Flickr flickr = null;
    Properties properties = null;

    public void setUp() throws
      ParserConfigurationException, IOException, FlickrException, SAXException {
        Flickr.debugRequest = false;
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/setup.properties");
            properties = new Properties();
            properties.load(in);

            REST rest = new REST();

            flickr = new Flickr(
                properties.getProperty("apiKey"),
                properties.getProperty("secret"),
                rest
            );

            RequestContext requestContext = RequestContext.getRequestContext();
            AuthInterface authInterface = flickr.getAuthInterface();
            Auth auth = authInterface.checkToken(properties.getProperty("token"));
            auth.setPermission(Permission.READ);
            requestContext.setAuth(auth);
        } finally {
            IOUtilities.close(in);
        }
    }

    public void testFind()
      throws FlickrException, IOException, SAXException {
        PlacesInterface placesInterface = flickr.getPlacesInterface();
        PlacesList list = placesInterface.find("Alabama");
        assertTrue(list.getTotal() == 3);
        Place place = (Place) list.get(0);
        assertEquals("VrrjuESbApjeFS4.", place.getPlaceId());
        assertEquals("/United+States/Alabama", place.getPlaceUrl());
        assertEquals(Place.TYPE_REGION, place.getPlaceType());

        place = (Place) list.get(1);
        assertEquals("cGHuc0mbApmzEHoP", place.getPlaceId());
        assertEquals("/United+States/New+York/Alabama", place.getPlaceUrl());
        assertEquals(Place.TYPE_LOCALITY, place.getPlaceType());

        place = (Place) list.get(2);
        assertEquals("o4yVPEqYBJvFMP8Q", place.getPlaceId());
        assertEquals("/South+Africa/North+West/Alabama", place.getPlaceUrl());
        assertEquals(Place.TYPE_LOCALITY, place.getPlaceType());
    }

    public void testResolvePlaceId()
      throws FlickrException, IOException, SAXException {
        PlacesInterface placesInterface = flickr.getPlacesInterface();
        Location location = placesInterface.resolvePlaceId("kH8dLOubBZRvX_YZ"); // SF
        placeAssertions(location);
    }

    public void testResolvePlaceUrl()
      throws FlickrException, IOException, SAXException {
        PlacesInterface placesInterface = flickr.getPlacesInterface();
        Location location = placesInterface.resolvePlaceURL("/United+States/California/San+Francisco");
        placeAssertions(location);
    }

    private void placeAssertions(Location location) {
        assertEquals(
            "kH8dLOubBZRvX_YZ",
            location.getPlaceId()
        );
        assertEquals(
            "San Francisco",
            location.getName()
        );
        assertEquals(
            "/United+States/California/San+Francisco",
            location.getPlaceUrl()
        );
        assertEquals(
            "kH8dLOubBZRvX_YZ",
            location.getLocality().getPlaceId()
        );
        assertEquals(
            "San Francisco",
            location.getLocality().getName()
        );

        assertEquals(
            "hCca8XSYA5nn0X1Sfw",
            location.getCounty().getPlaceId()
        );
        assertEquals(
            "San Francisco",
            location.getCounty().getName()
        );

        assertEquals(
            "SVrAMtCbAphCLAtP",
            location.getRegion().getPlaceId()
        );
        assertEquals(
            "California",
            location.getRegion().getName()
        );
        assertEquals(
            "4KO02SibApitvSBieQ",
            location.getCountry().getPlaceId()
        );
        assertEquals(
            "United States",
            location.getCountry().getName()
        );
    }
}
