/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr;

/**
 * Exception which wraps a Flickr error.
 *
 * @author Anthony Eden
 */
public class FlickrException extends Exception {

    private String errorCode;
    private String errorMessage;

    public FlickrException(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
