package com.example.tatil;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatil.Classes.Post;
import com.example.tatil.Classes.User;
import com.example.tatil.Fragments.HomeFragment;
import com.example.tatil.Fragments.LocationsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mapLocation;
    private SupportMapFragment supportMapFragment;
    private List<Address> LocationList;
    private Button btn_map_destination;
    private LatLng latLng;
    private String data;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle intent = getIntent().getExtras();
        btn_map_destination = findViewById(R.id.btn_map_directions);

        String location = intent.getString("location");
         data = getApplicationContext().getSharedPreferences("LOCATION", Context.MODE_PRIVATE).getString("location", "none");

        client = LocationServices.getFusedLocationProviderClient(this);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        getApplicationContext().getSharedPreferences("LOCATION", getApplicationContext().MODE_PRIVATE).edit().clear().apply();


        Geocoder geocoder = new Geocoder(MapActivity.this);
        try {
            LocationList = geocoder.getFromLocationName(location, 1);

            latLng = new LatLng(LocationList.get(0).getLatitude(), LocationList.get(0).getLongitude());
         } catch (IOException e) {
            e.printStackTrace();
        }


        btn_map_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTrack(data, location);
                if (ActivityCompat.checkSelfPermission(MapActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                }
            }
        });
        supportMapFragment.getMapAsync(this);

    }

    void displayTrack(String source, String destination) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source + "/" + destination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mapLocation = googleMap;
      //  mapLocation.addMarker(new MarkerOptions().position(latLng).title(data));
        mapLocation.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

    }


}