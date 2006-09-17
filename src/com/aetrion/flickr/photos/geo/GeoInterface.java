package com.aetrion.flickr.photos.geo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.photos.GeoData;
import com.aetrion.flickr.util.XMLUtilities;

/**
 * Access to the flickr.photos.geo methods.
 * @author till (Till Krech - flickr:extranoise)
 *
 */
public class GeoInterface {
    public static final String METHOD_GET_LOCATION = "flickr.photos.geo.getLocation";
    public static final String METHOD_GET_PERMS = "flickr.photos.geo.getPerms";
    public static final String METHOD_REMOVE_LOCATION = "flickr.photos.geo.removeLocation";
    public static final String METHOD_SET_LOCATION = "flickr.photos.geo.setLocation";
    public static final String METHOD_SET_PERMS = "flickr.photos.geo.setPerms";

    private String apiKey;
    private Transport transport;

    public GeoInterface(String apiKey, Transport transport) {
        this.apiKey = apiKey;
        this.transport = transport;
    }
    
    /**
     * Get the geo data (latitude and longitude and the accuracy level) for a photo.
     * This method does not require authentication.
     * @param photoId reqired photo id, not null
     * @return Geo Data, if the photo has it. 
     * @throws SAXException 
     * @throws IOException 
     * @throws FlickrException if photo id is invalid, if photo has no geodata 
     * or if any other error has been reported in the response.
     */
    public GeoData getLocation(String photoId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_LOCATION));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("photo_id", photoId));

        Response response = transport.get(transport.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        // response:
		// <photo id="123">
		//  <location latitude="-17.685895" longitude="-63.36914" accuracy="6" />
		// </photo>

        Element photoElement = response.getPayload();
        
        Element locationElement = XMLUtilities.getChild(photoElement, "location");
        String latStr = locationElement.getAttribute("latitude");
        String lonStr = locationElement.getAttribute("longitude");
        String accStr = locationElement.getAttribute("accuracy");
        // I ignore the id attribute. should be the same as the given
        // photo id.
        GeoData geoData = new GeoData(lonStr, latStr, accStr);
        return geoData;
    }
    
    /**
     * Get permissions for who may view geo data for a photo.
     * This method requires authentication with 'read' permission.
     * @param photoId reqired photo id, not null
     * @return the permissions
     * @throws SAXException 
     * @throws IOException 
     * @throws SAXException 
     * @throws IOException 
     * @throws FlickrException 
     * @throws FlickrException if photo id is invalid, if photo has no geodata 
     * or if any other error has been reported in the response.
     */
    public GeoPermissions getPerms(String photoId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_PERMS));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("photo_id", photoId));
        
        Response response = transport.get(transport.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        // response:
        // <perms id="240935723" ispublic="1" iscontact="0" isfriend="0" isfamily="0"/>
        GeoPermissions perms = new GeoPermissions();
        Element permsElement = response.getPayload();
        perms.setPublic("1".equals(permsElement.getAttribute("ispublic")));
        perms.setContact("1".equals(permsElement.getAttribute("iscontact")));
        perms.setFriend("1".equals(permsElement.getAttribute("isfriend")));
        perms.setFamily("1".equals(permsElement.getAttribute("isfamily")));
        // I ignore the id attribute. should be the same as the given
        // photo id.
		return perms;
    }
    
    /**
     * Removes the geo data associated with a photo.
     * This method requires authentication with 'write' permission.
     * @throws SAXException 
     * @throws IOException 
     * @throws FlickrException
     */
    public void removeLocation(String photoId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_REMOVE_LOCATION));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("photo_id", photoId));
    	// Note: This method requires an HTTP POST request.
        Response response = transport.post(transport.getPath(), parameters);
        // This method has no specific response - It returns an empty sucess response 
        // if it completes without error.
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    	
    }
    
    /**
     * Sets the geo data (latitude and longitude and, optionally, the accuracy level) for a photo. 
     * Before users may assign location data to a photo they must define who, by default, 
     * may view that information. Users can edit this preference 
     * at http://www.flickr.com/account/geo/privacy/. If a user has not set this preference, 
     * the API method will return an error.
     * This method requires authentication with 'write' permission.
     * @param photoId The id of the photo to cet permissions for.
     * @param location geo data with optional accuracy (1-16), accuracy 0 to use the default.
     * @throws SAXException 
     * @throws IOException 
     * @throws FlickrException 
     */
    public void setLocation(String photoId, GeoData location) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_SET_LOCATION));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("lat", String.valueOf(location.getLatitude())));
        parameters.add(new Parameter("lon", String.valueOf(location.getLongitude())));
        int accuracy = location.getAccuracy();
        if (accuracy > 0) {
            parameters.add(new Parameter("accuracy", String.valueOf(location.getAccuracy())));
        }
    	// Note: This method requires an HTTP POST request.
        Response response = transport.post(transport.getPath(), parameters);
        // This method has no specific response - It returns an empty sucess response 
        // if it completes without error.
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }
    
    /**
     * Set the permission for who may view the geo data associated with a photo.
     * This method requires authentication with 'write' permission.
     * @param photoId The id of the photo to set permissions for.
     * @param perms Permissions, who can see the geo data of this photo
     * @throws SAXException 
     * @throws IOException 
     * @throws FlickrException 
     */
    public void setPerms(String photoId, GeoPermissions perms) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_SET_PERMS));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("is_public", perms.isPublic() ? "1" : "0"));
        parameters.add(new Parameter("is_contact", perms.isContact() ? "1" : "0"));
        parameters.add(new Parameter("is_friend", perms.isFriend() ? "1" : "0"));
        parameters.add(new Parameter("is_family", perms.isFamily() ? "1" : "0"));
    	// Note: This method requires an HTTP POST request.
        Response response = transport.post(transport.getPath(), parameters);
        // This method has no specific response - It returns an empty sucess response 
        // if it completes without error.
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }



    

}
