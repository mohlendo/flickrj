/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.util.StringUtilities;

/**
 * @author Anthony Eden
 * @version $Id: SearchParameters.java,v 1.10 2007/09/10 13:50:51 x-mago Exp $
 */
public class SearchParameters {

    private String userId;
    private String groupId;
    private String[] tags;
    private String tagMode;
    private String text;
    private Date minUploadDate;
    private Date maxUploadDate;
    private Date minTakenDate;
    private Date maxTakenDate;
    private Date interestingnessDate;
    private String license;
    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat MYSQLDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean extrasLicense = false;
    private boolean extrasDateUpload = false;
    private boolean extrasDateTaken = false;
    private boolean extrasOwnerName = false;
    private boolean extrasIconServer = false;
    // default true, to ensure it's possible to generate a valid original-URL for requested Photos.
    private boolean extrasOriginalFormat = true;
    private boolean extrasLastUpdate = false;
    private boolean extrasGeo = false;
    private boolean extrasTags = false;
    private boolean extrasMachineTags = false;
    private String[] bbox;
    private int accuracy = 0;
    private int saveSearch = 0;

	/** order argument */
	public static int DATE_POSTED_DESC = 0;
	/** order argument */
	public static int DATE_POSTED_ASC = 1;
	/** order argument */
	public static int DATE_TAKEN_DESC = 2;
	/** order argument */
	public static int DATE_TAKEN_ASC = 3;
	/** order argument */
	public static int INTERESTINGNESS_DESC = 4;
	/** order argument */
	public static int INTERESTINGNESS_ASC = 5;
	/** order argument */
	public static int RELEVANCE = 6;
		
	private int sort = 0;

    public SearchParameters() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    /**
     * The id of a group who's pool to search. If specified, only matching photos posted to the group's pool will be returned.
     *
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTagMode() {
        return tagMode;
    }

    public void setTagMode(String tagMode) {
        this.tagMode = tagMode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getMinUploadDate() {
        return minUploadDate;
    }

    public void setMinUploadDate(Date minUploadDate) {
        this.minUploadDate = minUploadDate;
    }

    public Date getMaxUploadDate() {
        return maxUploadDate;
    }

    public void setMaxUploadDate(Date maxUploadDate) {
        this.maxUploadDate = maxUploadDate;
    }

    public Date getMinTakenDate() {
        return minTakenDate;
    }

    public void setMinTakenDate(Date minTakenDate) {
        this.minTakenDate = minTakenDate;
    }

    public Date getMaxTakenDate() {
        return maxTakenDate;
    }

    public void setMaxTakenDate(Date maxTakenDate) {
        this.maxTakenDate = maxTakenDate;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Date getInterestingnessDate() {
        return interestingnessDate;
    }

    /**
     * Set the date, for which interesting Photos to request.
     * 
     * @param intrestingnessDate
     */
    public void setInterestingnessDate(Date intrestingnessDate) {
        this.interestingnessDate = intrestingnessDate;
    }

    /**
     * Setting all toogles to get extra-fields in Photos-search.<br>
     * The default is false.
     * 
     * @param toggle to include or exclude all extra fields.
     */
    public void setExtras(boolean toggle) {
        setExtrasLicense(toggle);
        setExtrasDateUpload(toggle);
        setExtrasDateTaken(toggle);
        setExtrasOwnerName(toggle);
        setExtrasIconServer(toggle);
        setExtrasOriginalFormat(toggle);
        setExtrasLastUpdate(toggle);
        setExtrasGeo(toggle);
        setExtrasTags(toggle);
        setExtrasMachineTags(toggle);
    }

