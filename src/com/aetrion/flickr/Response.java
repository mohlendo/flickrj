/* Copyright 2004, Aetrion LLC.  All Rights Reserved. */

package com.aetrion.flickr;

import org.w3c.dom.Document;

/**
 * @author Anthony Eden
 */
public interface Response {

    void parse(Document document);

    boolean isError();

    String getErrorCode();

    String getErrorMessage();
}
