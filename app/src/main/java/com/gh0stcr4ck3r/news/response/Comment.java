package com.gh0stcr4ck3r.news.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("article")
    @Expose
    private Integer article;
    @SerializedName("user")
    @Expose
    private Author user;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Author getUser() {
        return user;
    }

    public void setUser(Author user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", details='" + details + '\'' +
                ", article=" + article +
                ", user=" + user +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}

