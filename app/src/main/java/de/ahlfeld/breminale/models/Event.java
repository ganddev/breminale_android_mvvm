package de.ahlfeld.breminale.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
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
    private String startTime;
    @SerializedName("soundcloud_url")
    @Expose
    private String soundcloudUrl;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    public Event() {
        super();
    }

    protected Event(Parcel in) {
        name = in.readString();
        description = in.readString();
        originalImageUrl = in.readString();
        mediumImageUrl = in.readString();
        thumbImageUrl = in.readString();
        startTime = in.readString();
        soundcloudUrl = in.readString();
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
     * @return The locationId
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * @param locationId The location_id
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(String startTime) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(originalImageUrl);
        dest.writeString(mediumImageUrl);
        dest.writeString(thumbImageUrl);
        dest.writeString(startTime);
        dest.writeString(soundcloudUrl);
    }
}