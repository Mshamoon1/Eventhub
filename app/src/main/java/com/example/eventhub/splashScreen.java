package com.example.eventhub;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide(); //to hide tool bar i used it

        Thread thread =new Thread(){

            public void run(){

                try {
                    sleep(4000);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                finally {
                    Intent intent=new Intent(splashScreen.this , log.class);
                    startActivity(intent);

                }
            }


        };thread.start();

    }
}