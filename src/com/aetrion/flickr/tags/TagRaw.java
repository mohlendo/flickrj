package com.aetrion.flickr.tags;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @see com.aetrion.flickr.tags.TagsInterface#getListUserRaw
 * @author mago
 * @version $Id: TagRaw.java,v 1.1 2007/07/22 17:34:47 x-mago Exp $
 */
public class TagRaw {
    String owner;
    String clean;
    List raw = new ArrayList();

    public TagRaw() {
    }

    public String getClean() {
        return clean;
    }

    public void setClean(String clean) {
        this.clean = clean;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List getRaw() {
        return raw;
    }

    public void addRaw(String rawStr) {
        raw.add(rawStr);
    }
}
