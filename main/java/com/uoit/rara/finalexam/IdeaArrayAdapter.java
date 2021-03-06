package com.uoit.rara.finalexam;
//adapted from demo provided in class
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.TopicHelper;

import java.util.List;

public class IdeaArrayAdapter extends ArrayAdapter<Idea> {

        private TopicHelper topicHelper = new TopicHelper();
        private List<Idea> data;
        private Context context;
    String[] media = {"Travel", "Eductaion", "Finance", "Music", "Technology"};
    int[] drawableIds = {R.drawable.ic_airplanemode_active_black_24dp, R.drawable.ic_sort_by_alpha_black_24dp, R.drawable.ic_attach_money_black_24dp, R.drawable.ic_library_music_black_24dp, R.drawable.ic_subtitles_black_24dp};


    public IdeaArrayAdapter(Context context, List<Idea> data) {
            super(context, R.layout.idea_list_item, data);
            this.context = context;
            this.data = data;
    }


        @Override
        public View getView(int position, View reusableView, ViewGroup parent) {
            Idea idea = data.get(position);

            if (reusableView == null) {
                // create a new view (this is the first item)
                LayoutInflater inflater = LayoutInflater.from(context);
                reusableView = inflater.inflate(R.layout.idea_list_item, parent, false);
            }

            TextView lblName = (TextView)reusableView.findViewById(R.id.lblName);
            lblName.setText(idea.getName());

            ImageView imgIco = (ImageView)reusableView.findViewById(R.id.imgIco);
            imgIco.setImageResource(drawableIds[topicHelper.indexOf(media, idea.getTopic())]);

            return reusableView;
        }



}
