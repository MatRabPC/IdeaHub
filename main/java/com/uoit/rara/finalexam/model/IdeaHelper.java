package com.uoit.rara.finalexam.model;

/*
Adapted from demos provided in class
SQLiteDemo
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

    public class IdeaHelper extends SQLiteOpenHelper {
        static final int DATABASE_VERSION = 1;

        static final String TABLE = "ideas";

        static final String CREATE_STATEMENT = "CREATE TABLE ideas (\n" +
                "      name varchar2(25) primary key ,\n" +
                "      desc varchar2(250) not null,\n" +
                "      topic varchar2(25) not null,\n" +
                "      sound varchar2(25) not null,\n" +
                "      loc varchar2(250) not null\n" +
                ")\n";


        static final String DROP_STATEMENT = "DROP TABLE ideas";

        public IdeaHelper(Context context) {
            super(context, "ideas", null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create the database, using CREATE SQL statement
            db.execSQL(CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersionNum,
                              int newVersionNum) {
            // delete the old database
            db.execSQL(DROP_STATEMENT);

            // re-create the database
            db.execSQL(CREATE_STATEMENT);
        }

        // CRUD functions

        // CREATE
        public Idea createIdea(String name,
                               String desc,
                               String topic,
                               String sound,
                               String loc) {
            // create a new entity object (Grades)
            Idea idea = new Idea(name, desc, topic, sound, loc);

            // put that data into the database
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues newValues = new ContentValues();
            newValues.put("name", name);
            newValues.put("desc", desc);
            newValues.put("topic", topic);
            newValues.put("sound", sound);
            newValues.put("loc", loc);

            long id = db.insert(TABLE, null, newValues);

            // update the idea's id
            //idea.setId(id);

            Log.i("LocationSpot", loc);

            return idea;
        }

        // READ
        public Idea getIdea(String name) {
            Idea idea = null;

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = new String[] {"name", "desc", "topic", "sound", "loc"};
            String where = "name = ?";
            String[] whereArgs = new String[] { "" + name };
            Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "");

            if (cursor.getCount() >= 1) {
                cursor.moveToFirst();

                String desc = cursor.getString(1);
                String topic = cursor.getString(2);
                String sound = cursor.getString(3);
                String loc = cursor.getString(4);



                idea = new Idea(name, desc, topic, sound, loc);

            }

//            Log.i("SQLite", "getIdea(): " + idea.getLoc());

            return idea;
        }

        public List<Idea> searchByTopic(String topic) {
            List<Idea> ideas = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = new String[] {"name", "desc", "topic", "sound", "loc"};
            String where = "topic = ?";
            String[] whereArgs = new String[] { "" + topic };
            Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "name");

            cursor.moveToFirst();
            do {
                if (!cursor.isAfterLast()) {
                    // long id = cursor.getLong(0);
                    String name = cursor.getString(0);
                    String desc = cursor.getString(1);
                    //String topic = cursor.getString(2);
                    String sound = cursor.getString(3);
                    String loc = cursor.getString(4);

                    Idea idea = new Idea(name, desc, topic, sound, loc);
                    //    idea.setId(id);

                    ideas.add(idea);
                }

                cursor.moveToNext();
            } while (!cursor.isAfterLast());

            Log.i("SQLite", "getAllIdeas(): num = " + ideas.size());

            return ideas;
        }

        public List<Idea> searchByName(String name) {
            List<Idea> ideas = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = new String[] {"name", "desc", "topic", "sound", "loc"};
            String where = "name = ?";
            String[] whereArgs = new String[] { "" + name };
            Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "name");

            cursor.moveToFirst();
            do {
                if (!cursor.isAfterLast()) {
                    // long id = cursor.getLong(0);
                    //String name = cursor.getString(0);
                    String desc = cursor.getString(1);
                    String topic = cursor.getString(2);
                    String sound = cursor.getString(3);
                    String loc = cursor.getString(4);

                    Idea idea = new Idea(name, desc, topic, sound, loc);
                    //    idea.setId(id);

                    ideas.add(idea);
                }

                cursor.moveToNext();
            } while (!cursor.isAfterLast());

            Log.i("SQLite", "getAllIdeas(): num = " + ideas.size());

            return ideas;
        }

        public List<Idea> searchBySound(String sound) {
            List<Idea> ideas = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = new String[] {"name", "desc", "topic", "sound", "loc"};
            String where = "sound = ?";
            String[] whereArgs = new String[] { "" + sound };
            Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "name");

            cursor.moveToFirst();
            do {
                if (!cursor.isAfterLast()) {
                    // long id = cursor.getLong(0);
                    String name = cursor.getString(0);
                    String desc = cursor.getString(1);
                    String topic = cursor.getString(2);
                    //String sound = cursor.getString(3);
                    String loc = cursor.getString(4);

                    Idea idea = new Idea(name, desc, topic, sound, loc);
                    //    idea.setId(id);

                    ideas.add(idea);
                }

                cursor.moveToNext();
            } while (!cursor.isAfterLast());

            Log.i("SQLite", "getAllIdeas(): num = " + ideas.size());

            return ideas;
        }

        public List<Idea> getAllIdeas() {
            List<Idea> ideas = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = new String[] {"name", "desc", "topic", "sound", "loc"};
            String where = "";
            String[] whereArgs = new String[] {  };
            Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "name");

            cursor.moveToFirst();
            do {
                if (!cursor.isAfterLast()) {
                   // long id = cursor.getLong(0);
                    String name = cursor.getString(0);
                    String desc = cursor.getString(1);
                    String topic = cursor.getString(2);
                    String sound = cursor.getString(3);
                    String loc = cursor.getString(4);

                    Idea idea = new Idea(name, desc, topic, sound, loc);
                    //    idea.setId(id);

                    ideas.add(idea);
                }

                cursor.moveToNext();
            } while (!cursor.isAfterLast());

            Log.i("SQLite", "getAllIdeas(): num = " + ideas.size());

            return ideas;
        }

        // UPDATE
        public boolean updateIdea(Idea idea, String originalName) {
            Log.i("Desc in Helper pre", "ANYTHING");
            SQLiteDatabase db = this.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("name", idea.getName());
            values.put("desc", idea.getDesc());
            values.put("topic", idea.getTopic());
            values.put("sound", idea.getSound());
            values.put("loc", idea.getLoc());
            Log.i("Desc in Helper", idea.getDesc());

            int numRows = db.update(TABLE, values, "name = ?", new String[] { "" + originalName });

            return (numRows == 1);
        }

        // DELETE
        public boolean deleteIdea(String name) {
            SQLiteDatabase db = this.getWritableDatabase();

            int numRows = db.delete(TABLE, "name = ?", new String[] { "" + name });

            return (numRows == 1);
        }

        public void deleteAllIdeas() {
            SQLiteDatabase db = this.getWritableDatabase();

            db.delete(TABLE, "", new String[] { });
        }
    }


