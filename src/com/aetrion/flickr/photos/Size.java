/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photos;

/**
 * This class descibes a Size of a Photo.<p>
 *
 * @author Anthony Eden
 * @version $Id: Size.java,v 1.5 2007/12/02 22:52:47 x-mago Exp $
 */
public class Size {
    /**
     * Thumbnail, 100 on longest side.
     *
     * @see com.aetrion.flickr.photos.Size#getLabel()
     * @see com.aetrion.flickr.photos.Size#setLabel(int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImage(Photo, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImageAsStream(Photo, int)
     */
    public static final int THUMB = 0;

    /**
     * Small square 75x75.
     *
     * @see com.aetrion.flickr.photos.Size#getLabel()
     * @see com.aetrion.flickr.photos.Size#setLabel(int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImage(Photo, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImageAsStream(Photo, int)
     */
    public static final int SQUARE = 1;

    /**
     * Small, 240 on longest side.
     *
     * @see com.aetrion.flickr.photos.Size#getLabel()
     * @see com.aetrion.flickr.photos.Size#setLabel(int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImage(Photo, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImageAsStream(Photo, int)
     */
    public static final int SMALL = 2;

    /**
     * Medium, 500 on longest side.
     *
     * @see com.aetrion.flickr.photos.Size#getLabel()
     * @see com.aetrion.flickr.photos.Size#setLabel(int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImage(Photo, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImageAsStream(Photo, int)
     */
    public static final int MEDIUM = 3;

    /**
     * Large, 1024 on longest side (only exists for very large original images).
     *
     * @see com.aetrion.flickr.photos.Size#getLabel()
     * @see com.aetrion.flickr.photos.Size#setLabel(int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImage(Photo, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImageAsStream(Photo, int)
     */
    public static final int LARGE = 4;

    /**
     * Original image, either a jpg, gif or png, depending on source format.<br>
     * Only from pro-users original images are available!
     *
     * @see com.aetrion.flickr.photos.Size#getLabel()
     * @see com.aetrion.flickr.photos.Size#setLabel(int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImage(Photo, int)
     * @see com.aetrion.flickr.photos.PhotosInterface#getImageAsStream(Photo, int)
     */
    public static final int ORIGINAL = 5;

    private int label;
    private int width;
    private int height;
    private String source;
    private String url;

    public Size() {

    }

    /**
     * Size of the Photo.
     *
     * @return label
     * @see com.aetrion.flickr.photos.Size#THUMB
     * @see com.aetrion.flickr.photos.Size#SQUARE
     * @see com.aetrion.flickr.photos.Size#SMALL
     * @see com.aetrion.flickr.photos.Size#MEDIUM
     * @see com.aetrion.flickr.photos.Size#LARGE
     * @see com.aetrion.flickr.photos.Size#ORIGINAL
     */
    public int getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label.equals("Square")) {
            setLabel(SQUARE);
        } else if (label.equals("Thumbnail")) {
            setLabel(THUMB);
        } else if (label.equals("Small")) {
            setLabel(SMALL);
        } else if (label.equals("Medium")) {
            setLabel(MEDIUM);
        } else if (label.equals("Large")) {
            setLabel(LARGE);
        } else if (label.equals("Original")) {
            setLabel(ORIGINAL);
        }
    }

    /**
     * Size of the Photo.
     *
     * @param label The integer-representation of a size
     * @see com.aetrion.flickr.photos.Size#THUMB
     * @see com.aetrion.flickr.photos.Size#SQUARE
     * @see com.aetrion.flickr.photos.Size#SMALL
     * @see com.aetrion.flickr.photos.Size#MEDIUM
     * @see com.aetrion.flickr.photos.Size#LARGE
     * @see com.aetrion.flickr.photos.Size#ORIGINAL
     */
    public void setLabel(int label) {
        this.label = label;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setWidth(String width) {
        if (width != null) {
            setWidth(Integer.parseInt(width));
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHeight(String height) {
        if (height != null) {
            setHeight(Integer.parseInt(height));
        }
    }

    /**
     * URL of the image.
     *
     * @return Image-URL
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * URL of the photopage.
     *
     * @return Page-URL
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
