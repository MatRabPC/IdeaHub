package com.uoit.rara.finalexam.model;
/*
read/write adapted from https://stackoverflow.com/a/14377185
indexOf adapted from https://www.w3resource.com/java-exercises/array/java-array-exercise-6.php
 */
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TopicHelper
{
    //taken off internet, edited therein
    public int indexOf (String[] haystack, String needle) {
        if (haystack == null) return -1;
        int len = haystack.length;
        int i = 0;
        while (i < len) {
            if (haystack[i].equals(needle)) return i;
            else i=i+1;
        }
        return -1;
    }

    public void writeToFile(String data, Context context, int mode) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("alarmList.txt", Context.MODE_PRIVATE));

            if (mode == 1)
                outputStreamWriter.append(data);

            else if (mode == 2)
                outputStreamWriter.write(data);

            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("alarmList.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("read", "File not found: " + e.toString());

            return "NULL";
        } catch (IOException e) {
            Log.e("read", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void clearThisAlarm(Context context, String name)
    {
        String all = readFromFile(context);
        String[] alarmList = all.split(";");
        String alarmSet = "";
        int skipIndex = 0;

        for(int i=0; i<alarmList.length; i++)
        {
            if (! alarmList[i].contains(name) && (alarmList[i] != null && !alarmList[i].isEmpty()) )
            {
                Log.i("look", "found");
                //alarmSet = alarmList[i];
                alarmSet += (alarmList[i]+";");
                //skipIndex = i;
            }
        }

        writeToFile(alarmSet, context, 2);
    }
}