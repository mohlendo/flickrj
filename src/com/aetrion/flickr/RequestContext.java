/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr;

import java.util.List;
import java.util.ArrayList;

/**
 * A thread local variable used to hold contextual information used in requests.  To get an instance of this class use
 * RequestContext.getRequestContext().  The method will return a RequestContext object which is only usable within the
 * current thread.
 *
 * @author Anthony Eden
 */
public class RequestContext {

    private static RequestContextThreadLocal threadLocal =
            new RequestContextThreadLocal();

    private Authentication authentication;
    private String sharedSecret;
    private List extras;

    /**
     * Get the RequestContext instance for the current Thread.
     *
     * @return The RequestContext
     */
    public static RequestContext getRequestContext() {
        return (RequestContext) threadLocal.get();
    }

    /**
     * Get the authentication object.
     *
     * @return The Authentication object
     * @deprecated Use new authentication API
     */
    public Authentication getAuthentication() {
        return authentication;
    }

    /**
     * Set the authentication object.
     *
     * @param authentication The Authentication object
     * @deprecated Use new authentication API
     */
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    /**
     * Get a shared secret which is used for any calls which require signing.
     *
     * @return The shared secret
     */
    public String getSharedSecret() {
        return sharedSecret;
    }

    /**
     * Set the shared secret which is used for any calls which require signing.
     *
     * @param sharedSecret The shared secret
     */
    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    /**
     * Get the List of extra return values requested.
     *
     * @return
     */
    public List getExtras() {
        if (extras == null) extras = new ArrayList();
        return extras;
    }

    public void setExtras(List extras) {
        this.extras = extras;
    }

    private static class RequestContextThreadLocal extends ThreadLocal {

        protected Object initialValue() {
            return new RequestContext();
        }
        
    }

}
