package com.aetrion.flickr.places;

/**
 * A Flickr place.
 *
 * @author mago
 * @version $Id: Location.java,v 1.1 2008/01/13 20:46:07 x-mago Exp $
 * @see com.aetrion.flickr.photos.SearchParameters#setPlaceId(String)
 * @see com.aetrion.flickr.photos.Photo#getPlaceId()
 */
public class Location {
    private String name = "";
    private String placeId = "";
    private String placeUrl = "";
    private Place locality = null;
    private Place county = null;
    private Place region = null;
    private Place country = null;

    public Location() {
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

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public Place getLocality() {
        return locality;
    }

    public void setLocality(Place locality) {
        this.locality = locality;
    }

    public Place getCounty() {
        return county;
    }

    public void setCounty(Place county) {
        this.county = county;
    }

    public Place getRegion() {
        return region;
    }

    public void setRegion(Place region) {
        this.region = region;
    }

    public Place getCountry() {
        return country;
    }

    public void setCountry(Place country) {
        this.country = country;
    }
}
