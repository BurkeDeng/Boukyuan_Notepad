package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.notepad.getdb.DatabaseHelper;
import com.example.notepad.getdb.GetDBData;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * @author boukyuan
 */
public class EditContentActivity extends AppCompatActivity {
    private int yearTime, monthTime, dayTime, hourOfDayTime, minuteTime;
    String inTheAfternoon;
    Calendar calendar = Calendar.getInstance();
    private EditText getYearMonthDay, getHourMinuteSecond;
    private TextView tipsTitle;
    Handler myHandler;
    SQLiteOpenHelper dbHelper = new DatabaseHelper(EditContentActivity.this, "test_notepad.db", null, 1);
    SQLiteDatabase dbWritableDatabase;
    ContentValues contentValues = new ContentValues();

    String id, text, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);

        setTitle("");
        clear();
        titleMethod();
        alarmClockMethod();
        selectYearMonthDay();
        selectHourMinuteSecond();
        copyAndPaste();
        tipsTitle = findViewById(R.id.tips);
    }

    /**
     * 返回
     */
    private void clear() {
        ImageView clearImageView = findViewById(R.id.write_clear);
        clearImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 重新加载布局
     */
    public void refresh() {
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    /**
     * 线程刷新 方法只能调用在类onCreate的地方
     */
    private void threadRefresh(String time) {
        //              myHandler.sendEmptyMessage(0);在点击处调用
        myHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    tipsTitle.setText(time);
                }
            }
        };
    }

    /**
     * 获取输入框的文字并发送过去
     */
    private void titleMethod() {
        EditText walkEditText = findViewById(R.id.write_editText);
        EditText descriptionEditText = findViewById(R.id.write_editText2);
        ImageButton yellowImageButton = findViewById(R.id.yellow_image_button);
        yellowImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
                dbWritableDatabase = dbHelper.getWritableDatabase();
                // 向该对象中插入键值对  不能重复插入相同的键值对
//                contentValues.put("id");
                contentValues.put("text", walkEditText.getText().toString());
                contentValues.put("title", descriptionEditText.getText().toString());
                //调用insert()方法将数据插入到数据库里
                dbWritableDatabase.insert("notepad", null, contentValues);
                System.out.println("add数据成功");
                queryDataBase();
                callBackInformation();
                finish();
            }
        });
    }

    /**
     * 回调信息
     */
    private void callBackInformation() {
        System.out.println("进入回调");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("text", text);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        System.out.println("回调成功");
    }

    /**
     * 根据switch来选择是否显示时间选择
     */
    private void alarmClockMethod() {
        SwitchCompat switchCompat = findViewById(R.id.two_rl_switch);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout visibility = findViewById(R.id.visibility_linear);
                visibility.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    /**
     * 选择时间 年月日
     */
    private void selectYearMonthDay() {
        yearTime = calendar.get(Calendar.YEAR);
        monthTime = calendar.get(Calendar.MONTH);
        dayTime = calendar.get(Calendar.DAY_OF_MONTH);
        getYearMonthDay = findViewById(R.id.time_getYearMonthDay);
        getYearMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        getYearMonthDay.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        getYearMonthDay.setTextColor(0xffFFC107);
                        getYearMonthDay.setTextSize(25);
                        yearTime = year;
                        monthTime = month;
                        dayTime = dayOfMonth;
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditContentActivity.this, 0, onDateSetListener, yearTime, monthTime, dayTime);
                datePickerDialog.show();
            }
        });
    }

    /**
     * 选择时分秒
     */
    private void selectHourMinuteSecond() {
        //小时
        hourOfDayTime = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        minuteTime = calendar.get(Calendar.MINUTE);
        getHourMinuteSecond = findViewById(R.id.time_getHourMinuteSecond);
        getHourMinuteSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(EditContentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        inTheAfternoon = (hourOfDay < 12) ? "AM" : "PM";
                        getHourMinuteSecond.setText(hourOfDay + ":" + minute + " " + inTheAfternoon);
                        getHourMinuteSecond.setTextColor(0xffFFC107);
                        getHourMinuteSecond.setTextSize(25);

                        tipsTitle.setText("设置的提醒时间为" + " " + yearTime + "-" + (monthTime + 1) + "-" + dayTime + "  " + hourOfDay + ":" + minute + "  " + inTheAfternoon);
                    }
                }, hourOfDayTime, minuteTime, false).show();
            }
        });
    }

    /**
     * 复制粘贴
     */
    private void copyAndPaste() {
        ImageView copyPaste = findViewById(R.id.two_rl_imageView2);
        copyPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * 查询数据库
     */
    private void queryDataBase() {
        ArrayList<ArrayList<String>> dbShow= new GetDBData().getDBData(EditContentActivity.this);
    }
}