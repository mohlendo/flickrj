package com.aetrion.flickr.tags;

import java.util.ArrayList;

/**
 * Cluster (list) of tags.
 *
 * @author mago
 * @since 1.2
 * @version $Id: Cluster.java,v 1.1 2008/07/18 22:23:35 x-mago Exp $
 */
public class Cluster {
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
}
