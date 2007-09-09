/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.reflection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Anthony Eden
 * @version $Id: Method.java,v 1.4 2007/09/09 17:20:04 x-mago Exp $
 */
public class Method {

    public static final int READ_PERMISSION = 1;
    public static final int WRITE_PERMISSION = 2;
    
	private String name;
    private boolean needsLogin;
    private boolean needsSigning;
    private int requiredPerms;
    private String description;
    private String response;
    private String explanation;
    private Collection arguments;
    private Collection errors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean needsLogin() {
        return needsLogin;
    }

    public void setNeedsLogin(boolean needsLogin) {
        this.needsLogin = needsLogin;
    }

    public boolean needsSigning() {
		return needsSigning;
	}

	public void setNeedsSigning(boolean needsSigning) {
		this.needsSigning = needsSigning;
	}

	public int getRequiredPerms() {
		return requiredPerms;
	}

	public void setRequiredPerms(int reqiredPerms) {
		this.requiredPerms = reqiredPerms;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @deprecated use getExplanation() instead.
     * @return the methods explanation
     */
    public String getExplaination() {
        return explanation;
    }

    /**
     * @deprecated use setExplanation() instead.
     */
    public void setExplaination(String explaination) {
        this.explanation = explaination;
    }

    public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public Collection getArguments() {
        if (arguments == null) {
            arguments = new ArrayList();
        }
        return arguments;
    }

    public void setArguments(Collection arguments) {
        this.arguments = arguments;
    }

    public Collection getErrors() {
        if (errors == null) {
            errors = new ArrayList();
        }
        return errors;
    }

    public void setErrors(Collection errors) {
        this.errors = errors;
    }

}
