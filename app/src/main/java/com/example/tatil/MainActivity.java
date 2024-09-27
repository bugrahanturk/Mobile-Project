package com.example.tatil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tatil.Fragments.HomeFragment;
import com.example.tatil.Fragments.LocationsFragment;
import com.example.tatil.Fragments.NotificationFragment;
import com.example.tatil.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        selectorFragment = new HomeFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {// MAİN ACTİVİTY navigasyon selected item yorumy
                switch (item.getItemId()){                                       //get item selected yeri
                    case R.id.nav_home:
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.nav_location:
                        selectorFragment = new LocationsFragment();
                        break;

                    case R.id.nav_add:
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this,PostActivity.class));
                        break;
                    case R.id.nav_favorite:
                        selectorFragment = new NotificationFragment();
                        break;
                    case R.id.nav_profile:
                        selectorFragment = new ProfileFragment();
                        break;
                }

                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectorFragment).commit();
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();


    }


}