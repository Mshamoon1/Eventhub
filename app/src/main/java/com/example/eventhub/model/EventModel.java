package com.example.eventhub.model;

public class EventModel {
    private String id;
    private String title;
    private String date;
    private String location;
    private String count;
    private String imageUrl;

    // Required empty constructor for Firebase
    public EventModel() {
    }

    public EventModel(String id, String title, String date, String location, String count, String imageUrl) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.count = count;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
