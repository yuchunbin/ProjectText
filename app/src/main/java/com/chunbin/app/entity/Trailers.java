package com.chunbin.app.entity;

import java.util.List;

public class Trailers {

    private long id;
    private String movieName;
    private String coverImg;
    private long movieId;
    private String url;
    private String hightUrl;
    private String videoTitle;
    private int videoLength;
    private int rating;
    private List<String> type;
    private String summary;
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getMovieName() {
        return movieName;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
    public String getCoverImg() {
        return coverImg;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
    public long getMovieId() {
        return movieId;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setHightUrl(String hightUrl) {
        this.hightUrl = hightUrl;
    }
    public String getHightUrl() {
        return hightUrl;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }
    public int getVideoLength() {
        return videoLength;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
    public List<String> getType() {
        return type;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getSummary() {
        return summary;
    }

}
