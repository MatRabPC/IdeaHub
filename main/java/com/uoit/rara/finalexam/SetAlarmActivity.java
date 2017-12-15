package com.uoit.rara.finalexam;
//adapted from http://android-er.blogspot.ca/2013/06/set-alarm-on-specified-datetime-with.html
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.IdeaHelper;
import com.uoit.rara.finalexam.model.TopicHelper;

import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity {

    DatePicker pickerDate;
    TimePicker pickerTime;
    Button btnSetAlarm;
    String originalName = "";

    TopicHelper topicHelper;
    IdeaHelper ideaHelper;

    final static int REQUEST_DATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        topicHelper = new TopicHelper();
        ideaHelper = new IdeaHelper(this);

        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);

        originalName = getIntent().getStringExtra("name");

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));

        btnSetAlarm = (Button)findViewById(R.id.btnSetAlarm);
        btnSetAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {Calendar current = Calendar.getInstance();

            Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getCurrentHour(),
                        pickerTime.getCurrentMinute(),
                        00);

                if(cal.compareTo(current) <= 0)
                {
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Date/Time Passed!",
                            Toast.LENGTH_SHORT).show();
                }

                else
                    setAlarm(cal);

            }});

    }

    private void setAlarm(Calendar targetCal){

        Log.i("alarmset", originalName);

        topicHelper.clearThisAlarm(this, originalName);

        String curr = topicHelper.readFromFile(this);
        curr += originalName+","+targetCal.getTime()+";";
        topicHelper.writeToFile(curr, this, 2);

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("name", originalName);
        intent.putExtra("topic", ideaHelper.getIdea(originalName).getTopic());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQUEST_DATA, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

        finish();
    }



}
