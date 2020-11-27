package com.example.notepad;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
/**
 * @author boukyuan
 * 封装  使通知发送到通知栏
 */
public class SimpleNotification extends AppCompatActivity {
    /**
     * 调用简单通知
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSimpleNotification(Context context) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.notepad_logo);
        Intent intent=new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        String channelId=createNotificationChannel("my_notepad_id","my_notepad_name", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification=new NotificationCompat.Builder(context,channelId)
                .setContentTitle("通知")
                .setContentText("收到一条信息")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.notepad_logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //在用户点按通知后自动移除通知
                .setAutoCancel(true);
//          .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.notepad_logo)))
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(100,notification.build());
    }
    /**
     * 创建通知渠道
     * api大于android8.0就创建渠道返回渠道id，否则返回空
     */
    private String createNotificationChannel(String channelId, String channelName, int level) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, level);
            manager.createNotificationChannel(channel);
            return channelId;
        } else {
            return null;
        }
    }
}
