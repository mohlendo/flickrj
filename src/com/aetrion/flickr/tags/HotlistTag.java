package com.aetrion.flickr.tags;

/**
 *
 * @author mago
 * @version $Id: HotlistTag.java,v 1.1 2007/07/22 17:34:47 x-mago Exp $
 */
public class HotlistTag {
    private String value;
    private int score = 0;

    public HotlistTag() {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScore(String score) {
        setScore(Integer.parseInt(score));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
