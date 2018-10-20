package com.example.sharansingh.solarifyenergy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Challenges extends AppCompatActivity implements LocationListener{
    ArrayList<String> challenge;
    int REQUEST_CODE_LOCATION=101;
    LocationManager locationManager;
    String locality="";

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CustomAdapter customAdapter;
    TextView addressTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        getPermission();

        challenge = new ArrayList<>();

  /*      challenge.add("Switch off the lights when not in use");
        challenge.add("Shower for less than 5 minutes");
        challenge.add("Turn off Computers,Printers and Office Lights when you leave");
        challenge.add("Change your normal bulbs to LED ones");
        challenge.add("Choose walking over vehicles whenever possible");*/


        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        addressTextView=(TextView)findViewById(R.id.textView);

        recyclerView.setLayoutManager(linearLayoutManager);

        customAdapter = new CustomAdapter(this,challenge);
        recyclerView.setAdapter(customAdapter);
        Toast.makeText(this,"Please Wait challenges are being loaded",Toast.LENGTH_SHORT).show();
        getLocation();

    }

    public void getPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_LOCATION && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
        }
        else
        {
            getPermission();
        }

    }

    public void getLocation()
    {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
     //   locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        Log.i("LOCATIONVALUE"," latitudeValue "+location.getLatitude()+ "Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

           //Log.i("locvalues "," "+addresses.get(0).getSubLocality()+"   "+addresses.get(0).getLocality()+"  "+addresses.get(0).getPostalCode());
            String address="empty";
           if( addresses.get(0).getAddressLine(0)!=null)
            address=addresses.get(0).getAddressLine(0);

           if(address!="empty")
           addressTextView.setText("Location - "+address);
            String pincode= addresses.get(0).getPostalCode();
            int pin=Integer.parseInt(pincode);


            getChallenges(pin);

        }catch(Exception e)
        {
            Log.i("errorgeocoder",e.toString());
        }

    }

    private void getChallenges(int pincode) {

      if(pincode<560050){
          Log.i("areaselected","1");
          challenge.clear();
          challenge.add("Switch off the lights when not in use");
          challenge.add("Shower for less than 5 minutes");
          customAdapter.notifyDataSetChanged();
      }
      else if(pincode<560100){
          Log.i("areaselected","2");
          challenge.clear();
          challenge.add("Turn off Computers,Printers and Office Lights when you leave");
          challenge.add("Change your normal bulbs to LED ones");
          customAdapter.notifyDataSetChanged();
      }
      else if(pincode<560150)
          Log.i("areaselected","3");
      else
          Log.i("areaselected","4");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}


