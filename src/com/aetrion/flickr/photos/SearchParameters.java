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
 */
public class SearchParameters {

    private String userId;
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
	private boolean interestingnessLicense = false;
	private boolean interestingnessDateUpload = false;
	private boolean interestingnessDateTaken = false;
	private boolean interestingnessOwnerName = false;
	private boolean interestingnessIconServer = false;
	private boolean interestingnessOriginalFormat = false;

    public SearchParameters() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
	 * Setting all toogles to get extra-fields in interestingness-search.<br>
	 * Default is false.
	 * 
	 * @param toggle to include or exclude all extra fields.
	 */
	public void setInterestingnessExtras(boolean toggle) {
		setInterestingnessLicense(toggle);
		setInterestingnessDateUpload(toggle);
		setInterestingnessDateTaken(toggle);
		setInterestingnessOwnerName(toggle);
		setInterestingnessIconServer(toggle);
		setInterestingnessOriginalFormat(toggle);
	}
	
	public void setInterestingnessLicense(boolean toggle) {
		this.interestingnessLicense = toggle;
	}	
	public void setInterestingnessDateUpload(boolean toggle) {
		this.interestingnessDateUpload = toggle;
	}
	public void setInterestingnessDateTaken(boolean toggle) {
		this.interestingnessDateTaken = toggle;
	}
	public void setInterestingnessOwnerName(boolean toggle) {
		this.interestingnessOwnerName = toggle;
	}
	public void setInterestingnessIconServer(boolean toggle) {
		this.interestingnessIconServer = toggle;
	}
	public void setInterestingnessOriginalFormat(boolean toggle) {
		this.interestingnessOriginalFormat = toggle;
	}
	
    public Collection getAsParameters() {
        List parameters = new ArrayList();

        String userId = getUserId();
        if (userId != null) {
            parameters.add(new Parameter("user_id", userId));
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
            parameters.add(new Parameter("min_upload_date", new Long(minUploadDate.getTime())));
        }

        Date maxUploadDate = getMaxUploadDate();
        if (maxUploadDate != null) {
            parameters.add(new Parameter("max_upload_date", new Long(maxUploadDate.getTime())));
        }

        Date minTakenDate = getMinUploadDate();
        if (minTakenDate != null) {
            parameters.add(new Parameter("min_taken_date", new Long(minTakenDate.getTime())));
        }

        Date maxTakenDate = getMinUploadDate();
        if (maxTakenDate != null) {
            parameters.add(new Parameter("max_taken_date", new Long(maxTakenDate.getTime())));
        }

        String license = getLicense();
        if (license != null) {
            parameters.add(new Parameter("license", license));
        }
        
        Date intrestingnessDate = getInterestingnessDate();
        if (intrestingnessDate != null) {
            parameters.add(new Parameter("date", DF.format(intrestingnessDate)));
        }
    	if(interestingnessLicense || interestingnessDateUpload ||
    	   interestingnessDateTaken || interestingnessOwnerName ||
    	   interestingnessIconServer || interestingnessOriginalFormat) {
    		Vector argsList = new Vector();
    		if(interestingnessLicense) argsList.add("license");
    		if(interestingnessDateUpload) argsList.add("date_upload");
    		if(interestingnessDateTaken) argsList.add("date_taken");
    		if(interestingnessOwnerName) argsList.add("owner_name");
    		if(interestingnessIconServer) argsList.add("icon_server");
    		if(interestingnessOriginalFormat) argsList.add("original_format");
            parameters.add(new Parameter("extras", StringUtilities.join(argsList,",")));
    	}
        return parameters;
    }
}
