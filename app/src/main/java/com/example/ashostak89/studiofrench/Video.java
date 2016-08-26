package com.example.ashostak89.studiofrench;

/**
 * Created by ashostak89 on 8/26/2016.
 */
public class Video {
   private String videoName;
    private String videoUrl;

    public Video() {
    }

    public Video(String videoName, String videoUrl) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;

    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
