package com.example.eventhub;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dx97hxvj1");
        config.put("api_key", "744886357881173");
        config.put("api_secret", "SR3aLsk6fgkUrONwEA2rh5aUCO4");
        MediaManager.init(this, config);
    }
}