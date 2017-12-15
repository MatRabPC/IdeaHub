package com.uoit.rara.finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.IdeaHelper;
import com.uoit.rara.finalexam.model.TopicHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
//location reminder
    private IdeaArrayAdapter adapter;
    private ListView ideaList;
    private IdeaHelper ideaHelper;
    String thisTopic = "";
    String thisName = "";
    String thisDesc = "";
    String thisSound = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ideaList = (ListView) findViewById(R.id.lstIdeas);
        ideaHelper = new IdeaHelper(this);

        ideaList.setOnItemClickListener(this);

        thisName = getIntent().getStringExtra("name");
        thisDesc = getIntent().getStringExtra("desc");
        thisTopic = getIntent().getStringExtra("topic");
        thisSound = getIntent().getStringExtra("sound");

        updateIdeaList();

    }

    @Override
    public void onItemClick(AdapterView aView, View source, int position, long id) {
        Idea idea = (Idea) adapter.getItem(position);
        showShowIdea(idea.getName()); //ShowIdeaActivity
    }

    private void updateIdeaList() {
        List<Idea> ideaName = new ArrayList<>();
        List<Idea> ideaTopic = new ArrayList<>();
        List<Idea> ideaSound = new ArrayList<>();

        if( (thisName != null && !thisName.isEmpty()))
        {
            ideaName = ideaHelper.searchByName(thisName);
        }

        if( (thisTopic != null && !thisTopic.isEmpty()))
        {
            ideaTopic = ideaHelper.searchByTopic(thisTopic);
        }

        if( (thisSound != null && !thisSound.isEmpty()))
        {
            ideaSound = ideaHelper.searchBySound(thisSound);
        }

        List<Idea> ideas = new ArrayList<>();

        for(int i =0; i< ideaName.size(); i++)
        {
            ideas.add(ideaName.get(i));
        }

        for(int i =0; i< ideaTopic.size(); i++)
        {
            ideas.add(ideaTopic.get(i));
        }

        for(int i =0; i< ideaSound.size(); i++)
        {
            ideas.add(ideaSound.get(i));
        }

        //in short, find and put all that apply in an array...

        List<String> searchResults = new ArrayList<>();

        for(int i = 0; i< ideas.size(); i++)
        {
            if (! searchResults.contains( ideas.get(i).getName()))
            {
                searchResults.add(ideas.get(i).getName());
            }
        }
        Log.i("Prev Search Res", Arrays.toString(searchResults.toArray()));
        ideas.clear();
        //...and remove duplicates
        for(int i = 0; i< searchResults.size(); i++) {

            ideas.add(ideaHelper.getIdea(searchResults.get(i)));

        } //here, do actual searching of database

        Log.i("Search Res", Arrays.toString(ideas.toArray()));

        adapter = new IdeaArrayAdapter(this, ideas);
        ideaList.setAdapter(adapter); //show results
    }

    public void showShowIdea(String name) {

        Intent intent = new Intent(this, ShowIdeaActivity.class);
        intent.putExtra("name", name);
        startActivityForResult(intent, 1);

    }

}
