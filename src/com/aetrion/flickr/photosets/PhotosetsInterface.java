/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photosets;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.photos.PhotoContext;

/**
 * Interface for working with photosets.
 *
 * @author Anthony Eden
 */
public abstract class PhotosetsInterface {
    
    public static final String METHOD_ADD_PHOTO = "flickr.photos.addPhoto";
    public static final String METHOD_CREATE = "flickr.photosets.create";
    public static final String METHOD_DELETE = "flickr.photosets.delete";
    public static final String METHOD_EDIT_META = "flickr.photosets.editMeta";
    public static final String METHOD_EDIT_PHOTOS = "flickr.photosets.editPhotos";
    public static final String METHOD_GET_CONTEXT = "flickr.photosets.getContext";
    public static final String METHOD_GET_INFO = "flickr.photosets.getInfo";
    public static final String METHOD_GET_LIST = "flickr.photosets.getList";
    public static final String METHOD_GET_PHOTOS = "flickr.photosets.getPhotos";
    public static final String METHOD_ORDER_SETS = "flickr.photosets.orderSets";
    public static final String METHOD_REMOVE_PHOTO = "flickr.photosets.removePhoto";
    
    public static PhotosetsInterface getInterface(String apiKey, Transport transport) {
        if (transport.getTransportType().equals(Transport.REST)) {
            return new PhotosetsInterfaceREST(apiKey, (REST)transport);
        }
        //put the SOAP version here
        return null;
    }
    
    /**
     * Add a photo to the end of the photoset.
     *
     * Note: requires authentication with the new authentication API with 'write' permission.
     *
     * @param photosetId The photoset ID
     * @param photoId The photo ID
     */
    public abstract void addPhoto(String photosetId, String photoId) throws IOException, SAXException, FlickrException;
    
    /**
     * Create a new photoset.
     *
     * @param title The photoset title
     * @param description The photoset description
     * @param primaryPhotoId The primary photo id
     * @return The new Photset
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Photoset create(String title, String description, String primaryPhotoId) throws IOException, SAXException, FlickrException;
    
    /**
     * Delete the specified photoset.
     *
     * @param photosetId The photoset ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void delete(String photosetId) throws IOException, SAXException, FlickrException;
    
    /**
     * Delete the specified photoset.
     *
     * @param photosetId The photoset ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void editMeta(String photosetId, String title, String description) throws IOException, SAXException, FlickrException;
    
    /**
     * Edit which photos are in the photoset.
     *
     * @param photosetId The photoset ID
     * @param primaryPhotoId The primary photo Id
     * @param photoIds The photo IDs for the photos in the set
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void editPhotos(String photosetId, String primaryPhotoId, String[] photoIds) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a photo's context in the specified photo set.
     *
     * @param photoId The photo ID
     * @param photosetId The photoset ID
     * @return The PhotoContext
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract PhotoContext getContext(String photoId, String photosetId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get the information for a specified photoset.
     *
     * @param photosetId The photoset ID
     * @return The Photoset
     * @throws FlickrException
     * @throws IOException
     * @throws SAXException
     */
    public abstract Photoset getInfo(String photosetId) throws FlickrException, IOException, SAXException;
    
    /**
     * Get a list of all photosets for the specified user.
     *
     * @param userId The User id
     * @return The Photosets object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Photosets getList(String userId) throws IOException, SAXException, FlickrException;
    
    /**
     * Get a collection of Photo objects for the specified Photoset.
     *
     * @param photosetId The photoset ID
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract Collection getPhotos(String photosetId) throws IOException, SAXException, FlickrException;
    
    /**
     * Set the order in which sets are returned for the user.
     *
     * @param photosetIds
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void orderSets(String[] photosetIds) throws IOException, SAXException, FlickrException;
    
    /**
     * Remove a photo from the set.
     *
     * @param photosetId The photoset ID
     * @param photoId The photo ID
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public abstract void removePhoto(String photosetId, String photoId) throws IOException, SAXException, FlickrException;
    
}
