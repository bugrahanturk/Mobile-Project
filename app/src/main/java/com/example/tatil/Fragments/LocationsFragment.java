package com.example.tatil.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.tatil.MapActivity;
import com.example.tatil.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocationsFragment extends Fragment implements OnMapReadyCallback{
    SearchView searchView;
    GoogleMap mapLocation ;
    SupportMapFragment supportMapFragment  ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locations, container, false);
        searchView = view.findViewById(R.id.sv_location);

        String data = getContext().getSharedPreferences("LOCATION", Context.MODE_PRIVATE).getString("location","none");

        supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.googleMap);
           String location_name = data;
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                System.out.println(location+"*****************");
                System.out.println(location_name+"--------------");

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList =  geocoder.getFromLocationName(location,1);
                    }
                    catch (IOException exception){
                        exception.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    System.out.println(addressList.get(0));
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    System.out.println(latLng);
                   // mapLocation.addMarker(new MarkerOptions().position(latLng).title(location));
                    mapLocation.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,9));
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        supportMapFragment.getMapAsync(this);
      /* SupportMapFragment supportMapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        System.out.println("++++++++++++++++++++++++MAPA GİRDİ");
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {


                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        System.out.println("++++++++++++++++++++++++MAPA TIKLAMA");
                        /// tıklanıldığında initialize et
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng,10
                        ));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });*/

        return view;

    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapLocation = googleMap;
    }
}