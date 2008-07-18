package com.aetrion.flickr.tags;

import java.util.ArrayList;

/**
 * List (tag-)clusters.
 *
 * @author mago
 * @since 1.2
 * @version $Id: ClustersList.java,v 1.1 2008/07/18 22:23:35 x-mago Exp $
 */
public class ClustersList {
    private static final long serialVersionUID = -5289011879992607535L;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    public void addCluster(Cluster cluster) {
        clusters.add(cluster);
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
}
