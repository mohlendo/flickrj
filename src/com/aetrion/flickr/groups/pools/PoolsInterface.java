package com.aetrion.flickr.groups.pools;

import com.aetrion.flickr.REST;
import com.aetrion.flickr.Authentication;

/**
 * @author Anthony Eden
 */
public class PoolsInterface {

    public static final String METHOD_FIND_BY_EMAIL = "flickr.people.findByEmail";
    public static final String METHOD_FIND_BY_USERNAME = "flickr.people.findByUsername";
    public static final String METHOD_GET_INFO = "flickr.people.getInfo";
    public static final String METHOD_GET_ONLINE_LIST = "flickr.people.getOnlineList";
    public static final String METHOD_GET_PUBLIC_PHOTOS = "flickr.people.getPublicPhotos";

    private String apiKey;
    private REST restInterface;

    public PoolsInterface(String apiKey, REST restInterface) {
        this.apiKey = apiKey;
        this.restInterface = restInterface;
    }

    public void add(Authentication auth, String photoId, String groupId) {
        
    }

}
