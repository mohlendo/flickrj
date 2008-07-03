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
 * @version $Id: PlacesInterfaceTest.java,v 1.4 2008/07/03 21:37:44 x-mago Exp $
 */
public class PlacesInterfaceTest extends TestCase {

    Flickr flickr = null;
    Properties properties = null;

    public void setUp() throws
      ParserConfigurationException, IOException, FlickrException, SAXException {
        Flickr.debugRequest = false;
        Flickr.debugStream = true;
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

    public void testFindByLonLat()
      throws FlickrException, IOException, SAXException {
        PlacesInterface placesInterface = flickr.getPlacesInterface();
        PlacesList list = placesInterface.findByLatLon(
            52.524577D,
            13.412247D,
            Flickr.ACCURACY_CITY
        );
        assertTrue(list.getTotal() == 1);
        Place place = (Place) list.get(0);
        assertEquals("sRdiycKfApRGrrU", place.getPlaceId());
        assertEquals("/Germany/Berlin/Berlin", place.getPlaceUrl());
        assertEquals(Place.TYPE_LOCALITY, place.getPlaceType());
        assertEquals("638242", place.getWoeId());
        assertEquals(52.515D, place.getLatitude());
        assertEquals(13.377D, place.getLongitude());
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
            "/United+States/California/San+Francisco",
            location.getPlaceUrl()
        );
        assertEquals(
            "2487956",
            location.getWoeId()
        );
        assertEquals(
            37.779D,
            location.getLatitude()
        );
        assertEquals(
            -122.420D,
            location.getLongitude()
        );
        assertEquals(
            Place.TYPE_LOCALITY,
            location.getPlaceType()
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
            "2487956",
            location.getLocality().getWoeId()
        );
        assertEquals(
            37.779D,
            location.getLocality().getLatitude()
        );
        assertEquals(
            -122.420D,
            location.getLocality().getLongitude()
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
            "12587707",
            location.getCounty().getWoeId()
        );
        assertEquals(
            37.759D,
            location.getCounty().getLatitude()
        );
        assertEquals(
            -122.435D,
            location.getCounty().getLongitude()
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
            "2347563",
            location.getRegion().getWoeId()
        );
        assertEquals(
            37.271D,
            location.getRegion().getLatitude()
        );
        assertEquals(
            -119.270D,
            location.getRegion().getLongitude()
        );

        assertEquals(
            "4KO02SibApitvSBieQ",
            location.getCountry().getPlaceId()
        );
        assertEquals(
            "United States",
            location.getCountry().getName()
        );
        assertEquals(
            "23424977",
            location.getCountry().getWoeId()
        );
        assertEquals(
            48.890D,
            location.getCountry().getLatitude()
        );
        assertEquals(
            -116.982D,
            location.getCountry().getLongitude()
        );
    }
}
