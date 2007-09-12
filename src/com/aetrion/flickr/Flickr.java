/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.activity.ActivityInterface;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.blogs.BlogsInterface;
import com.aetrion.flickr.contacts.ContactsInterface;
import com.aetrion.flickr.favorites.FavoritesInterface;
import com.aetrion.flickr.groups.GroupsInterface;
import com.aetrion.flickr.groups.pools.PoolsInterface;
import com.aetrion.flickr.interestingness.InterestingnessInterface;
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.licenses.LicensesInterface;
import com.aetrion.flickr.photos.notes.NotesInterface;
import com.aetrion.flickr.photos.transform.TransformInterface;
import com.aetrion.flickr.photosets.PhotosetsInterface;
import com.aetrion.flickr.prefs.PrefsInterface;
import com.aetrion.flickr.reflection.ReflectionInterface;
import com.aetrion.flickr.tags.TagsInterface;
import com.aetrion.flickr.test.TestInterface;
import com.aetrion.flickr.urls.UrlsInterface;

/**
 * Main entry point for the Flickrj API.
 * This class is used to acquire Interface classes which wrap the Flickr API.
 *
 * @author Anthony Eden
 * @version $Id: Flickr.java,v 1.31 2007/09/12 22:18:37 x-mago Exp $
 */
public class Flickr {

    /**
     * The default endpoint host.
     */
    public static final String DEFAULT_HOST = "www.flickr.com";

    /**
     * Set to true to enable response debugging (print the response stream)
     */
    public static boolean debugStream = false;

    /**
     * Set to true to enable request debugging (print the request stream, used for "post")
     */
    public static boolean debugRequest = false;

    /**
     * If set to true, trace messages will be printed to STDOUT.
     */
    public static boolean tracing = false;

    private String apiKey;
    private Transport transport;

    private AuthInterface authInterface;
    private ActivityInterface activityInterface;
    private BlogsInterface blogsInterface;
    private ContactsInterface contactsInterface;
    private FavoritesInterface favoritesInterface;
    private GroupsInterface groupsInterface;
    private LicensesInterface licensesInterface;
    private NotesInterface notesInterface;
    private PoolsInterface poolsInterface;
    private PeopleInterface peopleInterface;
    private PhotosInterface photosInterface;
    private PhotosetsInterface photosetsInterface;
    private PrefsInterface prefsInterface;
    private ReflectionInterface reflectionInterface;
    private TagsInterface tagsInterface;
    private TestInterface testInterface;
    private TransformInterface transformInterface;
    private UrlsInterface urlsInterface;
    private InterestingnessInterface interestingnessInterface;

    /**
     * @see com.aetrion.flickr.prefs.PrefsInterface#getContentType()
     * @see com.aetrion.flickr.uploader.UploadMetaData#setContentType(String)
     */
    public static final String CONTENTTYPE_PHOTO = "1";

    /**
     * @see com.aetrion.flickr.prefs.PrefsInterface#getContentType()
     * @see com.aetrion.flickr.uploader.UploadMetaData#setContentType(String)
     */
    public static final String CONTENTTYPE_SCREENSHOT = "2";

    /**
     * @see com.aetrion.flickr.prefs.PrefsInterface#getContentType()
     * @see com.aetrion.flickr.uploader.UploadMetaData#setContentType(String)
     */
    public static final String CONTENTTYPE_OTHER = "3";

	/**
	 * The lowest accuracy for bounding-box searches.
	 *
	 * @see com.aetrion.flickr.photos.SearchParameters#setAccuracy(int)
	 */
	public static int ACCURACY_WORLD = 1;

	/**
	 * @see com.aetrion.flickr.photos.SearchParameters#setAccuracy(int)
	 */
	public static int ACCURACY_COUNTRY = 3;

	/**
	 * @see com.aetrion.flickr.photos.SearchParameters#setAccuracy(int)
	 */
	public static int ACCURACY_REGION = 6;

	/**
	 * @see com.aetrion.flickr.photos.SearchParameters#setAccuracy(int)
	 */
	public static int ACCURACY_CITY = 11;

	/**
	 * The highest accuracy for bounding-box searches.
	 *
	 * @see com.aetrion.flickr.photos.SearchParameters#setAccuracy(int)
	 */
	public static int ACCURACY_STREET = 16;
    
