package com.aetrion.flickr.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

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

    /**
     * Get the text value for the specified element.  If the element is null, or the element's body is empty then this
     * method will return null.
     *
     * @param element The Element
     * @return The value String or null
     */
    public static String getValue(Element element) {
        if (element != null) {
            Node dataNode = element.getFirstChild();
            if (dataNode != null) {
                return ((Text) dataNode).getData();
            }
        }
        return null;
    }

}
