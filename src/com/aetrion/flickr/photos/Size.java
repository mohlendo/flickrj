/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photos;

/**
 * @author Anthony Eden
 * @version $Id: Size.java,v 1.3 2007/09/13 22:12:40 x-mago Exp $
 */
public class Size {

    private String label;
    private int width;
    private int height;
    private String source;
    private String url;

    public Size() {

    }

    /**
     * Size-descriptor. Possible labels are:
     * Square, Thumbnail, Small, Medium, Large, Original.
     * 
     * @return label
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setWidth(String width) {
        if (width != null) setWidth(Integer.parseInt(width));
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHeight(String height) {
        if (height != null) setHeight(Integer.parseInt(height));
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
