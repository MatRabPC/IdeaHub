package com.uoit.rara.finalexam;
//adapted from http://android-er.blogspot.ca/2013/06/set-alarm-on-specified-datetime-with.html
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.TopicHelper;

import static android.content.Context.POWER_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    TopicHelper topicHelper;
    String[] media = {"Travel", "Eductaion", "Finance", "Music", "Technology"};
    int[] drawableIds = {R.drawable.ic_airplanemode_active_black_24dp, R.drawable.ic_sort_by_alpha_black_24dp, R.drawable.ic_attach_money_black_24dp, R.drawable.ic_library_music_black_24dp, R.drawable.ic_subtitles_black_24dp};


    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager.WakeLock screenLock = ((PowerManager)context.getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire(); //SO

//later

        topicHelper = new TopicHelper();
        String name = intent.getStringExtra("name");
        String topic = intent.getStringExtra("topic");

        Log.i("Topic alarm2", topic);

       // Log.i("at alarm", name);

       // Toast.makeText(context, "Alarm received!"+name, Toast.LENGTH_LONG).show();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(drawableIds[topicHelper.indexOf(media, topic)])//R.drawable.ic_launcher_background)
                        .setContentTitle("IdeaHub")
                        .setContentText("Is now a good time to think about "+name);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

        screenLock.release();

        topicHelper = new TopicHelper();
        topicHelper.clearThisAlarm(context, name); //clear old alarms

    }
}
