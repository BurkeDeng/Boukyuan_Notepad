package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author boukyuan
 */
public class MainActivity extends AppCompatActivity {
    private String mainId, mainText, mainTitle;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_page_toolBar_icon) {
            System.out.println("点击了home_page_toolBar_icon");
        }
        return true;
    }

    /**
     * 添加时件 add
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
        assert data != null;
        mainId = data.getExtras().getString("id");
        mainText = data.getExtras().getString("text");
        mainTitle = data.getExtras().getString("title");
    }

    /**
     * A Activity启动B Activity：A#onPause() -> B#onCreate() -> B#onStart() -> B#onResume() -> A#onStart()
     */
    @Override
    protected void onStart() {
        super.onStart();
        CardView cardView = findViewById(R.id.main_cardView);
        LinearLayout linearLayout = findViewById(R.id.home_page_linearLayout);
        cardView.setVisibility(mainId == null ? View.GONE : View.VISIBLE);
        linearLayout.setVisibility(mainId == null ? View.VISIBLE : View.GONE);
        TextView cardText=findViewById(R.id.card_text);
        TextView cardTitle=findViewById(R.id.card_title);
        cardText.setText(mainText);
        cardTitle.setText(mainTitle);
        try {
            Button cardButtonText=findViewById(R.id.card_button);
            cardButtonText.setText(mainText.substring(0,1));
        }catch(Exception e){
            System.out.println("内容为空"+e);
        }
    }
}