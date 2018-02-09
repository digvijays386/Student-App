package com.example.dell.studentapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.studentapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static  final int REQUEST_LOCATION_CODE=99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final String URL = getIntent().getExtras().getString("url");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            checkLocationPermission();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Getting locations...");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                MapsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Code", response);
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                JSONArray PostOffice = null;
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    PostOffice = jsonObject.getJSONArray("PostOffice");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                PostOffice[] postOffices = gson.fromJson(String.valueOf(PostOffice), PostOffice[].class);
                                 int rows=postOffices.length;
                                 getLocations(postOffices,rows);

                                 Toast.makeText(MapsActivity.this,"get the location",Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MapsActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
                        queue.add(request);
                    }
                });
            }
        });
        t.start();
        progressDialog.show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (client == null)
                            buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                    }
                }else
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    public void getLocations(PostOffice[] postOffices, int rows){
        String[]  locations = new String[rows];
       for (int j=0;j<rows;j++) {
           locations[j] = postOffices[j].getName() +" "+ postOffices[j].getDistrict();
           List<Address> addressList = null;
           MarkerOptions mo = new MarkerOptions();

           if (!locations[j].equals("")) {
               Geocoder geocoder = new Geocoder(this);
               try {
                   addressList = geocoder.getFromLocationName(locations[j], 4);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               for (int i = 0; i < addressList.size(); i++) {
                   Address myAddtess = addressList.get(i);
                   LatLng latLng = new LatLng(myAddtess.getLatitude(), myAddtess.getLongitude());
                   mo.position(latLng);
                   mo.title(locations[j]);
                   mMap.addMarker(mo);
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
               }
           }
       }
    }
    protected synchronized void buildGoogleApiClient(){
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

       /* lastLocation = location;
        if (currentLocationMarker != null)
            currentLocationMarker.remove();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("current location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(2));*/
        if (client != null)
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);

       /* mMap.clear();
        Marker[] allMarkers = new Marker[4];

        for (int i = 0; i < 4; i++)
        {
            LatLng latLng = new LatLng(locations[i][0], locations[i][1]);
            if (mMap != null) {
                //mMap.setOnMarkerClickListener(this);
                allMarkers[i] = mMap.addMarker(new MarkerOptions().position(latLng));
                // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            }
        }*/

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            else
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            return false;
        }
        else
            return false;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
