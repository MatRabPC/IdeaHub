package com.uoit.rara.finalexam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.IdeaHelper;
import com.uoit.rara.finalexam.model.TopicHelper;

import java.util.List;

//TODO make search an asynctask, maybe?
//simple form activity. See SearchResultsActivity
public class SearchIdeaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_idea);

        Spinner spinTopic = (Spinner) findViewById(R.id.spinTopic);
        ArrayAdapter topicAdapter = ArrayAdapter.createFromResource(
                this, R.array.media, android.R.layout.simple_spinner_item);
       // topicAdapter.insert("", 0);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTopic.setAdapter(topicAdapter);


        Spinner spinSound = (Spinner) findViewById(R.id.spinSound);
        ArrayAdapter soundAdapter = ArrayAdapter.createFromResource(
                this, R.array.soundfx, android.R.layout.simple_spinner_item);
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSound.setAdapter(soundAdapter);

    }

    public void searchDatabase(View view)
    {
        EditText txtName = (EditText)findViewById(R.id.txtName);
        String name = txtName.getText().toString();

        EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
        String desc = txtDesc.getText().toString();

        Spinner spinTopic = (Spinner) findViewById(R.id.spinTopic);
        String topic = spinTopic.getSelectedItem().toString();

        Spinner spinSound = (Spinner) findViewById(R.id.spinSound);
        String sound = spinSound.getSelectedItem().toString();


       // if( (name != null && !name.isEmpty()) && (desc != null && !desc.isEmpty())
        //        && (sound != null && !sound.isEmpty()) && (topic != null && !topic.isEmpty())) {

            Log.i("Recieved", name+ sound+ desc+ topic);


           // finish();
       // }

       // ideaHelper.searchByTopic(topic);

        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("desc", desc);
        intent.putExtra("topic", topic);
        intent.putExtra("sound", sound);
        startActivity(intent);


        //else
        //{
          //  Toast.makeText(this, "All fields must be filled",
            //        Toast.LENGTH_LONG).show();
        //}



    }


}
