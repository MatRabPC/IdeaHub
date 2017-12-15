package com.uoit.rara.finalexam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uoit.rara.finalexam.model.Idea;
import com.uoit.rara.finalexam.model.IdeaHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddIdeaActivity extends AppCompatActivity implements LocationListener {

    private IdeaHelper ideaHelper;
    private LocationManager locationManager;
    private double latitude, longitude;
    String locationName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idea);


        requestLocPerm();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        requestLocationUpdates();

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

    }

    public void onProviderEnabled(String provider) {
        Log.i("MapsDemo", "Provider enabled: " + provider);
    }

    public void onProviderDisabled(String provider) {
        Log.i("MapsDemo", "Provider disabled: " + provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("MapsDemo", "Provider ("+provider+") status changed: " + status);
    }

    public void requestLocPerm()
    {
        if (ActivityCompat.checkSelfPermission(AddIdeaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddIdeaActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AddIdeaActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;

        }else{
            // Write you code here if permission already given.
        }
    }

    public String geocode(double latitude, double longitude) {
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> results = geocoder.getFromLocation(latitude, longitude, 1);

                if (results.size() > 0) {
                    return results.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] perms,
                                           int[] results) {
        if (requestCode == 1) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                // geolocation permission granted, so request location updates
                verifyGeolocationEnabled();
            }
            else
                finish();
        }
    }

    public void verifyGeolocationEnabled() {
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        // check if geolocation is enabled in settings
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // TODO:  Request location updates
            requestLocationUpdates();
        } else {
            // show the settings app to let the user enable it
            String locationSettings = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            Intent enableGeoloc = new Intent(locationSettings);
            startActivity(enableGeoloc);

            // Note:  startActivityForResult() may be better here
        }
    }

    public void requestLocationUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        String recommendedProvider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(AddIdeaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(recommendedProvider,1000,0,this);
            Log.i("MapsDemo", "requestLocationUpdates()");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("MapsDemo", "Location changed: " + location);

        // remember the coordinates for the map activity's backup location
        latitude = location.getLatitude();
        longitude = location.getLongitude();


        // geocode the result - get the location name
         locationName = geocode(location.getLatitude(), location.getLongitude());

    }

    public void addThisIdea(View view)
    {
        EditText txtName = (EditText)findViewById(R.id.txtName);
        String name = txtName.getText().toString();
        //take as string now, convert later, for sake of easy transfer

        EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
        String desc = txtDesc.getText().toString();

        Spinner spinTopic = (Spinner) findViewById(R.id.spinTopic);
        String topic = spinTopic.getSelectedItem().toString();

        Spinner spinSound = (Spinner) findViewById(R.id.spinSound);
        String sound = spinSound.getSelectedItem().toString();

        Location location = new Location(LocationManager.GPS_PROVIDER);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

//        Toast.makeText(this, ""+latitude,
  //              Toast.LENGTH_LONG).show();


        // geocode the result - get the location name
        locationName = geocode(latitude, longitude);

        Log.i("thisloc", ""+latitude);

        if (ideaHelper.getIdea(name) != null)
        {
            Toast.makeText(this, "Name already used!",
                    Toast.LENGTH_SHORT).show();
        }

        else if( (name != null && !name.isEmpty()) && (desc != null && !desc.isEmpty())
                && (topic != null && !topic.isEmpty()) && (sound != null && !sound.isEmpty()) ) {

            if (latitude == 0.0 && longitude == 0.0) {
                locationName = "Unidentified";
            }
            ideaHelper.createIdea(name, desc, topic, sound, locationName);

         //   Toast.makeText(this, locationName,
           //         Toast.LENGTH_SHORT).show();

            finish();
        }
        else
        {
            Toast.makeText(this, "All fields must be filled",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
