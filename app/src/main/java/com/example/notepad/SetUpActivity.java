package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author boukyuan
 * 设置
 */
public class SetUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        TextView aboutTitle = findViewById(R.id.tool_title);
        aboutTitle.setVisibility(View.VISIBLE);
        ImageView aboutImage = findViewById(R.id.tool_imageView);
        aboutImage.setVisibility(View.VISIBLE);
        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CheckBox atNightButton = findViewById(R.id.atNight_checkBox);
        atNightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView atNightText = findViewById(R.id.atNight_text);
                if (isChecked) {
                    atNightText.setText("夜间模式已开启");
                } else {
                    atNightText.setText("夜间模式已关闭");
                }
            }
        });
    }}


