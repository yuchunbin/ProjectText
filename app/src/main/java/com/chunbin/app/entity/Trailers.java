package com.chunbin.app.entity;

import java.util.List;
import java.util.Map;

public class Trailers {


    //id
    private Long id;
    //电影名字
    private String movieName;
    //封面图像
    private String coverImg;
    //电影ID
    private Long movieId;
    //网址
    private String url;
    //高网址
    private String hightUrl;
    //视频标题
    private String videoTitle;
    //视频长度
    private Integer videoLength;
    //评级
    private String rating;
    //类型
    private String[] type;
    //总结
    private String summary;

    @Override
    public String toString() {
        return "Wares{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", movieId='" + movieId + '\'' +
                ", url='" + url + '\'' +
                ", hightUrl='" + hightUrl + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoLength='" + videoLength + '\'' +
                ", rating='" + rating + '\'' +
                ", type='" + type + '\'' +
                ", summary='" + summary+
                '}';
    }
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

    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getRating() {
        return rating;
    }

    public void setType( String[] type) {
        this.type = type;
    }
    public  String [] getType() {
        return type;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getSummary() {
        return summary;
    }

}
