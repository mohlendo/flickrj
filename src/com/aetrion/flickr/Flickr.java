package com.aetrion.flickr;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.blogs.BlogsInterface;
import com.aetrion.flickr.contacts.ContactsInterface;
import com.aetrion.flickr.favorites.FavoritesInterface;
import com.aetrion.flickr.groups.GroupsInterface;
import com.aetrion.flickr.groups.pools.PoolsInterface;
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photosets.PhotosetsInterface;
import com.aetrion.flickr.tags.TagsInterface;
import com.aetrion.flickr.test.TestInterface;
import com.aetrion.flickr.urls.UrlsInterface;

/**
 * Main entry point for the Flickrj API.  This class is used to acquire Interface classes which wrap the Flickr API.
 *
 * @author Anthony Eden
 */
public class Flickr {

    public static final String DEFAULT_HOST = "www.flickr.com";

    /**
     * Set to true to enable stream debugging.
     */
    public static boolean debugStream = false;

    private String apiKey;
    private REST restInterface;

    private BlogsInterface blogsInterface;
    private ContactsInterface contactsInterface;
    private FavoritesInterface favoritesInterface;
    private GroupsInterface groupsInterface;
    private PoolsInterface poolsInterface;
    private PeopleInterface peopleInterface;
    private PhotosInterface photosInterface;
    private PhotosetsInterface photosetsInterface;
    private TagsInterface tagsInterface;
    private TestInterface testInterface;
    private UrlsInterface urlsInterface;

    /**
     * Construct a new Flickr gateway instance.
     *
     * @param apiKey The API key.
     */
    public Flickr(String apiKey) {
        setApiKey(apiKey);
        try {
            setRestInterface(new REST(DEFAULT_HOST));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Construct a new Flickr gateway instance.
     *
     * @param apiKey The API key, must be non-null
     * @param restInterface The REST interface, must be non-null
     */
    public Flickr(String apiKey, REST restInterface) {
        setApiKey(apiKey);
        setRestInterface(restInterface);
    }

    /**
     * Get the API key.
     *
     * @return The API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Set the API key to use which must not be null.
     *
     * @param apiKey The API key which cannot be null
     */
    public void setApiKey(String apiKey) {
        if (apiKey == null) {
            throw new IllegalArgumentException("API key must not be null");
        }
        this.apiKey = apiKey;
    }

    /**
     * Get the REST interface.
     *
     * @return The REST interface
     */
    public REST getRestInterface() {
        return restInterface;
    }

    /**
     * Set the REST interface which must not be null.
     *
     * @param restInterface
     */
    public void setRestInterface(REST restInterface) {
        if (restInterface == null) {
            throw new IllegalArgumentException("REST interface must not be null");
        }
        this.restInterface = restInterface;
    }

    public synchronized BlogsInterface getBlogsInterface() {
        if (blogsInterface == null) {
            blogsInterface = new BlogsInterface(apiKey, restInterface);
        }
        return blogsInterface;
    }

    public ContactsInterface getContactsInterface() {
        if (contactsInterface == null) {
            contactsInterface = new ContactsInterface(apiKey, restInterface);
        }
        return contactsInterface;
    }

    public FavoritesInterface getFavoritesInterface() {
        if (favoritesInterface == null) {
            favoritesInterface = new FavoritesInterface(apiKey, restInterface);
        }
        return favoritesInterface;
    }

    public GroupsInterface getGroupsInterface() {
        if (groupsInterface == null) {
            groupsInterface = new GroupsInterface(apiKey, restInterface);
        }
        return groupsInterface;
    }

    public PoolsInterface getPoolsInterface() {
        if (poolsInterface == null) {
            poolsInterface = new PoolsInterface(apiKey, restInterface);
        }
        return poolsInterface;
    }

    public PeopleInterface getPeopleInterface() {
        if (peopleInterface == null) {
            peopleInterface = new PeopleInterface(apiKey, restInterface);
        }
        return peopleInterface;
    }

    public PhotosInterface getPhotosInterface() {
        if (photosInterface == null) {
            photosInterface = new PhotosInterface(apiKey, restInterface);
        }
        return photosInterface;
    }

    public PhotosetsInterface getPhotosetsInterface() {
        if (photosetsInterface == null) {
            photosetsInterface = new PhotosetsInterface(apiKey, restInterface);
        }
        return photosetsInterface;
    }

    /**
     * Get the TagsInterface for working with Flickr Tags.
     * 
     * @return The TagsInterface
     */
    public TagsInterface getTagsInterface() {
        if (tagsInterface == null) {
            tagsInterface = new TagsInterface(apiKey, restInterface);
        }
        return tagsInterface;
    }

    public TestInterface getTestInterface() {
        if (testInterface == null) {
            testInterface = new TestInterface(apiKey, restInterface);
        }
        return testInterface;
    }

    public UrlsInterface getUrlsInterface() {
        if (urlsInterface == null) {
            urlsInterface = new UrlsInterface(apiKey, restInterface);
        }
        return urlsInterface;
    }

}
