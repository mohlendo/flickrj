package com.aetrion.flickr;

import com.aetrion.flickr.blogs.BlogsInterface;
import com.aetrion.flickr.contacts.ContactsInterface;
import com.aetrion.flickr.favorites.FavoritesInterface;
import com.aetrion.flickr.groups.GroupsInterface;
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.test.TestInterface;
import com.aetrion.flickr.photosets.PhotosetsInterface;
import com.aetrion.flickr.tags.TagsInterface;
import com.aetrion.flickr.urls.UrlsInterface;

/**
 * @author Anthony Eden
 */
public class Flickr {

    private String apiKey;
    private REST restInterface;

    private BlogsInterface blogsInterface;
    private ContactsInterface contactsInterface;
    private FavoritesInterface favoritesInterface;
    private GroupsInterface groupsInterface;
    private PeopleInterface peopleInterface;
    private PhotosInterface photosInterface;
    private PhotosetsInterface photosetsInterface;
    private TagsInterface tagsInterface;
    private TestInterface testInterface;
    private UrlsInterface urlsInterface;

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

    public String getApiKey() {
        return apiKey;
    }

    /**
     * Set the API key to use which must not be null.
     *
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        if (apiKey == null) {
            throw new IllegalArgumentException("API key must not be null");
        }
        this.apiKey = apiKey;
    }

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
