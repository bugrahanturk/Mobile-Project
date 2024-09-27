package com.example.tatil.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tatil.Adapter.CityAdapter;
import com.example.tatil.Adapter.PhotoAdapter;
import com.example.tatil.Adapter.PostAdapter;
import com.example.tatil.Classes.City;
import com.example.tatil.Classes.Post;
import com.example.tatil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerViewCity;
    private CityAdapter cityAdapter;
    private List<City> cityList ;
    private TextView tv_city_mentors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerViewCity = view.findViewById(R.id.recycler_view_city);
        tv_city_mentors = view.findViewById(R.id.tv_city_mentors);

        recyclerViewCity.setHasFixedSize(true);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cityList = new ArrayList<>();
        cityAdapter = new CityAdapter(cityList,getContext());
        recyclerViewCity.setAdapter(cityAdapter);


        FirebaseDatabase.getInstance().getReference().child("City").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cityList.clear();
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                    City city = datasnapshot.getValue(City.class);
                    cityList.add(city);
                }
                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}