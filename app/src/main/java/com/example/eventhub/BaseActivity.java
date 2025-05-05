package com.example.eventhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private FrameLayout baseContainer;
    private View loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        baseContainer = findViewById(R.id.base_content);
        loader = findViewById(R.id.loader_container);
    }

    protected void setContentViewWithLoader(View view) {
        baseContainer.addView(view);
    }

    public void showLoader() {
        if (loader != null) loader.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        if (loader != null) loader.setVisibility(View.GONE);
    }
}