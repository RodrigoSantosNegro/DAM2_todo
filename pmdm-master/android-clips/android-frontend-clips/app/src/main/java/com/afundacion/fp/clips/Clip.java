package com.afundacion.fp.clips;

import org.json.JSONException;
import org.json.JSONObject;

public class Clip {
    private int id;
    private String videoTitle;
    private String videoUrl;

    public Clip(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.videoTitle = json.getString("title");
        this.videoUrl = json.getString("videoUrl");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
