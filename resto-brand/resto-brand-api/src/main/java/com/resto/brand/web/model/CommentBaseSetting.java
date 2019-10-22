package com.resto.brand.web.model;

import java.io.Serializable;

public class CommentBaseSetting implements Serializable {
    private String id;

    private Integer commentCount;

    private Integer distanceTime;

    private Integer fourSmall;

    private Integer fiveSmall;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(Integer distanceTime) {
        this.distanceTime = distanceTime;
    }

    public Integer getFourSmall() {
        return fourSmall;
    }

    public void setFourSmall(Integer fourSmall) {
        this.fourSmall = fourSmall;
    }

    public Integer getFiveSmall() {
        return fiveSmall;
    }

    public void setFiveSmall(Integer fiveSmall) {
        this.fiveSmall = fiveSmall;
    }
}