package de.ahlfeld.breminale.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.BooleanResult;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.caches.LocationSources;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 04.04.16.
 */

public class Event extends RealmObject implements Parcelable, JsonDeserializer<Event> {
    private static final String TAG = Event.class.getSimpleName();
    @PrimaryKey
    private Integer id;
    private String name;
    private String description;
    private Location location;
    private String originalImageUrl;
    private String mediumImageUrl;
    private String thumbImageUrl;
    private Date startTime;
    private String soundcloudUrl;
    private Boolean deleted;

    private Boolean favorit = false;
    private String soundcloudUserId;

    public Event() {
        super();
    }

    protected Event(Parcel in) {
        name = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        description = in.readString();
        originalImageUrl = in.readString();
        mediumImageUrl = in.readString();
        thumbImageUrl = in.readString();
        startTime = new Date(in.readLong());
        soundcloudUrl = in.readString();
        soundcloudUserId = in.readString();
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
     * @return The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocationId(Location location) {
        this.location = location;
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
        dest.writeString(name);
        dest.writeParcelable(this.location, flags);
        dest.writeString(description);
        dest.writeString(originalImageUrl);
        dest.writeString(mediumImageUrl);
        dest.writeString(thumbImageUrl);
        dest.writeLong(startTime.getTime());
        dest.writeString(soundcloudUrl);
        dest.writeString(soundcloudUserId);
    }

    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final Event event = new Event();
        JsonObject obj = json.getAsJsonObject();

        if (!obj.get("id").isJsonNull()) {
            event.setId(obj.get("id").getAsInt());
        }

        if (!obj.get("name").isJsonNull()) {
            event.setName(obj.get("name").getAsString());
        }
        if (!obj.get("description").isJsonNull()) {
            event.setDescription(obj.get("description").getAsString());
        }
        if (!obj.get("location_id").isJsonNull()) {
            LocationSources sources = new LocationSources();
            Observable<Location> call = Observable.concat(sources.memory(obj.get("location_id").getAsInt()), sources.network(obj.get("location_id").getAsInt())).first(new Func1<Location, Boolean>() {
                @Override
                public Boolean call(Location location) {
                    return location != null;
                }
            });

            Subscription subscription = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Location>() {
                        @Override
                        public void onCompleted() {
                           Log.d(TAG, "on conplete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Error loading location.", e);
                        }

                        @Override
                        public void onNext(Location location) {
                            Log.d(TAG, "on next");
                            event.setLocationId(location);
                        }
                    });
        }
        if (!obj.get("original_image_url").isJsonNull()) {
            event.setOriginalImageUrl(obj.get("original_image_url").getAsString());
        }
        if (!obj.get("medium_image_url").isJsonNull()) {
            event.setMediumImageUrl(obj.get("medium_image_url").getAsString());
        }
        if (!obj.get("thumb_image_url").isJsonNull()) {
            event.setThumbImageUrl(obj.get("thumb_image_url").getAsString());
        }
        if (!obj.get("start_time").isJsonNull()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try {
                event.setStartTime(sdf.parse(obj.get("start_time").getAsString()));
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (!obj.get("soundcloud_url").isJsonNull()) {
            event.setSoundcloudUrl(obj.get("soundcloud_url").getAsString());
        } else {
            event.setSoundcloudUrl(null);
        }
        if (!obj.get("soundcloud_user_id").isJsonNull()) {
            event.setSoundcloudUserId(obj.get("soundcloud_user_id").getAsString());
        }
        if (!obj.get("deleted").isJsonNull()) {
            event.setDeleted(obj.get("deleted").getAsBoolean());
        }
        return event;
    }
}