    /**
     * @see com.aetrion.flickr.photos.PhotosInterface#setSafetyLevel(String, String, Boolean)
     * @see com.aetrion.flickr.prefs.PrefsInterface#getSafetyLevel()
     * @see com.aetrion.flickr.uploader.UploadMetaData#setSafetyLevel(String)
	 * @see com.aetrion.flickr.photos.SearchParameters#setSafeSearch(int)
     */
    public static final String SAFETYLEVEL_SAFE = "1";
    /**
     * @see com.aetrion.flickr.photos.PhotosInterface#setSafetyLevel(String, String, Boolean)
     * @see com.aetrion.flickr.prefs.PrefsInterface#getSafetyLevel()
     * @see com.aetrion.flickr.uploader.UploadMetaData#setSafetyLevel(String)
	 * @see com.aetrion.flickr.photos.SearchParameters#setSafeSearch(int)
     */
    public static final String SAFETYLEVEL_MODERATE = "2";
    /**
     * @see com.aetrion.flickr.photos.PhotosInterface#setSafetyLevel(String, String, Boolean)
     * @see com.aetrion.flickr.prefs.PrefsInterface#getSafetyLevel()
     * @see com.aetrion.flickr.uploader.UploadMetaData#setSafetyLevel(String)
	 * @see com.aetrion.flickr.photos.SearchParameters#setSafeSearch(int)
     */
    public static final String SAFETYLEVEL_RESTRICTED = "3";

    /**
     * 
     * All the extras-constants here by mistake.
     * The intended location is com.aetrion.flickr.photos.Extras
     * 
     * @deprecated
     */
    public static final String KEY_EXTRAS = "extras";

    /**
     * @deprecated
     */
    public static final String EXTRAS_LICENSE = "license";
    /**
     * @deprecated
     */
    public static final String EXTRAS_DATE_UPLOAD = "date_upload";
    /**
     * @deprecated
     */
    public static final String EXTRAS_DATE_TAKEN = "date_taken";
    /**
     * @deprecated
     */
    public static final String EXTRAS_OWNER_NAME = "owner_name";
    /**
     * @deprecated
     */
    public static final String EXTRAS_ICON_SERVER = "icon_server";
    /**
     * @deprecated
     */
    public static final String EXTRAS_ORIGINAL_FORMAT = "original_format";
    /**
     * @deprecated
     */
    public static final String EXTRAS_LAST_UPDATE = "last_update";
    /**
     * @deprecated
     */
    public static final String EXTRAS_GEO = "geo";
    /**
     * @deprecated
     */
    public static final String EXTRAS_TAGS = "tags";
    /**
     * @deprecated
     */
    public static final String EXTRAS_MACHINE_TAGS = "machine_tags";

    /**
     * Set of extra-arguments. Used for requesting lists of photos in the
     * following methods:<p>
     * PeopleInterface.getPublicPhotos()<br>
     * PoolsInterface.getPhotos()<br>
     * PhotosetsInterface.getPhotos()<br>
     * 
     * @deprecated
     */
    public static final Set ALL_EXTRAS = new HashSet();
    /**
     * @deprecated
     */
    public static final Set MIN_EXTRAS = new HashSet();

    static {
        ALL_EXTRAS.add(EXTRAS_DATE_TAKEN);
        ALL_EXTRAS.add(EXTRAS_DATE_UPLOAD);
        ALL_EXTRAS.add(EXTRAS_ICON_SERVER);
        ALL_EXTRAS.add(EXTRAS_LAST_UPDATE);
        ALL_EXTRAS.add(EXTRAS_LICENSE);
        ALL_EXTRAS.add(EXTRAS_ORIGINAL_FORMAT);
        ALL_EXTRAS.add(EXTRAS_OWNER_NAME);
        ALL_EXTRAS.add(EXTRAS_GEO);
        ALL_EXTRAS.add(EXTRAS_TAGS);
        ALL_EXTRAS.add(EXTRAS_MACHINE_TAGS);
    }

    static {
        MIN_EXTRAS.add(EXTRAS_ORIGINAL_FORMAT);
        MIN_EXTRAS.add(EXTRAS_OWNER_NAME);
    }
    