    public void setExtrasLicense(boolean toggle) {
        this.extrasLicense = toggle;
    }
    public void setExtrasDateUpload(boolean toggle) {
        this.extrasDateUpload = toggle;
    }
    public void setExtrasDateTaken(boolean toggle) {
        this.extrasDateTaken = toggle;
    }
    public void setExtrasOwnerName(boolean toggle) {
        this.extrasOwnerName = toggle;
    }
    public void setExtrasIconServer(boolean toggle) {
        this.extrasIconServer = toggle;
    }
    public void setExtrasOriginalFormat(boolean toggle) {
        this.extrasOriginalFormat = toggle;
    }
    public void setExtrasGeo(boolean extrasGeo) {
        this.extrasGeo = extrasGeo;
    }
    public void setExtrasLastUpdate(boolean extrasLastUpdate) {
        this.extrasLastUpdate = extrasLastUpdate;
    }
    public void setExtrasMachineTags(boolean extrasMachineTags) {
        this.extrasMachineTags = extrasMachineTags;
    }
    public void setExtrasTags(boolean extrasTags) {
        this.extrasTags = extrasTags;
    }

    /**
     * 4 values defining the Bounding Box of the area that 
     * will be searched.<p>
     * The 4 values represent the bottom-left corner of the box
     * and the top-right corner, minimum_longitude, minimum_latitude,
     * maximum_longitude, maximum_latitude.<p>
     *
     * Longitude has a range of -180 to 180,
     * latitude of -90 to 90. Defaults to -180,
     * -90, 180, 90 if not specified.<p>
     *
     * Unlike standard photo queries, geo (or bounding box)
     * queries will only return 250 results per page.<p>
     *
     * Geo queries require some sort of limiting agent in
     * order to prevent the database from crying.
     * This is basically like the check against "parameterless searches"
     * for queries without a geo component.<p>
     *
     * A tag, for instance, is considered a limiting agent as are
     * user defined min_date_taken and min_date_upload parameters.
     * If no limiting factor is passed flickr returns only photos
     * added in the last 12 hours (though flickr may extend the
     * limit in the future).
     *
     * @param minimum_longitude
     * @param minimum_latitude
     * @param maximum_longitude
     * @param maximum_latitude
     */
    public void setBBox(
        String minimum_longitude,
        String minimum_latitude,
        String maximum_longitude,
        String maximum_latitude
    ) {
        this.bbox = new String[] {
            minimum_longitude,
            minimum_latitude,
            maximum_longitude,
            maximum_latitude
        };
    }

    public String[] getBBox() {
        return bbox;
    }

    /**
     * Optional to use, if BBox is set.<p>
     * Defaults to maximum value if not specified.
     *
     * @param accuracy from 1 to 16
     * @see com.aetrion.flickr.Flickr#ACCURACY_WORLD
     * @see com.aetrion.flickr.Flickr#ACCURACY_COUNTRY
     * @see com.aetrion.flickr.Flickr#ACCURACY_REGION
     * @see com.aetrion.flickr.Flickr#ACCURACY_CITY
     * @see com.aetrion.flickr.Flickr#ACCURACY_STREET
     */
    public void setAccuracy(int accuracy) {
    	this.accuracy = accuracy;
    }

    public int getAccuracy() {
    	return accuracy;
    }

    /**
     * Optional safe search setting.<br>
     * Un-authed calls can only see Safe content.
     *
     * @param level 1, 2 or 3
     * @see com.aetrion.flickr.Flickr#SAFETYLEVEL_SAFE
     * @see com.aetrion.flickr.Flickr#SAFETYLEVEL_MODERATE
     * @see com.aetrion.flickr.Flickr#SAFETYLEVEL_RESTRICTED
     */
    public void setSaveSearch(int level) {
    	this.saveSearch = level;
    }
    
    public int getSaveSearch() {
    	return saveSearch;
    }

    public int getSort() {
        return sort;
    }

    /**
     * Set the sort-order.<p>
     * The default is <a href="#DATE_POSTED_DESC">DATE_POSTED_DESC</a>
     *
     * @see com.aetrion.flickr.photos.SearchParameters#DATE_POSTED_ASC
     * @see com.aetrion.flickr.photos.SearchParameters#DATE_POSTED_DESC
     * @see com.aetrion.flickr.photos.SearchParameters#DATE_TAKEN_ASC
     * @see com.aetrion.flickr.photos.SearchParameters#DATE_TAKEN_DESC
     * @see com.aetrion.flickr.photos.SearchParameters#INTERESTINGNESS_ASC
     * @see com.aetrion.flickr.photos.SearchParameters#INTERESTINGNESS_DESC
     * @see com.aetrion.flickr.photos.SearchParameters#RELEVANCE
     * @param order
     */
    public void setSort(int order) {
        this.sort = order;
    }

