/**
 * 
 */
package com.aetrion.flickr.util;

import java.io.IOException;

import com.aetrion.flickr.auth.Auth;

/**
 * Defines an interface for possibly persistent storage of token information.
 * 
 * @author Matthew MacKenzie
 *
 */
public interface AuthStore {
	/**
	 * Store an Auth.
	 * @param token Auth object to be stored.
	 * @throws IOException 
	 */
	void store(Auth token) throws IOException;
	
	/**
	 * Retrieve Auth for a given NSID.
	 * @param nsid NSID
	 * @return Auth
	 */
	Auth retrieve(String nsid);
	
	/**
	 * Retrieve all Auth objects being stored.
	 * @return
	 */
	Auth[] retrieveAll();
	
	/**
	 * Clear out the store.
	 *
	 */
	void clearAll();
	
	/**
	 * Clear for a given NSID.
	 * @param nsid
	 */
	void clear(String nsid);
}
