/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating authentication data.
 *
 * @author Anthony Eden
 * @deprecated Use the authentication API instead
 */
public class Authentication {

    private String email;
    private String password;

    public Authentication() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the authentication data as a collection of Parameter objects.
     *
     * @return The Parameter collection
     */
    public Collection getAsParameters() {
        List parameters = new ArrayList();
        parameters.add(new Parameter("email", getEmail()));
        parameters.add(new Parameter("password", getPassword()));
        return parameters;
    }

}
