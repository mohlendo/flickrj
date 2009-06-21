package com.aetrion.flickr.panda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.auth.AuthUtilities;
import com.aetrion.flickr.groups.Group;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotoUtils;
import com.aetrion.flickr.util.XMLUtilities;

public class PandaInterface {
    public static final String METHOD_GET_PHOTOS = "flickr.panda.getPhotos";
    public static final String METHOD_GET_LIST = "flickr.panda.getList";

    private String apiKey;
    private String sharedSecret;
    private Transport transportAPI;

    public PandaInterface(
        String apiKey,
        String sharedSecret,
        Transport transportAPI
    ) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.transportAPI = transportAPI;
    }

    public ArrayList getList() throws FlickrException, IOException, SAXException {
        ArrayList pandas = new ArrayList();
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(
            new Parameter(
                "api_sig",
                AuthUtilities.getSignature(sharedSecret, parameters)
            )
        );

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        Element pandaElement = response.getPayload();
        NodeList pandaNodes = pandaElement.getElementsByTagName("panda");
        for (int i = 0; i < pandaNodes.getLength(); i++) {
            pandaElement = (Element) pandaNodes.item(i);
            Panda panda = new Panda();
            panda.setName(XMLUtilities.getValue(pandaElement));
            pandas.add(panda);
        }
        return pandas;
    }

    public PhotoList getPhotos(Panda panda, Set extras, int perPage, int page) throws FlickrException, IOException, SAXException {
        ArrayList pandas = new ArrayList();
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_PHOTOS));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("panda_name", panda.getName()));

        if (extras != null && !extras.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Iterator it = extras.iterator();
            while (it.hasNext()) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(it.next());
            }
            parameters.add(new Parameter("extras", sb.toString()));
        }
        if (perPage > 0) {
            parameters.add(new Parameter("per_page", perPage));
        }
        if (page > 0) {
            parameters.add(new Parameter("page", page));
        }

        parameters.add(
            new Parameter(
                "api_sig",
                AuthUtilities.getSignature(sharedSecret, parameters)
            )
        );

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        Element photosElement = response.getPayload();
        PhotoList photos = PhotoUtils.createPhotoList(photosElement);
        return photos;
    }
}
