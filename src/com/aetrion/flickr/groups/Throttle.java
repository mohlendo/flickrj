package com.aetrion.flickr.groups;

/**
 * Throttle - Limit the abilitiy to add Photos to a group
 * on a per user per post basis. "No more than X in Y days".
 *
 * @author Anthony Eden
 * @version $Id: Throttle.java,v 1.2 2007/12/17 19:00:16 x-mago Exp $
 */
public class Throttle {
    private int count;
    private String mode;
    private int remaining;

    /**
     * Posts are limited to this number of Photos.
     *
     * @return mx number of posts allowed
     */
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Throttle mode - day, month or none.
     *
     * @return mode
     */
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Number of remaining Photos to add.<br>
     * (lokks, like not longer available)
     *
     * @return units
     */
    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
