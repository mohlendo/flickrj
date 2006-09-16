/*
 *-------------------------------------------------------
 * (c) 2006 Das Büro am Draht GmbH - All Rights reserved
 *-------------------------------------------------------
 */
package com.aetrion.flickr.interestingness;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoFactory;
import com.aetrion.flickr.photos.PhotoList;

public class InterestingnessInterface {
  
  public static final String METHOD_GET_LIST = "flickr.interestingness.getList";


  public static final String EXTRAS_LICENSE = "license";
  public static final String EXTRAS_DATE_UPLOAD = "date_upload";
  public static final String EXTRAS_DATE_TAKEN = "date_taken";
  public static final String EXTRAS_OWNER_NAME = "owner_name";
  public static final String EXTRAS_ICON_SERVER = "icon_server";
  public static final String EXTRAS_ORIGINAL_FORMAT = "original_format";
  public static final String EXTRAS_LAST_UPDATE = "last_update";
  public static final String EXTRAS_GEO = "geo";
  
  private static final String KEY_METHOD = "method";
  private static final String KEY_API_KEY = "api_key";
  private static final String KEY_DATE = "date";
  private static final String KEY_EXTRAS = "extras";
  private static final String KEY_PER_PAGE = "per_page";
  private static final String KEY_PAGE = "page";
  
  public static final Set ALL_EXTRAS = new HashSet();
  
  static {
    ALL_EXTRAS.add(EXTRAS_DATE_TAKEN);
    ALL_EXTRAS.add(EXTRAS_DATE_UPLOAD);
    ALL_EXTRAS.add(EXTRAS_ICON_SERVER);
    ALL_EXTRAS.add(EXTRAS_LAST_UPDATE);
    ALL_EXTRAS.add(EXTRAS_LICENSE);
    ALL_EXTRAS.add(EXTRAS_ORIGINAL_FORMAT);
    ALL_EXTRAS.add(EXTRAS_OWNER_NAME);    
    ALL_EXTRAS.add(EXTRAS_GEO);    
  }
  
  private String apiKey;
  private Transport transportAPI;
  
  
  public InterestingnessInterface(String apiKey, Transport transportAPI) {
    this.apiKey = apiKey;
    this.transportAPI = transportAPI;
  }

  public PhotoList getList(String date, Set extras, int perPage, int page) throws FlickrException, IOException, SAXException {
    List parameters = new ArrayList();
    PhotoList photos = new PhotoList();
    
    parameters.add(new Parameter(KEY_METHOD, METHOD_GET_LIST));
    parameters.add(new Parameter(KEY_API_KEY, apiKey));
    
    if (date != null) {
      parameters.add(new Parameter(KEY_DATE, date));
    }
    
    if (extras != null) {
      StringBuffer sb = new StringBuffer();
      Iterator it = extras.iterator();
      for (int i = 0; it.hasNext(); i++) {
        if (i > 0) {
          sb.append(",");
        }
        sb.append(it.next());
      }
      parameters.add(new Parameter(KEY_EXTRAS, sb.toString()));
    }
    
    if (perPage > 0) {
      parameters.add(new Parameter(KEY_PER_PAGE, String.valueOf(perPage)));      
    }
    if (page > 0) {
      parameters.add(new Parameter(KEY_PAGE, String.valueOf(page)));      
    }
    
    Response response = transportAPI.get(transportAPI.getPath(), parameters);
    if (response.isError()) {
        throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
    }
    Element photosElement = response.getPayload();
    photos.setPage(photosElement.getAttribute("page"));
	photos.setPages(photosElement.getAttribute("pages"));
	photos.setPerPage(photosElement.getAttribute("perpage"));
	photos.setTotal(photosElement.getAttribute("total"));

    NodeList photoNodes = photosElement.getElementsByTagName("photo");
    for (int i = 0; i < photoNodes.getLength(); i++) {
    	Element photoElement = (Element)photoNodes.item(i);
    	Photo photo = PhotoFactory.createPhoto(photoElement);
        photos.add(photo);
        
 
    }
    return photos;
  }

  public PhotoList getList(Date date, Set extras, int perPage, int page) throws FlickrException, IOException, SAXException {
    String dateString = null;
    if (date != null) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      dateString = df.format(date);
    }
    return getList(dateString, extras, perPage, page);
  }

  /**
   * convenience method to get the list of all 500 most recent photos
   * in flickr explore with all known extra attributes.
   * @return a List of Photos
   * @throws FlickrException
   * @throws IOException
   * @throws SAXException
   */
  public PhotoList getList() throws FlickrException, IOException, SAXException {
    return getList((String)null, ALL_EXTRAS, 500, 1);
  }

}