    /**
     * Construct a new Flickr gateway instance.  Defaults to a REST transport.
     *
     * @param apiKey The API key, must be non-null
     */
    public Flickr(String apiKey) {
        setApiKey(apiKey);
        try {
            setTransport(new REST(DEFAULT_HOST));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Construct a new Flickr gateway instance.
     *
     * @param apiKey The API key, must be non-null
     * @param transport The transport (REST or SOAP), must be non-null
     */
    public Flickr(String apiKey, Transport transport) {
        setApiKey(apiKey);
        setTransport(transport);
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
     * Get the Transport interface.
     *
     * @return The Tranport interface
     */
    public Transport getTransport() {
        return transport;
    }

    /**
     * Set the Transport which must not be null.
     *
     * @param transport
     */
    public void setTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport must not be null");
        }
        this.transport = transport;
    }

    /**
     * Get the AuthInterface.
     *
     * @return The AuthInterface
     */
    public AuthInterface getAuthInterface() {
        if (authInterface == null) {
            authInterface = new AuthInterface(apiKey, transport);
        }
        return authInterface;
    }

    /**
     * Get the ActivityInterface.
     *
     * @return The ActivityInterface
     */
    public ActivityInterface getActivityInterface() {
        if (activityInterface == null) {
            activityInterface = new ActivityInterface(apiKey, transport);
        }
        return activityInterface;
    }

    public synchronized BlogsInterface getBlogsInterface() {
        if (blogsInterface == null) {
            blogsInterface = new BlogsInterface(apiKey, transport);
        }
        return blogsInterface;
    }

    public ContactsInterface getContactsInterface() {
        if (contactsInterface == null) {
            contactsInterface = new ContactsInterface(apiKey, transport);
        }
        return contactsInterface;
    }

    public FavoritesInterface getFavoritesInterface() {
        if (favoritesInterface == null) {
            favoritesInterface = new FavoritesInterface(apiKey, transport);
        }
        return favoritesInterface;
    }

    public GroupsInterface getGroupsInterface() {
        if (groupsInterface == null) {
            groupsInterface = new GroupsInterface(apiKey, transport);
        }
        return groupsInterface;
    }

    public LicensesInterface getLicensesInterface() {
        if (licensesInterface == null) {
            licensesInterface = new LicensesInterface(apiKey, transport);
        }
        return licensesInterface;
    }

    public NotesInterface getNotesInterface() {
        if (notesInterface == null) {
            notesInterface = new NotesInterface(apiKey, transport);
        }
        return notesInterface;
    }

    public PoolsInterface getPoolsInterface() {
        if (poolsInterface == null) {
            poolsInterface = new PoolsInterface(apiKey, transport);
        }
        return poolsInterface;
    }

    public PeopleInterface getPeopleInterface() {
        if (peopleInterface == null) {
            peopleInterface = new PeopleInterface(apiKey, transport);
        }
        return peopleInterface;
    }

    public PhotosInterface getPhotosInterface() {
        if (photosInterface == null) {
            photosInterface = new PhotosInterface(apiKey, transport);
        }
        return photosInterface;
    }

    public PhotosetsInterface getPhotosetsInterface() {
        if (photosetsInterface == null) {
            photosetsInterface = new PhotosetsInterface(apiKey, transport);
        }
        return photosetsInterface;
    }

    public PrefsInterface getPrefsInterface() {
        if (prefsInterface == null) {
            prefsInterface = new PrefsInterface(apiKey, transport);
        }
        return prefsInterface;
    }

    public ReflectionInterface getReflectionInterface() {
        if (reflectionInterface == null) {
            reflectionInterface = new ReflectionInterface(apiKey, transport);
        }
        return reflectionInterface;
    }

    /**
     * Get the TagsInterface for working with Flickr Tags.
     *
     * @return The TagsInterface
     */
    public TagsInterface getTagsInterface() {
        if (tagsInterface == null) {
            tagsInterface = new TagsInterface(apiKey, transport);
        }
        return tagsInterface;
    }

    public TestInterface getTestInterface() {
        if (testInterface == null) {
            testInterface = new TestInterface(apiKey, transport);
        }
        return testInterface;
    }

    public TransformInterface getTransformInterface() {
        if (transformInterface == null) {
            transformInterface = new TransformInterface(apiKey, transport);
        }
        return transformInterface;
    }

    public UrlsInterface getUrlsInterface() {
        if (urlsInterface == null) {
            urlsInterface = new UrlsInterface(apiKey, transport);
        }
        return urlsInterface;
    }

    /**
     * @return the interface to the flickr.interestingness methods
     */
    public synchronized InterestingnessInterface getInterestingnessInterface() {
        if (interestingnessInterface == null) {
            interestingnessInterface = new InterestingnessInterface(apiKey, transport);
        }
        return interestingnessInterface;
    }

}
