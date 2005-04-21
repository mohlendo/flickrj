/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */

package com.aetrion.flickr.reflection;

/**
 * @author Anthony Eden
 */
public class Error {

    private int code;
    private String message;
    private String explaination;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCode(String code) {
        setCode(Integer.parseInt(code));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

}
