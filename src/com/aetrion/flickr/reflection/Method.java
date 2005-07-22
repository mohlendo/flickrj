/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.reflection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Anthony Eden
 */
public class Method {

    private String name;
    private boolean needsLogin;
    private String description;
    private String response;
    private String explaination;
    private Collection arguments;
    private Collection errors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedsLogin() {
        return needsLogin;
    }

    public void setNeedsLogin(boolean needsLogin) {
        this.needsLogin = needsLogin;
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

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
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
