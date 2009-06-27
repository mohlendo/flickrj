package com.aetrion.flickr.panda;

/**
 * Holds the name of a panda.
 *
 * @author mago
 * @version $Id: Panda.java,v 1.2 2009/06/27 22:35:24 x-mago Exp $
 * @see com.aetrion.flickr.panda.PandaInterface#getPhotos(Panda, java.util.Set, int, int)
 */
public class Panda {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
