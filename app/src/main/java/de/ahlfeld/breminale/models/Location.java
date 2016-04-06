package de.ahlfeld.breminale.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class Location extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("original_image_url")
    @Expose
    private String originalImageUrl;
    @SerializedName("medium_image_url")
    @Expose
    private String mediumImageUrl;
    @SerializedName("thumb_image_url")
    @Expose
    private String thumbImageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    protected Location(Parcel in) {
        name = in.readString();
        originalImageUrl = in.readString();
        mediumImageUrl = in.readString();
        thumbImageUrl = in.readString();
        description = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
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
     * @return The latitude
     */
    public Integer getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public Integer getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
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
        dest.writeString(originalImageUrl);
        dest.writeString(mediumImageUrl);
        dest.writeString(thumbImageUrl);
        dest.writeString(description);
    }
}
