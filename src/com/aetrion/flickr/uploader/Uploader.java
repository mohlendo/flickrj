/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.uploader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.util.StringUtilities;

/**
 * @author Anthony Eden
 */
public class Uploader {

    private String apiKey;
    private Transport transport;

    /**
     * Construct an Uploader.
     *
     * @param apiKey The API key
     */
    public Uploader(String apiKey) {
        try {
            this.apiKey = apiKey;
            this.transport = new REST(Flickr.DEFAULT_HOST);
            this.transport.setResponseClass(UploaderResponse.class);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Construct an uploader using the specified Transport interface.
     *
     * @param apiKey The API key
     * @param transport The Transport interface
     */
    public Uploader(String apiKey, Transport transport) {
        this.apiKey = apiKey;
        this.transport = transport;
        this.transport.setResponseClass(UploaderResponse.class);
    }

    /**
     * Upload a photo.
     *
     * @param data The photo data as a byte array
     * @param metaData The meta data
     * @throws FlickrException
     * @throws IOException
     * @throws SAXException
     */
    public String upload(byte[] data, UploadMetaData metaData) throws FlickrException, IOException, SAXException {
        List parameters = new ArrayList();

        parameters.add(new Parameter("api_key", apiKey));

        String title = metaData.getTitle();
        if (title != null)
            parameters.add(new Parameter("title", title));

        String description = metaData.getDescription();
        if (description != null)
            parameters.add(new Parameter("description", description));

        Collection tags = metaData.getTags();
        if (tags != null)
            parameters.add(new Parameter("tags", StringUtilities.join(tags, " ")));

        parameters.add(new Parameter("is_public", metaData.isPublicFlag() ? "1" : "0"));
        parameters.add(new Parameter("is_family", metaData.isFamilyFlag() ? "1" : "0"));
        parameters.add(new Parameter("is_friend", metaData.isFriendFlag() ? "1" : "0"));

        parameters.add(new Parameter("photo", data));

        UploaderResponse response = (UploaderResponse) transport.post("/services/upload/", parameters, true);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } 
        return response.getPhotoId();
    }

    public String upload(InputStream in, UploadMetaData metaData) throws IOException, FlickrException, SAXException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b = -1;
        while ((b = in.read()) != -1) {
            out.write((byte) b);
        }
        return upload(out.toByteArray(), metaData);
    }

}
