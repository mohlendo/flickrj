package com.aetrion.flickr.util;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 * @author Anthony Eden
 */
public class XMLUtilities {

    private XMLUtilities() {

    }

    public static Collection getChildElements(Node node) {
        List elements = new ArrayList();
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node childNode = nodes.item(i);
            if (childNode instanceof Element) {
                elements.add(childNode);
            }
        }
        return elements;
    }

}
