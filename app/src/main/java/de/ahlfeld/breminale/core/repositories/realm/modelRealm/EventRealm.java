package de.ahlfeld.breminale.core.repositories.realm.modelRealm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventRealm extends RealmObject {

    @PrimaryKey
    private Integer id;

    @Required
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getSoundcloudUrl() {
        return soundcloudUrl;
    }

    public void setSoundcloudUrl(String soundcloudUrl) {
        this.soundcloudUrl = soundcloudUrl;
    }

    public String getSoundcloudUserId() {
        return soundcloudUserId;
    }

    public void setSoundcloudUserId(String soundcloudUserId) {
        this.soundcloudUserId = soundcloudUserId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isFavorit() {
        return favorit;
    }

    public void setFavorit(boolean favorit) {
        this.favorit = favorit;
    }

    @Required
    private String description;

    private String imageUrl;
    @Required
    private Date startTime;

    private String soundcloudUrl;

    private String soundcloudUserId;

    private boolean deleted;

    @Required
    private Integer locationId;

    private boolean favorit;
}
