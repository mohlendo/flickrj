package com.aetrion.flickr;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    private Class responseClass = RESTResponse.class;

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

    public Class getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
    }

    /**
     * Invoke an HTTP GET request on a remote host.  You must close the InputStream after you are done with.
     *
     * @param path The request path
     * @param parameters The parameters (collection of Parameter objects)
     * @return The Response
     * @throws IOException
     * @throws SAXException
     */
    public Response get(String path, Collection parameters) throws IOException, SAXException {
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
        while (iter.hasNext()) {
            Parameter p = (Parameter) iter.next();
            buffer.append(p.getName());
            buffer.append("=");
            buffer.append(p.getValue());
            if (iter.hasNext()) buffer.append("&");
        }

//        System.out.println("Executing GET: " + buffer);

        URL url = new URL(buffer.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream in = null;
        try {
            in = conn.getInputStream();
            Document document = builder.parse(in);
            Response response = (Response) responseClass.newInstance();
            response.parse(document);
            return response;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); // TODO: Replace with a better exception
        } catch (InstantiationException e) {
            throw new RuntimeException(e); // TODO: Replace with a better exception
        } finally {
            IOUtilities.close(in);
        }
    }

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
    public Response post(String path, Collection parameters, boolean multipart) throws IOException, SAXException {
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

        HttpURLConnection conn = null;
        try {
            String boundary = "---------------------------7d273f7a0d3";

            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            if (multipart) {
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            }
            conn.connect();

            DataOutputStream out = null;
            try {
                out = new DataOutputStream(conn.getOutputStream());

                // construct the body
                if (multipart) {
                    out.writeBytes("--" + boundary + "\r\n");
                    Iterator iter = parameters.iterator();
                    while (iter.hasNext()) {
                        Parameter p = (Parameter) iter.next();
                        writeParam(p.getName(), p.getValue(), out, boundary);
                    }
                } else {
                    Iterator iter = parameters.iterator();
                    while (iter.hasNext()) {
                        Parameter p = (Parameter) iter.next();
                        out.writeBytes(p.getName());
                        out.writeBytes("=");
                        out.writeBytes(String.valueOf(p.getValue()));
                        if (iter.hasNext()) out.writeBytes("&");
                    }
                }
                out.flush();
            } finally {
                IOUtilities.close(out);
            }

            InputStream in = null;
            try {
                in = conn.getInputStream();
                Document document = builder.parse(in);
                Response response = (Response) responseClass.newInstance();
                response.parse(document);
                return response;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e); // TODO: Replace with a better exception
            } catch (InstantiationException e) {
                throw new RuntimeException(e); // TODO: Replace with a better exception
            } finally {
                IOUtilities.close(in);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void writeParam(String name, Object value, DataOutputStream out, String boundary)
            throws IOException {
        if (value instanceof byte[]) {
            out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"image.jpg\";\r\n");
            out.writeBytes("Content-Type: image/jpeg" + "\r\n\r\n");
            out.write((byte[]) value);
            out.writeBytes("\r\n" + "--" + boundary + "\r\n");
        } else {
            out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
            out.writeBytes(String.valueOf(value));
            out.writeBytes("\r\n" + "--" + boundary + "\r\n");
        }
    }

}
