package com.aetrion.flickr;

import java.util.Collection;
import java.util.Iterator;

import com.aetrion.flickr.util.XMLUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Flickr Response object.
 *
 * @author Anthony Eden
 */
public class RESTResponse {

    private String stat;
    private Collection payload;

    private String errorCode;
    private String errorMessage;

    protected RESTResponse() {

    }

    public static RESTResponse parse(Document document) {
        RESTResponse response = new RESTResponse();

        Element rspElement = document.getDocumentElement();
        rspElement.normalize();
        response.stat = rspElement.getAttribute("stat");
        if ("ok".equals(response.stat)) {
            // TODO: Verify that the payload is always a single XML node
            response.payload = XMLUtilities.getChildElements(rspElement);
        } else if ("fail".equals(response.stat)) {
            Element errElement = (Element) rspElement.getElementsByTagName("err").item(0);
            response.errorCode = errElement.getAttribute("code");
            response.errorMessage = errElement.getAttribute("msg");
        }

        return response;
    }

    public String getStat() {
        return stat;
    }

    public Element getPayload() {
        Iterator iter = payload.iterator();
        if (iter.hasNext()) {
            return (Element) iter.next();
        } else {
            throw new RuntimeException("REST response payload has no elements");
        }
    }

    public Collection getPayloadCollection() {
        return payload;
    }

    public boolean isError() {
        return errorCode != null;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }



}
