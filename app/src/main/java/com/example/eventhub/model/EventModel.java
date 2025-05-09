package com.example.eventhub.model;



public class EventModel {
    private String title;
    private String date;
    private String location;
    private String count;
    private String imageUri;

    // Required empty constructor for Firebase
    public EventModel() {
    }

    public EventModel(String title, String date, String location, String count, String imageUri) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.count = count;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getCount() {
        return count;
    }

    public String getImageUri() {
        return imageUri;
    }
}

