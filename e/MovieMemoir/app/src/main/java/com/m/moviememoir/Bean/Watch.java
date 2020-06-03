package com.m.moviememoir.Bean;

public class Watch {
    private String title;
    private String releaseTime;
    private String addTime;
    private String imgUrl;

    public String getAddTime() {
        return addTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getTitle() {
        return title;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
