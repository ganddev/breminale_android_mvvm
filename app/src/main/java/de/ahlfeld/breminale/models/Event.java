package de.ahlfeld.breminale.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by bjornahlfeld on 04.04.16.
 */

public class Event extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("original_image_url")
    @Expose
    private String originalImageUrl;
    @SerializedName("medium_image_url")
    @Expose
    private String mediumImageUrl;
    @SerializedName("thumb_image_url")
    @Expose
    private String thumbImageUrl;
    @SerializedName("start_time")
    @Expose
    private Date startTime;
    @SerializedName("soundcloud_url")
    @Expose
    private String soundcloudUrl;
    @SerializedName("soundcloud_user_id")
    @Expose
    private String soundcloudUserId;

    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("location_id")
    @Expose
    private int locationId;


    private Boolean favorit = false;

    public Event() {
        super();
    }


    protected Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        originalImageUrl = in.readString();
        mediumImageUrl = in.readString();
        thumbImageUrl = in.readString();
        startTime = new Date(in.readLong());
        soundcloudUrl = in.readString();
        soundcloudUserId = in.readString();
        locationId = in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return The originalImageUrl
     */
    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    /**
     * @param originalImageUrl The original_image_url
     */
    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    /**
     * @return The mediumImageUrl
     */
    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    /**
     * @param mediumImageUrl The medium_image_url
     */
    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    /**
     * @return The thumbImageUrl
     */
    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    /**
     * @param thumbImageUrl The thumb_image_url
     */
    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    /**
     * @return The startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The soundcloudUrl
     */
    public String getSoundcloudUrl() {
        return soundcloudUrl;
    }

    /**
     * @param soundcloudUrl The soundcloud_url
     */
    public void setSoundcloudUrl(String soundcloudUrl) {
        this.soundcloudUrl = soundcloudUrl;
    }

    /**
     * @return The deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted The deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setSoundcloudUserId(String soundcloudUserId) {
        this.soundcloudUserId = soundcloudUserId;
    }

    public String getSoundcloudUserId() {
        return soundcloudUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(originalImageUrl);
        dest.writeString(mediumImageUrl);
        dest.writeString(thumbImageUrl);
        dest.writeLong(startTime.getTime());
        dest.writeString(soundcloudUrl);
        dest.writeString(soundcloudUserId);
        dest.writeInt(locationId);
    }

    public Boolean getFavorit() {
        return favorit;
    }

    public void setFavorit(Boolean favorit) {
        this.favorit = favorit;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

}