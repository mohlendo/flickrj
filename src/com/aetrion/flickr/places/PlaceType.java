package com.aetrion.flickr.places;

/**
 * PlaceType as requested by {@link PlacesInterface#getPlaceTypes()}
 *
 * @see Place#TYPE_COUNTRY
 * @see Place#TYPE_COUNTY
 * @see Place#TYPE_LOCALITY
 * @see Place#TYPE_REGION
 * @see Place#TYPE_NEIGHBOURHOOD
 * @see Place#TYPE_CONTINENT
 * @author mago
 * @version $Id: PlaceType.java,v 1.1 2009/01/04 21:18:19 x-mago Exp $
 */
public class PlaceType {
    int placeTypeId;
    String placeTypeName;

    public int getPlaceTypeId() {
        return placeTypeId;
    }

    public void setPlaceTypeId(String placeTypeId) {
        try {
            setPlaceTypeId(Integer.parseInt(placeTypeId));
        } catch (NumberFormatException e) {}
    }

    public void setPlaceTypeId(int placeTypeId) {
        this.placeTypeId = placeTypeId;
    }

    public String getPlaceTypeName() {
        return placeTypeName;
    }

    public void setPlaceTypeName(String placeTypeName) {
        this.placeTypeName = placeTypeName;
    }

}
