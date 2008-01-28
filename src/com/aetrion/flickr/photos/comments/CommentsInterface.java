package com.aetrion.flickr.photos.comments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.auth.AuthUtilities;
import com.aetrion.flickr.util.XMLUtilities;

/**
 * Work on Comments.
 *
 * @author till (Till Krech) flickr:extranoise
 * @version $Id: CommentsInterface.java,v 1.2 2008/01/28 23:01:48 x-mago Exp $
 */
public class CommentsInterface {
    public static final String METHOD_ADD_COMMENT    = "flickr.photos.comments.addComment";
    public static final String METHOD_DELETE_COMMENT = "flickr.photos.comments.deleteComment";
    public static final String METHOD_EDIT_COMMENT   = "flickr.photos.comments.editComment";
    public static final String METHOD_GET_LIST       = "flickr.photos.comments.getList";

    private String apiKey;
    private String sharedSecret;
    private Transport transportAPI;

    public CommentsInterface(
        String apiKey,
        String sharedSecret,
        Transport transport
    ) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.transportAPI = transport;
    }

    /**
     * Add comment to a photo as the currently authenticated user.
     * This method requires authentication with 'write' permission.
     * @param photoId The id of the photo to add a comment to.
     * @param commentText Text of the comment.
     * @return a unique comment id.
     * @throws SAXException
     * @throws IOException
     * @throws FlickrException
     */
    public String addComment(String photoId, String commentText) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_ADD_COMMENT));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("comment_text", commentText));
        parameters.add(
            new Parameter(
                "api_sig",
                AuthUtilities.getSignature(sharedSecret, parameters)
            )
        );

        //Note: This method requires an HTTP POST request.
        Response response = transportAPI.post(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        Element commentElement = (Element)response.getPayload();
        return commentElement.getAttribute("id");
    }

    /**
     * Delete a comment as the currently authenticated user.
     * This method requires authentication with 'write' permission.
     * @param commentId The id of the comment to delete.
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public void deleteComment(String commentId) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_DELETE_COMMENT));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("comment_id", commentId));
        parameters.add(
            new Parameter(
                "api_sig",
                AuthUtilities.getSignature(sharedSecret, parameters)
            )
        );

        //Note: This method requires an HTTP POST request.
        Response response = transportAPI.post(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        // This method has no specific response - It returns an empty 
        // sucess response if it completes without error.
    }

    /**
     * Edit the text of a comment as the currently authenticated user.
     * This method requires authentication with 'write' permission.
     * @param commentId The id of the comment to edit.
     * @param commentText Update the comment to this text.
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public void editComment(String commentId, String commentText) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_EDIT_COMMENT));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("comment_id", commentId));
        parameters.add(new Parameter("comment_text", commentText));
        parameters.add(
            new Parameter(
                "api_sig",
                AuthUtilities.getSignature(sharedSecret, parameters)
            )
        );

        //Note: This method requires an HTTP POST request.
        Response response = transportAPI.post(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        // This method has no specific response - It returns an empty 
        // sucess response if it completes without error.
    }

    /**
     * Returns the comments for a photo.
     * @param photoId The id of the photo to fetch comments for.
     * @return a List of {@link Comment} objects.
     * @throws FlickrException
     * @throws IOException
     * @throws SAXException
     */
    public List getList(String photoId)
      throws FlickrException, IOException, SAXException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_LIST));
        parameters.add(new Parameter("api_key", apiKey));
        parameters.add(new Parameter("photo_id", photoId));
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
        List comments = new ArrayList();
        Element commentsElement = response.getPayload();
        NodeList commentNodes = commentsElement.getElementsByTagName("comment");
        int n = commentNodes.getLength();
        for (int i = 0; i < n; i++) {
            Comment comment = new Comment();
            Element commentElement = (Element)commentNodes.item(i);
            comment.setId(commentElement.getAttribute("id"));
            comment.setAuthor(commentElement.getAttribute("author"));
            comment.setAuthorName(commentElement.getAttribute("authorname"));
            comment.setPermaLink(commentElement.getAttribute("permalink"));
            long unixTime = 0;
            try {
                unixTime = Long.parseLong(commentElement.getAttribute("datecreate"));
            } catch (NumberFormatException e) {
                // what shall we do?
                e.printStackTrace();
            }
            comment.setDateCreate(new Date(unixTime * 1000L));
            comment.setPermaLink(commentElement.getAttribute("permalink"));
            comment.setText(XMLUtilities.getValue(commentElement));
            comments.add(comment);
        }
        return comments;
    }

}
