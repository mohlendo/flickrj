package com.aetrion.flickr.photos;

import org.w3c.dom.Element;

import com.aetrion.flickr.people.User;

public class PhotoFactory {

	private PhotoFactory() {
	}
	
	public static final Photo createPhoto(Element photoElement) {
        Photo photo = new Photo();
        photo.setId(photoElement.getAttribute("id"));
        photo.setSecret(photoElement.getAttribute("secret"));
        photo.setServer(photoElement.getAttribute("server"));
        // username ?

        User owner = new User();
        owner.setId(photoElement.getAttribute("owner"));
        String username = photoElement.getAttribute("username");
        String ownername = photoElement.getAttribute("ownername");
        // try to get the username either from the "username" attribute or
        // from the "ownername" attribute 
        if (username != null && !"".equals(username)) {
            owner.setUsername(username);
        } else if (ownername != null && !"".equals(ownername)) {
        	owner.setUsername(ownername);
        }
        photo.setOwner(owner);

        photo.setTitle(photoElement.getAttribute("title"));
        
        photo.setPublicFlag("1".equals(photoElement.getAttribute("ispublic")));
        photo.setFriendFlag("1".equals(photoElement.getAttribute("isfriend")));
        photo.setFamilyFlag("1".equals(photoElement.getAttribute("isfamily")));
        photo.setDateTaken(photoElement.getAttribute("datetaken"));
        photo.setTakenGranularity(photoElement.getAttribute("datetakengranularity"));
        photo.setLastUpdate(photoElement.getAttribute("lastupdate"));
        photo.setDatePosted(photoElement.getAttribute("dateupload"));
        photo.setIconServer(photoElement.getAttribute("iconserver"));
        photo.setLicense(photoElement.getAttribute("license"));
        photo.setOriginalFormat(photoElement.getAttribute("originalformat"));
        String longitude = photoElement.getAttribute("longitude");
        String latitude = photoElement.getAttribute("latitude");
        String accuracy = photoElement.getAttribute("accuracy");
     
        if (longitude != null && latitude != null 
        		&& longitude.length() > 0 && latitude.length() > 0
        		&& !("0".equals(longitude) && "0".equals(latitude))) {
        	photo.setGeoData(new GeoData(longitude, latitude, accuracy));
        }
        photo.setUrl("http://flickr.com/photos/" + owner.getId() + "/" + photo.getId());
        return photo;

	}

}
