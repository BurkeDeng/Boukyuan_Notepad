package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.notepad.getdb.GetDBData;

import java.util.ArrayList;


/**
 * @author boukyuan
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.home_page_toolBar);
        setSupportActionBar(toolbar);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.home_page_toolBar_icon:
                System.out.println("点击了home_page_toolBar_icon");
                break;
            case R.id.home_page_toolBar_icon2:
                Intent intent=new Intent(MainActivity.this,AboutWorksActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    /**
     * 添加事件 add
     */
    private void initView() {
        ImageButton homePageButton = findViewById(R.id.home_page_button);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditContentActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 接受回调信息
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("进入回调信息");
        String mainId, mainText, mainTitle;
        if (data != null) {
            mainId = data.getExtras().getString("id");
            mainText = data.getExtras().getString("text");
            mainTitle = data.getExtras().getString("title");
        } else {
            System.out.println("没有输入内容");
        }
    }


    /**
     * A Activity启动B Activity：A#onPause() -> B#onCreate() -> B#onStart() -> B#onResume() -> A#onStart()
     */
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(">>>>>>>>>>>>>>>>onStart<<<<<<<<<<<<<<<<<");
    }


    /**
     * 用户再次回到原Activity：onRestart()->onStart()->onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(">>>>>>>>>>>>>>>>onResume<<<<<<<<<<<<<<<<<");
        LinearLayout linearLayout = findViewById(R.id.home_page_linearLayout);
        ArrayList<ArrayList<String>> arrayLists = null;
        try {
            GetDBData getDBData = new GetDBData();
            arrayLists = getDBData.getDBData(MainActivity.this);
            ListView listViewCard = findViewById(R.id.listView_card);
            listViewCard.setAdapter(new CardAdapter(MainActivity.this, arrayLists));
        } catch (Exception e) {
            System.out.println("出错了" + e);
        }
        linearLayout.setVisibility(arrayLists.size() > 0 ? View.GONE : View.VISIBLE);
    }
}