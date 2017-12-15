package com.uoit.rara.finalexam;
//some generic tips from stackoverflow may have been used herein
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.IdeaHelper;
import com.uoit.rara.finalexam.model.TopicHelper;

public class ShowIdeaActivity extends AppCompatActivity {

    //set up vars
    private IdeaHelper ideaHelper;
    private TopicHelper topicHelper = new TopicHelper();
    String[] media = {"Travel", "Eductaion", "Finance", "Music", "Technology"};
    int[] drawableIds = {R.drawable.ic_airplanemode_active_black_24dp, R.drawable.ic_sort_by_alpha_black_24dp, R.drawable.ic_attach_money_black_24dp, R.drawable.ic_library_music_black_24dp, R.drawable.ic_subtitles_black_24dp};
    String[] soundlist = {"Riff", "Jazz", "Piano", "Dragon"};
    int[] soundIds = {R.raw.drive, R.raw.trumpet, R.raw.persona, R.raw.dragon};
    String originalName = "";
    public MediaPlayer musicMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_idea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ideaHelper = new IdeaHelper(this);
        originalName = getIntent().getStringExtra("name");

        updateViewHere(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_idea_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteIdea();
                return true;

            case R.id.action_alarm:
                setAlarm();
                return true;


            case R.id.action_edit:
                editIdea();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void updateViewHere(boolean playSound) //update the current view, specify sound prefs
    {
        Idea idea = ideaHelper.getIdea(originalName);

        //Log.i("Sound Search", idea.getSound() + " " + topicHelper.indexOf(soundlist, idea.getSound()));

        TextView lblName = (TextView) findViewById(R.id.lblName);
        lblName.setText(idea.getName());

        ImageView imgIco = (ImageView)findViewById(R.id.imgIco);
        imgIco.setImageResource(drawableIds[topicHelper.indexOf(media, idea.getTopic())]);

        TextView lblDesc = (TextView) findViewById(R.id.lblDesc);
        lblDesc.setText(idea.getDesc());

        TextView lblLoc = (TextView) findViewById(R.id.lblLoc);
        lblLoc.setText("Thought of at: " + idea.getLoc());

        checkAlarmSet();

        musicMediaPlayer = MediaPlayer.create(this, soundIds[topicHelper.indexOf(soundlist, idea.getSound())]);

        Log.i("Sound Playing?", "True AF");
      //  musicMediaPlayer.pause();
        if (playSound)
            musicMediaPlayer.start();


    }

    public void editIdea()
    {
        Intent intent = new Intent(this, EditIdeaActivity.class);
        intent.putExtra("name", originalName);
        startActivityForResult(intent, 2);
    }

    public void deleteIdea()
    {
        musicMediaPlayer.stop();
        if (ideaHelper.deleteIdea(originalName))
        {
            Toast.makeText(this, originalName+" deleted successfully.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Failed to delete "+originalName,
                    Toast.LENGTH_SHORT).show();
        }

        finish();
    }


    public void setAlarm()
    {
        Intent intent = new Intent(this, SetAlarmActivity.class);
        intent.putExtra("name", originalName);
        musicMediaPlayer.stop();
        startActivityForResult(intent, 3);
    }

    public void checkAlarmSet()
    {

        TextView lblAlarm = (TextView) findViewById(R.id.lblAlarm);
        String alarm = topicHelper.readFromFile(this);
        String[] alarmList = alarm.split(";");
        String alarmSet = "";
        String[] digit;

        for(int i=0; i<alarmList.length; i++)
        {
            if (alarmList[i].contains(originalName))
            {
                Log.i("AlarmList", alarmList[i]);
                alarmSet = alarmList[i].split(",")[1];
                Log.i("AlarmList Split", alarmSet);
            }
        }

        if (alarmSet != null && !alarmSet.isEmpty() )
        {
            String alarmSetTime = alarmSet.split(" ")[3];
            digit = alarmSetTime.split(":");
            String[] fullTimeConvert = alarmSet.split(" ");

            int hr = Integer.parseInt(digit[0]);

            String ampm = "";

            if (hr >= 12)
                ampm = "PM";
            else
                ampm = "AM";

            if (hr != 12 && hr != 0)
                hr = hr%12;
            if (hr == 12) {
                hr = 12;
                ampm = "PM";
            }
            if (hr == 0) {
                hr = 12;
                ampm = "AM";
            }


            lblAlarm.setText("Alarm set for " + fullTimeConvert[0] + ", "
                    + fullTimeConvert[1] + " "
                    + fullTimeConvert[2] + ", "
                    + hr + ":" + digit[1] + " " + ampm + " "
                    + fullTimeConvert[5]);
        }
        else
            lblAlarm.setText("No alarm set");

        //the above is a matter of converting from 24hr to 12hr

    }

    @Override
    public void onActivityResult(int requestCode,
                                 int responseCode,
                                 Intent result) {
        if (requestCode == 3) {
            //originalName = result.getData().toString();
            //musicMediaPlayer.stop();
            updateViewHere(false);
        }

        if (requestCode == 2) {
            originalName = result.getData().toString();
            musicMediaPlayer.stop();
            updateViewHere(false);
        }
    }

    @Override //overridden to ensure musicplayer stops on exit
    public void onBackPressed() {
        musicMediaPlayer.stop();
        finish();
    }


}
