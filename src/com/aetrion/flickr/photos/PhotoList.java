/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.photos;

import java.util.ArrayList;

/**
 * A list of photos with additional meta data.
 *
 * @author Anthony Eden
 */
public class PhotoList extends ArrayList {

    private int page;
    private int pages;
    private int perPage;
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPage(String page) {
        if (page != null) setPage(Integer.parseInt(page));
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPages(String pages) {
        if (pages != null) setPages(Integer.parseInt(pages));
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setPerPage(String perPage) {
        if (perPage != null) setPerPage(Integer.parseInt(perPage));
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTotal(String total) {
        if (total != null) setTotal(Integer.parseInt(total));
    }

}
