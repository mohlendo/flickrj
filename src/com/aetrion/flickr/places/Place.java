package com.aetrion.flickr.places;

/**
 * Describes a place inside a {@link Location}.
 *
 * Each place contain its place ID, corresponding URL
 * (underneath http://www.flickr.com/places/) and place type for
 * disambiguating different locations with the same name.<p>
 *
 * A place delivered by find contains an URL, whereas the URL is missing if
 * delivered by resolvePlaceId and resolvePlaceUrl.
 *
 * @author mago
 * @version $Id: Place.java,v 1.4 2008/07/03 21:37:44 x-mago Exp $
 */
public class Place {
    public static final int TYPE_UNSET = 0;
    public static final int TYPE_LOCALITY = 1;
    public static final int TYPE_COUNTY = 2;
    public static final int TYPE_REGION = 3;
    public static final int TYPE_COUNTRY = 4;

    private String name = "";
    private String placeId = "";
    private String woeId = "";
    private double latitude = 0.0;
    private double longitude = 0.0;
    /**
     * Set only if requested by find.
     */
    private String placeUrl = "";
    private int placeType = 0;

    public Place() {
    }

    public Place(String placeId, String name) {
        this.name = name;
        this.placeId = placeId;
    }

    public Place(String placeId, String name, int placeType) {
        this.name = name;
        this.placeId = placeId;
        this.placeType = placeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getWoeId() {
        return woeId;
    }

    public void setWoeId(String woeId) {
        this.woeId = woeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        try {
            setLatitude(Double.parseDouble(latitude));
        } catch (NumberFormatException e) {}
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        try {
            setLongitude(Double.parseDouble(longitude));
        } catch (NumberFormatException e) {}
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
