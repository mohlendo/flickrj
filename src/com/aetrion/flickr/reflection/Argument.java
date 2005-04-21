/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */

package com.aetrion.flickr.reflection;

/**
 * @author Anthony Eden
 */
public class Argument {

    private String name;
    private boolean optional;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
