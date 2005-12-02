/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.photos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.imageio.ImageIO;

import com.aetrion.flickr.people.User;
import com.aetrion.flickr.util.IOUtilities;

/**
 * Class representing metadata about a Flickr photo.  Instances do not actually contain the photo data, however you can
 * obtain the photo data by calling one of the getXXXImage() or getXXXAsStream() methods in this class.
 *
 * @author Anthony Eden
 */
public class Photo {

    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String ORIGINAL_IMAGE_SUFFIX = "_o.jpg";
    private static final String SMALL_SQUARE_IMAGE_SUFFIX = "_s.jpg";
    private static final String SMALL_IMAGE_SUFFIX = "_m.jpg";
    private static final String THUMBNAIL_IMAGE_SUFFIX = "_t.jpg";
    private static final String MEDIUM_IMAGE_SUFFIX = ".jpg";
    private static final String LARGE_IMAGE_SUFFIX = "_b.jpg";

    private String id;
    private User owner;
    private String secret;
    private String server;
    private boolean favorite;
    private String license;
    private boolean primary;
    private String title;
    private String description;
    private boolean publicFlag;
    private boolean friendFlag;
    private boolean familyFlag;
    private Date datePosted;
    private Date dateTaken;
    private String takenGranularity;
    private Permissions permissions;
    private Editability editability;
    private int comments;
    private Collection notes;
    private Collection tags;
    private Collection urls;
    private String iconServer;
    private String url;

    public Photo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setPrimary(String primary) {
        setPrimary("1".equals(primary));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(boolean publicFlag) {
        this.publicFlag = publicFlag;
    }

    public boolean isFriendFlag() {
        return friendFlag;
    }

    public void setFriendFlag(boolean friendFlag) {
        this.friendFlag = friendFlag;
    }

    public boolean isFamilyFlag() {
        return familyFlag;
    }

    public void setFamilyFlag(boolean familyFlag) {
        this.familyFlag = familyFlag;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setDatePosted(long datePosted) {
        setDatePosted(new Date(datePosted));
    }

    public void setDatePosted(String datePosted) {
        if (datePosted == null || "".equals(datePosted)) return;
        setDatePosted(Long.parseLong(datePosted));
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        if (dateTaken == null || "".equals(dateTaken)) return;
        try {
            setDateTaken(DF.parse(dateTaken));
        } catch (ParseException e) {
            // TODO: figure out what to do with this error
            e.printStackTrace();
        }
    }

    public String getTakenGranularity() {
        return takenGranularity;
    }

    public void setTakenGranularity(String takenGranularity) {
        this.takenGranularity = takenGranularity;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public Editability getEditability() {
        return editability;
    }

    public void setEditability(Editability editability) {
        this.editability = editability;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setComments(String comments) {
        if (comments != null) setComments(Integer.parseInt(comments));
    }

    public Collection getNotes() {
        return notes;
    }

    public void setNotes(Collection notes) {
        this.notes = notes;
    }

    public Collection getTags() {
        return tags;
    }

    public void setTags(Collection tags) {
        this.tags = tags;
    }

    public Collection getUrls() {
        return urls;
    }

    public void setUrls(Collection urls) {
        this.urls = urls;
    }

    public String getIconServer() {
        return iconServer;
    }

    public void setIconServer(String iconServer) {
        this.iconServer = iconServer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BufferedImage getOriginalImage() throws IOException {
        return getImage(ORIGINAL_IMAGE_SUFFIX);
    }

    /**
     * Get an InputStream for the original image. Callers must close the stream upon completion.
     *
     * @return The InputStream
     * @throws IOException
     */
    public InputStream getOriginalAsStream() throws IOException {
        return getImageAsStream(ORIGINAL_IMAGE_SUFFIX);
    }

    /**
     * Get the original image URL.
     *
     * @return The original image URL
     */
    public String getOriginalUrl() {
        return getBaseImageUrl() + ORIGINAL_IMAGE_SUFFIX;
    }

    /**
     * Get an Image object which is a 75x75 pixel square.
     *
     * @return An Image
     * @throws IOException
     */
    public BufferedImage getSmallSquareImage() throws IOException {
        return getImage(SMALL_SQUARE_IMAGE_SUFFIX);
    }

    public InputStream getSmallSquareAsInputStream() throws IOException {
        return getImageAsStream(SMALL_SQUARE_IMAGE_SUFFIX);
    }

    public String getSmallSquareUrl() {
        return getBaseImageUrl() + SMALL_SQUARE_IMAGE_SUFFIX;
    }

    public BufferedImage getThumbnailImage() throws IOException {
        return getImage(THUMBNAIL_IMAGE_SUFFIX);
    }

    public InputStream getThumbnailAsInputStream() throws IOException {
        return getImageAsStream(THUMBNAIL_IMAGE_SUFFIX);
    }

    public String getThumbnailUrl() {
        return getBaseImageUrl() + THUMBNAIL_IMAGE_SUFFIX;
    }

    public BufferedImage getSmallImage() throws IOException {
        return getImage(SMALL_IMAGE_SUFFIX);
    }

    public InputStream getSmallAsInputStream() throws IOException {
        return getImageAsStream(SMALL_IMAGE_SUFFIX);
    }

    public String getSmallUrl() {
        return getBaseImageUrl() + SMALL_IMAGE_SUFFIX;
    }

    public BufferedImage getMediumImage() throws IOException {
        return getImage(MEDIUM_IMAGE_SUFFIX);
    }

    public InputStream getMediumAsStream() throws IOException {
        return getImageAsStream(MEDIUM_IMAGE_SUFFIX);
    }

    public String getMediumUrl() {
        return getBaseImageUrl() + MEDIUM_IMAGE_SUFFIX;
    }

    public BufferedImage getLargeImage() throws IOException {
        return getImage(LARGE_IMAGE_SUFFIX);
    }

    public InputStream getLargeAsStream() throws IOException {
        return getImageAsStream(LARGE_IMAGE_SUFFIX);
    }

    public String getLargeUrl() {
        return getBaseImageUrl() + LARGE_IMAGE_SUFFIX;
    }

    /**
     * Get an image using the specified URL suffix.
     *
     * @param suffix The URL suffix, including the .extension
     * @return The BufferedImage object
     * @throws IOException
     */
    private BufferedImage getImage(String suffix) throws IOException {
        StringBuffer buffer = getBaseImageUrl();
        buffer.append(suffix);
        URL url = new URL(buffer.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream in = null;
        try {
            in = conn.getInputStream();
            return ImageIO.read(in);
        } finally {
            IOUtilities.close(in);
        }
    }

    /**
     * Get an image as a stream. Callers must be sure to close the stream when they are done with it.
     *
     * @param suffix The suffix
     * @return The InputStream
     * @throws IOException
     */
    private InputStream getImageAsStream(String suffix) throws IOException {
        StringBuffer buffer = getBaseImageUrl();
        buffer.append(suffix);
        URL url = new URL(buffer.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        return conn.getInputStream();
    }

    private StringBuffer getBaseImageUrl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://static.flickr.com/");
        buffer.append(getServer());
        buffer.append("/");
        buffer.append(getId());
        buffer.append("_");
        buffer.append(getSecret());
        return buffer;
    }

}
