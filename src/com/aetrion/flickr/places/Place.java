package com.aetrion.flickr.places;

/**
 * Describes a place inside a {@link Location}.
 *
 * @author mago
 * @version $Id: Place.java,v 1.1 2008/01/13 20:46:07 x-mago Exp $
 */
public class Place {
    private String name = "";
    private String placeId = "";

    public Place() {
    }

    public Place(String placeId, String name) {
        this.name = name;
        this.placeId = placeId;
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

}
