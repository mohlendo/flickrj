package com.aetrion.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.aetrion.flickr.util.DebugInputStream;
import com.aetrion.flickr.util.IOUtilities;
import com.aetrion.flickr.util.UrlUtilities;


/**
 * SOAP interface to flickr
 * 
 * @author Matt Ray
 */
public class SOAP extends Transport {
    
    public static final String URN = "urn:flickr";
    public static final String BODYELEMENT = "FlickrRequest";
    
    Class responseClass = SOAPResponse.class;
    
    private DocumentBuilder builder;
    
    public SOAP() throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builder = builderFactory.newDocumentBuilder();
        setTransportType(SOAP);
    }
    
    public SOAP(String host) throws ParserConfigurationException {
        this();
        setHost(host);
    }
    
    public SOAP(String host, int port) throws ParserConfigurationException {
        this();
        setHost(host);
        setPort(port);
    }
    
    public Class getResponseClass() {
        return responseClass;
    }
    
    public void setResponseClass(Class responseClass) {
        if (responseClass == null) {
            throw new IllegalArgumentException("The response Class cannot be null");
        }
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
    public Response get(String path, List parameters) throws IOException, SAXException {
        URL url = UrlUtilities.buildUrl(getHost(), getPort(), path, parameters);
        System.out.println("GET: " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream in = null;
        try {
            if (Flickr.debugStream) {
                in = new DebugInputStream(conn.getInputStream(), System.out);
            } else {
                in = conn.getInputStream();
            }

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
     * @param multipart Use multipart
     * @return The Response object
     * @throws IOException
     * @throws SAXException
     */
    public Response post(String path, Collection parameters, boolean multipart) 
    throws IOException, SAXException {
        URL url = UrlUtilities.buildUrl(getHost(), getPort(), path, Collections.EMPTY_LIST);
        System.out.println(url.toString());

        try {
            //build the envelope
            SOAPEnvelope env = new SOAPEnvelope();
            env.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
            env.addNamespaceDeclaration("xsd","http://www.w3.org/1999/XMLSchema");
            
            //build the body
            Name bodyName = env.createName(BODYELEMENT, "x", URN);
            SOAPBodyElement body = new SOAPBodyElement(bodyName);
            
            //set the format to soap2
            Element e =  XMLUtils.StringToElement("","format","soap2");
            SOAPElement sbe = new SOAPBodyElement(e);
            body.addChildElement(sbe);
            
            //add all the parameters to the body
            for (Iterator i = parameters.iterator(); i.hasNext(); ) {
                Parameter p = (Parameter)i.next();
                e =  XMLUtils.StringToElement("",p.getName(),p.getValue().toString());
                sbe = new SOAPBodyElement(e);
                body.addChildElement(sbe);
            }
            
            //put the body in the envelope
            env.addBodyElement(body);

            if (Flickr.debugStream) {
                System.out.println("SOAP ENVELOPE");
                System.out.println(env.toString());
            }
            
            // build the call.
            Service service = new Service();
            Call call = (Call)service.createCall();
            call.setTargetEndpointAddress(url);
            SOAPEnvelope envelope = call.invoke(env);
            
            if (Flickr.debugStream) {
                System.out.println("SOAP RESPONSE:");
                System.out.println(envelope.toString());
            }
            
            SOAPResponse response = new SOAPResponse(envelope);
            
            return response; 
            
        } catch (SOAPException se) {
            se.printStackTrace();
            throw new RuntimeException(se); // TODO: Replace with a better exception
        } catch (ServiceException se) {
            se.printStackTrace();
            throw new RuntimeException(se); // TODO: Replace with a better exception
        }
    }
        
}