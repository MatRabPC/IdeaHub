package com.uoit.rara.finalexam;
//very repetitive of AddIdeaActivity, small vital changes
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.IdeaHelper;
import com.uoit.rara.finalexam.model.TopicHelper;

public class EditIdeaActivity extends AppCompatActivity {

    private IdeaHelper ideaHelper;
    private String originalName = "";
    private TopicHelper topicHelper;
    String[] media = {"Video Games", "Television", "Music", "Knitting"};
    String[] soundlist = {"Riff", "Jazz", "Piano", "Dragon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);

        topicHelper = new TopicHelper();

        //set up spinner content
        Spinner spinTopic = (Spinner) findViewById(R.id.spinTopic);
        ArrayAdapter topicAdapter = ArrayAdapter.createFromResource(
                this, R.array.media, android.R.layout.simple_spinner_item);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTopic.setAdapter(topicAdapter);

        Spinner spinSound = (Spinner) findViewById(R.id.spinSound);
        ArrayAdapter soundAdapter = ArrayAdapter.createFromResource(
                this, R.array.soundfx, android.R.layout.simple_spinner_item);
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSound.setAdapter(soundAdapter);

        ideaHelper = new IdeaHelper(this);
        originalName = getIntent().getStringExtra("name");

        EditText txtName = (EditText)findViewById(R.id.txtName);
        EditText txtDesc = (EditText)findViewById(R.id.txtDesc);

        Idea idea = ideaHelper.getIdea(originalName);

        //set the fields to previous stuff -> recognition over recall heuristic
        txtName.setText(idea.getName());
        txtDesc.setText(idea.getDesc());
        spinTopic.setSelection(topicHelper.indexOf(media,idea.getTopic()));
        spinSound.setSelection(topicHelper.indexOf(soundlist,idea.getSound()));

    }



    public void editIdea(View view)
    {
        EditText txtName = (EditText)findViewById(R.id.txtName);
        String name = txtName.getText().toString();

        EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
        String desc = txtDesc.getText().toString();

        Spinner spinTopic = (Spinner) findViewById(R.id.spinTopic);
        String topic = spinTopic.getSelectedItem().toString();

        Spinner spinSound = (Spinner) findViewById(R.id.spinSound);
        String sound = spinSound.getSelectedItem().toString();

        Idea originalIdea = ideaHelper.getIdea(originalName);

        if( (name != null && !name.isEmpty()) && (desc != null && !desc.isEmpty())
                && (topic != null && !topic.isEmpty()) && (sound != null && !sound.isEmpty()) ) {

            Log.i("Desc", desc);

            Idea idea = new Idea(name, desc, topic, sound, originalIdea.getLoc()); //location thought shouldnt change

            ideaHelper.updateIdea(idea, originalName);

            Intent data = new Intent();
            data.setData(Uri.parse(name));
            setResult(RESULT_OK, data);

            finish();
        }

        else
        {
            Toast.makeText(this, "All fields must be filled",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.setData(Uri.parse(originalName));
        setResult(RESULT_OK, data);
        finish();
    }

}
