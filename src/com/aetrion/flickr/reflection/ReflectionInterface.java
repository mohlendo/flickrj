/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.reflection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aetrion.flickr.Authentication;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.Response;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.util.XMLUtilities;

/**
 * Interface for working with Flickr tags.
 *
 * @author Anthony Eden
 */
public class ReflectionInterface {

    public static final String METHOD_GET_METHOD_INFO = "flickr.reflection.getMethodInfo";
    public static final String METHOD_GET_METHODS = "flickr.reflection.getMethods";

    private String apiKey;
    private Transport transportAPI;
    
    /**
     * Construct a ReflectionInterface.
     *
     * @param apiKey The API key
     * @param transport The Transport interface
     */
    public ReflectionInterface(String apiKey, Transport transportAPI) {
        this.apiKey = apiKey;
        this.transportAPI = transportAPI;
    }
    
    /**
     * Get the info for the specified method.
     *
     * @param methodName The method name
     * @return The Method object
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Method getMethodInfo(String methodName) throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_METHOD_INFO));
        parameters.add(new Parameter("api_key", apiKey));
        
        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }
        
        parameters.add(new Parameter("method_name", methodName));
        
        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } 
        
        Element methodElement = response.getPayload();
        Method method = new Method();
        method.setName(methodElement.getAttribute("name"));
        method.setNeedsLogin("1".equals(methodElement.getAttribute("needslogin")));
        method.setDescription(XMLUtilities.getChildValue(methodElement, "description"));
        method.setResponse(XMLUtilities.getChildValue(methodElement, "response"));
        method.setExplaination(XMLUtilities.getChildValue(methodElement, "explaination"));
        
        List arguments = new ArrayList();
        Element argumentsElement = XMLUtilities.getChild(methodElement, "arguments");
        NodeList argumentElements = argumentsElement.getElementsByTagName("argument");
        for (int i = 0; i < argumentElements.getLength(); i++) {
            Argument argument = new Argument();
            Element argumentElement = (Element) argumentElements.item(i);
            argument.setName(argumentElement.getAttribute("name"));
            argument.setOptional("1".equals(argumentElement.getAttribute("optional")));
            argument.setDescription(XMLUtilities.getValue(argumentElement));
            arguments.add(argument);
        }
        method.setArguments(arguments);
        
        List errors = new ArrayList();
        Element errorsElement = XMLUtilities.getChild(methodElement, "errors");
        NodeList errorElements = errorsElement.getElementsByTagName("error");
        for (int i = 0; i < errorElements.getLength(); i++) {
            Error error = new Error();
            Element errorElement = (Element) errorElements.item(i);
            error.setCode(errorElement.getAttribute("code"));
            error.setMessage(errorElement.getAttribute("messahe"));
            error.setExplaination(XMLUtilities.getValue(errorElement));
            errors.add(error);
        }
        method.setErrors(errors);
        
        return method;
    }
    
    /**
     * Get a list of all methods.
     *
     * @return The method names
     * @throws IOException
     * @throws SAXException
     * @throws FlickrException
     */
    public Collection getMethods() throws IOException, SAXException, FlickrException {
        List parameters = new ArrayList();
        parameters.add(new Parameter("method", METHOD_GET_METHODS));
        parameters.add(new Parameter("api_key", apiKey));
        
        RequestContext requestContext = RequestContext.getRequestContext();
        Authentication auth = requestContext.getAuthentication();
        if (auth != null) {
            parameters.addAll(auth.getAsParameters());
        }
        
        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        } 
        
        Element methodsElement = response.getPayload();
        
        List methods = new ArrayList();
        NodeList methodElements = methodsElement.getElementsByTagName("method");
        for (int i = 0; i < methodElements.getLength(); i++) {
            Element methodElement = (Element) methodElements.item(i);
            methods.add(XMLUtilities.getValue(methodElement));
        }
        return methods;
    }
    
}
