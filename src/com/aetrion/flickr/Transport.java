package com.aetrion.flickr;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.xml.sax.SAXException;

/**
 * @author Matt Ray
 */
public abstract class Transport {
    public static final String REST="REST";
    public static final String SOAP="SOAP";
    
    private String transportType;

    private String host;
    private int port = 80;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transport) {
        this.transportType = transport;
    }
    
    public abstract Class getResponseClass();

    public abstract void setResponseClass(Class responseClass);

    /**
     * Invoke an HTTP GET request on a remote host.  You must close the InputStream after you are done with.
     *
     * @param path The request path
     * @param parameters The parameters (collection of Parameter objects)
     * @return The Response
     * @throws IOException
     * @throws SAXException
     */
    public abstract Response get(String path, List parameters) throws IOException, SAXException;

    /**
     * Invoke an HTTP POST request on a remote host.
     *
     * @param path The request path
     * @param parameters The parameters (collection of Parameter objects)
     * @return The Response object
     * @throws IOException
     * @throws SAXException
     */
    public Response post(String path, Collection parameters) throws IOException, SAXException {
        return post(path, parameters, false);
    }

    /**
     * Invoke an HTTP POST request on a remote host.
     *
     * @param path The request path
     * @param parameters The parameters (collection of Parameter objects)
     * @param multipart Use multipart
     * @return The Response object
     * @throws IOException
     * @throws SAXException
     */
    public abstract Response post(String path, Collection parameters, boolean multipart) throws IOException, SAXException;

}
