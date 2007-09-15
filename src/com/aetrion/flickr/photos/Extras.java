/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.photos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anthony Eden
 * @version $Id: Extras.java,v 1.4 2007/09/15 23:00:28 x-mago Exp $
 */
public class Extras {

    public static final String KEY_EXTRAS = "extras";

    public static final String LICENSE = "license";
    public static final String DATE_UPLOAD = "date_upload";
    public static final String DATE_TAKEN = "date_taken";
    public static final String OWNER_NAME = "owner_name";
    public static final String ICON_SERVER = "icon_server";
    public static final String ORIGINAL_FORMAT = "original_format";
    public static final String LAST_UPDATE = "last_update";
    public static final String GEO = "geo";
    public static final String TAGS = "tags";
    public static final String MACHINE_TAGS = "machine_tags";

    /**
     * Set of all extra-arguments. Used for requesting lists of photos.
     *
     * @see com.aetrion.flickr.people.PeopleInterface#getPublicPhotos(String, Set, int, int)
     * @see com.aetrion.flickr.groups.pools.PoolsInterface#getPhotos(String, String[], Set, int, int)
     * @see com.aetrion.flickr.photosets.PhotosetsInterface#getPhotos(String, Set, int, int, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getContactsPublicPhotos(String, Set, int, boolean, boolean, boolean)
     * @see com.aetrion.flickr.photos.PhotosInterface#getWithGeoData(java.util.Date, java.util.Date, java.util.Date, java.util.Date, int, String, Set, int, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getWithoutGeoData(java.util.Date, java.util.Date, java.util.Date, java.util.Date, int, String, Set, int, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#recentlyUpdated(java.util.Date, Set, int, int)
     */
    public static final Set ALL_EXTRAS = new HashSet();

    /**
     * Minimal Set of extra-arguments. Used by convenience-methods
     * that request lists of photos.
     *
     * @see com.aetrion.flickr.people.PeopleInterface#getPublicPhotos(String, int, int)
     * @see com.aetrion.flickr.groups.pools.PoolsInterface#getPhotos(String, String[], int, int)
     * @see com.aetrion.flickr.photosets.PhotosetsInterface#getPhotos(String, int, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getContactsPublicPhotos(String, int, boolean, boolean, boolean)
     */
    public static final Set MIN_EXTRAS = new HashSet();

    static {
        ALL_EXTRAS.add(DATE_TAKEN);
        ALL_EXTRAS.add(DATE_UPLOAD);
        ALL_EXTRAS.add(ICON_SERVER);
        ALL_EXTRAS.add(LAST_UPDATE);
        ALL_EXTRAS.add(LICENSE);
        ALL_EXTRAS.add(ORIGINAL_FORMAT);
        ALL_EXTRAS.add(OWNER_NAME);
        ALL_EXTRAS.add(GEO);
        ALL_EXTRAS.add(TAGS);
        ALL_EXTRAS.add(MACHINE_TAGS);
    }

    static {
        MIN_EXTRAS.add(ORIGINAL_FORMAT);
        MIN_EXTRAS.add(OWNER_NAME);
    }

    /**
     * No-op constructor.
     */
    private Extras() {
    }

}
