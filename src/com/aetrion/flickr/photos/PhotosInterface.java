/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photos;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;

/**
 * @author Anthony Eden
 */
public abstract class PhotosInterface {
    
    public static final String METHOD_ADD_TAGS = "flickr.photos.addTags";
    public static final String METHOD_GET_CONTACTS_PHOTOS = "flickr.photos.getContactsPhotos";
    public static final String METHOD_GET_CONTACTS_PUBLIC_PHOTOS = "flickr.photos.getContactsPublicPhotos";
    public static final String METHOD_GET_CONTEXT = "flickr.photos.getContext";
    public static final String METHOD_GET_COUNTS = "flickr.photos.getCounts";
    public static final String METHOD_GET_EXIF = "flickr.photos.getExif";
    public static final String METHOD_GET_INFO = "flickr.photos.getInfo";
    public static final String METHOD_GET_NOT_IN_SET = "flickr.photos.getNotInSet";
    public static final String METHOD_GET_PERMS = "flickr.photos.getPerms";
    public static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    public static final String METHOD_GET_SIZES = "flickr.photos.getSizes";
    public static final String METHOD_GET_UNTAGGED = "flickr.photos.getUntagged";
    public static final String METHOD_REMOVE_TAG = "flickr.photos.removeTag";
    public static final String METHOD_SEARCH = "flickr.photos.search";
    public static final String METHOD_SET_DATES = "flickr.photos.setDates";
    public static final String METHOD_SET_META = "flickr.photos.setMeta";
    public static final String METHOD_SET_PERMS = "flickr.photos.setPerms";
    public static final String METHOD_SET_TAGS = "flickr.photos.setTags";
    
    public static PhotosInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new PhotosInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    /**
     * Add tags to a photo.
     *
     * @param photoId The photo ID
     * @param tags The tags
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void addTags(String photoId, String[] tags) throws IOException, SAXException, FlickrException;
    
    /**
     * Get photos from the user's contacts.
     *
     * @param count The number of photos to return
     * @param justFriends Set to true to only show friends photos
     * @param singlePhoto Set to true to get a single photo
     * @param includeSelf Set to true to include self
     * @return The Collection of photos
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getContactsPhotos(int count, boolean justFriends, boolean singlePhoto, boolean includeSelf)
    throws IOException, SAXException, FlickrException;
    
    /**
     * Get public photos from the user's contacts.
     *
     * @param userId The user ID
     * @param count The number of photos to return
     * @param justFriends True to include friends
     * @param singlePhoto True to get a single photo
     * @param includeSelf True to include self
     * @return A collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getContactsPublicPhotos(String userId, int count, boolean justFriends, boolean singlePhoto, boolean includeSelf)
    throws IOException, SAXException, FlickrException;
    
    /**
     * Get the context for the specified photo.
     *
     * @param photoId The photo ID
     * @return The PhotoContext
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract PhotoContext getContext(String photoId) throws IOException, SAXException, FlickrException;
    
    /**
     * Gets a collection of photo counts for the given date ranges for the calling user.
     *
     * @param dates An array of dates, denoting the periods to return counts for. They should be specified smallest
     * first.
     * @param takenDates An array of dates, denoting the periods to return counts for. They should be specified smallest
     * first.
     * @return A Collection of Photocount objects
     */
    
    public abstract Collection getCounts(Date[] dates, Date[] takenDates) throws IOException, SAXException,
    FlickrException;
    
    /**
     * Get the Exif data for the photo.
     *
     * @param photoId The photo ID
     * @param secret The secret
     * @return A collection of Exif objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getExif(String photoId, String secret) throws IOException, SAXException, FlickrException;
    
    /**
     * Get all info for the specified photo.
     *
     * @param photoId The photo Id
     * @param secret The optional secret String
     * @return The Photo
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Photo getInfo(String photoId, String secret) throws IOException, SAXException, FlickrException;
    
    /**
     * Return a collection of Photo objects not in part of any sets.
     *
     * @param perPage The per page
     * @param page The page
     * @return The collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getNotInSet(int perPage, int page) throws IOException, SAXException, FlickrException;
    
    /**
     * Get the permission information for the specified photo.
     *
     * @param photoId The photo id
     * @return The Permissions object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Permissions getPerms(String photoId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a collection of recent photos.
     *
     * @param perPage The number of photos per page
     * @param page The page offset
     * @return A collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getRecent(int perPage, int page) throws IOException, SAXException, FlickrException;
    
    /**
     * Get the available sizes for sizes.
     *
     * @param photoId The photo ID
     * @return The size collection
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getSizes(String photoId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get the collection of untagged photos.
     *
     * @param perPage
     * @param page
     * @return A Collection of Photos
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getUntagged(int perPage, int page) throws IOException, SAXException,
    FlickrException;
    
    /**
     * Remove a tag from a photo.
     *
     * @param tagId The tag ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void removeTag(String tagId) throws IOException, SAXException, FlickrException;
    
    /**
     * Search for photos which match the given search parameters.
     *
     * @param params The search parameters
     * @param perPage The number of photos to show per page
     * @param page The page offset
     * @return A PhotoList
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract PhotoList search(SearchParameters params, int perPage, int page) throws IOException, SAXException,
    FlickrException;
    
    /**
     * Set the dates for the specified photo.
     *
     * @param photoId The photo ID
     * @param datePosted The date the photo was posted or null
     * @param dateTaken The date the photo was taken or null
     * @param dateTakenGranularity The granularity of the taken date or null
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void setDates(String photoId, Date datePosted, Date dateTaken, String dateTakenGranularity)
    throws IOException, SAXException, FlickrException;
    
    /**
     * Set the meta data for the photo.
     *
     * @param photoId The photo ID
     * @param title The new title
     * @param description The new description
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void setMeta(String photoId, String title, String description) throws IOException,
    SAXException, FlickrException;
    
    /**
     * Set the permissions for the photo.
     *
     * @param photoId The photo ID
     * @param permissions The permissions object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void setPerms(String photoId, Permissions permissions) throws IOException,
    SAXException, FlickrException;
    
    /**
     * Set the tags for a photo.
     *
     * @param photoId The photo ID
     * @param tags The tag array
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void setTags(String photoId, String[] tags) throws IOException, SAXException,
    FlickrException;
    
}