    public Collection getAsParameters() {
        List parameters = new ArrayList();

        String userId = getUserId();
        if (userId != null) {
            parameters.add(new Parameter("user_id", userId));
        }

        String groupId = getGroupId();
        if (groupId != null) {
            parameters.add(new Parameter("group_id", groupId));
        }

        String[] tags = getTags();
        if (tags != null) {
            parameters.add(new Parameter("tags", StringUtilities.join(tags, ",")));
        }

        String tagMode = getTagMode();
        if (tagMode != null) {
            parameters.add(new Parameter("tag_mode", tagMode));
        }

        String text = getText();
        if (text != null) {
            parameters.add(new Parameter("text", text));
        }

        Date minUploadDate = getMinUploadDate();
        if (minUploadDate != null) {
            parameters.add(new Parameter("min_upload_date", new Long(minUploadDate.getTime() / 1000L)));
        }

        Date maxUploadDate = getMaxUploadDate();
        if (maxUploadDate != null) {
            parameters.add(new Parameter("max_upload_date", new Long(maxUploadDate.getTime() / 1000L)));
        }

        Date minTakenDate = getMinTakenDate();
        if (minTakenDate != null) {
            parameters.add(new Parameter("min_taken_date", MYSQLDF.format(minTakenDate)));
        }

        Date maxTakenDate = getMaxTakenDate();
        if (maxTakenDate != null) {
            parameters.add(new Parameter("max_taken_date", MYSQLDF.format(maxTakenDate)));
        }

        String license = getLicense();
        if (license != null) {
            parameters.add(new Parameter("license", license));
        }

        Date intrestingnessDate = getInterestingnessDate();
        if (intrestingnessDate != null) {
            parameters.add(new Parameter("date", DF.format(intrestingnessDate)));
        }

        String[] bbox = getBBox();
        if (bbox != null) {
            parameters.add(new Parameter("bbox", StringUtilities.join(bbox, ",")));
            if (accuracy > 0) {
                parameters.add(new Parameter("accuracy", accuracy));
            }
        }
        
        int saveSearch = getSaveSearch();
        if (saveSearch > 0) {
            parameters.add(new Parameter("save_search", saveSearch));
        }

        if (extrasLicense || extrasDateUpload ||
           extrasDateTaken || extrasOwnerName ||
           extrasIconServer || extrasOriginalFormat ||
           extrasLastUpdate || extrasGeo ||
           extrasTags || extrasMachineTags) {
            Vector argsList = new Vector();
            if (extrasLicense) argsList.add("license");
            if (extrasDateUpload) argsList.add("date_upload");
            if (extrasDateTaken) argsList.add("date_taken");
            if (extrasOwnerName) argsList.add("owner_name");
            if (extrasIconServer) argsList.add("icon_server");
            if (extrasOriginalFormat) argsList.add("original_format");
            if (extrasLastUpdate) argsList.add("last_update");
            if (extrasGeo) argsList.add("geo");
            if (extrasTags) argsList.add("tags");
            if (extrasMachineTags) argsList.add("machine_tags");
            parameters.add(new Parameter("extras", StringUtilities.join(argsList,",")));
        }

        if (sort != DATE_POSTED_DESC) {
            String sortArg = null;
            if(sort == DATE_POSTED_ASC) sortArg = "date-posted-asc";
            if(sort == DATE_TAKEN_DESC) sortArg = "date-taken-desc";
            if(sort == DATE_TAKEN_ASC) sortArg = "date-taken-asc";
            if(sort == INTERESTINGNESS_DESC) sortArg = "interestingness-desc";
            if(sort == INTERESTINGNESS_ASC) sortArg = "interestingness-asc";
            if(sort == RELEVANCE) sortArg = "relevance";
            if(sortArg != null) parameters.add(new Parameter("sort", sortArg));
        }

        return parameters;
    }

}
