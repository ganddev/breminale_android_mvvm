package de.ahlfeld.breminale.core.domain.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public class Event implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
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
    private boolean deleted;
    @SerializedName("location_id")
    @Expose
    private int locationId;

    private boolean favorit;

    public Event() {
        super();
    }


    protected Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        startTime = new Date(in.readLong());
        soundcloudUrl = in.readString();
        soundcloudUserId = in.readString();
        locationId = in.readInt();
        favorit = in.readInt() == 1 ? true : false;
        deleted = in.readInt() == 1 ? true : false;
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
    public String imageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl The original_image_url
     */
    public void imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        dest.writeString(imageUrl);
        dest.writeLong(startTime.getTime());
        dest.writeString(soundcloudUrl);
        dest.writeString(soundcloudUserId);
        dest.writeInt(locationId);
        dest.writeInt(favorit ? 1 : 0);
        dest.writeInt(deleted ? 1 : 0);
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
