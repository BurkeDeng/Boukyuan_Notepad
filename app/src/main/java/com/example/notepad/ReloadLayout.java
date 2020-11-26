package com.example.notepad;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author boukyuan
 * 封装 重新加载布局
 */
public class ReloadLayout extends AppCompatActivity {
    public void refresh() {
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}
