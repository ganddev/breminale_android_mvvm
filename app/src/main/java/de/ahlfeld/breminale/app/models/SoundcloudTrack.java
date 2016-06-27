package de.ahlfeld.breminale.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by bjornahlfeld on 02.05.16.
 */
public class SoundcloudTrack {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("created_at")
    @Expose
    private Date createdAt;

    @SerializedName("user_id")
    @Expose
    private Long userId;

    @SerializedName("duration")
    @Expose
    private Long duration;

    @SerializedName("stream_url")
    @Expose
    private String streamUrl;

    @SerializedName("uri")
    @Expose
    private String uri;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("streamable")
    @Expose
    private boolean streamable;


    public SoundcloudTrack() {
        super();
    }

    public boolean isStreamable() {
        return streamable;
    }

    public void setStreamable(boolean streamable) {
        this.streamable = streamable;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
