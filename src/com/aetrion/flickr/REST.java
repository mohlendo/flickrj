package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import com.aetrion.flickr.util.IOUtilities;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Anthony Eden
 */
public class REST {

    private String host;
    private int port = 80;

    private DocumentBuilder builder;

    public REST() throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builder = builderFactory.newDocumentBuilder();
    }

    public REST(String host) throws ParserConfigurationException {
        this();
        setHost(host);
    }

    public REST(String host, int port) throws ParserConfigurationException {
        this();
        setHost(host);
        setPort(port);
    }

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

    /**
     * Invoke an HTTP GET request on a remote host.  You must close the InputStream after you are done with.
     *
     * @param path The request path
     * @param parameters The parameters (collection of Parameter objects)
     * @return The RESTResponse
     * @throws IOException
     * @throws SAXException
     */
    public RESTResponse get(String path, Collection parameters) throws IOException, SAXException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://");
        buffer.append(host);
        if (port > 0) {
            buffer.append(":");
            buffer.append(port);
        }
        if (path == null) {
            path = "/";
        }
        buffer.append(path);

        Iterator iter = parameters.iterator();
        if (iter.hasNext()) {
            buffer.append("?");
        }
        while(iter.hasNext()) {
            Parameter p = (Parameter) iter.next();
            buffer.append(p.getName());
            buffer.append("=");
            buffer.append(p.getValue());
            if (iter.hasNext()) buffer.append("&");
        }

        System.out.println("Executing GET: " + buffer);

        URL url = new URL(buffer.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream in = null;
        try {
            in = conn.getInputStream();
            Document document = builder.parse(in);
            return RESTResponse.parse(document);
        } finally {
            IOUtilities.close(in);
        }
    }

    /**
     * Invoke an HTTP GET request on a remote host.  You must close the InputStream after you are done with.
     *
     * @param path The request path
     * @param parameters The parameters (collection of Parameter objects)
     * @return The RESTResponse object
     * @throws IOException
     * @throws SAXException
     */
    public RESTResponse post(String path, Collection parameters) throws IOException, SAXException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://");
        buffer.append(host);
        if (port > 0) {
            buffer.append(":");
            buffer.append(port);
        }
        if (path == null) {
            path = "/";
        }
        buffer.append(path);
        URL url = new URL(buffer.toString());

        buffer = new StringBuffer();
        Iterator iter = parameters.iterator();
        while(iter.hasNext()) {
            Parameter p = (Parameter) iter.next();
            buffer.append(p.getName());
            buffer.append("=");
            buffer.append(p.getValue());
            if (iter.hasNext()) buffer.append("&");
        }

        System.out.println("Executing POST: " + buffer);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        PrintWriter out = null;
        try {
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            out.print(buffer.toString());
        } finally {
            IOUtilities.close(out);
        }
        conn.connect();

        InputStream in = null;
        try {
            in = conn.getInputStream();
            Document document = builder.parse(in);
            return RESTResponse.parse(document);
        } finally {
            IOUtilities.close(in);
        }
    }

}
