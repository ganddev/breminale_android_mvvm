package de.ahlfeld.breminale.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bjornahlfeld on 02.05.16.
 */
public class SoundcloudUser {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("permalink_url")
    @Expose
    private String permalinkUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("discogs_name")
    @Expose
    private Object discogsName;
    @SerializedName("myspace_name")
    @Expose
    private Object myspaceName;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("website_title")
    @Expose
    private String websiteTitle;
    @SerializedName("online")
    @Expose
    private Boolean online;
    @SerializedName("track_count")
    @Expose
    private Integer trackCount;
    @SerializedName("playlist_count")
    @Expose
    private Integer playlistCount;
    @SerializedName("followers_count")
    @Expose
    private Integer followersCount;
    @SerializedName("followings_count")
    @Expose
    private Integer followingsCount;
    @SerializedName("public_favorites_count")
    @Expose
    private Integer publicFavoritesCount;


    public SoundcloudUser() {
        super();
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The permalink
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     * @param permalink The permalink
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return The permalinkUrl
     */
    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    /**
     * @param permalinkUrl The permalink_url
     */
    public void setPermalinkUrl(String permalinkUrl) {
        this.permalinkUrl = permalinkUrl;
    }

    /**
     * @return The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The discogsName
     */
    public Object getDiscogsName() {
        return discogsName;
    }

    /**
     * @param discogsName The discogs_name
     */
    public void setDiscogsName(Object discogsName) {
        this.discogsName = discogsName;
    }

    /**
     * @return The myspaceName
     */
    public Object getMyspaceName() {
        return myspaceName;
    }

    /**
     * @param myspaceName The myspace_name
     */
    public void setMyspaceName(Object myspaceName) {
        this.myspaceName = myspaceName;
    }

    /**
     * @return The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return The websiteTitle
     */
    public String getWebsiteTitle() {
        return websiteTitle;
    }

    /**
     * @param websiteTitle The website_title
     */
    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    /**
     * @return The online
     */
    public Boolean getOnline() {
        return online;
    }

    /**
     * @param online The online
     */
    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     * @return The trackCount
     */
    public Integer getTrackCount() {
        return trackCount;
    }

    /**
     * @param trackCount The track_count
     */
    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    /**
     * @return The playlistCount
     */
    public Integer getPlaylistCount() {
        return playlistCount;
    }

    /**
     * @param playlistCount The playlist_count
     */
    public void setPlaylistCount(Integer playlistCount) {
        this.playlistCount = playlistCount;
    }

    /**
     * @return The followersCount
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     * @param followersCount The followers_count
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @return The followingsCount
     */
    public Integer getFollowingsCount() {
        return followingsCount;
    }

    /**
     * @param followingsCount The followings_count
     */
    public void setFollowingsCount(Integer followingsCount) {
        this.followingsCount = followingsCount;
    }

    /**
     * @return The publicFavoritesCount
     */
    public Integer getPublicFavoritesCount() {
        return publicFavoritesCount;
    }

    /**
     * @param publicFavoritesCount The public_favorites_count
     */
    public void setPublicFavoritesCount(Integer publicFavoritesCount) {
        this.publicFavoritesCount = publicFavoritesCount;
    }

}
