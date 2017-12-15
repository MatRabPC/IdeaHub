package com.uoit.rara.finalexam;
/*
Uses content from Android Dev Docs
specifically for onCreateOptionsMenu & onOptionItemsSelected
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.IdeaHelper;
import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.TopicHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static int IDEA_UPDATE_REQUEST = 1;

    private IdeaArrayAdapter adapter;
    private ListView ideaList;
    private IdeaHelper ideaHelper;
    private TopicHelper topicHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ideaList = (ListView) findViewById(R.id.lstIdeas);
        ideaHelper = new IdeaHelper(this);
        topicHelper = new TopicHelper();
        ideaList.setOnItemClickListener(this);

        Log.i("infileNow", topicHelper.readFromFile(this));
        updateIdeaList(); //wanna update the screen, first thing
    }

    @Override //setup for actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override //actionbar actions
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addIdea();
                return true;

            case R.id.action_search:
                searchIdea();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //get all ideas, sorted by name, and stuff into adapter->listview
    private void updateIdeaList() {
        List<Idea> ideas = ideaHelper.getAllIdeas();
        adapter = new IdeaArrayAdapter(this, ideas);
        ideaList.setAdapter(adapter);
    }


    @Override //get the specific clicked item
    public void onItemClick(AdapterView aView, View source, int position, long id) {
        Idea idea = (Idea) adapter.getItem(position);
        Log.i("SQLite", "selected contact: " + idea);

        showShowIdea(idea.getName());
    }

    public void showShowIdea(String name) {

        Intent intent = new Intent(this, ShowIdeaActivity.class);
        intent.putExtra("name", name);
        startActivityForResult(intent, 1);

    }

    public void addIdea()
    {
        Intent intent = new Intent(this, AddIdeaActivity.class);
        startActivityForResult(intent, 1);
    }

    public void searchIdea()
    {
        Intent intent = new Intent(this, SearchIdeaActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override //always want to update the view in case of change
    public void onActivityResult(int requestCode,
                                 int responseCode,
                                 Intent result) {
        if (requestCode == IDEA_UPDATE_REQUEST) {
            updateIdeaList();
        }
    }


}
