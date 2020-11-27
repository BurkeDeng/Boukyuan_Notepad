package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * @author boukyuan
 * 关于作品
 */
public class AboutWorksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_works);

        TextView aboutTitle=findViewById(R.id.tool_title);
        aboutTitle.setVisibility(View.VISIBLE);
        ImageView aboutImage=findViewById(R.id.tool_imageView);
        aboutImage.setVisibility(View.VISIBLE);
        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}